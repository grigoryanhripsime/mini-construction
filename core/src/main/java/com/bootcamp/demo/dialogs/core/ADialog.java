package com.bootcamp.demo.dialogs.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

public abstract class ADialog extends Table {
    // title
    protected Table titleSegment;
    protected Label titleLabel;
    protected Cell<Label> titleLabelCell;

    // content
    protected Table contentWrapper;
    protected Table content;

//    @Getter
//    protected IconButton closeButton;

    @Getter
    protected Table dialog;
    protected Table overlayTable;

    @Getter
    private boolean shown;
    private boolean animatingShow;

    protected float showingDuration = 0.08f;

    @Getter
    protected boolean selfHide = true;

    protected Cell<Table> titleSegmentCell;

    private float backgroundAlpha = 1f;

    protected Table dialogBorder;

    public ADialog () {
        initialisation();
    }

    protected void initialisation () {
        initDialog();

        initCloseButton();

        // init overlay
        overlayTable = new Table();
        constructOverlay(overlayTable);

        // init title segment
        initTitleSegment();
        constructTitleSegment(titleSegment);

        // init content
        content = new Table();
        constructContent(content);

        // wrap content
        contentWrapper = constructContentWrapper();

        // assemble dialog
        dialog = new Table();
        constructDialog(dialog);

        constructLayout();
    }

    protected void initTitleSegment () {
        titleSegment = new Table();
    }

    protected void constructLayout () {
        add(dialog);
    }

    @Override
    protected void drawBackground (Batch batch, float parentAlpha, float x, float y) {
        super.drawBackground(batch, backgroundAlpha, x, y);
    }

    protected void constructOverlay (Table overlayTable) {
//        overlayTable.add(closeButton).expand().top().right().pad(20).padTop(18);
    }

    protected void initCloseButton () {
        // init close button
//        closeButton = WidgetLibrary.CLOSE_BUTTON_SMALL();
//        closeButton.setOnClick(this::hide);
    }

    protected Table initDialogBorder (Drawable borderDrawable) {
        dialogBorder = new Table();
        dialogBorder.setBackground(borderDrawable);
        dialogBorder.setFillParent(true);
        contentWrapper.addActor(dialogBorder);

        content.setZIndex(0);
        dialogBorder.setZIndex(1);

        if (titleSegment != null) {
            titleSegment.setZIndex(2);
        }
        return dialogBorder;
    }

    protected void initDialog () {
        setBackground(Resources.getDrawable("basics/white-pixel", Color.valueOf("#000000bf")));
        setTouchable(Touchable.enabled);
        setFillParent(true);
        addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                if (event.getTarget() == ADialog.this && isSelfHide()) {
                    API.get(DialogManager.class).hide(ADialog.this.getClass());
                }
            }
        });
    }

    protected Table constructContentWrapper () {
        final Table contentWrapper = new Table();
        titleSegmentCell = contentWrapper.add(titleSegment).growX();
        contentWrapper.row();
        contentWrapper.add(content).grow();
        return contentWrapper;
    }

    protected void constructDialog (Table dialog) {
        dialog.setBackground(getDialogBackground());
        dialog.setTouchable(Touchable.enabled);
        dialog.stack(contentWrapper, overlayTable).grow();
    }

    protected void constructTitleSegment (Table titleSegment) {
        titleLabel = Labels.make(getTitleFont(), getTitleFontColor());
        titleLabel.setText(getTitle());
        titleLabelCell = titleSegment.add(titleLabel);
    }

    protected Color getTitleFontColor () {
        return Color.WHITE;
    }

    protected GameFont getTitleFont () {
        return GameFont.BOLD_50;
    }

    protected String getTitle () {
        return "Title";
    }

    protected Drawable getDialogBackground () {
        return Squircle.SQUIRCLE_35.getDrawable(Color.WHITE);
    }

    protected void setTitle (CharSequence title) {
        titleLabel.setText(title);
    }

    protected abstract void constructContent (Table content);

    public void show (Runnable onComplete) {
        shown = true;

        animateShow(onComplete);
    }

    private void animateShow (Runnable onComplete) {
        animatingShow = true;

        dialog.pack();
        dialog.setTransform(true);
        dialog.setScale(0.9f);
        dialog.getColor().a = 0.0f;
        dialog.setOrigin(Align.center);
        dialog.clearActions();

        dialog.addAction(Actions.parallel(
            Actions.fadeIn(showingDuration),
            Actions.sequence(
                Actions.scaleTo(1f, 1f, showingDuration),
                Actions.run(() -> {
                    dialog.setTransform(false);
                    animatingShow = false;

                    if (onComplete != null) {
                        onComplete.run();
                        ;
                    }
                })
            )
        ));
    }

    public void hide (Runnable onComplete) {
        if (animatingShow) return;

        shown = false;
        clearActions();

        remove();
        reset();
        if (onComplete != null) {
            onComplete.run();
        }
    }
}
