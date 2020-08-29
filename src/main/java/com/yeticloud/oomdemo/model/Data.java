package com.yeticloud.oomdemo.model;

import java.util.UUID;

public class Data {
    private final int[] bigData;
    private final String id;
    private final String name;

    public Data(String name) {
        this.name = name;
        this.bigData = new int[500_000_000];
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
