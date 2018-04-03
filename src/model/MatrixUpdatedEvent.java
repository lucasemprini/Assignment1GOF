package model;

public class MatrixUpdatedEvent implements MatrixEvent {

    private Matrix m;

    public MatrixUpdatedEvent(final Matrix m) {
        this.m = m;
    }

    @Override
    public boolean[][] matrixUpdate() {
        final boolean[][] matrixToReturn = new boolean [m.getNumRows()][m.getNumColumns()];
        for(int i = 0; i < m.getNumRows(); i++) {
            for(int j = 0; j < m.getNumColumns(); j++) {
                matrixToReturn[i][j] = m.getCellAt(i, j);
            }
        }
        return matrixToReturn;
    }
}
