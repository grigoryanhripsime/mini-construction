package com.bootcamp.demo.pages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Squircle;
import lombok.Getter;
import lombok.Setter;

public class StatWidget extends Table {
    @Getter @Setter
    private String statName;
    @Getter @Setter
    private float statPercentage;

    StatWidget() {
        this.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
//        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
//        Label label = new Label("StatName", skin);
//        this.add(label).padLeft(20);
    }
}
