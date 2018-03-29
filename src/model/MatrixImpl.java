package model;
import model.utility.RulesUtility;

import java.util.Random;

public class MatrixImpl implements Matrix {

    private int numRows;
    private int numColumns;

    private final Cell[][] matrix;

    public MatrixImpl(final int numRows, final int numColumns) {
        this.numColumns = numColumns;
        this.numRows = numRows;
        this.matrix = new Cell[numRows][numColumns];
        this.generateRandomMatrix();
    }

    @Override
    public void generateRandomMatrix() {
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
     * Metodo per computare l'aggiornamento di una certa cella.
     * @param x la coordinata x.
     * @param y la coordinata y
     */
    private void computeCellUpdate(final int x, final int y) {
        this.matrix[x][y].updateState();
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
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    @Override
    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    @Override
    public boolean isOver() {
        //TODO
        return false;
    }

    @Override
    public void update(final int startRow, final int stopRow,
                       final int startColumn, final int stopColumn) {
        for(int i = startRow; i < stopRow; i++) {
            for(int j = startColumn; j < stopColumn; j++) {
                this.updateCell(i, j);
            }
        }
    }

    @Override
    public boolean getCellAt(int x, int y) {
        return this.matrix[x][y].getCurrentState();
    }


    @Override
    public void computeUpdate(final int startRow, final int stopRow,
                              final int startColumn, final int stopColumn) {
        for(int i = startRow; i < stopRow; i++) {
            for(int j = startColumn; j < stopColumn; j++) {
                this.computeCellUpdate(i, j);
            }
        }
    }
}
