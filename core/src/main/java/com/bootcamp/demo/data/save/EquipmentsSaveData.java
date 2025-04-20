package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.EquipmentGameData;
import com.bootcamp.demo.data.game.StatGameData;
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

    public void setGear(ObjectMap<String, EquipmentGameData> equips) {
        int i = 0;
        for (ObjectMap.Entry<String, EquipmentGameData> equip : equips.entries()) {
            final EquipmentSaveData equipmentSaveData = new EquipmentSaveData();
            equipmentSaveData.setName(equip.value.getName());
            equipmentSaveData.setLevel(i + 1);
            equipmentSaveData.setStars(1);
            getEquips().put(i, equipmentSaveData);
            i++;
        }
    }
}
