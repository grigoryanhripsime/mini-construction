package com.bootcamp.demo.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.localization.GameFont;
import lombok.Getter;

public enum Rarity {
    RUSTED(1, Color.valueOf("#B29986"), "Rusted"),
    RUSTED_2(2, Color.valueOf("#B29986"), "Rusted"),
    SCRAP(1, Color.valueOf("#5F99C4"), "Scrap"),
    SCRAP_2(2, Color.valueOf("#5F99C4"), "Scrap"),
    HARDENED(1, Color.valueOf("#B892C4"), "Hardened"),
    HARDENED_2(2, Color.valueOf("#B892C4"), "Hardened"),
    ELITE(1, Color.valueOf("#E1AE50"), "Elite"),
    ELITE_2(2, Color.valueOf("#E1AE50"), "Elite"),
    ASCENDANT(1, Color.valueOf("#DA5857"), "Ascendant"),
    ASCENDANT_2(2, Color.valueOf("#DA5857"), "Ascendant"),
    NUCLEAR(1, Color.valueOf("#A8D53C"), "Nuclear"),
    JUGGERNAUT(1, Color.valueOf("#7A6DEC"), "Juggernaut"),
    DOMINION(1, Color.valueOf("#FC647C"), "Dominion"),
    OBLIVION(1, Color.valueOf("#4FFFBA"), "Oblivion"),
    IMMORTAL(1, Color.valueOf("#68FEDF"), "Immortal"),
    ETHEREAL(1, Color.valueOf("#ded978"), "Ethereal");

    @Getter
    private final int starCount;
    @Getter
    private final Color backgroundColor;
    @Getter
    private final String title;

    //TODO: maybe add power for each rarity, as well as stats for each rarity

    Rarity (int starCount, Color backgroundColor, String title) {
        this.starCount = starCount;
        this.backgroundColor = backgroundColor;
        this.title = title;
    }
}
