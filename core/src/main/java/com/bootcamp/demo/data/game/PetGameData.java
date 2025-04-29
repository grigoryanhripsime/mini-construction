package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;
import lombok.Setter;

public class PetGameData implements IGameData {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Drawable icon;

    @Override
    public void load(XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
    }
}
