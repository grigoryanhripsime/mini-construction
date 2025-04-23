package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class StatSaveData implements Json.Serializable {

    @Getter @Setter
    private EnumMap<Stat, Float> stat = new EnumMap<>(Stat.class);


    @Override
    public void write (Json json) {
        json.writeObjectStart("stats");
        for (Map.Entry<Stat, Float> statEntry : stat.entrySet()) {
            json.writeValue(statEntry.getKey().name(), statEntry.getValue());
        }
        json.writeObjectEnd();
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        stat.clear();
        JsonValue statsJson = jsonValue.get("s");
        if (statsJson != null) {
            for (JsonValue entry = statsJson.child; entry != null; entry = entry.next) {
                Stat key = Stat.valueOf(entry.name.toUpperCase(Locale.ENGLISH));
                float value = entry.asFloat();
                stat.put(key, value);
            }
        }
    }

    @Getter
    public enum Stat {
        HP,
        ATK,
        DODGE,
        COMBO,
        CRIT,
        STUN,
        REGEN,
        STEAL,
        POISON
    }

}
