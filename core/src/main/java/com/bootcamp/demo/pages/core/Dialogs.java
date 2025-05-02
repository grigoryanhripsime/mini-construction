package com.bootcamp.demo.pages.core;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.bootcamp.demo.data.GenerateManager;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.LootingPage;
import lombok.Getter;

import java.util.Map;

public class Dialogs {
    public static class GearDialog extends ADialog {
        private GearWidget gearInfo;
        private Pool<Widgets.StarWidget> starPool;

        @Override
        protected void constructTitleSegment (Table titleSegment) {
            Label titleLabel = Labels.make(GameFont.BOLD_48, Color.DARK_GRAY, "Gear info");

            CloseButton closeButton = new CloseButton();

            titleSegment.add(titleLabel).expandX();
            titleSegment.add(closeButton);
            titleSegment.pad(30);
        }

        @Override
        protected void constructContent(Table content) {
            starPool = new Pool<Widgets.StarWidget>() {
                @Override
                protected Widgets.StarWidget newObject() {
                    return new Widgets.StarWidget();
                }
            };

            gearInfo = new GearWidget();

            content.defaults().pad(30);
            content.add(gearInfo).width(800);
        }

        public class GearWidget extends BorderedTable {
            private final Image icon;
            private final BorderedTable iconSegment;
            private final Label name;
            private final  Label lvl;
            private final Label rarity;
            private final WidgetsContainer<Widgets.StatWidget> stats;

            public GearWidget () {
                name = Labels.make(GameFont.BOLD_48);

                icon = new Image();
                icon.setFillParent(true);
                lvl = Labels.make(GameFont.BOLD_24);
                lvl.setPosition(50, 50);
                rarity = Labels.make(GameFont.BOLD_24);

                iconSegment = new BorderedTable();
                iconSegment.addActor(icon);
                iconSegment.addActor(lvl);

                Table iconAndRarityWrapper = new Table();
                iconAndRarityWrapper.add(iconSegment).size(400, 400);
                iconAndRarityWrapper.row();
                iconAndRarityWrapper.add(rarity);

                stats = new WidgetsContainer<>(1);
                stats.defaults().size(350, 50).space(30);

                Table iconAndStatsWrapper = new Table();
                iconAndStatsWrapper.defaults().pad(30);
                iconAndStatsWrapper.add(iconAndRarityWrapper);
                iconAndStatsWrapper.row();
                iconAndStatsWrapper.add(stats);

                add(name);
                row();
                add(iconAndStatsWrapper).fillX();
            }

            public void setData (GearSaveData gearSaveData) {
                GearGameData gearGameData = API.get(GameData.class).getGearsGameData().getGears().get(gearSaveData.getName());

                icon.setDrawable(gearGameData.getIcon());
                iconSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(gearSaveData.getRarity().getBackgroundColor()));
                int xPos = 0;
                for (int j = 0; j < gearSaveData.getRarity().getStarCount(); j++) {
                    Widgets.StarWidget star = starPool.obtain();
                    star.setSize(80, 80);
                    star.setPosition(xPos, 320);
                    xPos += 60;
                    iconSegment.addActor(star);
                }
                name.setText(gearGameData.getTitle());
                lvl.setText("Lv." + gearSaveData.getLevel());
                rarity.setText(gearSaveData.getRarity().getTitle());
                rarity.setColor(gearSaveData.getRarity().getBackgroundColor());

                stats.clearChildren();

                for (Map.Entry<Stat, Float> entry : gearSaveData.getStatsSaveData().getStat().entrySet()) {
                    Widgets.StatWidget statWidget = new Widgets.StatWidget(); //TODO: take stats from pool
                    statWidget.setData(entry);
                    stats.add(statWidget);
                }
            }

            public void freeStars() {
                for (int i = iconSegment.getChildren().size - 1; i >= 0; i--) {
                    Actor actor = iconSegment.getChildren().get(i);
                    if (actor instanceof Widgets.StarWidget) {
                        starPool.free((Widgets.StarWidget) actor);
                        actor.remove();
                    }
                }
            }
        }

        public class CloseButton extends OffsetButton {
            private final Image icon;

            public CloseButton () {
                icon = new Image(Resources.getDrawable("ui/close-button"));
                icon.setSize(100, 100);

                build(Style.RED_35);
            }

            @Override
            public void buildInner(Table container) {
                super.buildInner(container);
                container.add(icon);

                setOnClick(() -> {
                    freeStars();
                    API.get(DialogManager.class).hide(GearDialog.class);
                });
            }
        }

        public void setData (GearSaveData gearSaveData) {
            freeStars();
            gearInfo.setData(gearSaveData);
        }

        public void freeStars() {
            gearInfo.freeStars();
        }

    }

    public static class FlagsDialog extends ADialog {
        private EquippedFlag equippedFlag;
        private FlagsContainer flagsContainer;
        private String newSelectedFlag;
        private EquipButton equipButton;
        private Pool<Widgets.StarWidget> starPool;
        private Pool<BlockedWidget> blockedPool;


        @Override
        protected void constructTitleSegment (Table titleSegment) {
            Label titleLabel = Labels.make(GameFont.BOLD_48, Color.DARK_GRAY, "Flags");

            CloseButton closeButton = new CloseButton();

            titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#debc7c")));
            titleSegment.add(titleLabel).expandX();
            titleSegment.add(closeButton);
            titleSegment.pad(30);
        }

        @Override
        protected void constructContent(Table content) {
            starPool = new Pool<Widgets.StarWidget>() {
                @Override
                protected Widgets.StarWidget newObject() {
                    return new Widgets.StarWidget();
                }
            };
            blockedPool = new Pool<BlockedWidget>() {
                @Override
                protected BlockedWidget newObject() {
                    return new BlockedWidget();
                }
            };

            equippedFlag = new EquippedFlag();
            flagsContainer = new FlagsContainer();
            equipButton = new EquipButton();

            Table general = new Table();
            general.defaults().pad(17);
            general.add(equippedFlag).fillX();
            general.row();
            general.add(flagsContainer);
            general.row();
            general.add(equipButton);

            content.pad(30);
            content.add(general).width(1200);
        }

        protected class EquippedFlag extends BorderedTable {
            private final Image icon;
            private final Label name;
            private final Label lvl;
            private final Label rarity;
            private final Table stars;
            private final WidgetsContainer<Widgets.StatWidget> stats;

            public EquippedFlag () {
                defaults().pad(60);
                icon = new Image();

                rarity = Labels.make(GameFont.BOLD_24);
                stars = new Table();
                Table rarityWrapper = new Table();
                rarityWrapper.add(rarity);
                rarityWrapper.add(stars).expand();

                name = Labels.make(GameFont.BOLD_36);
                lvl = Labels.make(GameFont.BOLD_24);
                stats = new WidgetsContainer<>(1);
                stats.defaults().size(350, 50).space(30);

                Table lvlStatsWrapper = new Table();
                lvlStatsWrapper.defaults().pad(10);
                lvlStatsWrapper.add(name);
                lvlStatsWrapper.row();
                lvlStatsWrapper.add(lvl);
                lvlStatsWrapper.row();
                lvlStatsWrapper.add(rarityWrapper);
                lvlStatsWrapper.row();
                lvlStatsWrapper.add(stats);

                add(icon).size(400, 400).expandX().left();
                add(lvlStatsWrapper);
            }

            public void setData (FlagsSaveData flagsSaveData) {
                FlagSaveData equipped = flagsSaveData.getFlags().get(flagsSaveData.getEquipped());
                FlagsGameData flagsGameData = API.get(GameData.class).getFlagsGameData();

                icon.setDrawable(flagsGameData.getFlags().get(flagsSaveData.getEquipped()).getIcon());
                name.setText(equipped.getName() + " flag");
                lvl.setText("Lvl: " + equipped.getLevel());
                rarity.setText(equipped.getRarity().getTitle());
                rarity.setColor(equipped.getRarity().getBackgroundColor());

                int xPos = 0;
                for (int j = 0; j < equipped.getRarity().getStarCount(); j++) {
                    Widgets.StarWidget star = starPool.obtain();
                    star.setSize(80, 80);
                    star.setPosition(xPos, -30);
                    xPos += 60;
                    stars.addActor(star);
                }

                stats.clearChildren();
                for (Map.Entry<Stat, Float> entry : equipped.getStatsSaveData().getStat().entrySet()) {
                    Widgets.StatWidget statWidget = new Widgets.StatWidget();
                    statWidget.setData(entry);
                    stats.add(statWidget);
                }
            }
        }

        protected class FlagsContainer extends WidgetsContainer<FlagWidget> {
            public FlagsContainer () {
                super(4);
                defaults().size(250).space(20);
                setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
                center().pad(20);

                for (int i = 0; i < 7; i++) {
                    FlagWidget flagWidget = new FlagWidget();
                    add(flagWidget);
                }
            }

            public void setData (FlagsSaveData flagsSaveData) {
                FlagsGameData flagsGameData = API.get(GameData.class).getFlagsGameData();

                int i = 0;
                for (ObjectMap.Entry<String, FlagGameData> entry : flagsGameData.getFlags()) {
                    boolean isAvailable = flagsSaveData.getFlags().containsKey(entry.key);
                    getWidgets().get(i).setData(entry.value, isAvailable, flagsSaveData.getEquipped());
                    i++;
                }
            }
        }

        protected class FlagWidget extends BorderedTable {
            private final Image icon;

            public FlagWidget () {
                icon = new Image();
                add(icon);
            }

            public void setData (@Null FlagGameData value, boolean isAvailable, String equippedFlag) {
                if (value == null) {
                    setEmpty();
                    return;
                }
                icon.setDrawable(value.getIcon());
                setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#81776E")));

                if (!isAvailable)
                {
                    BlockedWidget blocked = blockedPool.obtain();
                    blocked.setPosition(120, 10);
                    addActor(blocked);
                    return;
                }

                setOnClick(() -> {
                    newSelectedFlag = value.getName();
                    if (equippedFlag.equals(newSelectedFlag))
                        return;
                    setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.BLUE));
                    equipButton.setStyle(OffsetButton.Style.GREEN_35);
                });
            }
        }

        public class BlockedWidget extends Image implements Pool.Poolable {

            public BlockedWidget () {
                setDrawable(Resources.getDrawable("ui/flags/blocked"));
                setSize(120, 120);
            }

            @Override
            public void reset() {}
        }

        protected class EquipButton extends OffsetButton {
            private final Label label;

            public EquipButton () {
                label = Labels.make(GameFont.BOLD_28);

                build();
            }

            @Override
            public void buildInner(Table container) {
                super.buildInner(container);
                container.add(label).pad(30);

                setOnClick(() -> {
                    FlagsSaveData flagsSaveData = API.get(SaveData.class).getFlagsSaveData();
                    if (newSelectedFlag.isEmpty() || flagsSaveData.getEquipped().compareTo(newSelectedFlag) == 0)
                        return ;
                    flagsSaveData.setEquipped(newSelectedFlag);
                    freeStars();
                    freeBlockedWidgets();
                    hide(super.onClick);
                    // During flag changing there can be a lot of changes (like general stats can be changed)
                    API.get(PageManager.class).getPage(LootingPage.class).setData();
                });
            }

            public void setData () {
                setStyle(Style.GRAY_35);
                label.setText("Equip");
            }
        }

        protected class CloseButton extends OffsetButton {
            private final Image icon;

            public CloseButton () {
                icon = new Image(Resources.getDrawable("ui/close-button"));
                icon.setSize(100, 100);

                build(Style.RED_35);
            }

            @Override
            public void buildInner(Table container) {
                super.buildInner(container);
                container.add(icon);

                setOnClick(() -> {
                    freeStars();
                    freeBlockedWidgets();
                    API.get(DialogManager.class).hide(FlagsDialog.class);
                });
            }
        }

        public void setData () {
            freeStars();
            freeBlockedWidgets();
            SaveData saveData = API.get(SaveData.class);

            equippedFlag.setData(saveData.getFlagsSaveData());
            flagsContainer.setData(saveData.getFlagsSaveData());
            newSelectedFlag = "";
            equipButton.setData();
            API.get(PageManager.class).getPage(LootingPage.class).setData();
        }

        @Override
        public void show(Runnable onComplete) {
            super.show(onComplete);
            setData();
        }

        public void freeStars() {
            for (int i = equippedFlag.stars.getChildren().size - 1; i >= 0; i--) {
                Actor actor = equippedFlag.stars.getChildren().get(i);
                if (actor instanceof Widgets.StarWidget) {
                    starPool.free((Widgets.StarWidget) actor);
                    actor.remove();
                }
            }
        }

        public void freeBlockedWidgets() {
            for (FlagWidget widget : flagsContainer.getWidgets()) {
                for (int i = widget.getChildren().size - 1; i >= 0; i--) {
                    Actor actor = widget.getChildren().get(i);
                    if (actor instanceof BlockedWidget) {
                        blockedPool.free((BlockedWidget) actor);
                        actor.remove();
                    }
                }
            }
        }

    }

    public static class PetsDialog extends ADialog {
        private EquippedPet equippedPet;
        private PetsContainer petsContainer;
        private String newSelectedPet;
        private EquipButton equipButton;
        private Pool<Widgets.StarWidget> starPool;

        @Override
        protected void constructTitleSegment (Table titleSegment) {
            Label titleLabel = Labels.make(GameFont.BOLD_40, Color.DARK_GRAY, "Pets");

            CloseButton closeButton = new CloseButton();

            titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#7ed97b")));
            titleSegment.add(titleLabel).expandX();
            titleSegment.add(closeButton);
            titleSegment.pad(30);
        }

        @Override
        protected void constructContent(Table content) {
            starPool = new Pool<Widgets.StarWidget>() {
                @Override
                protected Widgets.StarWidget newObject() {
                    return new Widgets.StarWidget();
                }
            };
            equippedPet = new EquippedPet();
            petsContainer = new PetsContainer();

            Table general = new Table();
            general.defaults().pad(17);
            general.add(equippedPet).fillX();
            general.row();
            general.add(petsContainer);

            content.add(general).width(1200);
        }

        protected class EquippedPet extends Table {
            Image icon;
            Label name;
            Label lvl;
            Table iconWrapper;
            Label rarity;
            WidgetsContainer<Widgets.StatWidget> stats;

            public EquippedPet () {
                rarity = Labels.make(GameFont.BOLD_24);
                rarity.setPosition(10, 430);
                icon = new Image(Squircle.SQUIRCLE_35.getDrawable(Color.BLUE));
                icon.setSize(400, 400);
                icon.setPosition(300, 30);
                name = Labels.make(GameFont.BOLD_24);
                name.setPosition(10, 480);
                lvl = Labels.make(GameFont.BOLD_24);
                lvl.setPosition(10, 50);

                iconWrapper = new Table();
                iconWrapper.setBackground(Resources.getDrawable("ui/pets/pet-background"));
                iconWrapper.addActor(icon);
                iconWrapper.addActor(name);
                iconWrapper.addActor(rarity);
                iconWrapper.addActor(lvl);

                stats = new WidgetsContainer<>(2);
                stats.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
                stats.defaults().size(350, 50).pad(30);

                equipButton = new EquipButton();

                Table statsAndButtonWrapper = new Table();
                statsAndButtonWrapper.add(stats).fillX();
                statsAndButtonWrapper.add(equipButton).pad(40);


                add(iconWrapper).size(1100, 515).pad(20);
                row();
                add(statsAndButtonWrapper);
            }

            public void setData (PetsSaveData petsSaveData) {
                PetSaveData equipped = petsSaveData.getPets().get(petsSaveData.getEquipped());
                PetsGameData petsGameData = API.get(GameData.class).getPetsGameData();
                icon.setDrawable(petsGameData.getPets().get(equipped.getName()).getIcon());
                name.setText(petsGameData.getPets().get(equipped.getName()).getTitle());
                lvl.setText("Lvl: " + equipped.getLevel());
                rarity.setText(equipped.getRarity().getTitle());
                rarity.setColor(equipped.getRarity().getBackgroundColor());

                int xPos = 10;
                for (int j = 0; j < equipped.getRarity().getStarCount(); j++) {
                    Widgets.StarWidget star = starPool.obtain();
                    star.setSize(80, 80);
                    star.setPosition(xPos,  320);
                    xPos += 60;
                    iconWrapper.addActor(star);
                }

                stats.clearChildren();
                for (Map.Entry<Stat, Float> entry : equipped.getStatsSaveData().getStat().entrySet()) {
                    Widgets.StatWidget statWidget = new Widgets.StatWidget();
                    statWidget.setData(entry);
                    stats.add(statWidget);
                }
                equipButton.setData();
            }
        }

        protected class PetsContainer extends WidgetsContainer<PetWidget> {
            public PetsContainer() {
                super(4);
                defaults().size(250).space(20);
                setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
                center().pad(20);

                for (int i = 0; i < 8; i++) {
                    PetWidget petWidget = new PetWidget();
                    add(petWidget);
                }
            }

            public void setData (PetsSaveData petsSaveData) {
                int i = 0;
                for (ObjectMap.Entry<String, PetSaveData> entry : petsSaveData.getPets()) {
                    getWidgets().get(i).setData(entry.value, petsSaveData.getEquipped());
                    i++;
                }
            }

            public void freeStars () {
                for (PetWidget widget : getWidgets()) {
                    widget.freeStars();
                }
            }
        }

        protected class PetWidget extends BorderedTable {
            Image icon;

            public PetWidget () {
                icon = new Image();

                add(icon);
            }

            public void setData (@Null PetSaveData value, String equippedPet) {
                if (value == null) {
                    setEmpty();
                    return;
                }
                PetGameData petGameData = API.get(GameData.class).getPetsGameData().getPets().get(value.getName());
                icon.setDrawable(petGameData.getIcon());
                setBackground(Squircle.SQUIRCLE_35.getDrawable(value.getRarity().getBackgroundColor()));
                setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#81776E")));
                int xPos = 10;
                for (int j = 0; j < value.getRarity().getStarCount(); j++) {
                    Widgets.StarWidget star = starPool.obtain();
                    star.setPosition(xPos, 200);
                    xPos += 30;
                    addActor(star);
                }
                setOnClick(() -> {
                    newSelectedPet = value.getName();
                    if (equippedPet.equals(newSelectedPet))
                        return;
                    setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.BLUE));
                    equipButton.setStyle(OffsetButton.Style.GREEN_35);
                });
            }

            public void freeStars() {
                for (int i = getChildren().size - 1; i >= 0; i--) {
                    Actor actor = getChildren().get(i);
                    if (actor instanceof Widgets.StarWidget) {
                        starPool.free((Widgets.StarWidget) actor);
                        actor.remove();
                    }
                }
            }
        }

        protected class EquipButton extends OffsetButton {
            private final Label label;

            public EquipButton () {
                label = Labels.make(GameFont.BOLD_28);

                build();
            }

            @Override
            public void buildInner(Table container) {
                super.buildInner(container);
                container.add(label).pad(30);

                setOnClick(() -> {
                    PetsSaveData petsSaveData = API.get(SaveData.class).getPetsSaveData();
                    if (newSelectedPet.isEmpty() || petsSaveData.getEquipped().compareTo(newSelectedPet) == 0)
                        return ;
                    freeStars();
                    petsSaveData.setEquipped(newSelectedPet);
                    hide(super.onClick);
                    // During flag changing there can be a lot of changes (like general stats can be changed)
                    API.get(PageManager.class).getPage(LootingPage.class).setData();
                });
            }

            public void setData () {
                setStyle(Style.GRAY_35);
                label.setText("Equip");
            }
        }

        protected class CloseButton extends OffsetButton {
            private final Image icon;

            public CloseButton () {
                icon = new Image(Resources.getDrawable("ui/close-button"));
                icon.setSize(100, 100);

                build(Style.RED_35);
            }

            @Override
            public void buildInner(Table container) {
                super.buildInner(container);
                container.add(icon);

                setOnClick(() -> {
                    freeStars();
                    API.get(DialogManager.class).hide(PetsDialog.class);
                });
            }
        }

        public void setData () {
            freeStars();
            SaveData saveData = API.get(SaveData.class);

            equippedPet.setData(saveData.getPetsSaveData());
            petsContainer.setData(saveData.getPetsSaveData());
            newSelectedPet = "";
            API.get(PageManager.class).getPage(LootingPage.class).setData();
        }

        @Override
        public void show(Runnable onComplete) {
            super.show(onComplete);
            setData();
        }

        public void freeStars() {
            for (int i = equippedPet.iconWrapper.getChildren().size - 1; i >= 0; i--) {
                Actor actor = equippedPet.iconWrapper.getChildren().get(i);
                if (actor instanceof Widgets.StarWidget) {
                    starPool.free((Widgets.StarWidget) actor);
                    actor.remove();
                }
            }
            petsContainer.freeStars();
        }
    }

    public static class RandomGearDialog extends ADialog {
        private CurrentGear currentGear;
        private DroppedGear droppedGear;
        private GearSaveData generatedItem;
        private Pool<Widgets.StarWidget> starPool;

        @Override
        protected void constructContent(Table content) {
            starPool = new Pool<Widgets.StarWidget>() {
                @Override
                protected Widgets.StarWidget newObject() {
                    return new Widgets.StarWidget();
                }
            };
            currentGear = new CurrentGear();
            droppedGear = new DroppedGear();

            Label currentLabel = Labels.make(GameFont.BOLD_32, Color.DARK_GRAY, "Current");
            Table forCurrLabel = new Table();
            forCurrLabel.add(currentLabel);
            Label newLabel = Labels.make(GameFont.BOLD_32, Color.DARK_GRAY, "Upcoming");
            Table forNewLabel = new Table();
            forNewLabel.add(newLabel);

            Table general = new Table();
            general.defaults().width(1000).pad(20);
            general.add(forCurrLabel);
            general.row();
            general.add(currentGear).expandX().center();
            general.row();
            general.add(forNewLabel).expandX().center();
            general.row();
            general.add(droppedGear).fillX();

            content.add(general);
        }

        public class CurrentGear extends BorderedTable {
            private final GearWidget gearWidget;

            public  CurrentGear () {
                gearWidget = new GearWidget();

                setPressedScale(1);
                add(gearWidget);
            }

            public void setData (GearSaveData gearSaveData) {
                gearWidget.setData(gearSaveData);
            }
        }

        public class DroppedGear extends BorderedTable {
            private final GearWidget gearWidget;
            private final DropButton dropButton;
            private final EquipButton equipButton;

            public  DroppedGear () {
                gearWidget = new GearWidget();

                dropButton = new DropButton();
                equipButton = new EquipButton();

                final Table buttons = new Table();
                buttons.defaults().pad(30);
                buttons.add(dropButton);
                buttons.add(equipButton);

                setPressedScale(1);
                add(gearWidget);
                row();
                add(buttons);
            }

            public void setData (GearSaveData gearSaveData) {
                gearWidget.setData(gearSaveData);
                dropButton.setData();
                equipButton.setData();
            }

            public class DropButton extends OffsetButton {
                private final Label label;

                public DropButton () {
                    label = Labels.make(GameFont.BOLD_28);

                    build();
                }

                @Override
                public void buildInner(Table container) {
                    super.buildInner(container);
                    container.add(label).pad(30);

                    setOnClick(RandomGearDialog.this::hideDialog);
                }

                public void setData () {
                    setStyle(Style.RED_35);
                    label.setText("  Drop  ");
                }
            }

            public class EquipButton extends OffsetButton {
                private final Label label;

                public EquipButton () {
                    label = Labels.make(GameFont.BOLD_28);

                    build();
                }

                @Override
                public void buildInner(Table container) {
                    super.buildInner(container);
                    container.add(label).pad(30);

                    setOnClick(() -> {
                        API.get(SaveData.class).getGearsSaveData().getEquippedGears().put(gearWidget.getType(), generatedItem);
                        hideDialog();
                    });
                }

                public void setData () {
                    setStyle(Style.GREEN_35);
                    label.setText(" Equip ");
                }
            }
        }

        public class GearWidget extends Table {
            private final Image icon;
            private final BorderedTable iconSegment;
            private final Label name;
            private final Label lvl;
            private final Label rarity;
            private final WidgetsContainer<Widgets.StatWidget> stats;
            @Getter
            private GearGameData.Type type;

            public GearWidget () {
                name = Labels.make(GameFont.BOLD_40);

                icon = new Image();
                icon.setFillParent(true);
                lvl = Labels.make(GameFont.BOLD_24);
                lvl.setPosition(50, 50);
                rarity = Labels.make(GameFont.BOLD_24);

                iconSegment = new BorderedTable();
                iconSegment.addActor(icon);
                iconSegment.addActor(lvl);

                Table iconAndRarityWrapper = new Table();
                iconAndRarityWrapper.add(iconSegment).size(400, 400);
                iconAndRarityWrapper.row();
                iconAndRarityWrapper.add(rarity);

                stats = new WidgetsContainer<>(1);
                stats.defaults().size(350, 50).space(30);

                Table iconAndStatsWrapper = new Table();
                iconAndStatsWrapper.defaults().pad(30);
                iconAndStatsWrapper.add(iconAndRarityWrapper);
                iconAndStatsWrapper.add(stats);

                add(name);
                row();
                add(iconAndStatsWrapper).fillX();
            }

            public void setData (@Null  GearSaveData gearSaveData) {
                freeStars();
                if (gearSaveData == null) {
                    return;
                }
                GearGameData gearGameData = API.get(GameData.class).getGearsGameData().getGears().get(gearSaveData.getName());

                icon.setDrawable(gearGameData.getIcon());
                iconSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(gearSaveData.getRarity().getBackgroundColor()));
                name.setText(gearGameData.getTitle());
                lvl.setText("Lv." + gearSaveData.getLevel());
                rarity.setText(gearSaveData.getRarity().getTitle());
                rarity.setColor(gearSaveData.getRarity().getBackgroundColor());
                type = gearGameData.getType();

                stats.clearChildren();

                for (Map.Entry<Stat, Float> entry : gearSaveData.getStatsSaveData().getStat().entrySet()) {
                    Widgets.StatWidget statWidget = new Widgets.StatWidget();
                    statWidget.setData(entry);
                    stats.add(statWidget);
                }

                int xPos = 10;
                for (int j = 0; j < gearSaveData.getRarity().getStarCount(); j++) {
                    Widgets.StarWidget star = starPool.obtain();
                    star.setSize(80, 80);
                    star.setPosition(xPos,  320);
                    xPos += 60;
                    iconSegment.addActor(star);
                }

            }

            public void freeStars() {
                for (int i = iconSegment.getChildren().size - 1; i >= 0; i--) {
                    Actor actor = iconSegment.getChildren().get(i);
                    if (actor instanceof Widgets.StarWidget) {
                        starPool.free((Widgets.StarWidget) actor);
                        actor.remove();
                    }
                }
            }
        }

        @Override
        public void show(Runnable onComplete) {
            freeStars();
            super.show(onComplete);
            GearsGameData gearsGameData = API.get(GameData.class).getGearsGameData();
            GearsSaveData gearsSaveData = API.get(SaveData.class).getGearsSaveData();

            generatedItem = GenerateManager.generateRandomGear();
            String name = generatedItem.getName();
            GearGameData.Type type = gearsGameData.getGears().get(name).getType();

            GearSaveData gearSaveData = gearsSaveData.getEquippedGears().get(type);
            currentGear.setData(gearSaveData);
            droppedGear.setData(generatedItem);
        }

        public void freeStars() {
            currentGear.gearWidget.freeStars();
            droppedGear.gearWidget.freeStars();
        }

        public void hideDialog() {
            freeStars();
            hide(null);
            API.get(PageManager.class).getPage(LootingPage.class).setData();
        }
    }
}
