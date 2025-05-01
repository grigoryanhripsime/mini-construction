package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.GearGameData;
import com.bootcamp.demo.data.game.PetGameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.managers.API;

import java.util.EnumMap;
import java.util.Random;

public class GenerateManager {
    private final static Random rand = new Random();
    private final static String[] flags = {"Rebel", "Pirate", "Knights", "Fire", "Snowflake", "Royal", "Jester"};
    private final static String[] gears = {"kitchen-gloves", "swat-boots", "gun", "brass-knuckles", "sombrero"};


    public static void generatePets () {
        ObjectMap<String, PetGameData> pets = API.get(GameData.class).getPetsGameData().getPets();
        for (ObjectMap.Entry<String, PetGameData> entry : pets) {
            PetSaveData petSaveData = new PetSaveData();
            petSaveData.setLevel(rand.nextInt(1, 25));
            petSaveData.setStatsSaveData(generateStats());
            petSaveData.setRarity(Rarity.values()[rand.nextInt(15)]);
            petSaveData.setName(entry.key);
            API.get(SaveData.class).getPetsSaveData().getPets().put(entry.key, petSaveData);
            API.get(SaveData.class).getPetsSaveData().setEquipped(entry.key);
        }
    }

    public static void generateFlags () {
        for (int i = 0; i < 4; i++) {
            int index = rand.nextInt(0, flags.length);
            FlagSaveData flagSaveData = new FlagSaveData();
            flagSaveData.setLevel(rand.nextInt(1, 25));
            flagSaveData.setStatsSaveData(generateStats());
            flagSaveData.setRarity(Rarity.values()[rand.nextInt(15)]);
            flagSaveData.setName(flags[index]);
            API.get(SaveData.class).getFlagsSaveData().getFlags().put(flagSaveData.getName(), flagSaveData);
            API.get(SaveData.class).getFlagsSaveData().setEquipped(flagSaveData.getName());
        }
    }

    public static void generateGears () {
        ObjectMap<String, GearGameData> gears = API.get(GameData.class).getGearsGameData().getGears();

        for (ObjectMap.Entry<String, GearGameData> entry : gears.entries()) {
            GearSaveData gearSaveData = new GearSaveData();
            gearSaveData.setLevel(rand.nextInt(1, 25));
            gearSaveData.setStatsSaveData(generateStats());
            gearSaveData.setRarity(Rarity.values()[rand.nextInt(15)]);
            gearSaveData.setName(entry.key);
            API.get(SaveData.class).getGearsSaveData().getEquippedGears().put(entry.value.getType(), gearSaveData);
        }
    }

    public static StatsSaveData generateStats () {
        EnumMap<Stat, Float> stats = new EnumMap<>(Stat.class);
        for (int i = 0; i < 5; i++) {
            Stat stat = Stat.values()[rand.nextInt(8)];
            stats.put(stat, rand.nextFloat(1, 100));
        }

        StatsSaveData statsSaveData = new StatsSaveData();
        statsSaveData.setStat(stats);
        return statsSaveData;
    }

    public static GearSaveData generateRandomGear() {
        int index = rand.nextInt(0, gears.length);
        GearSaveData gearSaveData = new GearSaveData();
        gearSaveData.setName(gears[index]);
        gearSaveData.setLevel(rand.nextInt(1, 25));
        gearSaveData.setRarity(Rarity.values()[rand.nextInt(15)]);
        gearSaveData.setStatsSaveData(generateStats());

        return gearSaveData;
    }
}
