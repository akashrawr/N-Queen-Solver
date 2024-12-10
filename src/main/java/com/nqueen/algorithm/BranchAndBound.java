package com.nqueen.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BranchAndBound {
    
    public static List<int[]> solutions = new ArrayList<>();
    
    // Method to solve the N-Queens problem using Branch and Bound
    public static void solveNQueens(int boardSize) {
        solutions.clear();  // Clear previous solutions
        int[] board = new int[boardSize];  // The board will store the column positions of queens (indexed by row)
        boolean[] leftDiagonal = new boolean[2 * boardSize - 1];  // For tracking the diagonals
        boolean[] rightDiagonal = new boolean[2 * boardSize - 1]; // For tracking the diagonals
        boolean[] columns = new boolean[boardSize];  // For tracking the columns
        solveNQueensRecursively(board, 0, boardSize, leftDiagonal, rightDiagonal, columns);

        // Print the total number of solutions found
        if (solutions.isEmpty()) {
            System.out.println("No solutions found for Branch And Bound.");
            System.out.println();
        } else {
            System.out.println("Total solutions found in Branch And Bound: " + solutions.size());
            System.out.println();
        }
    }
    
    // Recursive function to solve the N-Queens problem
    private static void solveNQueensRecursively(int[] board, int row, int boardSize,
                                                boolean[] leftDiagonal, boolean[] rightDiagonal, boolean[] columns) {
        if (row == boardSize) {
            // All queens are placed correctly, store the solution
            solutions.add(board.clone());

            // Print the solution (e.g., in an array representation)
            System.out.println("Branch and Bound Solution: " + Arrays.toString(board));
            return;
        }

        for (int col = 0; col < boardSize; col++) {
            if (!columns[col] && !leftDiagonal[row - col + boardSize - 1] && !rightDiagonal[row + col]) {
                // Place queen on the board and mark the columns and diagonals as occupied
                board[row] = col;
                columns[col] = true;
                leftDiagonal[row - col + boardSize - 1] = true;
                rightDiagonal[row + col] = true;
                
                // Recur to place the queen in the next row
                solveNQueensRecursively(board, row + 1, boardSize, leftDiagonal, rightDiagonal, columns);
                
                // Backtrack: remove the queen and unmark the columns and diagonals
                columns[col] = false;
                leftDiagonal[row - col + boardSize - 1] = false;
                rightDiagonal[row + col] = false;
            }
        }
    }
}
