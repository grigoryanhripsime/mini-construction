package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class GameData {

    private final XmlReader xmlReader = new XmlReader();

    @Getter
    private final TacticalsGameData tacticalsGameData;

    @Getter
    private final EquipnemtsGameData equipnemtsGameData;

    @Getter
    private final StatsGameData statsGameData;

    public GameData () {
        tacticalsGameData = new TacticalsGameData();
        equipnemtsGameData = new EquipnemtsGameData();
        statsGameData = new StatsGameData();
    }

    public void load () {
        tacticalsGameData.load(xmlReader.parse(Gdx.files.internal("data/tacticals.xml")));
        equipnemtsGameData.load(xmlReader.parse(Gdx.files.internal("data/equipments.xml")));
        statsGameData.load(xmlReader.parse(Gdx.files.internal("data/stats.xml")));
    }
}
