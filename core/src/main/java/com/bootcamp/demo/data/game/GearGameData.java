package com.bootcamp.demo.data.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

import java.util.Locale;

public class GearGameData implements IGameData {
    @Getter
    private String name;
    @Getter
    private Type type;
    @Getter
    private Drawable icon;

    @Override
    public void load (XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        type = Type.valueOf(rootXml.getAttribute("type").toUpperCase(Locale.ENGLISH));
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
    }

    public enum Type {
        WEAPON,
        MELEE,
        HEAD,
        BODY,
        GLOVES,
        SHOES;
    }
}
