package com.mygdx.game;

import com.mygdx.game.cars.Player;

public interface Database {
    Player select(long id);
    GameLevel select_level(long id);
    CarSkin select_skin(int id);
    long insert(int id, int coins, String path);
    long insert(int id, String name, int cost, int goal, int bool, int c, String d);
    long insert_skin(int id, String name, int cost, String path, int bool, String description);

        int update(Player player);
    int update_skins(CarSkin skin);
    int update(String path);
    int update(GameLevel gameLevel, int record);
    int update(GameLevel level);
}