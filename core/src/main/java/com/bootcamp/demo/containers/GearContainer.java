package com.bootcamp.demo.containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.widgets.GearWidget;

public class GearContainer extends WidgetsContainer<GearWidget> {
    Array<GearWidget> equipments = new Array<>(6);

    public GearContainer () {
        super(3);
        defaults().size(225).space(20);
        setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
        center().pad(20);
        for (int i = 0; i < 6; i++) {
            GearWidget equip = new GearWidget();
            add(equip);
            equipments.add(equip);
        }
    }
}
