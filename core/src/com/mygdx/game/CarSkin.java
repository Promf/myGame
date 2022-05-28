package com.mygdx.game;

public class CarSkin {
    private final String name;
    private final String description;
    private boolean bought;
    private final int price;

    public String getDescription() {
        return description;
    }

    private final String texture;

    public String getName() {
        return name;
    }

    public CarSkin(String name, boolean bought, int price, String texture, String description) {
        this.name=name;
        this.bought = bought;
        this.price = price;
        this.texture=texture;
        this.description = description;
    }

    public boolean isBought() {
        return bought;
    }

    public int getPrice() {
        return price;
    }

    public String getTexture() {
        return texture;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
