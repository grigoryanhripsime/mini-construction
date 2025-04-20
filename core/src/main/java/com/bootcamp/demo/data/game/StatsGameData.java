package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.data.save.StatSaveData;
import lombok.Getter;

public class StatsGameData implements IGameData{

    @Getter
    private final ObjectMap<String, StatGameData> stats = new ObjectMap<>();

    @Override
    public void load(XmlReader.Element rootXml) {
        stats.clear();
        final Array<XmlReader.Element> statsXml = rootXml.getChildrenByName("stat");
        for (XmlReader.Element statXml : statsXml) {
            final StatGameData statGameData = new StatGameData();
            statGameData.load(statXml);
            stats.put(statGameData.getName(), statGameData);
        }
    }
}
