package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearGameData;
import lombok.Getter;

import java.util.Locale;

public class GearsSaveData implements Json.Serializable {
    @Getter
    private ObjectMap<GearGameData.Type, GearSaveData> equippedGears = new ObjectMap<>();

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<GearGameData.Type, GearSaveData> entry : equippedGears) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        equippedGears.clear();

        for (JsonValue value : jsonValue) {
            final GearGameData.Type type = GearGameData.Type.valueOf(value.name.toUpperCase(Locale.ENGLISH));
            final GearSaveData gearSaveData  = json.readValue(GearSaveData.class, value);
            equippedGears.put(type, gearSaveData);
        }
    }
}
