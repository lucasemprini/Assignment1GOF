package model;

public class MatrixUpdatedEvent implements MatrixEvent {

    private Matrix m;
    private long liveCells;

    public MatrixUpdatedEvent(final Matrix m) {
        this.m = m;
        this.liveCells = 0;
    }

    @Override
    public boolean[][] matrixUpdate() {
        final boolean[][] matrixToReturn = new boolean [m.getNumRows()][m.getNumColumns()];
        for(int i = 0; i < m.getNumRows(); i++) {
            for(int j = 0; j < m.getNumColumns(); j++) {
                matrixToReturn[i][j] = m.getCellAt(i, j);
                if(matrixToReturn[i][j]) {
                    this.liveCells++;
                }
            }
        }
        return matrixToReturn;
    }

    @Override
    public long getLiveCells() {
        return this.liveCells;
    }
}
