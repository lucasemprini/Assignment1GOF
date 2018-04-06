package test;

import model.Matrix;
import model.MatrixImpl;
import model.utility.Chrono;
import model.utility.DebugUtility;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixImplTest {

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private Matrix matrix;

    private void setup() {
        this.matrix = new MatrixImpl(ROWS, COLUMNS);
    }

    @Test
    public void getNumRows() {
        this.setup();
        DebugUtility.printMatrix(matrix, 0);
        assertEquals(this.matrix.getNumRows(), ROWS);
    }

    @Test
    public void getNumColumns() {
        this.setup();
        DebugUtility.printMatrix(matrix, 0);
        assertEquals(this.matrix.getNumColumns(), COLUMNS);
    }

    @Test
    public void isOver() {
    }

    @Test
    public void update() {
    }

    @Test
    public void updateCell() {

    }

    @Test
    public void lifeTest() {
        this.setup();
        Chrono cron = new Chrono();
        cron.start();
        for(int i = 0; i < 10; i++) {
            this.matrix.update(0, ROWS, 0, COLUMNS);
            this.matrix.computeUpdate(0, ROWS, 0, COLUMNS);
            DebugUtility.printMatrix(matrix, i);
        }
        cron.stop();
        System.out.println("Time elapsed: "+cron.getTime()+" ms.");
    }
}