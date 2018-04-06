package view;

import controller.GameAgent;
import controller.concurrency.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Matrix;

import java.util.Optional;

public class MainLayoutController {

    private static final int LINE_WIDTH = 1;
    private static final Color GRID_COLOR = Color.LIGHTGRAY;
    private static final Color ALIVE_COLOR = Color.BLACK;
    private static final Color DEAD_COLOR = Color.WHITE;
    private static final int MAX_CANVAS_SIZE = 8192;
    private static final int MIN_CELL_SIZE = LINE_WIDTH + 1;
    private static final int MAX_CELL_SIZE = 10;

    private static final String LABEL_LIVE_CELLS = "    Live Cells: ";

    public ScrollPane scrollPane;
    public Canvas canvas;
    public Button buttonStart;
    public Button buttonStop;
    public AnchorPane anchorPane;
    public BorderPane buttonPane;
    public Label labelLiveCells;

    private boolean[][] myMatrix;
    private int cellSize = -1;
    private long aliveCells = 0;
    private GameAgent agent;

    @FXML
    public void initialize() {
        this.buttonStop.setDisable(true);
        this.setScrollPane();
        this.addListenersToButtons();
    }

    /**
     * Metodo per settare il Game all'interno della view
     * @param game il modello di Game passato dal Main.
     */
    public void setModel(final Game game) {
        this.setAgent(new GameAgent(game));
        final Matrix matrix = game.getMatrix();
        final int numRows = matrix.getNumRows();
        final int numColumns = matrix.getNumColumns();
        this.myMatrix = new boolean[numRows][numColumns];

        game.addListener(ev -> {
            final boolean[][] newMatrix = ev.matrixUpdate();
            for(int i = 0; i < numRows; i++) {
                for(int j = 0; j < numColumns; j++) {
                    boolean newCellValue = newMatrix[i][j];
                    if(myMatrix[i][j] != newCellValue) {
                        this.setCell(i, j, newCellValue);
                    }
                }
            }
            this.setLabelLiveCells(ev.getLiveCells());
        });
        this.drawGrid(numRows, numColumns);
    }

    /**
     * Metodo che setta con un valore specificato una cella della griglia di gioco
     * nel Canvas.
     * @param x la riga della cella.
     * @param y la colonna della cella.
     * @param newState lo stato da settare.
     */
    private void setCell(final int x, int y, boolean newState) {
        this.myMatrix[x][y] = newState;
        fillCell(this.canvas.getGraphicsContext2D(), x * cellSize,
                y * cellSize, this.cellSize,
                newState ? ALIVE_COLOR : DEAD_COLOR);
    }

    /**
     * Metodo per settare le preferenze di layout sullo ScrollPane.
     */
    @FXML
    private void setScrollPane() {
        this.scrollPane.setContent(this.canvas);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    /**
     * Metodo che setta i listener ai bottoni e li abilita e disabilita
     * a seconda di quale venga premuto.
     */
    private void addListenersToButtons() {
        this.buttonStart.setOnAction( e -> {
            agent.start();
            buttonStart.setDisable(true);
            buttonStop.setDisable(false);
        });

        this.buttonStop.setOnAction( e -> {
            agent.notifyStop();
            buttonStart.setDisable(false);
            buttonStop.setDisable(true);
            this.setAgent(new GameAgent(agent.getGame()));
        });
    }

    /**
     * Metodo per settare il campo agent.
     * @param agent il GameAgent del gioco.
     */
    private void setAgent(final GameAgent agent) {
        this.agent = agent;
    }

    /**
     * Metodo che ritorna la dimensione in pixel dei separatori di linea.
     * @param dimension il numero di celle in una dimensione.
     * @return la lunghezza in pixel
     */
    private static int getPixelsForDimensionSeparators(final int dimension) {
        return (dimension - 2 + LINE_WIDTH);
    }

    /**
     * Metodo che ritorna il numero di pixel usati per disegnare un dato
     * numero di celle di una data dimensione.
     * @param dimension il numero di celle in una dimensione.
     * @param cellSize  la dimensione delle celle.
     * @return il numero di pixel usati.
     */
    private static int getPixelsForDimension(final int dimension, final int cellSize) {
        return dimension * cellSize + getPixelsForDimensionSeparators(dimension);
    }

    /**
     * Metodo che disegna le celle dato un GraphicContext specifico e i vari parametri.
     * @param gc il GraphicContext su cui disegnare la cella.
     * @param row la riga della cella.
     * @param column la colonna della cella.
     * @param cellSize la dimensione della cella.
     * @param color il colore della cella.
     */
    private static void fillCell(final GraphicsContext gc, final int row, final int column,
                                 final int cellSize, final Color color) {
        gc.setStroke(GRID_COLOR);
        gc.setFill(color);
        gc.fillRect(column, row, cellSize, cellSize);
        gc.strokeRect(column, row, cellSize, cellSize);
    }

    /**
     * Disegna la griglia di gioco sul Canvas.
     * @param numRows    il numero delle righe.
     * @param numColumns il numero delle colonne.
     * @throws IllegalArgumentException se la dimensione delle celle non risulta identificabile
     * per il dato numero di righe e colonne.
     */
    private void drawGrid(final int numRows, final int numColumns) {

        //Se la dimensione delle celle non è ancora stata definita, chiama setCellSize
        if (this.cellSize == -1) {
            setCellSize(numRows, numColumns);
        }

        final GraphicsContext gc = this.canvas.getGraphicsContext2D();

        final double maxWidth = this.canvas.getWidth();
        final double maxHeight = this.canvas.getHeight();
        gc.setLineWidth(LINE_WIDTH);
        gc.setStroke(GRID_COLOR);

        gc.setFill(DEAD_COLOR);
        gc.fillRect(0, 0, maxWidth, maxHeight);

        for (int r = 0; r * cellSize < maxHeight && r < numRows; r++) {
            gc.strokeLine(0, r * cellSize, maxWidth, r * cellSize);
        }

        for (int c = 0; c * cellSize < maxHeight && c < numColumns; c++) {
            gc.strokeLine(c * cellSize, 0, c * cellSize, maxHeight);
        }

    }

    /**
     * Metodo per settare la dimensione delle celle in base alla dimensione del canvas,
     * il numero delle righe e il numero delle colonne.
     * @param numRows    il numero delle righe.
     * @param numColumns il numero delle colonne.
     * @throws IllegalArgumentException se la dimensione delle celle non risulta identificabile
     * per il dato numero di righe e colonne.
     */
    private void setCellSize(final int numRows, final int numColumns) {
        final Optional<Integer> proposedCellSize;

        if (numRows > numColumns) {
            proposedCellSize = suggestCellSize(numRows);
        } else {
            proposedCellSize = suggestCellSize(numColumns);
        }

        this.cellSize = proposedCellSize.orElseThrow(IllegalArgumentException::new);

        this.canvas.setHeight(getPixelsForDimension(numRows, cellSize));
        this.canvas.setWidth(getPixelsForDimension(numColumns, cellSize));
    }
    /**
     * Metodo collegato all'omonimo, ma con diversa signature: controlla se la dimensione proposta
     * per le celle risulta accettabile o meno.
     *
     * @param dimension la dimensione della cella da controllare
     * @return un Optional: vuoto se la dimensione non è accettabile, altrimenti
     * contenente il valore della dimensione suggerita
     */
    private Optional<Integer> suggestCellSize(final int dimension) {
        return this.suggestCellSize(dimension, MIN_CELL_SIZE);
    }

    /**
     * Metodo che suggerisce una dimensione accettabile per una cella: la dimensione massima
     * è {@value MAX_CELL_SIZE}. La dimensione non verrà accettata se sarà maggiore del
     * massimo valore in pixel del Canvas.
     * @param dimension la dimensione da controllare.
     * @param minimumCellSize la dimensione della cella proposta.
     * @return un Optional: vuoto se la dimensione non è accettabile, altrimenti
     * contenente il valore della dimensione suggerita.
     */
    private Optional<Integer> suggestCellSize(final int dimension, final int minimumCellSize) {
        if (getPixelsForDimension(dimension, minimumCellSize) > MAX_CANVAS_SIZE || minimumCellSize > MAX_CELL_SIZE) {
            return Optional.empty();
        } else {
            final Optional<Integer> checkDouble = suggestCellSize(dimension, minimumCellSize + 1);
            return checkDouble.isPresent() ? checkDouble : Optional.of(minimumCellSize);
        }
    }

    /**
     * Metodo che setta la Label che indica il numero di celle vive.
     * @param liveCells il numero di celle vive.
     */
    private void setLabelLiveCells(final long liveCells) {
        if(aliveCells != liveCells) {
            this.aliveCells = liveCells;
            Platform.runLater(() -> this.labelLiveCells.setText(LABEL_LIVE_CELLS + liveCells));
        }
    }
}
