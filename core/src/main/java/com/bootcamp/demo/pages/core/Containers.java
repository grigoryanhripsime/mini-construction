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

        public void setData (IntMap<EquipmentSaveData> equipsSaveData) {
//            int i = 0;
//            for (IntMap.Entry<EquipmentSaveData> equip : equipsSaveData) {
//                Widgets.GearWidget gearWidget = new Widgets.GearWidget();
//                gearWidget.setData(equip.value.getName(), equip.value.getLevel(), equip.value.getStars(), equipsGameData.get(equip.value.getName()).getIcon());
//                i++;
//            }

        }
    }

    public static class StatContainer extends WidgetsContainer<Widgets.StatWidget> {
        public StatContainer () {
            super(3);
            defaults().size(350, 50).space(30);
            for (int i = 0; i < 9; i++) {
                Widgets.StatWidget statWidget = new Widgets.StatWidget();
                statWidget.setData("HP", 0);
                add(statWidget);
            }
        }

        public void setData (IntMap<StatSaveData> stats1) {

        }
    }

    public static class TacticalsContainer extends WidgetsContainer<Table> {

        public TacticalsContainer () {
            super(2);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c4b4a3")));
            defaults().size(90).pad(10);
            for (int i = 0; i < 4; i++) {
                Widgets.TacticalWidget tacticalWidget = new Widgets.TacticalWidget();
                add(tacticalWidget);
            }
        }
        public void setData(IntMap<TacticalSaveData> tacticalsSaveData) {
//            int i = 0;
//            for (IntMap.Entry<TacticalSaveData> entry : tacticalsSaveData) {
//                if (i >= 4)
//                    break;
//                Widgets.TacticalWidget tacticalWidget = new Widgets.TacticalWidget();
//                tacticalWidget.setData(entry.value.getName(), tacticalsGameData.get(entry.value.getName()).getIcon(), entry.value.getLevel());
//                tacticals.add(tacticalWidget);
//                add(tacticalWidget);
//                i++;
//            }

        }
    }
}
