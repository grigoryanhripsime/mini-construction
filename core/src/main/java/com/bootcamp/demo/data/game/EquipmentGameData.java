package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

public class EquipmentGameData implements IGameData {
    @Getter
    private String name;
    @Getter
    private int lvl;
    @Getter
    private Drawable icon;

    @Override
    public void load (XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        lvl = Integer.parseInt(rootXml.getAttribute("lvl"));
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
    }
}
