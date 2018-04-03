package controller.concurrency;

import controller.SemaphoreManager;
import controller.SemaphoreManagerImpl;
import model.*;
import model.utility.Chrono;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final boolean DEBUG = true;
    private final Chrono chronometer = new Chrono();
    private final int nCores;
    private Matrix matrix;
    private final SemaphoreManager computeSemaphore;
    private final SemaphoreManager updateSemaphore;
    private final List<MatrixEventListener> listenersList = new ArrayList<>();

    /**
     * Metodo che permette, dati i constraints, di creare un GameThread per gestire
     * una certa porzione della Matrix.
     * @param id id intero ai fini di DEBUG.
     * @param startRow la riga di partenza.
     * @param stopRow la riga di fine.
     * @param startColumn la colonna di partenza.
     * @param stopColumn la colonna di fine.
     */
    private void createThreadAndStart(final int id, final int startRow, final int stopRow,
                                      final int startColumn, final int stopColumn) {
        if(DEBUG) {
            GameThread c = new GameThread(id, startRow, stopRow, startColumn, stopColumn,
                    this.matrix, this.computeSemaphore, this.updateSemaphore);
            c.start();
        } else {
            GameThread c = new GameThread(startRow, stopRow, startColumn, stopColumn,
                    this.matrix, this.computeSemaphore, this.updateSemaphore);
            c.start();
        }
    }

    /**
     * Metodo che permette di creare un numero di Thread coerente con
     * il numero di processori della macchina su cui l'applicazione sta girando.
     */
    private void threadsSetup() {
        final int blockRows = this.matrix.getNumRows() / this.nCores;
        int stepRows = 0;
        int i;
        for(i = 0; i < this.nCores - 1; i++) {
            this.createThreadAndStart(i, stepRows, blockRows + stepRows,
                    0, this.matrix.getNumColumns());
            stepRows += blockRows;
        }

        this.createThreadAndStart(i, stepRows, this.matrix.getNumRows(),
                0, this.matrix.getNumColumns());
    }

    /**
     * Crea un nuovo Event e lo comunica alla lista dei listeners.
     */
    private void notifyMatrixUpdatedEvent() {
        final MatrixEvent event = new MatrixUpdatedEvent(this.matrix);
        for(MatrixEventListener l : this.listenersList) {
            l.matrixUpdated(event);
        }
    }

    /**
     * Costruttore principale per la classe Game.
     * @param nCores il numero di Core della macchina su cui sta girando l'applicazione.
     * @param numRows il numero di righe della matrice.
     * @param numColumns il numero di colonne della matrice.
     */
    public Game(final int nCores, final int numRows, final int numColumns) {
        this.nCores = nCores;
        this.matrix = new MatrixImpl(numRows, numColumns);
        this.computeSemaphore = new SemaphoreManagerImpl(this.nCores);
        this.updateSemaphore = new SemaphoreManagerImpl(this.nCores);
    }

    /**
     * Aggiunge un nuovo Listener per l'aggiornamento della matrice di gioco.
     * @param l il Listener da aggiungere.
     */
    public void addListener(final MatrixEventListener l) {
        this.listenersList.add(l);
    }

    /**
     * Setta i Semafori e chiama threadsSetup
     */
    public void setupSemaphores() throws InterruptedException {
        this.computeSemaphore.waitAllWorkers();
        this.updateSemaphore.waitAllWorkers();
        this.computeSemaphore.waitAllManager();
        this.updateSemaphore.waitAllManager();
        this.threadsSetup();
    }

    public void playGame() {
        try {
            this.chronometer.start();
            if(DEBUG) {
                System.out.println("Manager rilascia tutte le compute");
            }
            computeSemaphore.releaseAllWorkers();
            computeSemaphore.waitAllManager();
            if(DEBUG) {
                System.out.println("Manager ha ricevuto tutti i segnali di compute");
            }
            computeSemaphore.waitAllWorkers();
            if(DEBUG) {
                System.out.println("Manager rilascia tutte le update");
            }
            updateSemaphore.releaseAllWorkers();
            updateSemaphore.waitAllManager();
            if(DEBUG) {
                System.out.println("Manager ha ricevuto tutti i segnali di update");
            }
            updateSemaphore.waitAllWorkers();
            if(DEBUG) {
                System.out.println(this.chronometer.getTime());
            }
            this.notifyMatrixUpdatedEvent();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}
