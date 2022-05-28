package com.mygdx.game;

public class GameLevel {
    private final int id;
    private final String name;
    private boolean available;
    private final int goal;
    private final int cost;
    private final int record;
    private final int carsCount;
    private final String fon;
    public int getCost() {
        return cost;
    }

    public int getCarsCount() {
        return carsCount;
    }

    public int getId() {
        return id;
    }

    public int getRecord() {
        return record;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getGoal() {
        return goal;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public GameLevel(String name, boolean x, int goal, int cost, int record, int id, int count, String fon) {
        this.name = name;
        this.available=x;
        this.goal=goal;
        this.cost=cost;
        this.record=record;
        this.id = id;
        this.carsCount=count;
        this.fon=fon;

    }
}
