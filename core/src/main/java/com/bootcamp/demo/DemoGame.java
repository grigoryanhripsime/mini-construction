package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.Rarity;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.GearGameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.events.GameStartedEvent;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;

import java.util.EnumMap;

public class DemoGame extends Game {

    @Override
    public void create () {
        Gdx.input.setInputProcessor(new InputMultiplexer());

        final GameData gameData = new GameData();
        API.Instance().register(GameData.class, gameData);
        gameData.load();

        loadSaveData();


        /// ////////////////////////

        final TacticalSaveData tacticalsSaveData = new TacticalSaveData();
        tacticalsSaveData.setName("compass");
        tacticalsSaveData.setLevel(3);
        tacticalsSaveData.setRarity(Rarity.ELITE);

        EnumMap<Stat, Float> stats0 = new EnumMap<>(Stat.class);
        for (Stat statType : Stat.values()) {
            stats0.put(statType, 20f); // or any other default value
        }

        StatsSaveData statsSaveData0 = new StatsSaveData();
        statsSaveData0.setStat(stats0);
        tacticalsSaveData.setStatsSaveData(statsSaveData0);

        API.get(SaveData.class).getTacticalsSaveData().getTacticals().put(0, tacticalsSaveData);

        /// ////////////////////

        final GearSaveData gearSaveData = new GearSaveData();
        gearSaveData.setName("poncho");
        gearSaveData.setLevel(15);
        gearSaveData.setRarity(Rarity.HARDENED);

        EnumMap<Stat, Float> stats = new EnumMap<>(Stat.class);
        for (Stat statType : Stat.values()) {
            stats.put(statType, 15.9f); // or any other default value
        }

        StatsSaveData statsSaveData = new StatsSaveData();
        statsSaveData.setStat(stats);
        gearSaveData.setStatsSaveData(statsSaveData);

        API.get(SaveData.class).getGearsSaveData().getEquippedGears().put(GearGameData.Type.BODY, gearSaveData);

        /// ////////////////////////////

        final GearSaveData gearSaveData1 = new GearSaveData();
        gearSaveData1.setName("gun");
        gearSaveData1.setLevel(10);
        gearSaveData1.setRarity(Rarity.IMMORTAL);

        EnumMap<Stat, Float> stats1 = new EnumMap<>(Stat.class);
        for (Stat statType : Stat.values()) {
            stats1.put(statType, 42f); // or any other default value
        }
        StatsSaveData statsSaveData1 = new StatsSaveData();
        statsSaveData1.setStat(stats1);
        gearSaveData1.setStatsSaveData(statsSaveData1);

        API.get(SaveData.class).getGearsSaveData().getEquippedGears().put(GearGameData.Type.MELEE, gearSaveData1);

        /// /////////////////////

        final GearSaveData gearSaveData2 = new GearSaveData();
        gearSaveData2.setName("skate-shoes");
        gearSaveData2.setLevel(11);
        gearSaveData2.setRarity(Rarity.ASCENDANT_2);

        EnumMap<Stat, Float> stats2 = new EnumMap<>(Stat.class);
        stats2.put(Stat.HP, 98f);
        stats2.put(Stat.CRIT, 34.5f);

        StatsSaveData statsSaveData2 = new StatsSaveData();
        statsSaveData2.setStat(stats2);
        gearSaveData2.setStatsSaveData(statsSaveData2);

        API.get(SaveData.class).getGearsSaveData().getEquippedGears().put(GearGameData.Type.SHOES, gearSaveData2);

        /// //////////////////////

        GenerateManager.generateFlags();
        GenerateManager.generatePets();
        savePlayerData();

        setScreen(new GameScreen());
        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    private void loadSaveData () {
        final FileHandle file = getPlayerDataFileHandler();
        if (file.exists()) {
            createNewSaveData();
            return;
        }

        final JsonReader jsonReader = new JsonReader();
        final Json json = new Json();
        json.setIgnoreUnknownFields(true);

        final String dataString = file.readString();
        final JsonValue jsonValue = jsonReader.parse(dataString);
        final SaveData saveData = json.readValue(SaveData.class, jsonValue);
        API.Instance().register(SaveData.class, saveData);
    }

    private void createNewSaveData () {
        final SaveData saveData = new SaveData();
        API.Instance().register(SaveData.class, saveData);
        savePlayerData();
    }

    public void savePlayerData () {
        final SaveData saveData = API.get(SaveData.class);

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setIgnoreUnknownFields(true);

        final String dataToPersist = json.toJson(saveData);
        getPlayerDataFileHandler().writeString(dataToPersist, false);
    }

    private FileHandle getPlayerDataFileHandler () {
        final FileHandle playerDataFile = Gdx.files.local("usercache").child("player-data");
        if (!playerDataFile.exists()) {
            playerDataFile.writeString("", false);
        }
        return playerDataFile;
    }

    @Override
    public void dispose () {
        super.dispose();
        API.Instance().dispose();
        Gdx.app.exit();
    }
}
