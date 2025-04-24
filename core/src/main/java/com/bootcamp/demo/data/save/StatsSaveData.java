package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class StatsSaveData implements Json.Serializable {
    @Getter
    @Setter
    private EnumMap<Stat, Float> stat = new EnumMap<>(Stat.class);

    @Override
    public void write(Json json) {
        for (Map.Entry<Stat, Float> entry : stat.entrySet()) {
            json.writeValue(entry.getKey().name(), entry.getValue());
        }
    }
    @Override
    public void read(Json json, JsonValue jsonValue) {
        stat.clear();
        for (JsonValue entry = jsonValue.child; entry != null; entry = entry.next) {
            try {
                Stat key = Stat.valueOf(entry.name().toUpperCase(Locale.ROOT));
                float value = entry.asFloat();
                stat.put(key, value);
            } catch (IllegalArgumentException e) {
                System.err.println("Unknown stat: " + entry.name());
            }
        }
    }
}













//    @Getter
//    private final IntMap<StatSaveData> stats = new IntMap<>();
//
//    @Override
//    public void write(Json json) {
//        for (IntMap.Entry<StatSaveData> entry : stats.entries()) {
//            json.writeValue(String.valueOf(entry.key), entry.value);
//        }
//    }
//
//    @Override
//    public void read(Json json, JsonValue jsonValue) {
//        stats.clear();
//
//        for (JsonValue value : jsonValue) {
//            final Integer slotIndex = Integer.valueOf(value.name);
//            final StatSaveData statSaveData = json.readValue(StatSaveData.class, value);
//            stats.put(slotIndex, statSaveData);
//        }
//    }

