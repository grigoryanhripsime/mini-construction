package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.StatSaveData;
import com.bootcamp.demo.data.save.TacticalSaveData;
import com.bootcamp.demo.data.save.TacticalsSaveData;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.managers.API;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.flogger.Flogger;

public class Containers {
    public static class GearContainer extends WidgetsContainer<Widgets.GearWidget> {
        Array<Widgets.GearWidget> equipments = new Array<>(6);

        public GearContainer () {
            super(3);
            defaults().size(225).space(20);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
            center().pad(20);
            for (int i = 0; i < 6; i++) {
                Widgets.GearWidget equip = new Widgets.GearWidget();
                add(equip);
                equipments.add(equip);
            }
            setData();
        }

        public void setData() {
            GameData gameData = API.get(GameData.class);
            EquipnemtsGameData equipnemtsGameData = gameData.getEquipnemtsGameData();
            int i = 0;
            for (ObjectMap.Entry<String, EquipmentGameData> entry : equipnemtsGameData.getEquips().entries()) {
                EquipmentGameData value = entry.value;
                equipments.get(i).setData(value.getName(), value.getLvl(), value.getIcon());
                i++;
            }
        }
    }

    public static class StatContainer extends WidgetsContainer<Widgets.StatWidget> {

        private Array<Widgets.StatWidget> stats = new Array<>(9);

        public StatContainer () {
            super(3);
            defaults().size(350, 50).space(15);
        }

        public void setData (IntMap<StatSaveData> stats1) {
            for (IntMap.Entry<StatSaveData> stat : stats1) {
                Widgets.StatWidget statWidget = new Widgets.StatWidget();
                statWidget.setData(stat.value.getName(), stat.value.getValue());
                add(statWidget);
                stats.add(statWidget);
            }

        }
    }

    public static class TacticalsContainer extends WidgetsContainer<Table> {

        @Getter @Setter
        private Array<Widgets.TacticalWidget> tacticals;

        public TacticalsContainer () {
            super(2);
            tacticals = new Array<>(4);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
            defaults().size(90).pad(10);
            for (int i = 0; i < 4; i++) {
                Widgets.TacticalWidget tacticalWidget = new Widgets.TacticalWidget();
                tacticals.add(tacticalWidget);
                add(tacticalWidget);
            }
            setData();
        }
        public void setData() {
            GameData gameData = API.get(GameData.class);
            TacticalsGameData tacticalsGameData = gameData.getTacticalsGameData();
            int i = 0;
            for (ObjectMap.Entry<String, TacticalGameData> entry : tacticalsGameData.getTacticals().entries()) {
                TacticalGameData value = entry.value;
                tacticals.get(i).setData(value.getName(), value.getIcon());
                i++;
            }
//            SaveData saveData = API.get(SaveData.class);
//            for (IntMap.Entry<TacticalSaveData> j : saveData.getTacticalsSaveData().getTacticals()) {
//                System.out.println("hello: " + j.value.getName()+ "    " + j.value.getLevel());
//            }
        }
    }
}
