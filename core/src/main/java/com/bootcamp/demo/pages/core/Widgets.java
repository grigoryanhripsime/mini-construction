package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.GearSaveData;
import com.bootcamp.demo.data.save.TacticalSaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
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

import java.util.Objects;

public class Widgets {

    public static class GearWidget extends BorderedTable {
        @Getter @Setter
        private String name;
        @Getter @Setter
        private Image icon;
        @Getter @Setter
        private Label lvl;
        @Getter @Setter
        private Label stars;

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
            lvl.setText("Lv." + gearSaveData.getLevel());
            icon.setDrawable(API.get(GameData.class).getGearsGameData().getGears().get(gearSaveData.getName()).getIcon());
            addActor(icon);
            addActor(lvl);

            int xPos = 170;
            for (int j = 0; j < gearSaveData.getRarity().getStarCount(); j++) {
                final Image star = new Image(Resources.getDrawable("ui/star")); //this need to be pulled, not created everytime
                star.setSize(50, 50);
                star.setPosition(xPos, 0);
                xPos += 30;
                addActor(star);
            }
        }
    }

    public static class StatWidget extends Table {
        @Getter @Setter
        private String statName;
        @Getter @Setter
        private float value;

        public void setData (String name, float value) {
        Label nameLabel = Labels.make(GameFont.BOLD_22);
        nameLabel.setText(name);
        nameLabel.setSize(340, nameLabel.getPrefHeight());
        nameLabel.setAlignment(Align.left);
        nameLabel.setColor(Color.valueOf("#423d37"));

        Label valueLabel = Labels.make(GameFont.BOLD_22);
        if (!Objects.equals(name, "HP") && !Objects.equals(name, "ATK"))
            valueLabel.setText(String.valueOf(value) + "%");
        else
            valueLabel.setText(String.valueOf(value));
        valueLabel.setSize(340, valueLabel.getPrefHeight());
        valueLabel.setAlignment(Align.right);
        addActor(nameLabel);
        addActor(valueLabel);
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

        public void setData (TacticalSaveData tacticalSaveData) {
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

            setOnClick(new Runnable() {
                @Override
                public void run() {
                    System.out.println("hello");
                    API.get(DialogManager.class).show(randomGear.class);
                }
            });

            build(Style.GREEN_35);
        }

        @Override
        public void buildInner(Table container) {
            super.buildInner(container);
            container.add(label).pad(30);
            container.add(shovel).size(130, 130);
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
        }

        public void setData () {
            icon.setDrawable(Resources.getDrawable("ui/rush-icon-main"));
        }
    }

    public static class PetWidget extends BorderedTable {
        private Image pet;
        private HomeButton homeButton;

        public PetWidget () {
            pet = new Image();
            homeButton = new HomeButton();

            setPressedScale(1);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2b8b0")));
            add(pet).expandY().bottom();
            row();
            add(homeButton).size(223, 160).bottom();
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

    public static class randomGear extends ADialog {

        @Override
        protected void constructContent(Table content) {
            Table t = new Table();
//            t.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.PURPLE));
            content.add(t).size(1000, 1500);
            content.debugAll();
        }
    }
}
