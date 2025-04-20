package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
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
        }

        public void setData (IntMap<EquipmentSaveData> equipsSaveData, ObjectMap<String, EquipmentGameData> equipsGameData) {
            int i = 0;
            for (IntMap.Entry<EquipmentSaveData> equip : equipsSaveData) {
                if (i >= 6)
                    break;
                Widgets.GearWidget gearWidget = new Widgets.GearWidget();
                gearWidget.setData(equip.value.getName(), equip.value.getLevel(), equip.value.getStars(), equipsGameData.get(equip.value.getName()).getIcon());
                add(gearWidget);
                equipments.add(gearWidget);
                i++;
            }
            for (; i < 6; i++) {
                Widgets.GearWidget gearWidget = new Widgets.GearWidget();
//                gearWidget.setData(equip.value.getName(), equip.value.getLevel(), equip.value.getStars(), equipsGameData.get(equip.value.getName()).getIcon());
                add(gearWidget);
                equipments.add(gearWidget);
            }
        }
    }

    public static class StatContainer extends WidgetsContainer<Widgets.StatWidget> {

        private Array<Widgets.StatWidget> stats = new Array<>(9);

        public StatContainer () {
            super(3);
            defaults().size(350, 50).space(30);
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
        }
        public void setData(IntMap<TacticalSaveData> tacticalsSaveData, ObjectMap<String, TacticalGameData> tacticalsGameData) {
            int i = 0;
            for (IntMap.Entry<TacticalSaveData> entry : tacticalsSaveData) {
                if (i >= 4)
                    break;
                Widgets.TacticalWidget tacticalWidget = new Widgets.TacticalWidget();
                tacticalWidget.setData(entry.value.getName(), tacticalsGameData.get(entry.value.getName()).getIcon(), entry.value.getLevel());
                tacticals.add(tacticalWidget);
                add(tacticalWidget);
                i++;
            }
            for (; i < 4; i++) {
                Widgets.TacticalWidget tacticalWidget = new Widgets.TacticalWidget();
                tacticals.add(tacticalWidget);
                add(tacticalWidget);
            }
        }
    }
}
