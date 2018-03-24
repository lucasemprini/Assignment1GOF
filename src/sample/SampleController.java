package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import model.Matrix;
import model.MatrixImpl;
import view.GameCell;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

    private static final int NUMCOLUMNS = 100;
    private static final int NUMROWS = 100;
    private static final int CELL_DIMENSION = 10;

    private final Matrix myMatrix;

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
    @FXML
    public BorderPane buttonPane;

    public SampleController() {
        this.myMatrix = new MatrixImpl(NUMROWS, NUMCOLUMNS);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setResizeOptions();
        this.addCellsToGridPane(NUMROWS, NUMCOLUMNS);
        this.setScrollPane();
    }

    /**
     * Metodo per settare le preferenze di Resize dei vari pannelli.
     */
    @FXML
    private void setResizeOptions() {
        this.scrollPane.prefHeightProperty().bind(anchorPane.heightProperty());
        this.scrollPane.prefWidthProperty().bind(anchorPane.widthProperty());
        this.buttonPane.prefWidthProperty().bind(anchorPane.widthProperty());
    }

    /**
     * Metodo utilizzato per costruire le celle della griglia di gioco.
     * @param numRows il numero di righe.
     * @param numColumns il numero di colonne.
     */
    private void addCellsToGridPane(final int numRows, final int numColumns) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                GameCell cell = new GameCell(CELL_DIMENSION, CELL_DIMENSION,
                        myMatrix.getCellAt(row, col).getCurrentState());
                gameGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Metodo per settare le preferenze sullo ScrollPane
     */
    @FXML
    private void setScrollPane() {
        this.scrollPane.setContent(this.gameGrid);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    /*SERVE???? Probabilmente no.*/
    private void setGridConstraints(final int numRows, final int numColumns) {
        ColumnConstraints colConst = new ColumnConstraints();
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(100.0 / numRows);
        colConst.setPercentWidth(100.0 / numColumns);
        for (int i = 0; i < numColumns; i++) {
            gameGrid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            gameGrid.getRowConstraints().add(rowConst);
        }
    }

    public void addCanvasOnGrid() {
        //TODO Aggiungi canvas a ogni quadrato della griglia.
    }

    public void addListenersToButtons() {
        //TODO Aggiungi onClickListeners ai due bottoni.
     }

     public void updateLabelGenerations() {
        //TODO Aggiorna la Label delle generazioni.
     }

}
