package com.mygdx.game;

import com.mygdx.game.cars.Player;

public interface Database {
    Player select(long id);
    GameLevel select(String name);
    long insert(int id, int coins, String path);
    long insert(String name, int cost, int goal, int bool);
    int update(Player player);
    int update(String path);
}