package org.practice.servlets.socket;

public class Message {
    private MessageType type;
    private Object data;

    public Message() {
    }

    public Message(MessageType messageType, Object data) {
        this.type = messageType;
        this.data = data;
    }

    public Message(MessageType messageType) {
        this.type = messageType;
        this.data = null;
    }

    public MessageType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public enum MessageType {
        ITEM_ADDED,
        VIEWING_UPDATE,
        START_VIEWING,
        STOP_VIEWING,
    }
}
