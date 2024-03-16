package com.newsoft.super_note.data.model;

public class MenuItem {
    private int id;
    private int icon;
    private int color;
    private String title;

    public MenuItem(int id, int icon , int color, String title) {
        this.id = id;
        this.icon = icon;
        this.color = color;
        this.title = title;
    }

    public MenuItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}
