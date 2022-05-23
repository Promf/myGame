package com.mygdx.game.serialize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.Serializer;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.mygdx.game.cars.Player;

import java.io.Serializable;
import java.util.Arrays;

public class PlayerSerialize extends Serializer<Player> {
    @Override
    public void write(Kryo kryo, Output output, Player object) {
        output.writeInt((int)object.coin, false);
        output.writeInt(object.getLevelResults().length, false);
        output.writeInts(object.getLevelResults(), 0, object.getLevelResults().length, false);
        output.writeInt(object.getLevel(), false);
        String str = object.getTexturePath();
        output.writeString(str);
    }

    @Override
    public Player read(Kryo kryo, Input input, Class<? extends Player> aClass) {
        long i = input.readInt(false);
        long b = input.readInt(false);
        int[] arr = input.readInts((int) b, false);
        int level = input.readInt(false);
        String str = input.readString();


        Player player = new Player((int) (Gdx.graphics.getWidth()*0.45), (int) (Gdx.graphics.getHeight()/3.5), 399, 499, Gdx.graphics.getWidth(), str, (int) i);
        player.setLevelResults(arr);
        player.setLevel(level);
        return player;
    }


}
