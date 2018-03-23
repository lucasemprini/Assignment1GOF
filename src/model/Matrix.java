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
    public Cell getCell(final int x, final int y);

    /**
     * Metodo che restituisce il numero di vicini vivi per la cella specificata.
     * @param x la coordinata x della cella.
     * @param y la coordinata y della cella.
     * @return il numero dei vicini vivi compresa la cella stessa.
     */
    public int getNumNeighboursAlive(final int x, final int y);

    /**
     * Metodo per aggiornare una cella.
     * @param x la coordinata x.
     * @param y la coordinata y.
     */
    public void updateCell(final int x, final int y);
}
