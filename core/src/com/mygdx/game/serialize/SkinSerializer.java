package com.mygdx.game.serialize;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.Serializer;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.mygdx.game.CarSkin;


public class SkinSerializer extends Serializer<CarSkin> {
    @Override
    public void write(Kryo kryo, Output output, CarSkin skin) {

        output.writeBoolean(skin.isBought());
        output.writeInt(skin.getPrice(), false);
        output.writeString(skin.getTexture());

    }

    @Override
    public CarSkin read(Kryo kryo, Input input, Class<? extends CarSkin> aClass) {
        return new CarSkin(input.readBoolean(), input.readInt(false), input.readString());
    }
}
