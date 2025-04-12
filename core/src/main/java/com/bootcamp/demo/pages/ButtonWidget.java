package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.bootcamp.demo.engine.Squircle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ButtonWidget  extends Table{
    ButtonWidget(String color) {
        this.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(color)));
    }

}
