package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.pages.core.Containers;
import com.bootcamp.demo.pages.core.Widgets;
import lombok.Getter;

import javax.swing.text.Style;

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

        final Table incompleteAndMilGearSkinSetsWrapper = new Table();
        incompleteAndMilGearSkinSetsWrapper.add(incompleteSet).size(600, 50);
        incompleteAndMilGearSkinSetsWrapper.add(milGearSkinSets).size(100).left();


        final Containers.GearContainer mainGearContainer = new Containers.GearContainer();
        mainGearContainer.setData(saveData.getEquipmentSaveData().getEquips(), gameData.getEquipnemtsGameData().getEquips());

        Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
        segment.add(incompleteAndMilGearSkinSetsWrapper);
        segment.row();
        segment.add(mainGearContainer);

        return segment;
    }


    private Table constructSecondaryGearSegment () {
        final Image flag = new Image(Resources.getDrawable("ui/rush-icon-main"));
        final BorderedTable flagWidget = new BorderedTable();
        flagWidget.add(flag).grow();

        final Containers.TacticalsContainer tacticalsContainer = new Containers.TacticalsContainer();
        tacticalsContainer.setData(saveData.getTacticalsSaveData().getTacticals(), gameData.getTacticalsGameData().getTacticals());

        final Table tacticalsAndFlagWrapper = new Table();
        tacticalsAndFlagWrapper.add(tacticalsContainer);
        tacticalsAndFlagWrapper.row();
        tacticalsAndFlagWrapper.add(flagWidget).size(225).space(20);

        final Image pet = new Image(Resources.getDrawable("ui/pet-cat-orange"));

        final Image homeImage = new Image(Resources.getDrawable("ui/home-icon"));
        homeImage.setSize(100, 100);
        homeImage.setPosition(60, 40);

        final OffsetButton homeButton = new OffsetButton(OffsetButton.Style.ORANGE_35);
        homeButton.addActor(homeImage);

        final BorderedTable petWidgetContainer = new BorderedTable();
        petWidgetContainer.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2b8b0")));
        petWidgetContainer.add(pet).expandY().bottom();
        petWidgetContainer.row();
        petWidgetContainer.add(homeButton).size(223, 160).bottom();

        final Table segment = new Table();
        segment.add(tacticalsAndFlagWrapper).space(30);
        segment.add(petWidgetContainer).size(225, 480);

        return segment;
    }

    private  Table constructButtonSegment () {
        final OffsetButton shovelUpgradeButton = constructSovelUpgradeButton();
        final OffsetButton lootButton = constructLootButton();
        final OffsetButton autoLootButton = constructAutoLootButton();

        final Table segment = new Table();
        segment.defaults().size(420, 180).space(50);
        segment.add(shovelUpgradeButton);
        segment.add(lootButton);
        segment.add(autoLootButton);

        return segment;
    }
    OffsetButton constructSovelUpgradeButton () {
        final Image shovelUpgradeImage = new Image(Resources.getDrawable("ui/shovel-upgrade-icon"));
        shovelUpgradeImage.setSize(130, 130);
        shovelUpgradeImage.setPosition(30, 40);

        final Label blade = Labels.make(GameFont.BOLD_22);
        blade.setText("Lv.1");

        final Table bladeSegment = new Table();
        bladeSegment.setBackground(Squircle.SQUIRCLE_10.getDrawable(Color.valueOf("#a18867")));
        bladeSegment.add(blade).pad(10);

        final Label handle = Labels.make(GameFont.BOLD_22);
        handle.setText("Lv.1");

        final Table handleSegment = new Table();
        handleSegment.setBackground(Squircle.SQUIRCLE_10.getDrawable(Color.valueOf("#a18867")));
        handleSegment.add(handle).pad(10);

        final Table charastaristics = new Table();
        charastaristics.add(bladeSegment).size(180, 50).space(10);
        charastaristics.row();
        charastaristics.add(handleSegment).size(180, 50);
        charastaristics.setPosition(280, 100);

        final OffsetButton shovelUpgradeButton = new OffsetButton(OffsetButton.Style.ORANGE_35);
        shovelUpgradeButton.addActor(shovelUpgradeImage);
        shovelUpgradeButton.addActor(charastaristics);

        return shovelUpgradeButton;
    }

    OffsetButton constructLootButton () {
        final Label loot = Labels.make(GameFont.BOLD_28);
        loot.setText("Loot");
        final Table lootSegment = new Table();
        lootSegment.add(loot);

        final Image shovel = new Image(Resources.getDrawable("ui/shovel-icon"));
        shovel.setSize(130, 130);
        shovel.setPosition(100, -50);

        final Table wrapper = new Table();
        wrapper.add(lootSegment);
        wrapper.addActor(shovel);
        wrapper.setPosition(160, 100);

        final OffsetButton lootButton = new OffsetButton(OffsetButton.Style.GREEN_35);
        lootButton.addActor(wrapper);

        return lootButton;
    }

    OffsetButton constructAutoLootButton () {
        final Label loot = Labels.make(GameFont.BOLD_28);
        loot.setText("Auto Loot");
        final Table lootSegment = new Table();
        lootSegment.add(loot);

        final Image shovel = new Image(Resources.getDrawable("ui/auto-loot-icon"));
        shovel.setSize(130, 130);
        shovel.setPosition(100, -50);

        final Table wrapper = new Table();
        wrapper.add(lootSegment);
        wrapper.addActor(shovel);
        wrapper.setPosition(160, 100);

        final OffsetButton lootButton = new OffsetButton(OffsetButton.Style.GRAY_35);
        lootButton.addActor(wrapper);

        return lootButton;
    }

}
