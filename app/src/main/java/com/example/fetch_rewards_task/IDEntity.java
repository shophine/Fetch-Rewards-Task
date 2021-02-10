package com.example.fetch_rewards_task;

public class IDEntity {
    String id;
    String name;

    public IDEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public IDEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
