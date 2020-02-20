package com.example.androidlabs;

public class Message {
    private String message;
    private int sendOrReceive;
    private long id;

    public Message(long id, String message, int sendOrReceive) {
        this.message = message;
        this.sendOrReceive = sendOrReceive;
        this. id = id;
    }

    public String getMessage() {
        return message;
    }

    public int getSendOrReceive() {
        return sendOrReceive;
    }

    public long getId() {
        return id;
    }

}