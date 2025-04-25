package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

public class InventoryPage extends APage {

    private Containers.StatContainer statContainer;
    private Containers.GearContainer mainGearContainer;
    private Containers.TacticalsContainer tacticalsContainer;
    private Widgets.FlagWidget flagWidget;
    private Widgets.PetWidget petWidget;
    private Widgets.LootUpgradeButton lootUpgradeButton;
    private Widgets.LootButton lootButton;
    private Widgets.AutoLootButton autoLootButton;

    @Override
    protected void constructContent (Table content) {

        final Table powerSegment = constructPowerSegment();
        final Table inventory = constructInventory();

        content.add(powerSegment).size(600, 150).expandY().bottom();
        content.row();
        content.add(inventory).growX().bottom();
    }

    private Table constructPowerSegment () {
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

        return powerSegment;
    }

    private Table constructInventory () {
        final Table statSegment = constructStatSegment();
        final Table equippedGearSegment = constructEquippedGearSegment();
        final Table buttonSegment = constructButtonsSegment();


        final Table inventoryWrapper = new Table();
        inventoryWrapper.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#fae9d7")));

        inventoryWrapper.add(statSegment).growX().pad(40);
        inventoryWrapper.row();
        inventoryWrapper.add(equippedGearSegment);
        inventoryWrapper.row();
        inventoryWrapper.add(buttonSegment).space(40).pad(30);

        return inventoryWrapper;
    }

    private Table constructStatSegment () {
        statContainer = new Containers.StatContainer();

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
        final Table incompleteSetSegment = constructIncompleteSetSegment();
        mainGearContainer = new Containers.GearContainer();

        Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
        segment.add(incompleteSetSegment);
        segment.row();
        segment.add(mainGearContainer);

        return segment;
    }

    private Table constructIncompleteSetSegment () {
        final Label label = Labels.make(GameFont.BOLD_20);
        label.setText("Incomplete Set");
        label.setColor(Color.valueOf("#423d37"));

        final Table incompleteSet = new Table();
        incompleteSet.setBackground(Squircle.SQUIRCLE_20.getDrawable(Color.valueOf("#ae9e91")));
        incompleteSet.add(label);

        final Image button = new Image(Resources.getDrawable("ui/stat-menu-button"));

        final BorderedTable milGearSkinSets = new BorderedTable();
        milGearSkinSets.setBackground(Squircle.SQUIRCLE_20.getDrawable(Color.valueOf("#f5e0cb")));
        milGearSkinSets.add(button);

        final Table segment = new Table();
        segment.add(incompleteSet).size(600, 50);
        segment.add(milGearSkinSets).size(100).left();

        return segment;
    }

    private Table constructSecondaryGearSegment () {
        tacticalsContainer = new Containers.TacticalsContainer();
        flagWidget = new Widgets.FlagWidget();
        petWidget = new Widgets.PetWidget();

        final Table tacticalsAndFlagWrapper = new Table();
        tacticalsAndFlagWrapper.defaults().space(50);
        tacticalsAndFlagWrapper.add(tacticalsContainer);
        tacticalsAndFlagWrapper.row();
        tacticalsAndFlagWrapper.add(flagWidget).size(225, 225);

        final Table segment = new Table();
        segment.defaults().pad(30);
        segment.add(tacticalsAndFlagWrapper);
        segment.add(petWidget).size(225, 500);

        return segment;
    }

    private  Table constructButtonsSegment () {
        lootUpgradeButton = new Widgets.LootUpgradeButton();
        lootButton = new Widgets.LootButton();
        autoLootButton = new Widgets.AutoLootButton();

        final Table segment = new Table();
        segment.defaults().size(420, 180).space(50);
        segment.add(lootUpgradeButton);
        segment.add(lootButton);
        segment.add(autoLootButton);

        return segment;
    }

    @Override
    public void show(Runnable onComplete) {
        super.show(onComplete);
        SaveData saveData = API.get(SaveData.class);

        statContainer.setData(saveData);
        mainGearContainer.setData(saveData.getGearsSaveData());
        tacticalsContainer.setData(saveData.getTacticalsSaveData());


        flagWidget.setData(saveData.getFlagsSaveData());
        petWidget.setData();
        lootUpgradeButton.setData();
        lootButton.setData();
        autoLootButton.setData();
    }
}
