package controller;

import controller.concurrency.Game;
import model.Matrix;
import model.utility.Chrono;
import model.utility.DebugUtility;
import org.junit.Test;

import javax.jws.Oneway;

import static org.junit.Assert.*;

public class GameAgentTest {

    private final Chrono chronometer = new Chrono();
    private static final int ONE_THREAD = 1;
    private static final int FOUR_THREAD = 4;
    private static final int VERY_SIMPLE_DIM = 50;
    private static final int SIMPLE_DIM = 200;
    private static final int MEDIUM_DIM = 1000;
    private static final int HARD_DIM = 5000;

    private static final String TIME_STRING = "Time elapsed (in millis): ";

    private static final int NUM_GENERATION_TEST = 20;

    private final Game singleThreadMediumGame = new Game(ONE_THREAD, MEDIUM_DIM, MEDIUM_DIM);
    //private final Game singleThreadHardGame = new Game(ONE_THREAD, HARD_DIM, HARD_DIM);
    private final Game multiThreadMediumGame = new Game(FOUR_THREAD, MEDIUM_DIM, MEDIUM_DIM);
    //private final Game multiThreadHardGame = new Game(FOUR_THREAD, HARD_DIM, HARD_DIM);

    private int totGenerations = 0;

    private void addListenerToGame(final Game game, final int dimension) {

        boolean[][] myMatrix = new boolean[dimension][dimension];
        game.addListener(ev -> {
            final boolean[][] newMatrix = ev.matrixUpdate();
            for(int i = 0; i < SIMPLE_DIM; i++) {
                for(int j = 0; j < SIMPLE_DIM; j++) {
                    boolean newCellValue = newMatrix[i][j];
                    if(myMatrix[i][j] != newCellValue) {
                        myMatrix[i][j] = newCellValue;
                    }
                }
            }
        });
    }

    private void runWhat(final int numThreads, final int dimension) {
        final Game game = new Game(numThreads, dimension, dimension);
        this.addListenerToGame(game, dimension);
        this.chronometer.start();
        for(int i = 0; i < NUM_GENERATION_TEST; i++) {
            game.playGame();
            try {
                Thread.sleep(10);
            } catch(Exception ex){
                ex.printStackTrace();
            }
            DebugUtility.printOnlyGeneration(i);
        }
        this.chronometer.stop();
        System.out.println(TIME_STRING + this.chronometer.getTime());
    }

    @Test
    public void runSimpleSingle() {
        this.runWhat(ONE_THREAD, SIMPLE_DIM);
    }

    @Test
    public void runSimpleMulti() {
        this.runWhat(FOUR_THREAD, SIMPLE_DIM);
    }

    @Test
    public void runMediumSingle() {
        this.runWhat(ONE_THREAD, MEDIUM_DIM);
    }

    @Test
    public void runMediumMulti() {
        this.runWhat(FOUR_THREAD, MEDIUM_DIM);
    }

    @Test
    public void runhardSingle() {
        this.runWhat(ONE_THREAD, HARD_DIM);
    }

    @Test
    public void runhardMulti() {
        this.runWhat(FOUR_THREAD, HARD_DIM);
    }

    @Test
    public void getGame() {
    }

    @Test
    public void notifyStop() {
    }

    @Test
    public void getNumGenerations() {
    }
}