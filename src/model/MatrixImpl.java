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

    private void generateRandomMatrix() {
        for(int i=0; i<numRows; i++) {
            for(int j=0; j<numColumns; j++) {
                this.matrix[i][j] = new CellImpl(new Random().nextBoolean());
            }
        }
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
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public Cell getCell(int x, int y) {
        return this.matrix[x][y];
    }
}
