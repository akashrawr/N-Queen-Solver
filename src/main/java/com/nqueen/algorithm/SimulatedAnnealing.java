package com.nqueen.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SimulatedAnnealing {
    public static List<int[]> solutions = new ArrayList<>();

    public static void solveNQueens(int n) {
        solutions.clear(); // Clear previous solutions
        Set<String> uniqueSolutions = new HashSet<>(); // To store unique solutions
        int maxAttempts = 200; // Limit the number of solution attempts

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int[] currentState = generateSmartInitialState(n);
            double temperature = 5000.0;
            double coolingRate = 0.005; // Dynamic exploration and exploitation

            while (temperature > 1) {
                int[] neighborState = generateSmartNeighbor(currentState);
                int currentCost = calculateCost(currentState);
                int neighborCost = calculateCost(neighborState);

                // Move to neighbor if it improves or based on acceptance probability
                if (neighborCost < currentCost || acceptWorseSolution(currentCost, neighborCost, temperature)) {
                    currentState = neighborState.clone();
                }

                temperature *= (1 - coolingRate); // Cool down
            }

            // Check if a valid solution is found
            if (calculateCost(currentState) == 0) {
                String solutionKey = Arrays.toString(currentState); // String representation for uniqueness
                if (!uniqueSolutions.contains(solutionKey)) {
                    uniqueSolutions.add(solutionKey);
                    solutions.add(currentState.clone());
                    System.out.println("Simulated Annealing Solution: " + Arrays.toString(currentState));
                }
            }

            // Early exit if sufficient solutions found
            if (solutions.size() >= 1) break; // Adjust this threshold as needed
        }

        // Print results
        if (solutions.isEmpty()) {
            System.out.println("No solutions found for Simulated Annealing.");
            System.out.println();
        } else {
            System.out.println("Total solutions found for Simulated Annealing: " + solutions.size());
            System.out.println();
        }
    }

    private static int[] generateSmartInitialState(int n) {
        int[] state = new int[n];
        Random random = new Random();
        List<Integer> availableRows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            availableRows.add(i); // Generate non-repeating rows
        }
        for (int i = 0; i < n; i++) {
            state[i] = availableRows.remove(random.nextInt(availableRows.size()));
        }
        return state;
    }

    private static int[] generateSmartNeighbor(int[] state) {
        int[] neighbor = state.clone();
        Random random = new Random();
        int col = random.nextInt(state.length); // Random column
        int currentRow = neighbor[col];
        int bestRow = currentRow;
        int minConflicts = Integer.MAX_VALUE;

        // Evaluate all rows for this column
        for (int row = 0; row < state.length; row++) {
            if (row != currentRow) {
                neighbor[col] = row;
                int conflicts = calculateCost(neighbor);
                if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    bestRow = row;
                }
            }
        }

        neighbor[col] = bestRow; // Move to the best row
        return neighbor;
    }

    private static int calculateCost(int[] state) {
        int conflicts = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = i + 1; j < state.length; j++) {
                if (state[i] == state[j] || Math.abs(state[i] - state[j]) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    private static boolean acceptWorseSolution(int currentCost, int neighborCost, double temperature) {
        if (neighborCost >= currentCost) {
            double probability = Math.exp((currentCost - neighborCost) / temperature);
            return Math.random() < probability; // Accept with some probability
        }
        return true; // Always accept if the neighbor is better
    }
}
