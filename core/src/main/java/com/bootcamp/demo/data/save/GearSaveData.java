package com.bootcamp.demo.data.save;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.GearGameData;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class GearSaveData implements Json.Serializable {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private GearGameData.Type type; //es toxel em stex bayc kaskacum em vor piti mna stex
    @Getter @Setter
    private int level;
    @Getter @Setter
    private boolean equipped;
    @Getter @Setter
    private Rarity rarity;
    @Getter @Setter
    private StatSaveData statSaveData;

    @Override
    public void write(Json json) {
        json.writeValue("n", name);
        json.writeValue("t", type.name());
        json.writeValue("l", level);
        json.writeValue("e", equipped);
        json.writeValue("r", rarity.name());
        json.writeValue("s", statSaveData);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        type = GearGameData.Type.valueOf(jsonValue.getString("t").toUpperCase(Locale.ENGLISH));
        level = jsonValue.getInt("l");
        equipped = jsonValue.getBoolean("e");
        rarity = Rarity.valueOf(jsonValue.getString("r"));
        statSaveData = json.readValue(StatSaveData.class, jsonValue.get("s"));
    }

    public enum Rarity {
        RUSTED(1, Color.BROWN),
        RUSTED_2(2, Color.BROWN),
        SCRAP(1, Color.BLUE),
        SCRAP_2(2, Color.BLUE),
        HARDENED(1, Color.PURPLE),
        HARDENED_2(2, Color.PURPLE),
        ELITE(1, Color.YELLOW),
        ELITE_2(2, Color.YELLOW),
        ASCENDANT(1, Color.RED),
        ASCENDANT_2(2, Color.RED),
        NUCLEAR(1, Color.GREEN),
        JUGGERNAUT(1, Color.PURPLE),
        DOMINION(1, Color.PINK),
        OBLIVION(1, Color.GREEN),
        IMMORTAL(1, Color.BLUE),
        ETHERAL(1, Color.WHITE);

        @Getter
        private int starCount;
        @Getter
        private Color backgroundColor;

        //TODO: maybe add power for each rarity, as well as stats for each rarity

        Rarity (int starCount, Color backgroundColor) {
            this.starCount = starCount;
            this.backgroundColor = backgroundColor;
        };
    }
}
