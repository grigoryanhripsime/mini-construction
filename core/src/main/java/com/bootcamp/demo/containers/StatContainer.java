package com.bootcamp.demo.containers;

import com.badlogic.gdx.utils.Array;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.widgets.StatWidget;

public class StatContainer extends WidgetsContainer<StatWidget> {

    private Array<StatWidget> stats = new Array<>(9);

    public StatContainer () {
        super(3);
        defaults().size(350, 50).space(30);
        for (int i = 0; i < 9; i++) {
            StatWidget stat = new StatWidget();
            add(stat);
            stats.add(stat);
        }
    }
}
