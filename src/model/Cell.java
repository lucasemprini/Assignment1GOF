package model;

public interface Cell {

    /**
     * Metodo per fornire lo stato corrente in cui si trova la cella.
     * @return lo stato corrente della cella.
     */
    public boolean getCurrentState();

    /**
     * Metodo che fornisce il prossimo stato della cella.
     * @return il prossimo stato della cella.
     */
    public boolean getNextState();

    /**
     * Metodo che permette di settare il prossimos stato della cella.
     * @param nextState il prossimo stato della cella.
     */
    public void setNextState(final boolean nextState);

    /**
     * Metodo che internamente aggiorna lo stato della cella.
     */
    public void updateState();
}
