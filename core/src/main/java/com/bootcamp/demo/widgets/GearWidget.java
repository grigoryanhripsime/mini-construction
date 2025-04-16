package com.bootcamp.demo.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import lombok.Getter;
import lombok.Setter;

public class GearWidget extends BorderedTable {
    @Getter @Setter
    private String name;
    @Getter @Setter
    Texture widgetTexture;
    @Getter @Setter
    private int lvl;

    public GearWidget() {
        setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
        setData();
    }

    public void setData () {
        final Image icon = new Image(Resources.getDrawable("ui/ui-zombie-emoji"));
        icon.setSize(170, 150);
        icon.setPosition(30, 30);
        addActor(icon);
    }
}
