package view;

import controller.GameAgent;
import controller.concurrency.Game;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Matrix;

import java.util.Optional;

public class LayoutController {

    private static final int LINE_WIDTH = 1;
    private static final Color GRID_COLOR = Color.LIGHTGRAY;
    private static final Color ALIVE_COLOR = Color.BLACK;
    private static final Color DEAD_COLOR = Color.WHITE;
    private static final int MAX_CANVAS_SIZE = 8192;
    private static final int MIN_CELL_SIZE = LINE_WIDTH + 1;
    private static final int MAX_CELL_SIZE = 10;


    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Canvas canvas;
    @FXML
    public Button buttonStart;
    @FXML
    public Button buttonStop;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public BorderPane buttonPane;

    private boolean[][] myMatrix;
    private int cellSize = -1;
    private GameAgent agent;

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
    private static void fillCell(final GraphicsContext gc, final int row, final int column, final int cellSize, final Color color) {
        gc.setStroke(GRID_COLOR);
        gc.setFill(color);
        gc.fillRect(column, row, cellSize, cellSize);
        gc.strokeRect(column, row, cellSize, cellSize);
    }

    @FXML
    public void initialize() {
        this.buttonStop.setDisable(true);
        this.setResizeOptions();
        this.setScrollPane();
        this.addListenersToButtons();
    }

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
        });
        this.drawGrid(numRows, numColumns);
    }

    private void setCell(final int row, int column, boolean isAlive) {
        this.myMatrix[row][column] = isAlive;
        fillCell(this.canvas.getGraphicsContext2D(), row * cellSize,
                column * cellSize, this.cellSize,
                isAlive ? ALIVE_COLOR : DEAD_COLOR);
    }

    /**
     * Metodo per settare le preferenze di Resize dei vari pannelli.
     */
    @FXML
    private void setResizeOptions() {
    }

    /**
     * Metodo per settare le preferenze sullo ScrollPane
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
     * Metodo che setta i listener ai bottoni.
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

     private void setAgent(final GameAgent agent) {
        this.agent = agent;
     }

    /**
     * Draws the grid on the {@link Canvas}.
     * <p>
     * If not previously set, it will set the cell size
     *
     * @param rowsNumber    the number of row to draw
     * @param columnsNumber the number of columns to draw
     * @throws IllegalArgumentException if cell size can't be defined for specified rows and/or columns numbers
     */
    private void drawGrid(final int rowsNumber, final int columnsNumber) {
        if (this.cellSize == -1) {
            setUpCellSize(rowsNumber, columnsNumber);
        }

        final GraphicsContext gc = this.canvas.getGraphicsContext2D();

        final double maxWidth = this.canvas.getWidth();
        final double maxHeight = this.canvas.getHeight();
        gc.setLineWidth(1);
        gc.setStroke(GRID_COLOR);

        gc.setFill(DEAD_COLOR);
        gc.fillRect(0, 0, maxWidth, maxHeight);

        for (int r = 0; r * cellSize < maxHeight && r < rowsNumber; r++) {
            gc.strokeLine(0, r * cellSize, maxWidth, r * cellSize);
        }

        for (int c = 0; c * cellSize < maxHeight && c < columnsNumber; c++) {
            gc.strokeLine(c * cellSize, 0, c * cellSize, maxHeight);
        }
    }

    /**
     * Setup cell size defining the maximum square side (range: ) that fits
     * maximum canvas dimension of .
     *
     * @param rowsNumber    the number of rows to consider
     * @param columnsNumber the number of columns to consider
     * @throws IllegalArgumentException if cell size can't be defined for specified rows and/or columns numbers
     */
    private void setUpCellSize(final int rowsNumber, final int columnsNumber) {
        final Optional<Integer> proposedCellSize;

        if (rowsNumber > columnsNumber) {
            proposedCellSize = suggestCellSize(rowsNumber);
        } else {
            proposedCellSize = suggestCellSize(columnsNumber);
        }

        this.cellSize = proposedCellSize.orElseThrow(IllegalArgumentException::new);

        this.canvas.setHeight(getPixelsForDimension(rowsNumber, cellSize));
        this.canvas.setWidth(getPixelsForDimension(columnsNumber, cellSize));
    }
    /**
     * Checks if the dimension is acceptable, and returns an optional optimal cell size.
     * <p>
     * Minimum cell size is {@value MIN_CELL_SIZE} and maximum cell size is {@value MAX_CELL_SIZE}.
     * The dimension is not acceptable if it will use more than {@value MAX_CANVAS_SIZE} pixels.
     *
     * @param dimension the dimension to check
     * @return {@link Optional#empty()} if the dimension is not acceptable,
     * or an Optional of a cell size that will be acceptable
     */
    private Optional<Integer> suggestCellSize(final int dimension) {
        return this.suggestCellSize(dimension, MIN_CELL_SIZE);
    }

    /**
     * Checks if the dimension is acceptable, and returns an optional better cell size.
     * <p>
     * Maximum cell size is {@value MAX_CELL_SIZE}.
     * The dimension is not acceptable if it will use more than {@value MAX_CANVAS_SIZE} pixels.
     *
     * @param dimension       the dimension to check
     * @param minimumCellSize the proposed cell size
     * @return {@link Optional#empty()} if the dimension is not acceptable,
     * or an Optional of a bigger cell size that will be acceptable
     */
    private Optional<Integer> suggestCellSize(final int dimension, final int minimumCellSize) {
        if (getPixelsForDimension(dimension, minimumCellSize) > MAX_CANVAS_SIZE || minimumCellSize > MAX_CELL_SIZE) {
            return Optional.empty();
        } else {
            final Optional<Integer> checkDouble = suggestCellSize(dimension, minimumCellSize + 1);
            return checkDouble.isPresent() ? checkDouble : Optional.of(minimumCellSize);
        }
    }
}
