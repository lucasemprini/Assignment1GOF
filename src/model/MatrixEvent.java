package model;

/**
 * Evento creato quando una matrice viene aggiornata.
 */
public interface MatrixEvent {

    /**
     * Metodo che ritorna l'aggiornamento di una matrice.
     * @return la matrice aggiornata
     */
    public boolean[][] matrixUpdate();
}
