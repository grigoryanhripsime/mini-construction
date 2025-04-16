package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.containers.GearContainer;
import com.bootcamp.demo.containers.StatContainer;
import com.bootcamp.demo.containers.TacticalsContainer;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.widgets.ButtonWidget;


public class TestPage extends APage {

    @Override
    protected void constructContent (Table content) {

        final Image powerIcon = new Image(Resources.getDrawable("ui/ui-thunder-emoji"));
        powerIcon.setSize(100, 120);
        powerIcon.setPosition(170, 10);

        final BorderedTable powerSegment = new BorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#99826f")), Squircle.SQUIRCLE_35_BORDER_TOP.getDrawable(Color.WHITE));
        powerSegment.addActor(powerIcon);


        final Table inventory = constructInventory();

        content.add(powerSegment).size(600, 150).expandY().bottom();
        content.row();
        content.add(inventory).growX().bottom();
    }

    private Table constructInventory () {
        final Table statSegment = constructStatSegment();
        final Table equippedGearSegment = constructEquippedGearSegment();
        final Table buttonSegment = constructButtonSegment();


        final Table inventory = new Table();
        inventory.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#fae9d7")));

        inventory.add(statSegment).growX().pad(40);
        inventory.row();
        inventory.add(equippedGearSegment);
        inventory.row();
        inventory.add(buttonSegment).space(40).pad(30);

        return inventory;
    }

    private Table constructStatSegment () {
        final StatContainer statContainer = new StatContainer();

        final BorderedTable menuButton = new BorderedTable();
        menuButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5e0cb")));

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#ae9e91")));
        segment.add(statContainer).space(30).pad(30);
        segment.add(menuButton).size(120, 120).pad(30);

        return segment;
    }

    private Table constructEquippedGearSegment () {

        final Table mainGearContainer = constructMainGearSegment();
        final Table secondaryGearSegment = constructSecondaryGearSegment();


        final Table segment = new Table();
        segment.add(mainGearContainer).space(70);
        segment.add(secondaryGearSegment).padTop(30);

        return segment;
    }

    private Table constructMainGearSegment () {
       final Table incompleteSet = new Table();
       incompleteSet.setBackground(Squircle.SQUIRCLE_20.getDrawable(Color.valueOf("#ae9e91")));

        final BorderedTable milGearSkinSets = new BorderedTable();
        milGearSkinSets.setBackground(Squircle.SQUIRCLE_20.getDrawable(Color.valueOf("#f5e0cb")));

        final Table incompleteAndMilGearSkinSetsWrapper = new Table();
        incompleteAndMilGearSkinSetsWrapper.add(incompleteSet).size(650, 50);
        incompleteAndMilGearSkinSetsWrapper.add(milGearSkinSets).size(100).left();


        final GearContainer mainGearContainer = new GearContainer();


        Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
        segment.add(incompleteAndMilGearSkinSetsWrapper);
        segment.row();
        segment.add(mainGearContainer);

        return segment;
    }


    private Table constructSecondaryGearSegment () {
        final Image flag = new Image(Resources.getDrawable("ui/spinner-icon"));
        flag.setFillParent(true);
        final BorderedTable flagWidget = new BorderedTable();
        flagWidget.addActor(flag);

        final TacticalsContainer tacticalsContainer = new TacticalsContainer();

        final Table tacticalsAndFlagWrapper = new Table();
        tacticalsAndFlagWrapper.add(tacticalsContainer);
        tacticalsAndFlagWrapper.row();
        tacticalsAndFlagWrapper.add(flagWidget).size(225).space(20);


        final Image bankRobbery = new Image(Resources.getDrawable("ui/bank-robbery"));
        bankRobbery.setPosition(40, 0);
        bankRobbery.setSize(150, 160);
        final BorderedTable homeButton = new BorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#0a0a0a")), Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#ccb578")));
        homeButton.addActor(bankRobbery);

        final BorderedTable petWidgetContainer = new BorderedTable();
        petWidgetContainer.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2b8b0")));
        petWidgetContainer.add(homeButton).size(223, 160).growY().bottom();

        final Table segment = new Table();
        segment.add(tacticalsAndFlagWrapper).space(30);
        segment.add(petWidgetContainer).size(225, 480);

        return segment;
    }

    private  Table constructButtonSegment () {
        final ButtonWidget shovelUpgradeButton = new ButtonWidget("#ddb46d");
        final ButtonWidget lootButton = new ButtonWidget("#9bd781");
        final ButtonWidget autoLootButton = new ButtonWidget("#bababa");

        final Table segment = new Table();
        segment.defaults().size(420, 180).space(50);
        segment.add(shovelUpgradeButton);
        segment.add(lootButton);
        segment.add(autoLootButton);

        return segment;
    }
}
