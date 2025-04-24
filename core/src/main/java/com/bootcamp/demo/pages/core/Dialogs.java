package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.data.game.FlagsGameData;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.FlagSaveData;
import com.bootcamp.demo.data.save.FlagsSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.Stat;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;

import java.util.Map;

public class Dialogs {
    public static class RandomGear extends ADialog {

        @Override
        protected void constructContent(Table content) {
            Table t = new Table();
//            t.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.PURPLE));
            content.add(t).size(1000, 1500);
            content.debugAll();
        }
    }

    public static class FlagsDialog extends ADialog {
        EquippedFlag equippedFlag;

        @Override
        protected void constructContent(Table content) {
            equippedFlag = new EquippedFlag();

            Table general = new Table();
            general.add(equippedFlag).expandX().expandY().top();

            content.add(general).size(1000, 1000);
        }

        @Override
        public void show(Runnable onComplete) {
            super.show(onComplete);

            SaveData saveData = API.get(SaveData.class);

            equippedFlag.setData(saveData.getFlagsSaveData());
        }

        class EquippedFlag extends BorderedTable {
            Image icon;
            Label name;
            Label lvl;
            WidgetsContainer<Widgets.StatWidget> stats;

            EquippedFlag () {
                defaults().pad(30);
                icon = new Image(Squircle.SQUIRCLE_35.getDrawable(Color.BLUE));
                icon.setSize(500, 500);

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
                lvlStatsWrapper.add(stats);

                add(icon).size(400, 400).expandX().left();
                add(lvlStatsWrapper).size(400, 400);
            }

            public void setData (FlagsSaveData flagsSaveData) {
                FlagSaveData equipped = flagsSaveData.getFlags().get(flagsSaveData.getEquipped());
                FlagsGameData flagsGameData = API.get(GameData.class).getFlagsGameData();
                icon.setDrawable(flagsGameData.getFlags().get(flagsSaveData.getEquipped()).getIcon());
                name.setText(equipped.getName() + " flag");
                lvl.setText("Lvl: " + equipped.getLevel());

                for (Map.Entry<Stat, Float> entry : equipped.getStatsSaveData().getStat().entrySet()) {
                    Widgets.StatWidget statWidget = new Widgets.StatWidget();
                    statWidget.setData(entry);
                    stats.add(statWidget);
                }

            }
        }
    }
}
