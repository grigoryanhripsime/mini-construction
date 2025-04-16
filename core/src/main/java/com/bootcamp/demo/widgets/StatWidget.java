package com.bootcamp.demo.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.localization.GameFont;
import lombok.Getter;
import lombok.Setter;

public class StatWidget extends Table {
    @Getter @Setter
    private String statName;
    @Getter @Setter
    private float statPercentage;

    public StatWidget() {
        this.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
    }

    public void setData () {
//        Label name = Labels.make(GameFont.valueOf("HP: "));
//        add(name);
    }
}
