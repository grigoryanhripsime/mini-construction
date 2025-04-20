package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class StatGameData implements IGameData{

    //maybe change to enum
    @Getter
    private String name;

    @Override
    public void load(XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
    }
}
