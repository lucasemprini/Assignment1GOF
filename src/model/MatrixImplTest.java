package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixImplTest {

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private Matrix matrix;

    private void setup() {
        this.matrix = new MatrixImpl(ROWS, COLUMNS);
        this.printGeneration(0);
    }

    private void printGeneration(final int generation) {
        System.out.println("Generation: " + generation);
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                System.out.print(this.matrix.getCell(i, j).getCurrentState() + " ");
            }
            System.out.println("\n");
        }
    }

    @Test
    public void getNumRows() {
        this.setup();
        assertEquals(this.matrix.getNumRows(), ROWS);
    }

    @Test
    public void getNumColumns() {
        this.setup();
        assertEquals(this.matrix.getNumColumns(), COLUMNS);
    }

    @Test
    public void isOver() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getCell() {
    }

    @Test
    public void getNumNeighboursAlive() {
        this.setup();
        System.out.println(this.matrix.getNumNeighboursAlive(0, 0));
    }

    @Test
    public void updateCell() {

    }

    @Test
    public void lifeTest() {
        this.setup();
        for(int i = 0; i < 10; i++) {
            this.matrix.update();
            this.matrix.computeUpdate();
            this.printGeneration(i);
        }
    }
}