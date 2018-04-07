package controller.concurrency;

import controller.SemaphoreManager;
import model.Matrix;
import model.utility.RulesUtility;

public class GameThread extends Thread {

    private final SemaphoreManager justUpdateSemaphore;
    private final SemaphoreManager computeUpdateSemaphore;

    private final int startRow;
    private final int stopRow;
    private final int startColumn;
    private final int stopColumn;
    private final Matrix gameMatrix;

    //Campi utili ai fini di Debug.
    private int id;
    private static boolean DEBUG = true;

    /**
     * Costruttore principale della classe GameThread.
     * @param startRow la riga di partenza.
     * @param stopRow la riga di fine.
     * @param startColumn la colonna di partenza.
     * @param stopColumn la colonna di fine.
     * @param m la matrice su cui il Thread deve lavorare.
     * @param computeSemaphore un semaforo che regola il calcolo del prossimo stato della matrice.
     * @param updateSemaphore il semaforo che regola l'aggiornamento della matrice di gioco.
     */
    GameThread(final int startRow, final int stopRow, final int startColumn, final int stopColumn,
               final Matrix m, final SemaphoreManager computeSemaphore, final SemaphoreManager updateSemaphore) {
        DEBUG = false;
        this.startColumn = startColumn;
        this.stopColumn = stopColumn;
        this.startRow = startRow;
        this.stopRow = stopRow;
        this.gameMatrix = m;
        this.justUpdateSemaphore = computeSemaphore;
        this.computeUpdateSemaphore = updateSemaphore;
    }

    /**
     * Costruttore da utilizzare nella fase di DEBUG: setta automaticamente
     * a TRUE la variabile statica DEBUG.
     * @param id identificativo del Thread, utile ai fini di Debug.
     * @param startRow la riga di partenza.
     * @param stopRow la riga di fine.
     * @param startColumn la colonna di partenza.
     * @param stopColumn la colonna di fine.
     * @param m la matrice su cui il Thread deve lavorare.
     * @param computeSemaphore un semaforo che regola il calcolo del prossimo stato della matrice.
     * @param updateSemaphore il semaforo che regola l'aggiornamento della matrice di gioco.
     */
    GameThread(final int id, final int startRow, final int stopRow, final int startColumn, final int stopColumn,
               final Matrix m, final SemaphoreManager computeSemaphore, final SemaphoreManager updateSemaphore) {
        this.id = id;
        DEBUG = true;
        this.startColumn = startColumn;
        this.stopColumn = stopColumn;
        this.startRow = startRow;
        this.stopRow = stopRow;
        this.gameMatrix = m;
        this.justUpdateSemaphore = computeSemaphore;
        this.computeUpdateSemaphore = updateSemaphore;
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.justUpdateSemaphore.waitForWorker();
                if(DEBUG) {
                    System.out.println("Worker "+ id +" entra nella fase di calcolo");
                }
                RulesUtility.computeLifeInGivenMatrix(startRow, stopRow, startColumn, stopColumn, gameMatrix);
                if(DEBUG) {
                    System.out.println("Worker esce "+ id +" dalla fase di calcolo");
                }
                this.justUpdateSemaphore.releaseWorker();
                this.justUpdateSemaphore.releaseManager();
                this.computeUpdateSemaphore.waitForWorker();
                if(DEBUG) {
                    System.out.println("Worker "+ id +" entra in fase di aggiornamento");
                }
                RulesUtility.updateLifeInGivenMatrix(startRow, stopRow, startColumn, stopColumn, gameMatrix);
                if(DEBUG) {
                    System.out.println("Worker esce "+ id +" dalla fase di aggiornamento");
                }
                this.computeUpdateSemaphore.releaseWorker();
                this.computeUpdateSemaphore.releaseManager();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
