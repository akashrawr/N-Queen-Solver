package com.nqueen.algorithm;


import java.util.ArrayList;
import java.util.Arrays;

public class BackTracking {

    static int n;  // Number of queens
    static int[] col;  // Column positions for queens in rows
    public static ArrayList<int[]> solutions = new ArrayList<>(); // List to store all solutions

    // Method to check if the current queen placement is promising (safe)
    public static boolean promising(int i) {
        for (int k = 1; k < i; k++) {
            if (col[k] == col[i] || Math.abs(col[i] - col[k]) == (i - k)) {
                return false;  // Conflict with another queen
            }
        }
        return true;  // No conflicts
    }

    // Recursive method to solve the N-Queens problem
    public static void queens(int i) {
        if (promising(i)) {
            if (i == n) {
                // Convert 1-based index solution to 0-based and store it
                int[] solution = new int[n];
                for (int k = 1; k <= n; k++) {
                    solution[k - 1] = col[k] - 1;  // Convert 1-based to 0-based
                }
                solutions.add(solution); // Add solution to the list
                System.out.println("Backtracking Solution: " + Arrays.toString(solution));
            } else {
                // Try placing queens in each column of the next row
                for (int j = 1; j <= n; j++) {
                    col[i + 1] = j;  // Place queen in column j of row i+1
                    queens(i + 1);   // Recursively place next queen
                }
            }
        }
    }

    // Method to solve the N-Queens problem with a given number of queens
    public static void solveNQueens(int numberOfQueens) {
        n = numberOfQueens;  // Set the number of queens
        col = new int[n + 1];  // Initialize the column array
        solutions.clear(); // Clear previous solutions
        queens(0);  // Start solving from the 0th row

        // Print the total number of solutions found
        if (solutions.isEmpty()) {
            System.out.println("No solutions found for Backtracking.");
            System.out.println();
        } else {
            System.out.println("Total solutions found in Backtracking: " + solutions.size());
            System.out.println();
        }
    }
}
