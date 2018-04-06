package controller;

import java.util.concurrent.Semaphore;

public class SemaphoreManagerImpl implements SemaphoreManager {

    private final Semaphore managerSemaphore;
    private final Semaphore workerSemaphore;
    private final int numWorkers;

    public SemaphoreManagerImpl(final int numWorkers) {
        this.managerSemaphore = new Semaphore(numWorkers);
        this.workerSemaphore = new Semaphore(numWorkers);
        this.numWorkers = numWorkers;
    }

    @Override
    public void waitForManager() throws InterruptedException {
        this.managerSemaphore.acquire();
    }

    @Override
    public void waitForWorker() throws InterruptedException {
        this.workerSemaphore.acquire();
    }

    @Override
    public void releaseManager() {
        this.managerSemaphore.release();
    }

    @Override
    public void releaseWorker() {
        this.workerSemaphore.release();
    }

    @Override
    public void makeAllWorkersWait() {
        this.workerSemaphore.drainPermits();
    }

    @Override
    public void makeManagerWaitForAll() throws InterruptedException {
        for(int i = 0; i < this.numWorkers; i++) {
            this.waitForManager();
        }
    }

    @Override
    public void releaseAllWorkers() {
        for(int i = 0; i< this.numWorkers; i++) {
            workerSemaphore.release();
        }
    }
}
