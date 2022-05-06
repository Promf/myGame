package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class CarSkin {
    private boolean bought;
    private int price;
    private String texture;

    public CarSkin(boolean bought, int price, String texture) {
        this.bought = bought;
        this.price = price;
        this.texture=texture;
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
}
