package com.nqueen.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HillClimbing {
    public static List<int[]> solutions = new ArrayList<>();

    public static void solveNQueens(int boardSize) {
        solutions.clear();
        Set<String> uniqueSolutions = new HashSet<>();
        int maxRestarts = 1000; // Number of restarts to find more solutions

        for (int restart = 0; restart < maxRestarts; restart++) {
            int[] board = initializeBoard(boardSize);

            while (true) {
                int[] nextBoard = findBestNeighbor(board);

                if (calculateConflicts(nextBoard) >= calculateConflicts(board)) {
                    // Local optimum reached
                    if (calculateConflicts(board) == 0) {
                        String boardKey = Arrays.toString(board);
                        if (!uniqueSolutions.contains(boardKey)) {
                            uniqueSolutions.add(boardKey);
                            solutions.add(board.clone());
                            System.out.println("Hill Climbing Solution Found: " + Arrays.toString(board));
                        }
                    }
                    break; // Break out of the inner loop to restart
                }

                board = nextBoard; // Move to the better neighbor
            }
        }

        // Output results
        if (solutions.isEmpty()) {
            System.out.println("No solutions found for Hill Climbing.");
            System.out.println();
        } else {
            System.out.println("Total unique solutions found in Hill Climbing: " + solutions.size());
            System.out.println();
        }
    }

    private static int[] initializeBoard(int boardSize) {
        int[] board = new int[boardSize];
        for (int i = 0; i < boardSize; i++) {
            board[i] = (int) (Math.random() * boardSize); // Random row for each column
        }
        return board;
    }

    private static int[] findBestNeighbor(int[] board) {
        int[] bestBoard = board.clone();
        int minConflicts = calculateConflicts(board);

        for (int col = 0; col < board.length; col++) {
            int originalRow = board[col];
            for (int row = 0; row < board.length; row++) {
                if (row != originalRow) {
                    board[col] = row; // Move queen to a new row
                    int conflicts = calculateConflicts(board);
                    if (conflicts < minConflicts) {
                        minConflicts = conflicts;
                        bestBoard = board.clone();
                    }
                }
            }
            board[col] = originalRow; // Restore original position
        }

        return bestBoard;
    }

    private static int calculateConflicts(int[] board) {
        int conflicts = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = i + 1; j < board.length; j++) {
                if (board[i] == board[j] || Math.abs(board[i] - board[j]) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }
}
