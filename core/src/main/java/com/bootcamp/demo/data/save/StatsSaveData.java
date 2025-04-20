package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.StatGameData;
import lombok.Getter;

public class StatsSaveData implements Json.Serializable {
    @Getter
    private final IntMap<StatSaveData> stats = new IntMap<>();

    @Override
    public void write(Json json) {
        for (IntMap.Entry<StatSaveData> entry : stats.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        stats.clear();

        for (JsonValue value : jsonValue) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final StatSaveData statSaveData = json.readValue(StatSaveData.class, value);
            stats.put(slotIndex, statSaveData);
        }
    }

    private float getValueTmp(String name) {
        float valueTmp;
        switch (name) {
            case "HP":
                valueTmp = 17.3f;
                break;
            case "COMBO":
                valueTmp = 0f;
                break;
            case "REGEN":
                valueTmp = 11.31f;
                break;
            case "ATK":
                valueTmp = 6.67f;
                break;
            case "CRIT":
                valueTmp = 7.6f;
                break;
            case "STEAL":
                valueTmp = 6.19f;
                break;
            case "DODGE":
                valueTmp = 17.48f;
                break;
            case "STUN":
                valueTmp = 2.06f;
                break;
            case "POISON":
                valueTmp = 0f;
                break;
            default:
                valueTmp = 0f;
        }
        return valueTmp;
    }

    public void setSaveData(ObjectMap<String, StatGameData> stats) {

        int i = 0;
        for (ObjectMap.Entry<String, StatGameData> stat : stats.entries()) {
            final StatSaveData statSaveData = new StatSaveData();
            statSaveData.setName(stat.value.getName());
            statSaveData.setValue(getValueTmp(stat.value.getName()));
            getStats().put(i, statSaveData);
            i++;
        }
    }
}
