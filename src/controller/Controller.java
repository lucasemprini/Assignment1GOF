package controller;

import controller.concurrency.Game;

public class Controller extends Thread {

    private final Game game;

    private volatile boolean isPaused;

    public Controller(final Game game) {
        this.game = game;
    }

    @Override
    public void run(){
        this.isPaused = false;
        while (! this.isPaused){
            this.game.playGame();
            try {
                Thread.sleep(10);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void notifyStop(){
        super.interrupt();
        this.isPaused = true;
    }
}
