package com.bootcamp.demo.containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;

public class TacticalsContainer extends WidgetsContainer<Table> {
    Array<Table> tacticals = new Array<>(4);
    public TacticalsContainer () {
        super(2);
        setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
        defaults().size(90).pad(10);
        for (int i = 0; i < 4; i++) {
            Table tactical = new Table();
            tactical.setBackground(Squircle.SQUIRCLE_30.getDrawable(Color.valueOf("#ae9e91")));
            add(tactical);
            tacticals.add(tactical);
        }
    }
}
