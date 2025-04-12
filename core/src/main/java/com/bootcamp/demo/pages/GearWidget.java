package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Squircle;
import lombok.Getter;
import lombok.Setter;

public class GearWidget extends Table {
    @Getter @Setter
    private String name;
    @Getter @Setter
    Texture widgetTexture;
    @Getter @Setter
    private int lvl;

    GearWidget() {
        this.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
    }
}
