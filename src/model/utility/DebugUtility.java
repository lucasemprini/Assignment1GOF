package model.utility;

import model.Matrix;

public final class DebugUtility {

    private DebugUtility() {}

    public static void printMatrix(final Matrix myMatrix, final int gen) {
        System.out.println("Generation: " + gen);
        for(int i = 0; i < myMatrix.getNumRows(); i++) {
            for(int j = 0; j < myMatrix.getNumColumns(); j++) {
                System.out.print(myMatrix.getCellAt(i, j).getCurrentState() + " ");
            }
            System.out.println("\n");
        }
    }
}
