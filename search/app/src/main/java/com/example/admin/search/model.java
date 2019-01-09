package com.example.admin.search;

public class model {

    String name,topic;
    int icon;

    public model(String name, String topic, int icon) {
        this.name = name;
        this.topic = topic;
        this.icon = icon;
    }

    public String getName() {
        return this.name;
    }

    public String getTopic() {
        return this.topic;
    }

    public int getIcon() {
        return this.icon;
    }
}
