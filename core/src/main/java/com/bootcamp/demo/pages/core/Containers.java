package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.StatsManager;
import com.bootcamp.demo.data.game.GearGameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;

import java.util.EnumMap;
import java.util.Map;

public class Containers {
    public static class GearContainer extends WidgetsContainer<Widgets.GearWidget> {

        public GearContainer () {
            super(3);
            defaults().size(225).space(20);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
            center().pad(20);
            for (int i = 0; i < 6; i++) {
                Widgets.GearWidget gearWidget = new Widgets.GearWidget();
                add(gearWidget);
            }
        }

        public void setData (GearsSaveData gearsSaveData) {

            ObjectMap<GearGameData.Type, GearSaveData> equippedGears = gearsSaveData.getEquippedGears();
            int i = 0;
            for (ObjectMap.Entry<GearGameData.Type, GearSaveData> equippedGear : equippedGears) {
                getWidgets().get(i).setData(equippedGear.value);
                i++;
            }
        }
    }

    public static class StatContainer extends WidgetsContainer<Widgets.StatWidget> {
        public StatContainer () {
            super(3);
            defaults().size(350, 50).space(30);
            for (int i = 0; i < 9; i++) {
                Widgets.StatWidget statWidget = new Widgets.StatWidget();
                add(statWidget);
            }
        }

        public void setData (SaveData saveData) {
            EnumMap<Stat, Float> stats = StatsManager.getAllStats(saveData);
            int i = 0;
            for (Map.Entry<Stat, Float> entry : stats.entrySet()) {
                getWidgets().get(i).setData(entry);
                i++;
            }
        }
    }

    public static class TacticalsContainer extends WidgetsContainer<Widgets.TacticalWidget> {

        public TacticalsContainer () {
            super(2);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
            defaults().size(90).pad(10);
            for (int i = 0; i < 4; i++) {
                Widgets.TacticalWidget tacticalWidget = new Widgets.TacticalWidget();
                add(tacticalWidget);
            }
        }
        public void setData(TacticalsSaveData tacticalsSaveData) {
            int i = 0;
            for (IntMap.Entry<TacticalSaveData> tactical : tacticalsSaveData.getTacticals()) {
                getWidgets().get(i).setData(tactical.value);
                i++;
            }
        }
    }
}
