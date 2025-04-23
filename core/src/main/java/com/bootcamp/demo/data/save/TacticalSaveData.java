package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

public class TacticalSaveData implements Json.Serializable {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private int stars;
    @Getter @Setter
    private boolean equipped;


    @Override
    public void write(Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("s", stars);
        json.writeValue("e", equipped);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        level = jsonValue.getInt("l");
        stars = jsonValue.getInt("s");
        equipped = jsonValue.getBoolean("e");
    }
}
