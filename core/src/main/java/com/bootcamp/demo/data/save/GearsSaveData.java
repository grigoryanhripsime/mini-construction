package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearGameData;
import lombok.Getter;

public class GearsSaveData implements Json.Serializable {

    @Getter
    private IntMap<GearSaveData> gears = new IntMap<>();
    @Getter
    private ObjectMap<GearGameData.Type, GearSaveData> equippedGears = new ObjectMap<>();

    @Override
    public void write(Json json) {
        for (IntMap.Entry<GearSaveData> entry : gears.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        gears.clear();

        for (JsonValue value : jsonValue) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final GearSaveData gearSaveData  = json.readValue(GearSaveData.class, value);
            gears.put(slotIndex, gearSaveData);
            if (gearSaveData.isEquipped())
                equippedGears.put(gearSaveData.getType(), gearSaveData);
        }
    }
//
//    public void setGear(ObjectMap<String, GearGameData> equips) {
//        int i = 0;
//        for (ObjectMap.Entry<String, GearGameData> equip : equips.entries()) {
//            final GearSaveData equipmentSaveData = new GearSaveData();
//            equipmentSaveData.setName(equip.value.getName());
//            equipmentSaveData.setLevel(i + 1);
//            equipmentSaveData.setStars(1);
//            getEquips().put(i, equipmentSaveData);
//            i++;
//        }
//    }
}
