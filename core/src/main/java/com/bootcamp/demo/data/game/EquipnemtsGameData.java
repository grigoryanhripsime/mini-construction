package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.pages.core.Widgets;
import lombok.Getter;

public class EquipnemtsGameData implements IGameData{

    @Getter
    private final ObjectMap<String, EquipmentGameData> equips = new ObjectMap<>();

    @Override
    public void load(XmlReader.Element rootXml) {
        equips.clear();
        final Array<XmlReader.Element> equipsXml = rootXml.getChildrenByName("equip");
        for (XmlReader.Element equipXml : equipsXml) {
            final EquipmentGameData equipmentGameData = new EquipmentGameData();
            equipmentGameData.load(equipXml);
            equips.put(equipmentGameData.getName(), equipmentGameData);
        }
    }
}
