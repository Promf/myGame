package com.mygdx.game;

public class GameLevel {
    private String name;
    private boolean available;
    private int goal;
    private int cost;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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

    public GameLevel(String name, boolean x, int goal, int cost) {
        this.name = name;
        this.available=x;
        this.goal=goal;
        this.cost=cost;

    }
}
