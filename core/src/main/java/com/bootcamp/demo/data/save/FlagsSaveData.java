package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;
import lombok.Setter;

public class FlagsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, FlagSaveData> flags = new ObjectMap<>();
    @Getter @Setter
    private String equipped;

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<String, FlagSaveData> entry : flags.entries()) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        flags.clear();

        for (JsonValue value : jsonValue) {
            final String slot = value.name;
            final FlagSaveData flagSaveData = json.readValue(FlagSaveData.class, value);
            flags.put(slot, flagSaveData);
        }
    }
}
