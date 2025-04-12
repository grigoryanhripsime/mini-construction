package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.pages.core.APage;

public class TestPage extends APage {

    private Table constructStatSegment() {

        WidgetsContainer<StatWidget> statContainer = new WidgetsContainer<>(350, 50, 3, 10);
        for (int i = 0; i < 9; i++) {
            statContainer.add(new StatWidget());
        }

        Table menuButton = new Table();
        menuButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5e0cb")));

        final Table statSegment = new Table();
        statSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#ae9e91")));
        statSegment.add(statContainer).space(30);
        statSegment.add(menuButton).size(120, 120).padLeft(30);
        return statSegment;
    }

    private Table constructEquippedGearSegment() {
       WidgetsContainer<GearWidget> mainGearSegment = new WidgetsContainer<>(225, 225, 3, 20);
       mainGearSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
       mainGearSegment.center().pad(30).padTop(100);
       for (int i = 0; i < 6; i++)
           mainGearSegment.add(new GearWidget());

        // TODO: change the name
        Table leftSegment = new Table();
        leftSegment.add(new GearWidget()).size(225).space(20);
        leftSegment.row();
        leftSegment.add(new GearWidget()).size(225).space(20);


        Table petWidgetContainer = new Table();
        petWidgetContainer.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2b8b0")));

        Table secondaryGearSegment = new Table();
        secondaryGearSegment.add(leftSegment).space(30);
        secondaryGearSegment.add(petWidgetContainer).size(225, 480);


        final Table segment = new Table();
        segment.add(mainGearSegment).size(800, 600).padRight(50);
        segment.add(secondaryGearSegment).size(500, 500).padTop(30);
        return segment;
    }

    private  Table constructButtonSegment() {

        final Table segment = new Table();
        segment.add(new ButtonWidget("#ddb46d")).size(425, 180).space(30);
        segment.add(new ButtonWidget("#9bd781")).size(425, 180).space(30);
        segment.add(new ButtonWidget("#bababa")).size(425, 180);

        return segment;
    }

    private Table constructInventory() {
        final Table statSegment = constructStatSegment();
        final Table equippedGearSegment = constructEquippedGearSegment();
        final Table buttonSegment = constructButtonSegment();


        final Table inventory = new Table();
        inventory.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#fae9d7")));

        inventory.add(statSegment).size(1350, 220).padTop(50);
        inventory.row();
        inventory.add(equippedGearSegment).space(40);;
        inventory.row();
        inventory.add(buttonSegment).space(40);

        return inventory;
    }

    @Override
    protected void constructContent (Table content) {
        Table powerSegment = new Table();
        powerSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#99826f")));

        Table barSegment = new Table();
        barSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#453e3a")));

        content.add(powerSegment).size(600, 150).expandY().bottom();
        content.row();
        content.add(constructInventory()).size(1800, 1200).fillX().bottom();
        content.row();
        content.add(barSegment).size(1800, 200);

//        content.debugAll();
    }
}
