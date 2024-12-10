package com.nqueen.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AntColony {
    public static List<int[]> solutions = new ArrayList<>();
    private static final double ALPHA = 1.0; // Influence of pheromone
    private static final double BETA = 2.0; // Influence of heuristic
    private static final double EVAPORATION_RATE = 0.5; // Rate of pheromone evaporation
    private static final int ANT_COUNT = 50; // Number of ants
    private static final int MAX_ITERATIONS = 1000; // Maximum iterations
    private static double[][] pheromoneMatrix; // Pheromone matrix
    private static Random random = new Random();

    public static void solveNQueens(int n) {
        pheromoneMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(pheromoneMatrix[i], 1.0); // Initialize pheromone levels
        }

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            List<int[]> antsSolutions = new ArrayList<>();
            for (int ant = 0; ant < ANT_COUNT; ant++) {
                int[] solution = constructSolution(n);
                if (calculateConflicts(solution) == 0) {
                    antsSolutions.add(solution);
                    solutions.add(solution);
                }
            }

            updatePheromone(antsSolutions);
            evaporatePheromone();
        }

        // Print all the solutions found
        if (solutions.isEmpty()) {
            System.out.println("No solutions found after " + MAX_ITERATIONS + " iterations for Ant Colony.");
            System.out.println();
        } else {            // Remove duplicate solutions
            List<int[]> uniqueSolutions = removeDuplicates(solutions);
            for (int[] solution : uniqueSolutions) {
                System.out.println("Ant Colony solution: " + Arrays.toString(solution));
            }
            System.out.println("Total solutions found for Ant Colony: " + uniqueSolutions.size());
            System.out.println();
        }
    }

    private static int[] constructSolution(int n) {
        int[] solution = new int[n]; // Represents the position of queens in each row
        Arrays.fill(solution, -1); // Initialize with -1 (no queen placed)

        for (int row = 0; row < n; row++) {
            int col = chooseColumn(row, n, solution);
            solution[row] = col;
        }

        return solution;
    }

    private static int chooseColumn(int row, int n, int[] solution) {
        double[] probabilities = new double[n];
        double totalProbability = 0.0;

        for (int col = 0; col < n; col++) {
            if (isSafe(row, col, solution)) {
                // Calculate probability based on pheromone level and heuristic
                double pheromone = Math.pow(pheromoneMatrix[row][col], ALPHA);
                double heuristic = Math.pow(1.0 / (1 + calculateConflictsAtPosition(solution, row, col)), BETA);
                probabilities[col] = pheromone * heuristic;
                totalProbability += probabilities[col];
            } else {
                probabilities[col] = 0.0;
            }
        }

        // Choose column based on probabilities
        double randomValue = random.nextDouble() * totalProbability;
        double cumulativeProbability = 0.0;
        for (int col = 0; col < n; col++) {
            cumulativeProbability += probabilities[col];
            if (cumulativeProbability >= randomValue) {
                return col;
            }
        }

        return -1; // No valid column found (shouldn't reach here if heuristic and pheromones work correctly)
    }

    private static boolean isSafe(int row, int col, int[] solution) {
        for (int i = 0; i < row; i++) {
            if (solution[i] == col || Math.abs(solution[i] - col) == Math.abs(i - row)) {
                return false; // Same column or diagonal conflict
            }
        }
        return true;
    }

    private static int calculateConflicts(int[] solution) {
        int conflicts = 0;
        for (int i = 0; i < solution.length; i++) {
            for (int j = i + 1; j < solution.length; j++) {
                if (solution[i] == solution[j] || Math.abs(solution[i] - solution[j]) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    private static double calculateConflictsAtPosition(int[] solution, int row, int col) {
        int conflicts = 0;
        for (int i = 0; i < row; i++) {
            if (solution[i] == col || Math.abs(solution[i] - col) == Math.abs(i - row)) {
                conflicts++;
            }
        }
        return conflicts;
    }

    private static void updatePheromone(List<int[]> antsSolutions) {
        // Increase pheromone for each solution found
        for (int[] solution : antsSolutions) {
            double solutionQuality = 1.0 / (1 + calculateConflicts(solution)); // Reinforce good solutions
            for (int row = 0; row < solution.length; row++) {
                int col = solution[row];
                pheromoneMatrix[row][col] += solutionQuality;
            }
        }
    }

    private static void evaporatePheromone() {
        // Evaporate pheromone over time
        for (int i = 0; i < pheromoneMatrix.length; i++) {
            for (int j = 0; j < pheromoneMatrix[i].length; j++) {
                pheromoneMatrix[i][j] *= (1.0 - EVAPORATION_RATE); // Decay pheromone
            }
        }
    }

    // Function to remove duplicate solutions
    public static List<int[]> removeDuplicates(List<int[]> solutions) {
        List<int[]> uniqueSolutions = new ArrayList<>();
        for (int[] solution : solutions) {
            boolean isDuplicate = false;
            for (int[] existingSolution : uniqueSolutions) {
                if (Arrays.equals(solution, existingSolution)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueSolutions.add(solution);
            }
        }
        return uniqueSolutions;
    }
}
