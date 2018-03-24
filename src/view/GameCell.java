package view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameCell extends Pane {

    public GameCell(final double width, final double height, final boolean state) {
        this.setMinSize(width, height);
        Rectangle rec = new Rectangle(width, height,
                state ? Color.BLACK : Color.WHITE);
        rec.setWidth(width);
        rec.setHeight(height);
        getChildren().add(rec);
    }
}
