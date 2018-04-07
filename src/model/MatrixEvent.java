package model;

/**
 * Evento creato quando una matrice viene aggiornata.
 */
public interface MatrixEvent {

    /**
     * Metodo che ritorna l'aggiornamento di una matrice.
     * @return la matrice aggiornata.
     */
    public boolean[][] matrixUpdate();

    /**
     * Metodo che ritorna il numero di celle vive ad ogni update della matrice.
     * @return il numero di celle in stato ALIVE nella matrice.
     */
    public long getLiveCells();
}
