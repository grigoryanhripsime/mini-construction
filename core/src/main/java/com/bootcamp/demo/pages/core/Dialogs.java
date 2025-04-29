package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectMap;
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

import java.util.EnumMap;
import java.util.Map;

public class Dialogs {
    public static class GearDialog extends ADialog {
        private GearInfo gearInfo;

        @Override
        protected void constructContent(Table content) {
            gearInfo = new GearInfo();

            Table dialog = new Table();
            dialog.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
            dialog.add(gearInfo);


            content.add(dialog).width(1000);
        }

        public void setData (Widgets.GearWidget gearWidget) {
            gearInfo.setData(gearWidget);
        }

        public class GearInfo extends Table {
            private Image icon;
            private Label name;
            private Label lvl;
            private Label rarity;
            WidgetsContainer<Widgets.StatWidget> stats;

            public  GearInfo () {
                defaults().pad(30);
                icon = new Image(Squircle.SQUIRCLE_35.getDrawable(Color.BLUE));
                icon.setSize(500, 500);

                name = Labels.make(GameFont.BOLD_24);
                lvl = Labels.make(GameFont.BOLD_24);

                stats = new WidgetsContainer<>(1);
                stats.defaults().size(350, 50).space(30);

                Table lvlStatsWrapper = new Table();
                lvlStatsWrapper.defaults().pad(10);
                lvlStatsWrapper.add(name);
                lvlStatsWrapper.row();
                lvlStatsWrapper.add(lvl);
                lvlStatsWrapper.row();
                lvlStatsWrapper.add(stats);

                add(icon).size(400, 400).expandX();
                row();
                add(lvlStatsWrapper);
            }

            public void setData (Widgets.GearWidget gearWidget) {

                icon.setDrawable(gearWidget.getIcon().getDrawable());
                name.setText("Name: " + gearWidget.getName());
                lvl.setText("Lvl: " + gearWidget.getLvl());

                stats.clearChildren();

                for (Map.Entry<Stat, Float> entry : gearWidget.getStats().entrySet()) {
                    Widgets.StatWidget statWidget = new Widgets.StatWidget();
                    statWidget.setData(entry);
                    stats.add(statWidget);
                }

            }
        }
    }

    public static class FlagsDialog extends ADialog {
        private EquippedFlag equippedFlag;
        private FlagsContainer flagsContainer;
        private String newSelectedFlag;
        private EquipButton equipButton;

        @Override
        protected void constructTitleSegment (Table titleSegment) {
            Label titleLabel = Labels.make(GameFont.BOLD_48, Color.DARK_GRAY, "Flag Info");

            CloseButton closeButton = new CloseButton();

            titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#debc7c")));
            titleSegment.add(titleLabel).expandX();
            titleSegment.add(closeButton);
            titleSegment.pad(30);
        }

        @Override
        protected void constructContent(Table content) {
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

            content.add(general).size(1200, 1400);
        }

        protected class EquippedFlag extends BorderedTable {
            Image icon;
            Label name;
            Label lvl;
            Label rarity;
            WidgetsContainer<Widgets.StatWidget> stats;

            public EquippedFlag () {
                defaults().pad(60);
                icon = new Image(Squircle.SQUIRCLE_35.getDrawable(Color.BLUE));

                name = Labels.make(GameFont.BOLD_36);
                lvl = Labels.make(GameFont.BOLD_24);
                rarity = Labels.make(GameFont.BOLD_24);

                stats = new WidgetsContainer<>(1);
                stats.defaults().size(350, 50).space(30);

                Table lvlStatsWrapper = new Table();
                lvlStatsWrapper.defaults().pad(10);
                lvlStatsWrapper.add(name);
                lvlStatsWrapper.row();
                lvlStatsWrapper.add(lvl);
                lvlStatsWrapper.row();
                lvlStatsWrapper.add(rarity);
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
                rarity.setText("Rarity: " + equipped.getRarity().getTitle());


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
                boolean isAvailable;
                for (ObjectMap.Entry<String, FlagGameData> entry : flagsGameData.getFlags()) {
                    isAvailable = false;
                    if (flagsSaveData.getFlags().containsKey(entry.key))
                         isAvailable = true;
                    getWidgets().get(i).setData(entry.value, isAvailable, flagsSaveData.getEquipped());
                    i++;
                }
            }
        }

        protected class FlagWidget extends BorderedTable {
            Image icon;

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
                    Image blocked = new Image(Resources.getDrawable("ui/flags/blocked"));
                    blocked.setSize(210, 210);
                    blocked.setAlign(Align.center);
                    addActor(blocked);
                    return;
                }

                setOnClick(new Runnable() {
                    @Override
                    public void run() {
                        newSelectedFlag = value.getName();
                        if (equippedFlag.equals(newSelectedFlag))
                            return;
                        setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.BLUE));
                        equipButton.setStyle(OffsetButton.Style.GREEN_35);
                    }
                });
            }
        }

        protected class EquipButton extends OffsetButton {
            private Label label;

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
            private Label label;

            public CloseButton () {
                label = Labels.make(GameFont.BOLD_28);
                label.setText(" X ");

                build(Style.RED_35);
            }

            @Override
            public void buildInner(Table container) {
                super.buildInner(container);
                container.add(label).pad(30);

                setOnClick(() -> {
                    API.get(DialogManager.class).hide(FlagsDialog.class);
                });
            }
        }

        public void setData () {
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
    }

    public static class PetsDialog extends ADialog {
        private EquippedPet equippedPet;
        private PetsContainer petsContainer;
        private String newSelectedPet;
        private EquipButton equipButton;

        @Override
        protected void constructTitleSegment (Table titleSegment) {
            Label titleLabel = Labels.make(GameFont.BOLD_48, Color.DARK_GRAY, "Pet Info");

            CloseButton closeButton = new CloseButton();

            titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#7ed97b")));
            titleSegment.add(titleLabel).expandX();
            titleSegment.add(closeButton);
            titleSegment.pad(30);
        }

        @Override
        protected void constructContent(Table content) {
            equippedPet = new EquippedPet();
            petsContainer = new PetsContainer();
            equipButton = new EquipButton();

            Table general = new Table();
            general.defaults().pad(17);
            general.add(equippedPet).fillX();
            general.row();
            general.add(petsContainer);
            general.row();
            general.add(equipButton);

            content.add(general).size(1200, 1500);
        }

        protected class EquippedPet extends Table {
            Image icon;
            Label name;
            Label lvl;
            Label rarity;
            WidgetsContainer<Widgets.StatWidget> stats;

            public EquippedPet () {
                icon = new Image(Squircle.SQUIRCLE_35.getDrawable(Color.BLUE));
                icon.setSize(300, 300);
                icon.setPosition(100, 30);
                name = Labels.make(GameFont.BOLD_24,Color.CHARTREUSE, "Hello");
                name.setPosition(10, 450);
                rarity = Labels.make(GameFont.BOLD_24, Color.BLACK, "Ola");
                rarity.setPosition(10, 400);
                lvl = Labels.make(GameFont.BOLD_24, Color.BLUE, "Barev");
                lvl.setPosition(10, 0);

                Table iconWrapper = new Table();
                iconWrapper.setBackground(Resources.getDrawable("ui/pets/pet-background"));
                iconWrapper.addActor(icon);
                iconWrapper.addActor(name);
                iconWrapper.addActor(rarity);
                iconWrapper.addActor(lvl);


                stats = new WidgetsContainer<>(2);
                stats.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));;
                stats.defaults().size(350, 50).pad(30);

                add(iconWrapper);
                row();
                add(stats).size(1100, 100).pad(40);
            }

            public void setData (PetsSaveData petsSaveData) {
                PetSaveData equipped = petsSaveData.getPets().get(petsSaveData.getEquipped());
                PetsGameData petsGameData = API.get(GameData.class).getPetsGameData();
                icon.setDrawable(petsGameData.getPets().get(equipped.getName()).getIcon());
                name.setText(equipped.getName());
                lvl.setText("Lvl: " + equipped.getLevel());
                rarity.setText(equipped.getRarity().getTitle());


                stats.clearChildren();

                for (Map.Entry<Stat, Float> entry : equipped.getStatsSaveData().getStat().entrySet()) {
                    Widgets.StatWidget statWidget = new Widgets.StatWidget();
                    statWidget.setData(entry);
                    stats.add(statWidget);
                }
            }
        }

        protected class PetsContainer extends WidgetsContainer<PetWidget> {
            public PetsContainer() {
                super(4);
                defaults().size(250).space(20);
                setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
                center().pad(20);

                for (int i = 0; i < 7; i++) {
                    PetWidget petWidget = new PetWidget();
                    add(petWidget);
                }
            }

            public void setData (PetsSaveData petsSaveData) {
                PetsGameData petsGameData = API.get(GameData.class).getPetsGameData();
                int i = 0;
                for (ObjectMap.Entry<String, PetSaveData> entry : petsSaveData.getPets()) {
                    getWidgets().get(i).setData(petsGameData.getPets().get(entry.key), petsSaveData.getEquipped());
                    i++;
                }
            }
        }

        protected class PetWidget extends BorderedTable {
            Image icon;

            public PetWidget () {
                icon = new Image();

                add(icon);
            }

            public void setData (@Null PetGameData value, String equippedPet) {
                if (value == null) {
                    setEmpty();
                    return;
                }
                icon.setDrawable(value.getIcon());
                setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#81776E")));

                setOnClick(() -> {
                        newSelectedPet = value.getName();
                        if (equippedPet.equals(newSelectedPet))
                            return;
                        setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.BLUE));
                        equipButton.setStyle(OffsetButton.Style.GREEN_35);
                });
            }
        }

        protected class EquipButton extends OffsetButton {
            private Label label;

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
            private Label label;

            public CloseButton () {
                label = Labels.make(GameFont.BOLD_28);
                label.setText(" X ");

                build(Style.RED_35);
            }

            @Override
            public void buildInner(Table container) {
                super.buildInner(container);
                container.add(label).pad(30);

                setOnClick(() -> {
                    API.get(DialogManager.class).hide(PetsDialog.class);
                });
            }
        }

        public void setData () {
            SaveData saveData = API.get(SaveData.class);

            equippedPet.setData(saveData.getPetsSaveData());
            petsContainer.setData(saveData.getPetsSaveData());
            newSelectedPet = "";
            equipButton.setData();
            API.get(PageManager.class).getPage(LootingPage.class).setData();
        }

        @Override
        public void show(Runnable onComplete) {
            super.show(onComplete);
            setData();
        }
    }
}
