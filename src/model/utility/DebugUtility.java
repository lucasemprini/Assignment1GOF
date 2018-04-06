package model.utility;

import model.Matrix;

/**
 * Utility class utile in fase di Test.
 */
public final class DebugUtility {

    private DebugUtility() {}

    /**
     * Metodo di Debug che stampa una Matrice passata in input.
     * @param myMatrix la Matrix da stampare in standard output.
     * @param gen il numero di generazioni trascorse.
     */
    public static void printMatrix(final Matrix myMatrix, final int gen) {
        System.out.println("Generation: " + gen);
        for(int i = 0; i < myMatrix.getNumRows(); i++) {
            for(int j = 0; j < myMatrix.getNumColumns(); j++) {
                System.out.print(myMatrix.getCellAt(i, j) + " ");
            }
            System.out.println("\n");
        }
    }

    /**
     * Metodo di debug che stampa una Stringa dati i parametri.
     * @param m una Matrice su cui si stanno eseguendo Test.
     * @param numThreads il numero di Thread con cui si sta eseguendo il Test.
     */
    public static void printMatrixDimension(final Matrix m, final int numThreads) {
        System.out.println("TESTING GAME MATRIX " +
                m.getNumRows() + " X " + m.getNumColumns() +
                " ON " + numThreads +
                (numThreads > 1 ? " THREADS.": " THREAD.") + "\n");
    }

    /**
     * Metodo di debug che stampa una Stringa dati i parametri.
     * @param gen il numero di generazioni trascorse dall'inizio del gioco.
     * @param time il tempo trascorso dato dalla classe Chrono.
     * @param aliveCells le celle correntemente vive in quella generazione.
     */
    public static void printOnlyGeneration(final int gen, final long time, final long aliveCells) {
        System.out.println("Generation: " + gen +
                " with " + aliveCells + " alive cells." +
                "     " + time + " millis. ");
    }
}
