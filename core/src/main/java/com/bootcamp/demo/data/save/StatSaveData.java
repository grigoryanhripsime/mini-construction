package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

public class StatSaveData implements Json.Serializable {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Float value;


    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("v", value);

    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        value = jsonValue.getFloat("v");
    }
}
