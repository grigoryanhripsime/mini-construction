package com.bootcamp.demo.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;

public class ButtonWidget  extends BorderedTable{
    public ButtonWidget(String color) {
        final Image shovel = new Image(Resources.getDrawable("ui/shovel-icon"));
        shovel.setSize(160, 160);
        shovel.setPosition(30, 10);

        setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(color)));
        addActor(shovel);
    }

}
