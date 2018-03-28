package model;

import model.utility.RulesUtility;

import java.util.Random;

public class SmoothMatrix implements Matrix {

    private int numRows;
    private int numColumns;

    private final boolean[][] matrix;

    public SmoothMatrix(final int numRows, final int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.matrix  = new boolean[numRows][numColumns];
        this.generateRandomMatrix();
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
        this.matrix[x][y] = RulesUtility.nextStatus(
                this.getNumNeighboursAlive(x, y),
                this.matrix[x][y]);
        //TODO
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
                        if(matrix[i][j]) {
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
    public void generateRandomMatrix() {
        final Random r = new Random();
        for(int i=0; i<numRows; i++) {
            for(int j=0; j<numColumns; j++) {
                this.matrix[i][j] = r.nextBoolean();
            }
        }
    }

    @Override
    public boolean isOver() {
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
    public boolean getCellAt(int x, int y) {
        return matrix[x][y];
    }

    @Override
    public void computeUpdate() {

    }
}
