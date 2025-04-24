package com.bootcamp.demo.data.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

import java.util.Locale;

public class GearGameData implements IGameData {
    @Getter
    private String name;
    @Getter
    private Type type;
    @Getter
    private Drawable icon;

    @Override
    public void load (XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        type = Type.valueOf(rootXml.getAttribute("type").toUpperCase(Locale.ENGLISH));
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
    }

    public enum Type {
        WEAPON,
        MELEE,
        HEAD,
        BODY,
        GLOVES,
        SHOES;
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
        }
    }
}
