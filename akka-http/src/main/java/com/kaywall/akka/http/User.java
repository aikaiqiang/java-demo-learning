package com.kaywall.akka.http;

public class User {

    private final Long id;

    private final String name;

    public User() {
        this.name = "";
        this.id = null;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
