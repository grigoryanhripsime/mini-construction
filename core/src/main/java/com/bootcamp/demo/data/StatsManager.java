package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearGameData;
import com.bootcamp.demo.data.save.*;

import java.util.EnumMap;


public class StatsManager {
    public static EnumMap<Stat, Float> getAllStats (SaveData saveData) {
        final ObjectMap<GearGameData.Type, GearSaveData> equippedGears = saveData.getGearsSaveData().getEquippedGears();

        final EnumMap<Stat, Float> allStats = new EnumMap<>(Stat.class);
        for (Stat entry : Stat.values()) {
            System.out.println(entry.name());
            allStats.put(entry, 0f);
        }

        for (ObjectMap.Entry<GearGameData.Type, GearSaveData> entry : equippedGears) {
            for (EnumMap.Entry<Stat, Float> stat : entry.value.getStatsSaveData().getStat().entrySet()) {
                Stat key = stat.getKey();
                float result = allStats.get(key) + stat.getValue();
                allStats.put(key, result);
            }
        }

        IntMap<TacticalSaveData> tacticals = saveData.getTacticalsSaveData().getTacticals();

        for (IntMap.Entry<TacticalSaveData> tactical : tacticals) {
            for (EnumMap.Entry<Stat, Float> stat : tactical.value.getStatsSaveData().getStat().entrySet()) {
                Stat key = stat.getKey();
                float result = allStats.get(key) + stat.getValue();
                allStats.put(key, result);
            }
        }

        for (EnumMap.Entry<Stat, Float> entry : allStats.entrySet()) {
            if (entry.getKey().getType() == Stat.Type.MULTIPLICATIVE) {
                float value = allStats.get(entry.getKey()) / 100;
                allStats.put(entry.getKey(), value);
            }
        }


        return allStats;
    }
}
