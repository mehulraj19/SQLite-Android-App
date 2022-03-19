package com.example.sqliteapp.model;

public class User {
    private String username;
    private String occupation;

    public User(String username, String occupation) {
        this.username = username;
        this.occupation = occupation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
