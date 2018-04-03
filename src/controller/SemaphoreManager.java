package controller;

public interface SemaphoreManager {

    /**
     * Metodo che permette di aspettare che il manager completi la sua azione.
     */
    public void waitForManager() throws InterruptedException;

    /**
     * Metodo che permette di aspettare finchè un worker non viene rilasciato.
     */
    public void waitForWorker() throws InterruptedException;

    /**
     * Metodo per rilasciare un manager.
     */
    public void releaseManager();

    /**
     * Metodo per rilasciare un worker.
     */
    public void releaseWorker();

    /**
     * Metodo per far aspettare tutti i worker finchè il manager non li rilascerà.
     */
    public void waitAllWorkers();

    /**
     * Metodo che permette al manager di aspettare che tutti i worker finiscano il proprio compito.
     */
    public void waitAllManager() throws InterruptedException;

    /**
     * Metodo per rilasciare tutti i worker.
     */
    public void releaseAllWorkers();
}
