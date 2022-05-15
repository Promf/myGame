package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class CarSkin {
    private boolean bought;
    private final int price;
    private final String texture;

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

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
