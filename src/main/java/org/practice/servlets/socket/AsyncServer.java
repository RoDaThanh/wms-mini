package org.practice.servlets.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@ServerEndpoint(value = "/asyncServer")
public class AsyncServer {
    private final List<Session> peers = Collections.synchronizedList(new ArrayList<>());
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Map<String, Map<String, String>> viewingTracker = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        String guestId = "Guest-" + UUID.randomUUID().toString().substring(0, 5);
        session.getUserProperties().put("username", guestId);
        peers.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        peers.remove(session);
    }

    @OnError
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());
    }

    public void broadcast(String message) {
        peers.stream().filter(Session::isOpen).forEachOrdered(p -> p.getAsyncRemote().sendText(message));
    }

    @OnMessage
    public void OnMessage(String jsonMessage, Session peer) {
        try {
            Message messageObject = OBJECT_MAPPER.readValue(jsonMessage, Message.class);
            if (messageObject.getData() instanceof Map) {
                Map<?, ?> dataMap = (Map<?, ?>) messageObject.getData();
                String itemId = (String) dataMap.get("itemId");

                if (itemId != null) {
                    switch (messageObject.getType()) {
                        case START_VIEWING:
                            viewingTracker.computeIfAbsent(itemId, k ->
                                    Collections.synchronizedMap(new HashMap<>()))
                                    .put(peer.getId(), (String) peer.getUserProperties().get("username"));
                            break;
                        case STOP_VIEWING:
                            Set<String> viewers = viewingTracker.get(itemId).keySet();
                            viewers.remove(peer.getId());
                            if (viewers.isEmpty()) {
                                viewingTracker.remove(itemId);
                            }
                            break;
                        default:
                            return;
                    }
                    broadcastViewingStatus(itemId);
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing OnMessage: " + e.getMessage());
        }
    }

    private void broadcastViewingStatus(String itemId) throws JsonProcessingException {
        Map<String, String> viewerMap = viewingTracker.getOrDefault(itemId, Collections.emptyMap());
        List<String> viewers = new ArrayList<>(viewerMap.values());

        Map<String, Object> dataPayload = new HashMap<>();
        dataPayload.put("itemId", itemId);
        dataPayload.put("viewers", viewers);
        Message message = new Message(Message.MessageType.VIEWING_UPDATE, dataPayload);
        String jsonMessage = OBJECT_MAPPER.writeValueAsString(message);

        peers.stream().filter(p -> viewerMap.containsKey(p.getId())).forEach(
                p -> p.getAsyncRemote().sendText(jsonMessage)
        );
    }
}

