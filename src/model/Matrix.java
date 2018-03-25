package model;

import java.util.List;

public interface Matrix {

    /**
     * Metodo che ritorna il numero di righe della matrice
     * @return il numero di righe della matrice
     */
    public int getNumRows();

    /***
     * Metodo che ritorna il numero di colonne della matrice
     * @return il numero di colonne della matrice
     */
    public int getNumColumns();

    /**
     * Metodo da usare nel costruttore per generare una matrice di celle
     * inizializzata con valori Random.
     */
    public void generateRandomMatrix();

    /**
     * Metodo che permette di capire se la matrice ha terminato le possibilità di
     * progredire
     * @return true se altre generazioni sono possibili, false altrimenti
     */
    public boolean isOver();

    /**
     * Metodo che internamente aggiorna la matrice di celle.
     */
    public void update();

    /**
     * Metodo che permette di restituire la cella alla posizione [x][y].
     * @param x la coordinata x.
     * @param y la coordinata y.
     * @return la Cella selezionata.
     */
    public boolean getCellAt(final int x, final int y);


    /**
     * Metodo che aggiorna effettivamente lo stato corrente di ogni cella.
     */
    public void computeUpdate();
}
