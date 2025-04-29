package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;
import lombok.Setter;

public class PetsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, PetSaveData> pets = new ObjectMap<>();
    @Getter @Setter
    private String equipped;

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<String, PetSaveData> entry : pets.entries()) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        pets.clear();

        for (JsonValue value : jsonValue) {
            final String slot = value.name;
            final PetSaveData petSaveData = json.readValue(PetSaveData.class, value);
            pets.put(slot, petSaveData);
        }
    }
}
