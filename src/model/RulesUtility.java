package model;

/**
 * Utility-Class per modellare le regole del gioco.
 */
public final class RulesUtility {

    /**
     * Costruttore privato e vuoto.
     */
    private RulesUtility(){}

    /**
     * Metodo di utility che restituisce il prossimo stato da assegnare a una cella in base al
     * numero dei suoi vicini vivi e al suo stato corrente.
     * Metodo che incorpora la regola principale del Gioco.
     * @param aliveNeighbours il numero di vicini vivi compresa la cella considerata.
     * @param myStatus lo stato corrente della cella considerata.
     * @return il prossimo stato della cella considerata.
     */
    public static boolean nextStatus(final int aliveNeighbours, final boolean myStatus) {
        switch (aliveNeighbours) {
            case 0 : return false;
            case 1 : return false;
            case 2 : return false;
            case 3 : return true;
            case 4 : return myStatus;
            default: return false;
        }
    }
}
