package com.bootcamp.demo.pages.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.localization.GameFont;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Widgets {
//    public static class ButtonWidget  extends OffsetButton {
//        public ButtonWidget(String color) {
//            final Image shovel = new Image(Resources.getDrawable("ui/shovel-icon"));
//            shovel.setSize(160, 160);
//
//            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(color)));
//            add(shovel);
//        }
//    }

    public static class GearWidget extends BorderedTable {
        @Getter @Setter
        private String name;
        @Getter @Setter
        private Image icon;
        @Getter @Setter
        private Label lvl;

        @Getter @Setter
        private Label stars;

        public GearWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
            icon = new Image();
            icon.setSize(180, 180);
            icon.setPosition(25, 25);
        }

        public void setData (String name, int lvl, int stars, Drawable icon) {
            this.name = name;

            this.lvl = Labels.make(GameFont.BOLD_20);
            this.lvl.setText("Lv." + lvl);
            this.lvl.setPosition(20, 30);

            this.icon.setDrawable(icon);
//            this.icon.setFillParent(true);
            addActor(this.icon);
            addActor(this.lvl);
            int yPos = 170;
            for (int j = 0; j < stars; j++) {
                final Image star = new Image(Resources.getDrawable("ui/star"));
                star.setSize(50, 50);
                star.setPosition(0, yPos);
                yPos -= 30;
                addActor(star);
            }
        }
    }

    public static class StatWidget extends Table {
        @Getter @Setter
        private String statName;
        @Getter @Setter
        private float value;

        public void setData (String name, float value) {
        Label nameLabel = Labels.make(GameFont.BOLD_22);
        nameLabel.setText(name);
        nameLabel.setSize(340, nameLabel.getPrefHeight());
        nameLabel.setAlignment(Align.left);
        nameLabel.setColor(Color.valueOf("#423d37"));

        Label valueLabel = Labels.make(GameFont.BOLD_22);
        if (!Objects.equals(name, "HP") && !Objects.equals(name, "ATK"))
            valueLabel.setText(String.valueOf(value) + "%");
        else
            valueLabel.setText(String.valueOf(value));
        valueLabel.setSize(340, valueLabel.getPrefHeight());
        valueLabel.setAlignment(Align.right);
        addActor(nameLabel);
        addActor(valueLabel);
        }
    }

    public static class TacticalWidget extends Table {
        @Getter @Setter
        private String name;
        @Getter @Setter
        private Image icon;
        @Getter @Setter
        private int lvl;

        public TacticalWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b29985")));
            icon = new Image();
        }

        public void setData (String name, Drawable icon, int lvl) {
            this.name = name;
            this.lvl = lvl;
            this.icon.setDrawable(icon);
            this.icon.setFillParent(true);
            add(this.icon);
        }
    }

}
