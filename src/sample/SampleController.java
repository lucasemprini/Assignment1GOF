package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SampleController {

    @FXML
    public ScrollPane scrollPane;
    @FXML
    public GridPane gameGrid;
    @FXML
    public Button buttonStart;
    @FXML
    public Button buttonStop;
    @FXML
    public Label generationsLabel;
    @FXML
    public AnchorPane anchorPane;

    public SampleController() {
        this.setResizeOptions();
    }

    @FXML
    private void setResizeOptions() {
        this.scrollPane.prefHeightProperty().bind(anchorPane.heightProperty());
        this.scrollPane.prefWidthProperty().bind(anchorPane.widthProperty());
    }

    public void setGridPane(final int numRows, final int numColumns) {
        //TODO settare il numero di righe e colonne della griglia.
    }

    public void addCanvasOnGrid() {
        //TODO Aggiungi canvas a ogni quadrato della griglia.
    }

    public void addListenersToButtons() {
        //TODO Aggiungi onClickListeners ai due bottoni.
     }

     public void setScrollPane() {
        //TODO configura lo ScrollPane.
     }

     public void updateLabelGenerations() {
        //TODO Aggiorna la Label delle generazioni.
     }
}
