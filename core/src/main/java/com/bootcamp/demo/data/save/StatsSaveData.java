package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
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
}
