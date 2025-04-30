package com.bootcamp.demo.data.save;

import com.bootcamp.demo.data.Rarity;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.GearGameData;
import com.bootcamp.demo.data.game.GearsGameData;
import com.bootcamp.demo.managers.API;

import java.util.EnumMap;
import java.util.Random;

public class GenerateManager {
    public static void generatePets () {
        Random rand = new Random();
        String[] pets = {"Dog", "Koala", "Panda", "Rabbit", "Unicorn"};

        for (String pet : pets) {
            PetSaveData petSaveData = new PetSaveData();
            petSaveData.setLevel(rand.nextInt(1, 25));
            petSaveData.setStatsSaveData(generateStats());
            petSaveData.setRarity(Rarity.values()[rand.nextInt(15)]);
            petSaveData.setName(pet);
            API.get(SaveData.class).getPetsSaveData().getPets().put(pet, petSaveData);
            API.get(SaveData.class).getPetsSaveData().setEquipped(pet);
        }
    }

    public static void generateFlags () {
        Random rand = new Random();
        String[] flags = {"Rebel", "Pirate", "Knights", "Fire", "Snowflake", "Royal", "Jester"};
        int index = rand.nextInt(0, flags.length);

        for (int i = 0; i < 4; i++) {
            FlagSaveData flagSaveData = new FlagSaveData();
            flagSaveData.setLevel(rand.nextInt(1, 25));
            flagSaveData.setStatsSaveData(generateStats());
            flagSaveData.setRarity(Rarity.values()[rand.nextInt(15)]);
            flagSaveData.setName(flags[index]);
            API.get(SaveData.class).getFlagsSaveData().getFlags().put(flagSaveData.getName(), flagSaveData);
            API.get(SaveData.class).getFlagsSaveData().setEquipped(flagSaveData.getName());
        }
    }

    public static StatsSaveData generateStats () {
        Random rand = new Random();

        EnumMap<Stat, Float> stats = new EnumMap<>(Stat.class);
        for (int i = 0; i < 5; i++) {
            Stat stat = Stat.values()[rand.nextInt(8)];
            stats.put(stat, rand.nextFloat(1, 100));
        }

        StatsSaveData statsSaveData = new StatsSaveData();
        statsSaveData.setStat(stats);
        return statsSaveData;
    }

    public static GearSaveData generateGear () {
        Random rand = new Random();

        String[] gears = {"gloves", "poncho", "skate-shoes", "gun"};
        int index = rand.nextInt(0, gears.length);

        GearSaveData gearSaveData = new GearSaveData();
        gearSaveData.setName(gears[index]);
        gearSaveData.setLevel(rand.nextInt(1, 25));
        gearSaveData.setRarity(Rarity.values()[rand.nextInt(15)]);
        gearSaveData.setStatsSaveData(generateStats());

        return gearSaveData;
    }
}
