package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SampleController {
    public ScrollPane scrollPane;
    public GridPane gameGrid;
    public Button buttonStart;
    public Button buttonStop;
    public Label generationsLabel;
    public AnchorPane anchorPane;

    public void setResizeOptions() {
        this.scrollPane.prefHeightProperty().bind(anchorPane.heightProperty());
        this.scrollPane.prefWidthProperty().bind(anchorPane.widthProperty());
    }
}
