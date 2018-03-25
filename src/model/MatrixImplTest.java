package model;

import model.utility.Chrono;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixImplTest {

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private Matrix matrix;

    private void setup() {
        this.matrix = new MatrixImpl(ROWS, COLUMNS);
    }

    private void printGeneration(final int generation) {
        System.out.println("Generation: " + generation);
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                System.out.print(this.matrix.getCellAt(i, j).getCurrentState() + " ");
            }
            System.out.println("\n");
        }
    }

    @Test
    public void getNumRows() {
        this.setup();
        this.printGeneration(0);
        assertEquals(this.matrix.getNumRows(), ROWS);
    }

    @Test
    public void getNumColumns() {
        this.setup();
        this.printGeneration(0);
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
            this.matrix.update();
            this.matrix.computeUpdate();
            this.printGeneration(i);
        }
        cron.stop();
        System.out.println("Time elapsed: "+cron.getTime()+" ms.");
    }
}