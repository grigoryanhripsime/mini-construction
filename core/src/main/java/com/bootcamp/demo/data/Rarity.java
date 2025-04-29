package com.bootcamp.demo.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.localization.GameFont;
import lombok.Getter;

public enum Rarity {
    RUSTED(1, Color.BROWN, "Rusted"),
    RUSTED_2(2, Color.BROWN, "Rusted"),
    SCRAP(1, Color.BLUE, "Scrap"),
    SCRAP_2(2, Color.BLUE, "Scrap"),
    HARDENED(1, Color.PURPLE, "Hardened"),
    HARDENED_2(2, Color.PURPLE, "Hardened"),
    ELITE(1, Color.YELLOW, "Elite"),
    ELITE_2(2, Color.YELLOW, "Elite"),
    ASCENDANT(1, Color.RED, "Ascendant"),
    ASCENDANT_2(2, Color.RED, "Ascendant"),
    NUCLEAR(1, Color.GREEN, "Nuclear"),
    JUGGERNAUT(1, Color.PURPLE, "Juggernaut"),
    DOMINION(1, Color.PINK, "Dominion"),
    OBLIVION(1, Color.GREEN, "Oblivion"),
    IMMORTAL(1, Color.BLUE, "Immortal"),
    ETHEREAL(1, Color.WHITE, "Ethereal");

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
