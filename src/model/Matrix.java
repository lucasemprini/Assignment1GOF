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
     * Metodo che setta il numero di righe della matrice
     * @param numRows il numero di righe.
     */
    public void setNumRows(final int numRows);

    /***
     * Metodo che setta il numero di colonne della matrice
     * @param numColumns il numero di colonne.
     *
     */
    public void setNumColumns(final int numColumns);

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
     * @param startRow la riga di partenza.
     * @param stopRow la riga di fine.
     * @param startColumn la colonna di partenza.
     * @param stopColumn la colonna di fine.
     */
    public void update(final int startRow, final int stopRow,
                       final int startColumn, final int stopColumn);

    /**
     * Metodo che permette di restituire la cella alla posizione [x][y].
     * @param x la coordinata x.
     * @param y la coordinata y.
     * @return la Cella selezionata.
     */
    public boolean getCellAt(final int x, final int y);


    /**
     * Metodo che aggiorna effettivamente lo stato corrente di ogni cella.
     * @param startRow la riga di partenza.
     * @param stopRow la riga di fine.
     * @param startColumn la colonna di partenza.
     * @param stopColumn la colonna di fine.
     */
    public void computeUpdate(final int startRow, final int stopRow,
                              final int startColumn, final int stopColumn);
}
