package controller;

import controller.concurrency.Game;

public class GameAgent extends Thread {

    private final Game game;

    private volatile boolean isPaused;

    public GameAgent(final Game game) {
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

    public Game getGame() {
        return this.game;
    }

    public void notifyStop(){
        super.interrupt();
        this.isPaused = true;
    }
}
