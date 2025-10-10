package org.practice.servlets.socket;

import javax.ejb.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
@ServerEndpoint(value = "/asyncServer")
public class AsyncServer {
    private final List<Session> peers = Collections.synchronizedList(new ArrayList<>());

    @OnOpen
    public void onOpen(Session session) {
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
}

