package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.bootcamp.demo.data.game.FlagsGameData;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Map;

public class Widgets {

    public static class GearWidget extends BorderedTable {
        @Getter @Setter
        private String name;
        @Getter @Setter
        private Image icon;
        @Getter @Setter
        private Label lvl;
        @Getter @Setter
        EnumMap<Stat, Float> stats;

        public GearWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
            icon = new Image();
            icon.setSize(180, 180);
            icon.setPosition(25, 25);

            lvl = Labels.make(GameFont.BOLD_20);
            lvl.setPosition(20, 30);
        }

        public void setData (@Null GearSaveData gearSaveData) {
            if (gearSaveData == null)
            {
                setEmpty();
                return;
            }
            setBackground(Squircle.SQUIRCLE_35.getDrawable(gearSaveData.getRarity().getBackgroundColor()));
            icon.setDrawable(API.get(GameData.class).getGearsGameData().getGears().get(gearSaveData.getName()).getIcon());
            lvl.setText("Lv." + gearSaveData.getLevel());
            addActor(icon);
            addActor(lvl);

            int xPos = 0;
            for (int j = 0; j < gearSaveData.getRarity().getStarCount(); j++) {
                final Image star = new Image(Resources.getDrawable("ui/star")); //this need to be pulled, not created everytime
                star.setSize(50, 50);
                star.setPosition(xPos, 170);
                xPos += 30;
                addActor(star);
            }
            stats = gearSaveData.getStatsSaveData().getStat();

            for (Map.Entry<Stat, Float> entry : stats.entrySet()) {
                System.out.println(entry.getKey().name() + " " + entry.getValue());
            }

        }
    }

    public static class StatWidget extends Table {
        @Getter @Setter
        private Label statName;
        @Getter @Setter
        private Label value;

        public StatWidget () {
            statName = Labels.make(GameFont.BOLD_22);
            statName.setSize(340, statName.getPrefHeight());
            statName.setAlignment(Align.left);
            statName.setColor(Color.valueOf("#423d37"));

            value = Labels.make(GameFont.BOLD_22);
            value.setSize(340, value.getPrefHeight());
            value.setAlignment(Align.right);

            addActor(statName);
            addActor(value);
        }

        public void setData (Map.Entry<Stat, Float> stat) {
            statName.setText(stat.getKey().name() + ":");

            if (stat.getKey().getType() == Stat.Type.MULTIPLICATIVE)
                value.setText(String.format("%.2f", stat.getValue()) + "%");
            else
                value.setText(String.format("%.0f", stat.getValue()));
        }
    }

    public static class TacticalWidget extends Table {
        @Getter @Setter
        private String name;
        @Getter @Setter
        private Image icon;
        @Getter @Setter
        private int lvl;

        public TacticalWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
            icon = new Image();
            add(icon);
        }

        public void setData (@Null TacticalSaveData tacticalSaveData) {
            if (tacticalSaveData == null) {
                return;
            }

            name = tacticalSaveData.getName();
            lvl = tacticalSaveData.getLevel();
            icon.setDrawable(API.get(GameData.class).getTacticalsGameData().getTacticals().get(tacticalSaveData.getName()).getIcon());
            icon.setFillParent(true);
        }
    }

    public static class LootUpgradeButton extends OffsetButton{
        private Image shovelUpgradeImage;
        private Label bladeLevel;
        private Label handleLevel;

        public LootUpgradeButton () {
            shovelUpgradeImage = new Image();

            bladeLevel = Labels.make(GameFont.BOLD_22);
            handleLevel = Labels.make(GameFont.BOLD_22);

            build(Style.ORANGE_35);
        }

        @Override
        public void buildInner(Table container) {
            super.buildInner(container);

            final Table bladeSegment = new Table();
            bladeSegment.setBackground(Squircle.SQUIRCLE_10.getDrawable(Color.valueOf("#a18867")));
            bladeSegment.add(bladeLevel).pad(10);

            final Table handleSegment = new Table();
            handleSegment.setBackground(Squircle.SQUIRCLE_10.getDrawable(Color.valueOf("#a18867")));
            handleSegment.add(handleLevel).pad(10);

            final Table shovelLevelWrapper = new Table();
            shovelLevelWrapper.add(bladeSegment).size(180, 50).space(10);
            shovelLevelWrapper.row();
            shovelLevelWrapper.add(handleSegment).size(180, 50);

            container.add(shovelUpgradeImage).size(130, 130);
            container.add(shovelLevelWrapper).pad(30);

        }
        public void setData () {
            shovelUpgradeImage.setDrawable(Resources.getDrawable("ui/shovel-upgrade-icon"));
            bladeLevel.setText("Lv.1");
            handleLevel.setText("Lv.1");
        }
    }

    public static class LootButton extends OffsetButton {
        private Image shovel;
        private Label label;

        public LootButton () {
            label = Labels.make(GameFont.BOLD_28);
            shovel = new Image();

            build(Style.GREEN_35);
        }

        @Override
        public void buildInner(Table container) {
            super.buildInner(container);
            container.add(label).pad(30);
            container.add(shovel).size(130, 130);


            setOnClick(new Runnable() {
                @Override
                public void run() {
                    API.get(DialogManager.class).show(Dialogs.RandomGear.class);
                }
            });

        }

        public void setData () {
            shovel.setDrawable(Resources.getDrawable("ui/shovel-icon"));
            label.setText("LOOT");
        }
    }

    public static class AutoLootButton extends OffsetButton {
        private Label label;
        private Image shovel;

        public AutoLootButton () {
            label = Labels.make(GameFont.BOLD_28);
            shovel = new Image();

            build(Style.GRAY_35);
        }

        @Override
        public void buildInner(Table container) {
            super.buildInner(container);
            container.add(label).pad(30);
            container.add(shovel).size(130, 130);
        }

        public void setData () {
            shovel.setDrawable(Resources.getDrawable("ui/auto-loot-icon"));
            label.setText("Auto Loot");
        }

    }

    public static  class FlagWidget extends BorderedTable {
        private Image icon;

        public FlagWidget () {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
            icon = new Image();
            icon.setSize(180, 180);
            add(icon);

            setOnClick(new Runnable() {
                @Override
                public void run() {
                    API.get(DialogManager.class).show(Dialogs.FlagsDialog.class);
                }
            });
        }

        public void setData (@Null FlagsSaveData flagsSaveData) {
            if (flagsSaveData == null) {
                setEmpty();
                return;
            }
            FlagsGameData flagsGameData = API.get(GameData.class).getFlagsGameData();
            icon.setDrawable(flagsGameData.getFlags().get(flagsSaveData.getEquipped()).getIcon());
        }
    }

    public static class PetWidget extends BorderedTable {
        private Image pet;
        private HomeButton homeButton;

        public PetWidget () {
            pet = new Image();
            homeButton = new HomeButton();

            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2b8b0")));
            add(pet).expandY().bottom();
            row();
            add(homeButton).size(223, 120).bottom();
        }

        public void setData () {
            pet.setDrawable(Resources.getDrawable("ui/pet-cat-orange"));
            homeButton.setData();
        }

        private class HomeButton extends OffsetButton {
            private Image icon;

            HomeButton () {
                icon = new Image();

                build(Style.ORANGE_35);
            }

            @Override
            public void buildInner(Table container) {
                super.buildInner(container);

                container.add(icon).size(100, 100);
            }

            public void setData () {
                icon.setDrawable(Resources.getDrawable("ui/home-icon"));
            }
        }

    }
}
