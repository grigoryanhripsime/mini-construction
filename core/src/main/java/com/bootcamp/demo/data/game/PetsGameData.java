package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;
import lombok.Setter;

public class PetsGameData implements IGameData {
    @Getter @Setter
    private ObjectMap<String, PetGameData> pets = new ObjectMap<>();

    @Override
    public void load(XmlReader.Element rootXml) {
        pets.clear();
        final Array<XmlReader.Element> petsXml = rootXml.getChildrenByName("item");
        for (XmlReader.Element petXml : petsXml) {
            final PetGameData petGameData = new PetGameData();
            petGameData.load(petXml);
            pets.put(petGameData.getName(), petGameData);
        }
    }
}
