package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;
import com.bootcamp.demo.data.Rarity;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.game.*;
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
        private Image icon;
        @Getter @Setter
        private Label lvl;
        @Getter @Setter
        private Rarity rarity;
        @Getter @Setter
        EnumMap<Stat, Float> stats;
        Pool<StarWidget> starPool;

        public GearWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
            icon = new Image();
            icon.setSize(180, 180);
            icon.setPosition(25, 25);
            addActor(icon);

            lvl = Labels.make(GameFont.BOLD_20);
            lvl.setPosition(20, 30);
            addActor(lvl);

            starPool = new Pool<StarWidget>() {
                @Override
                protected StarWidget newObject() {
                    return new StarWidget();
                }
            };
        }

        public void setData (@Null GearSaveData gearSaveData) {
            //by this for we are getting back our stars
            for (int i = getChildren().size - 1; i >= 0; i--) {
                Actor actor = getChildren().get(i);
                if (actor instanceof StarWidget) {
                    starPool.free((StarWidget) actor);
                    actor.remove();
                }
            }
            if (gearSaveData == null)
            {
                setEmpty();
                return;
            }
            GearGameData gearGameData = API.get(GameData.class).getGearsGameData().getGears().get(gearSaveData.getName());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(gearSaveData.getRarity().getBackgroundColor()));
            icon.setDrawable(gearGameData.getIcon());
            lvl.setText("Lv." + gearSaveData.getLevel());
            rarity = gearSaveData.getRarity();

            int xPos = 10;
            for (int j = 0; j < rarity.getStarCount(); j++) {
                StarWidget star = starPool.obtain();
                star.setPosition(xPos, 170);
                xPos += 30;
                addActor(star);
            }
            stats = gearSaveData.getStatsSaveData().getStat();

            setOnClick(() -> {
                Dialogs.GearDialog gearDialog = API.get(DialogManager.class).getDialog(Dialogs.GearDialog.class);
                gearDialog.setData(gearSaveData);
                API.get(DialogManager.class).show(Dialogs.GearDialog.class);
            });
        }
    }

    public static class StarWidget extends Image implements Pool.Poolable {

        public StarWidget () {
            setDrawable(Resources.getDrawable("ui/star"));
            setSize(50, 50);
        }

        @Override
        public void reset() {}
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

    public static class LootUpgradeButton extends OffsetButton {
        private final Image shovelUpgradeImage;
        private final Label bladeLevel;
        private final Label handleLevel;

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
        private final Image shovel;
        private final Label label;

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


            setOnClick(() -> API.get(DialogManager.class).show(Dialogs.RandomGearDialog.class));

        }

        public void setData () {
            shovel.setDrawable(Resources.getDrawable("ui/shovel-icon"));
            label.setText("LOOT");
        }
    }

    public static class AutoLootButton extends OffsetButton {
        private final Label label;
        private final Image shovel;

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
        private final Image icon;
        private final Pool<StarWidget> starPool;

        public FlagWidget () {
            starPool = new Pool<StarWidget>() {
                @Override
                protected StarWidget newObject() {
                    return new StarWidget();
                }
            };
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
            icon = new Image();
            icon.setSize(180, 180);
            add(icon);

            setOnClick(() -> API.get(DialogManager.class).show(Dialogs.FlagsDialog.class));
        }

        public void setData (@Null FlagsSaveData flagsSaveData) {
            //by this for we are getting back our stars
            for (int i = getChildren().size - 1; i >= 0; i--) {
                Actor actor = getChildren().get(i);
                if (actor instanceof StarWidget) {
                    starPool.free((StarWidget) actor);
                    actor.remove();
                }
            }
            if (flagsSaveData == null) {
                setEmpty();
                return;
            }
            FlagSaveData equipped = flagsSaveData.getFlags().get(flagsSaveData.getEquipped());
            FlagGameData flagGameData = API.get(GameData.class).getFlagsGameData().getFlags().get(equipped.getName());
            icon.setDrawable(flagGameData.getIcon());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(equipped.getRarity().getBackgroundColor()));
            int xPos = 10;
            for (int j = 0; j < equipped.getRarity().getStarCount(); j++) {
                StarWidget star = starPool.obtain();
                star.setPosition(xPos, 170);
                xPos += 30;
                addActor(star);
            }
        }
    }

    public static class PetWidget extends BorderedTable {
        private final Image pet;
        private final HomeButton homeButton;
        Pool<StarWidget> starPool;


        public PetWidget () {
            starPool = new Pool<StarWidget>() {
                @Override
                protected StarWidget newObject() {
                    return new StarWidget();
                }
            };
            pet = new Image();
            homeButton = new HomeButton();

            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2b8b0")));
            add(pet).expand().bottom();
            row();
            add(homeButton).size(223, 120).bottom();
        }

        public void setData (PetsSaveData petsSaveData) {
            //by this for we are getting back our stars
            for (int i = getChildren().size - 1; i >= 0; i--) {
                Actor actor = getChildren().get(i);
                if (actor instanceof StarWidget) {
                    starPool.free((StarWidget) actor);
                    actor.remove();
                }
            }
            PetSaveData petSaveData = petsSaveData.getPets().get(petsSaveData.getEquipped());
            PetGameData petGameData = API.get(GameData.class).getPetsGameData().getPets().get(petSaveData.getName());
            pet.setDrawable(petGameData.getIcon());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(petSaveData.getRarity().getBackgroundColor()));
            homeButton.setData();
            setOnClick(() -> API.get(DialogManager.class).show(Dialogs.PetsDialog.class));
            int xPos = 10;
            for (int j = 0; j < petSaveData.getRarity().getStarCount(); j++) {
                StarWidget star = starPool.obtain();
                star.setPosition(xPos, 450);
                xPos += 30;
                addActor(star);
            }
        }

        private class HomeButton extends OffsetButton {
            private final Image icon;

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
