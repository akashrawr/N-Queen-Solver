package com.nqueen.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays; // Import Arrays class for printing

public class BruteForce {

    // List to store all valid solutions
    public static List<int[]> solutions = new ArrayList<>();

    // Method to solve N-Queens using brute force
    public static void solveNQueens(int boardSize) {
        solutions.clear(); // Clear previous solutions
        int[] board = new int[boardSize];
        solve(board, 0, boardSize);

        // Print the total number of solutions found
        if (solutions.isEmpty()) {
            System.out.println("No solutions found for Bruteforce.");
            System.out.println();
        } else {
            System.out.println("Total solutions found in Bruteforce: " + solutions.size());
            System.out.println();
        }
    }


    // Helper method to recursively generate all solutions
    private static void solve(int[] board, int row, int boardSize) {
        if (row == boardSize) {
            if (isValid(board)) {
                solutions.add(board.clone()); // Add the valid solution to the list
                // Print the solution
                System.out.println("Bruteforce Solution: " + Arrays.toString(board)); // Print the solution
            }
            return;
        }

        // Try placing a queen in each column of the current row
        for (int col = 0; col < boardSize; col++) {
            board[row] = col; // Place queen at (row, col)
            solve(board, row + 1, boardSize); // Recur to place queen in next row
        }
        
    }

    // Helper method to check if the current board configuration is valid
    private static boolean isValid(int[] board) {
        int boardSize = board.length;
        for (int i = 0; i < boardSize; i++) {
            for (int j = i + 1; j < boardSize; j++) {
                // Check if queens are attacking each other diagonally or in the same column
                if (board[i] == board[j] || Math.abs(i - j) == Math.abs(board[i] - board[j])) {
                    return false;
                }
            }
        }
        return true;
    }
}
