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
import model.SmoothMatrix;
import model.utility.DebugUtility;
import view.GameCell;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

    private static final int NUMCOLUMNS = 100;
    private static final int NUMROWS = 100;
    private static final int CELL_DIMENSION = 10;
    private static final String LABEL_INTRO = "Generations: ";
    private boolean isRunning = false;
    private int numGenerations = 0;

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
        //TODO this.myMatrix = new SmoothMatrix(NUMROWS, NUMCOLUMNS);
    }

    public void setDimensions(final int numRows, final int numColumns) {
        this.myMatrix.setNumRows(numRows);
        this.myMatrix.setNumColumns(numColumns);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setResizeOptions();
        this.addCellsToGridPane();
        this.setScrollPane();
        this.addListenersToButtons();
        this.updateLabelGenerations();
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
     */
    private void addCellsToGridPane() {
        for (int row = 0; row < myMatrix.getNumRows(); row++) {
            for (int col = 0; col < myMatrix.getNumColumns(); col++) {
                GameCell cell = new GameCell(CELL_DIMENSION, CELL_DIMENSION,
                        myMatrix.getCellAt(row, col));
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

    /**
     * Metodo che setta i listener ai bottoni.
     */
    private void addListenersToButtons() {
        this.buttonStart.setOnAction( e -> {
            this.isRunning = true;
            //TODO FA MERDA.
            this.myMatrix.update();
            this.myMatrix.computeUpdate();
            //DebugUtility.printMatrix(myMatrix, numGenerations);
            this.updateGridPane();
            this.numGenerations++;
            this.updateLabelGenerations();
        });

        this.buttonStop.setOnAction( e -> {
            this.isRunning = false;
            //TODO
        });
     }

    /**
     * Metodo INEFFICENTE, utile solo ai fini di test.
     */
    private void updateGridPane() {
        for (int row = 0; row < NUMROWS; row++) {
            for (int col = 0; col < NUMCOLUMNS; col++) {
                GameCell cell = new GameCell(CELL_DIMENSION, CELL_DIMENSION,
                        myMatrix.getCellAt(row, col));
                gameGrid.add(cell, col, row);
            }
        }
     }

    /**
     * Metodo che aggiorna la label che tiene il conto delle generazioni trascorse.
     */
    private void updateLabelGenerations() {
        this.generationsLabel.setText(LABEL_INTRO + this.numGenerations);
    }
}
