package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;

public class EquipmentsSaveData implements Json.Serializable {

    @Getter
    private final IntMap<EquipmentSaveData> equips = new IntMap<>();

    @Override
    public void write(Json json) {
        for (IntMap.Entry<EquipmentSaveData> entry : equips.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        equips.clear();

        for (JsonValue value : jsonValue) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final EquipmentSaveData equipmentSaveData  = json.readValue(EquipmentSaveData.class, value);
            equips.put(slotIndex, equipmentSaveData);
        }
    }
}
