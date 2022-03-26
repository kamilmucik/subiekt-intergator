package pl.estrix.warehouse.controller.list;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SlidingListTile extends StackPane {

    private final ListTile tile;
    private double iniX = 0d, iniY = 0d;

    /**
     * Minimun distance that listTile has to be slid to trigger swipe fake effect
     */
    private final DoubleProperty threshold = new SimpleDoubleProperty(150);
    /**
     * Visual effect applied to icons depending on  listTile allowed or not allowed
     */
    private final BooleanProperty allowed = new SimpleBooleanProperty(true);

    /**
     * Sliding the tile horizontally or scrolling the listview vertically
     */
    private final BooleanProperty scrolling = new SimpleBooleanProperty();
    private final BooleanProperty sliding = new SimpleBooleanProperty();

    /**
     * fake swipe events, in terms of boolean properties
     */
    private final BooleanProperty swipedLeft = new SimpleBooleanProperty();
    private final BooleanProperty swipedRight = new SimpleBooleanProperty();

    /*
    text behind the list tile, on left and right positions
    */
    private final StringProperty textLeft = new SimpleStringProperty();
    private final StringProperty textRight = new SimpleStringProperty();

    /**
     * Creates a new sliding list tile
     * @param tile the ListTile on top
     * @param allowed icons are enabled if allowed, or disabled otherwise
     * @param textLeft the icon on the left
     * @param textRight the icon on the right
     */
    public SlidingListTile(ListTile tile, boolean allowed, String textLeft, String textRight) {

        this.textLeft.set(textLeft);
        this.textRight.set(textRight);

        /*
        Back HBox
        */
        HBox backPane = new HBox();
        backPane.setAlignment(Pos.CENTER);
        backPane.setPadding(new Insets(10));
        backPane.getStyleClass().add("sliding");

        // sliding from right to left
        PseudoClass pseudoClassLeft = PseudoClass.getPseudoClass("left");
        tile.translateXProperty().addListener((obs, ov, nv) ->
                backPane.pseudoClassStateChanged(pseudoClassLeft, nv.doubleValue() < 0));

        Label labelLeft = new Label(this.textLeft.get());
        labelLeft.getStyleClass().add("icon-text");
        backPane.getChildren().add(labelLeft);

        HBox gap = new HBox();
        HBox.setHgrow(gap, Priority.ALWAYS);
        backPane.getChildren().add(gap);

        Label labelRight = new Label(this.textRight.get());
        labelRight.getStyleClass().add("icon-text");
        backPane.getChildren().add(labelRight);

        this.allowed.addListener((obs, ov, nv) -> {
            if (nv) {
                if (labelLeft.getStyleClass().contains("not-allowed")) {
                    labelLeft.getStyleClass().remove("not-allowed");
                }
                if (labelRight.getStyleClass().contains("not-allowed")) {
                    labelRight.getStyleClass().remove("not-allowed");
                }
            } else {
                if (!labelLeft.getStyleClass().contains("not-allowed")) {
                    labelLeft.getStyleClass().add("not-allowed");
                }
                if (!labelRight.getStyleClass().contains("not-allowed")) {
                    labelRight.getStyleClass().add("not-allowed");
                }
            }
        });
        this.allowed.set(allowed);

        /*
        Front ListTile
        */
        this.tile = tile;
        tile.getStyleClass().add("tile");

        /*
         Listen to mouse events, to generate fake swipe events
         */
        tile.setOnMousePressed(e -> {
            iniX=e.getSceneX();
            iniY=e.getSceneY();
            swipedLeft.set(false);
            swipedRight.set(false);
        });

        tile.setOnMouseDragged(e -> {
            // once sliding or scrolling have started, no change can be done
            // until release and start again
            if (scrolling.get() || (!sliding.get() && Math.abs(e.getSceneY() - iniY) > 10)) {
                e.consume();
                scrolling.set(true);

            }
            if (sliding.get() || (!scrolling.get() && Math.abs(e.getSceneX() - iniX) > 10)) {
                sliding.set(true);
                if (e.getSceneX() - iniX >= -tile.getWidth() + 20 &&
                        e.getSceneX() - iniX <= tile.getWidth() - 20) {
                    translateTile(e.getSceneX() - iniX);
                }
            }
        });

        // on mouse release, if sliding, generate swipe event
        tile.setOnMouseReleased(e->{
            if (scrolling.get()) {
                e.consume();
            }

            if (sliding.get()) {
                // on sliding, swipe event after slid distance greater than threshold
                if (e.getSceneX() - iniX > this.threshold.get()) {
                    swipedRight.set(true);
                } else if (e.getSceneX() - iniX < -this.threshold.get()) {
                    swipedLeft.set(true);
                } else {
                    // reset without transition
                    translateTile(0);
                }
            }
            // reset
            scrolling.set(false);
            sliding.set(false);
        });

        this.getChildren().addAll(backPane,tile);
    }

    private void translateTile(double posX) {
        tile.setTranslateX(posX);
    }

    /**
     * Reset position of tile with smooth transition
     */
    public void resetTilePosition() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), tile);
        transition.setInterpolator(Interpolator.EASE_OUT);
        transition.setFromX(tile.getTranslateX());
        transition.setToX(0);
        transition.playFromStart();
    }

    public BooleanProperty swipedLeftProperty() {
        return swipedLeft;
    }

    public BooleanProperty swipedRightProperty() {
        return swipedRight;
    }

    public DoubleProperty thresholdProperty() {
        return threshold;
    }

    public StringProperty textLeftProperty(){
        return textLeft;
    }

    public StringProperty textRightProperty(){
        return textRight;
    }

    public BooleanProperty slidingProperty() {
        return sliding;
    }

    public BooleanProperty scrollingProperty() {
        return scrolling;
    }

    public BooleanProperty allowedProperty() {
        return allowed;
    }
}