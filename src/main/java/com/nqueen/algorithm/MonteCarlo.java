package com.nqueen.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MonteCarlo {
    private int boardSize;
    private int maxIterations;
    private List<int[]> solutions;

    public MonteCarlo(int boardSize, int maxIterations) {
        this.boardSize = boardSize;
        this.maxIterations = maxIterations;
        this.solutions = new ArrayList<>();
    }

    public void solve() {
        solutions.clear();
        Random random = new Random();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            int[] board = generateRandomBoard();

            // Try to minimize conflicts for this board
            minimizeConflicts(board);

            if (isSolutionValid(board)) {
                // Add only unique solutions
                if (!containsSolution(board)) {
                    solutions.add(board.clone());
                    System.out.println("Monte Carlo Solution: " + Arrays.toString(board));
                }
            }
        }

        if (solutions.isEmpty()) {
            System.out.println("No solutions found for Monte Carlo.");
            System.out.println();
        } else {
            System.out.println("Total solutions found in Monte Carlo: " + solutions.size());
            System.out.println();
        }
    }

    private int[] generateRandomBoard() {
        int[] board = new int[boardSize];
        Random random = new Random();
        for (int i = 0; i < boardSize; i++) {
            board[i] = random.nextInt(boardSize);
        }
        return board;
    }

    private void minimizeConflicts(int[] board) {
        for (int col = 0; col < boardSize; col++) {
            int minConflictsRow = board[col];
            int minConflicts = Integer.MAX_VALUE;

            // Evaluate each possible row for the current column
            for (int row = 0; row < boardSize; row++) {
                board[col] = row; // Place queen in this row temporarily
                int conflicts = countConflicts(board, col);
                if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    minConflictsRow = row;
                }
            }
            board[col] = minConflictsRow; // Assign the position with minimum conflicts
        }
    }

    private int countConflicts(int[] board, int currentCol) {
        int conflicts = 0;
        for (int col = 0; col < board.length; col++) {
            if (col != currentCol) {
                if (board[col] == board[currentCol] || // Same row
                    Math.abs(board[col] - board[currentCol]) == Math.abs(col - currentCol)) { // Same diagonal
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    private boolean isSolutionValid(int[] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = i + 1; j < board.length; j++) {
                if (board[i] == board[j] || Math.abs(board[i] - board[j]) == Math.abs(i - j)) {
                    return false; // Queens are attacking each other
                }
            }
        }
        return true;
    }

    private boolean containsSolution(int[] board) {
        for (int[] solution : solutions) {
            if (Arrays.equals(solution, board)) {
                return true;
            }
        }
        return false;
    }

    public List<int[]> getSolutions() {
        return solutions;
    }
}
