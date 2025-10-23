package org.practice.servlets.socket;

public class ViewerInfo {
    private String username;
    private String color;

    public ViewerInfo() {
    }

    public ViewerInfo(String username, String color) {
        this.username = username;
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public String getColor() {
        return color;
    }
}
