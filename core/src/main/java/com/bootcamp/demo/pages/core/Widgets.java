package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.localization.GameFont;
import lombok.Getter;
import lombok.Setter;

public class Widgets {
    public static class ButtonWidget  extends BorderedTable {
        public ButtonWidget(String color) {
            final Image shovel = new Image(Resources.getDrawable("ui/shovel-icon"));
            shovel.setSize(160, 160);

            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(color)));
            add(shovel);
        }
    }

    public static class GearWidget extends BorderedTable {
        @Getter @Setter
        private String name;
        @Getter @Setter
        Image icon;
        @Getter @Setter
        private int lvl;

        public GearWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
        }

        public void setData (String name, int lvl, Drawable icon) {
            this.name = name;
            this.lvl = lvl;
            this.icon = new Image(icon);
            this.icon.setFillParent(true);
            addActor(this.icon);
        }
    }

    public static class StatWidget extends Table {
        @Getter @Setter
        private String statName;
        @Getter @Setter
        private float value;

        public void setData (String name, float value) {
        Label label = Labels.make(GameFont.BOLD_22);

        label.setText(String.format("%-10s%8.1f", name, value));
            System.out.println(label.getText());
        label.setPosition(5, 25);
        addActor(label);
        }
    }

    public static class TacticalWidget extends Table {
        @Getter @Setter
        private String name;
        @Getter @Setter
        private Image icon;

        public TacticalWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));

        }

        public void setData (String name, Drawable icon) {
            this.name = name;
            this.icon = new Image(icon);
            this.icon.setFillParent(true);
            addActor(this.icon);
        }
    }

}
