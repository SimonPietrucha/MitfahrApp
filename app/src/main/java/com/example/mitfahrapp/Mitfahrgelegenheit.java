package com.example.mitfahrapp;

public class Mitfahrgelegenheit {
    private int id;
    private String username;
    private String start;
    private String ziel;

    public Mitfahrgelegenheit(int id, String username, String start, String ziel) {
        this.id = id;
        this.username = username;
        this.start = start;
        this.ziel = ziel;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getStart() {
        return start;
    }

    public String getZiel() {
        return ziel;
    }
}
