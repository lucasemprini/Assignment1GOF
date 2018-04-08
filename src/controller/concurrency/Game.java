package controller.concurrency;

import controller.SemaphoreManager;
import controller.SemaphoreManagerImpl;
import model.*;
import model.utility.Chrono;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private boolean isInDebugMode = true;
    private final Chrono chronometer = new Chrono();
    private final int numCores;
    private Matrix matrix;
    private final SemaphoreManager justUpdateSemaphore;
    private final SemaphoreManager computeUpdateSemaphore;
    private final List<MatrixEventListener> listenersList = new ArrayList<>();

    /**
     * Costruttore principale per la classe Game.
     * @param nCores il numero di Core della macchina su cui sta girando l'applicazione.
     * @param numRows il numero di righe della matrice.
     * @param numColumns il numero di colonne della matrice.
     */
    public Game(final int nCores, final int numRows, final int numColumns) {
        this.numCores = nCores;
        this.matrix = new MatrixImpl(numRows, numColumns);
        this.justUpdateSemaphore = new SemaphoreManagerImpl(this.numCores);
        this.computeUpdateSemaphore = new SemaphoreManagerImpl(this.numCores);
    }

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
        if(this.isInDebugMode) {
            GameThread c = new GameThread(id, startRow, stopRow, startColumn, stopColumn,
                    this.matrix, this.justUpdateSemaphore, this.computeUpdateSemaphore);
            c.start();
        } else {
            GameThread c = new GameThread(startRow, stopRow, startColumn, stopColumn,
                    this.matrix, this.justUpdateSemaphore, this.computeUpdateSemaphore);
            c.start();
        }
    }

    /**
     * Metodo che permette di creare un numero di Thread coerente con
     * il numero di processori della macchina su cui l'applicazione sta girando.
     */
    private void threadsSetup() {
        final int blockRows = this.matrix.getNumRows() / this.numCores;
        int stepRows = 0;
        int i;
        for(i = 0; i < this.numCores - 1; i++) {
            this.createThreadAndStart(i, stepRows, blockRows + stepRows,
                    0, this.matrix.getNumColumns());
            stepRows += blockRows;
        }

        this.createThreadAndStart(i, stepRows, this.matrix.getNumRows(),
                0, this.matrix.getNumColumns());
    }

    /**
     * Crea un nuovo Event e lo notifica alla lista dei listeners.
     */
    private void notifyMatrixUpdatedEvent() {
        final MatrixEvent event = new MatrixUpdatedEvent(this.matrix);
        for(MatrixEventListener l : this.listenersList) {
            l.matrixUpdated(event);
            if(isInDebugMode) {
                System.out.println("Celle vive: " + event.getLiveCells());
            }
        }
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
        this.justUpdateSemaphore.makeAllWorkersWait();
        this.computeUpdateSemaphore.makeAllWorkersWait();
        this.justUpdateSemaphore.makeManagerWaitForAll();
        this.computeUpdateSemaphore.makeManagerWaitForAll();
        this.threadsSetup();
    }

    /**
     * Metodo pubblico che mi permette di settare a piacimento le stampe
     * del metodo run().
     * @param isDebug il valore da attribuire alla variabile che controlla il debug.
     */
    public void setInDebugMode(final boolean isDebug) {
        this.isInDebugMode = isDebug;
    }

    /**
     * Metodo pubblico che ritorna la Matrix usata da questo Game.
     * @return la Matrix utilizzata.
     */
    public Matrix getMatrix() {
        return this.matrix;
    }

    /**
     * Metodo che gestisce l'alternanza dei semafori all'interno del gioco.
     */
    public void playGame() {
        try {
            this.chronometer.start();
            if(isInDebugMode) {
                System.out.println("Manager rilascia tutti i permessi di update");
            }
            justUpdateSemaphore.releaseAllWorkers();
            justUpdateSemaphore.makeManagerWaitForAll();
            if(isInDebugMode) {
                System.out.println("Manager ha ricevuto tutti i segnali di update");
            }
            justUpdateSemaphore.makeAllWorkersWait();
            if(isInDebugMode) {
                System.out.println("Manager rilascia tutte li permessi di computeUpdate");
            }
            computeUpdateSemaphore.releaseAllWorkers();
            computeUpdateSemaphore.makeManagerWaitForAll();
            if(isInDebugMode) {
                System.out.println("Manager ha ricevuto tutti i segnali di computeUpdate");
            }
            computeUpdateSemaphore.makeAllWorkersWait();
            if(isInDebugMode) {
                System.out.println("Time elapsed: " + this.chronometer.getTime());
            }
            this.notifyMatrixUpdatedEvent();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
