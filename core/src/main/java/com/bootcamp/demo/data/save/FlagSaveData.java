package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

public class FlagSaveData implements Json.Serializable{
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private StatsSaveData statsSaveData = new StatsSaveData();
    //maybe later Ill add rarity too

    @Override
    public void write(Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("s", statsSaveData);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        level = jsonValue.getInt("l");
        statsSaveData = json.readValue(StatsSaveData.class, jsonValue.get("s"));
    }
}
