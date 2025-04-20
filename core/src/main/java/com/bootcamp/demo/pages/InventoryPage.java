package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.pages.core.Containers;
import com.bootcamp.demo.pages.core.Widgets;
import lombok.Getter;

public class InventoryPage extends APage {
    @Getter
    private SaveData saveData;

    @Getter
    private GameData gameData;

    @Override
    protected void constructContent (Table content) {

        saveData = API.get(SaveData.class);
        gameData = API.get(GameData.class);

        final Image powerIcon = new Image(Resources.getDrawable("ui/fist"));
        final Label label = Labels.make(GameFont.BOLD_32);
        label.setText("83.5K");
        label.setColor(Color.WHITE);
        label.setPosition(40, 50);

        final Table power = new Table();
        power.addActor(label);

        final BorderedTable powerSegment = new BorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#99826f")), Squircle.SQUIRCLE_35_BORDER_TOP.getDrawable(Color.WHITE));
        powerSegment.add(powerIcon);
        powerSegment.add(power).size(200, 100);


        final Table inventory = constructInventory();

        content.add(powerSegment).size(600, 150).expandY().bottom();
        content.row();
        content.add(inventory).growX().bottom();

//        debugAll();
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
        final Containers.StatContainer statContainer = new Containers.StatContainer();
        statContainer.setData(saveData.getStatsSaveData().getStats());

        final Image statMenuButton = new Image(Resources.getDrawable("ui/stat-menu-button"));

        final BorderedTable menuButton = new BorderedTable();
        menuButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5e0cb")));
        menuButton.add(statMenuButton);

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


        final Containers.GearContainer mainGearContainer = new Containers.GearContainer();


        Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
        segment.add(incompleteAndMilGearSkinSetsWrapper);
        segment.row();
        segment.add(mainGearContainer);

        return segment;
    }


    private Table constructSecondaryGearSegment () {
        final Image flag = new Image(Resources.getDrawable("ui/spinner-icon"));
        final BorderedTable flagWidget = new BorderedTable();
        flagWidget.add(flag).grow();

        final Containers.TacticalsContainer tacticalsContainer = new Containers.TacticalsContainer();

        final Table tacticalsAndFlagWrapper = new Table();
        tacticalsAndFlagWrapper.add(tacticalsContainer);
        tacticalsAndFlagWrapper.row();
        tacticalsAndFlagWrapper.add(flagWidget).size(225).space(20);


        final Image bankRobbery = new Image(Resources.getDrawable("ui/bank-robbery"));
        bankRobbery.setSize(150, 160);
        final BorderedTable homeButton = new BorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#0a0a0a")), Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#ccb578")));
        homeButton.add(bankRobbery);

        final BorderedTable petWidgetContainer = new BorderedTable();
        petWidgetContainer.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2b8b0")));
        petWidgetContainer.add(homeButton).size(223, 160).growY().bottom();

        final Table segment = new Table();
        segment.add(tacticalsAndFlagWrapper).space(30);
        segment.add(petWidgetContainer).size(225, 480);

        return segment;
    }

    private  Table constructButtonSegment () {
        final Widgets.ButtonWidget shovelUpgradeButton = new Widgets.ButtonWidget("#ddb46d");
        final Widgets.ButtonWidget lootButton = new Widgets.ButtonWidget("#9bd781");
        final Widgets.ButtonWidget autoLootButton = new Widgets.ButtonWidget("#bababa");

        final Table segment = new Table();
        segment.defaults().size(420, 180).space(50);
        segment.add(shovelUpgradeButton);
        segment.add(lootButton);
        segment.add(autoLootButton);

        return segment;
    }
}
