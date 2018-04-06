package controller;

import controller.concurrency.Game;

public class GameAgent extends Thread {

    private final Game game;
    private int numGenerations = 0;
    private static final int SLEEPTIME = 50;

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
                Thread.sleep(SLEEPTIME);
            } catch(Exception ex){
                ex.printStackTrace();
            }
            this.numGenerations++;
            System.out.println("Generazione: " + numGenerations);
        }
    }

    public Game getGame() {
        return this.game;
    }

    public void notifyStop(){
        //super.interrupt();
        this.isPaused = true;
    }

    public int getNumGenerations() {
        return numGenerations;
    }
}
