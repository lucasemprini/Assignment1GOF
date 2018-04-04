package controller;

import controller.concurrency.Game;
import model.utility.Chrono;
import model.utility.DebugUtility;
import org.junit.Test;

public class GameAgentTest {

    private final Chrono chronometer = new Chrono();

    private static final int ONE_THREAD = 1;
    private static final int FOUR_THREAD = 4;
    private static final int FIVE_THREAD = 5;
    private static final int TEN_THREAD = 10;
    private static final int SIMPLE_DIM = 200;
    private static final int MEDIUM_DIM = 1000;
    private static final int NOT_SO_HARD_DIM = 3000;
    private static final int HARD_DIM = 5000;

    private static final int NUM_GENERATION_TEST = 20;

    private static final String TIME_STRING = "Time elapsed (in millis): ";

    /**
     * Metodo per aggiungere Listeners al Model di Game
     * @param game il Game model
     * @param dimension la dimensione della matrice.
     */
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

    /**
     * Metodo utile per capire quale tipo di game lanciare: inizializza il Game,
     * lo lancia facendo stampe in standard output che monitorano il tempo
     * trascorso per ogni generazione e il tempo totale dopo
     * le {@value NUM_GENERATION_TEST} generazioni.
     * @param numThreads il numero di Thread con cui lanciare il Game.
     * @param dimension la dimensione della matrice di quel Game.
     */
    private void runWhat(final int numThreads, final int dimension) {
        final Game game = new Game(numThreads, dimension, dimension);
        DebugUtility.printMatrixDimension(game.getMatrix(), numThreads);
        long totalTime = 0;
        try {
            game.setInDebugMode(false);
            game.setupSemaphores();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.addListenerToGame(game, dimension);
        for(int i = 0; i < NUM_GENERATION_TEST; i++) {
            this.chronometer.start();
            game.playGame();
            try {
                Thread.sleep(10);
            } catch(Exception ex){
                ex.printStackTrace();
            }
            totalTime += this.chronometer.getTime();
            DebugUtility.printOnlyGeneration(i + 1, this.chronometer.getTime());
            this.chronometer.stop();
        }
        System.out.println(TIME_STRING + totalTime);
    }

    @Test
    public void runSimpleSingle() {
        this.runWhat(ONE_THREAD, SIMPLE_DIM);
    }
    @Test
    public void runSimpleMultiFour() {
        this.runWhat(FOUR_THREAD, SIMPLE_DIM);
    }
    @Test
    public void runSimpleMultiFive() {
        this.runWhat(FIVE_THREAD, SIMPLE_DIM);
    }
    @Test
    public void runSimpleMultiTen() {
        this.runWhat(TEN_THREAD, SIMPLE_DIM);
    }

    @Test
    public void runMediumSingle() {
        this.runWhat(ONE_THREAD, MEDIUM_DIM);
    }
    @Test
    public void runMediumMultiFour() {
        this.runWhat(FOUR_THREAD, SIMPLE_DIM);
    }
    @Test
    public void runMediumMultiFive() {
        this.runWhat(FIVE_THREAD, MEDIUM_DIM);
    }
    @Test
    public void runMediumMultiTen() {
        this.runWhat(TEN_THREAD, MEDIUM_DIM);
    }

    @Test
    public void runNotSoHardSingle() {
        this.runWhat(ONE_THREAD, NOT_SO_HARD_DIM);
    }
    @Test
    public void runNotSoHardMultiFour() {
        this.runWhat(FOUR_THREAD, SIMPLE_DIM);
    }
    @Test
    public void runNotSoHardMultiFive() {
        this.runWhat(FIVE_THREAD, NOT_SO_HARD_DIM);
    }
    @Test
    public void runNotSoHardMultiTen() {
        this.runWhat(TEN_THREAD, NOT_SO_HARD_DIM);
    }

    @Test
    public void runhardSingle() {
        this.runWhat(ONE_THREAD, HARD_DIM);
    }
    @Test
    public void runHardMultiFour() {
        this.runWhat(FOUR_THREAD, SIMPLE_DIM);
    }
    @Test
    public void runhardMultiFive() {
        this.runWhat(FIVE_THREAD, HARD_DIM);
    }
    @Test
    public void runhardMultiTen() {
        this.runWhat(TEN_THREAD, HARD_DIM);
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