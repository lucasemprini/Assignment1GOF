package model;
import java.util.Random;

public class MatrixImpl implements Matrix {

    private final int numRows;
    private final int numColumns;

    private final Cell[][] matrix;

    public MatrixImpl(final int numRows, final int numColumns) {
        this.numColumns = numColumns;
        this.numRows = numRows;
        this.matrix = new Cell[numRows][numColumns];
        this.generateRandomMatrix();
    }

    /**
     * Metodo da usare nel costruttore per generare una matrice di celle
     * inizializzata con valori Random.
     */
    private void generateRandomMatrix() {
        final Random r = new Random();
        for(int i=0; i<numRows; i++) {
            for(int j=0; j<numColumns; j++) {
                this.matrix[i][j] = new CellImpl(r.nextBoolean());
            }
        }
    }

    /**
     * Metodo che controlla che una coordinata x o y sia compresa nella matrice.
     * Da usare nel metodo che controlla i vicini.
     * @param x la coordinata x: riga o colonna.
     * @param isRow true se sto considerando le righe, false altimenti.
     * @return true se è compresa nella matrice, false altrimenti.
     */
    private boolean isInBound(final int x, final boolean isRow) {
        return isRow ? x >= 0 && x < numRows : x >= 0 && x < numColumns;
    }

    /**
     * Metodo per aggiornare una cella.
     * @param x la coordinata x.
     * @param y la coordinata y.
     */
    private void updateCell(int x, int y) {
        this.matrix[x][y].setNextState(RulesUtility.nextStatus(
                this.getNumNeighboursAlive(x, y),
                this.matrix[x][y].getCurrentState()));
    }

    /**
     * Metodo che restituisce il numero di vicini vivi per la cella specificata.
     * @param x la coordinata x della cella.
     * @param y la coordinata y della cella.
     * @return il numero dei vicini vivi compresa la cella stessa.
     */
    private int getNumNeighboursAlive(int x, int y) {
        int counterAlive = 0;
        for(int i = x - 1; i <= x + 1; i++) {
            if(isInBound(i, true)) {
                for(int j = y - 1; j <= y + 1; j++) {
                    if(isInBound(j, false)) {
                        if(matrix[i][j].getCurrentState()) {
                            counterAlive++;
                        }
                    }
                }
            }
        }
        return counterAlive;
    }

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumColumns() {
        return this.numColumns;
    }

    @Override
    public boolean isOver() {
        //TODO
        return false;
    }

    @Override
    public void update() {
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numColumns; j++) {
                this.updateCell(i, j);
            }
        }
    }

    @Override
    public Cell getCellAt(int x, int y) {
        return this.matrix[x][y];
    }


    @Override
    public void computeUpdate() {
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numColumns; j++) {
                matrix[i][j].updateState();
            }
        }
    }
}
