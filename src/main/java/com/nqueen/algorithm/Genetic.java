package com.nqueen.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Genetic {
    private int boardSize;
    private int mutationChance;
    private int selectionFactor;
    private int populationSize;
    private int numGenerations;
    private int[][] population;
    private Random random = new Random();
    private int noImprovementGenerations = 0;
    private int lastBestFitness = Integer.MAX_VALUE;
    private double mutationRate;
    private List<int[]> solutions = new ArrayList<>(); // List to store all solutions

    // Constructor to initialize genetic algorithm parameters
    public Genetic(int boardSize, int mutationChance, int selectionFactor, int populationSize, int numGenerations) {
        this.boardSize = boardSize;
        this.mutationChance = mutationChance;
        this.selectionFactor = selectionFactor;
        this.populationSize = populationSize;
        this.numGenerations = numGenerations;
        this.population = new int[populationSize][boardSize];
        this.mutationRate = mutationChance / 100.0; // Convert to probability
        initializePopulation();
    }

    // Initialize the population randomly
    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                population[i][j] = random.nextInt(boardSize); // Random position of queen in each row
            }
        }
    }

    // Calculate the fitness of a board configuration (lower fitness = better)
    public int calculateFitness(int[] positions) {
        int totalAttacks = 0;

        // Row attacks
        for (int i = 0; i < boardSize; i++) {
            for (int j = i + 1; j < boardSize; j++) {
                if (positions[i] == positions[j]) {
                    totalAttacks++;
                }
            }
        }

        // Diagonal attacks
        for (int i = 0; i < boardSize; i++) {
            for (int j = i + 1; j < boardSize; j++) {
                if (Math.abs(positions[i] - positions[j]) == Math.abs(i - j)) {
                    totalAttacks++;
                }
            }
        }
        return totalAttacks;
    }

    // Dynamic mutation rate adjustment
    private void adjustMutationRate(int bestFitness) {
        if (bestFitness >= lastBestFitness) {
            noImprovementGenerations++;
            if (noImprovementGenerations > 50) {
                mutationRate = Math.min(0.5, mutationRate + 0.05); // Increase mutation rate
            }
        } else {
            noImprovementGenerations = 0;
            mutationRate = mutationChance / 100.0; // Reset mutation rate
        }
        lastBestFitness = bestFitness;
    }

    // Mutation operation (find the best position for a queen)
    public void mutate(int[] board) {
        for (int i = 0; i < board.length; i++) {
            if (random.nextDouble() < mutationRate) {
                int currentRow = board[i];
                int bestRow = currentRow;
                int minConflicts = calculateFitness(board);

                // Check all rows to find the best position for this queen
                for (int row = 0; row < boardSize; row++) {
                    board[i] = row; // Temporarily place queen in this row
                    int conflicts = calculateFitness(board);
                    if (conflicts < minConflicts) {
                        minConflicts = conflicts;
                        bestRow = row;
                    }
                }
                board[i] = bestRow; // Place queen in the best row found
            }
        }
    }

    // Tournament selection operation
    public int[] tournamentSelection() {
        int tournamentSize = Math.min(selectionFactor, populationSize);
        int[] bestIndividual = population[random.nextInt(populationSize)];
        int bestFitness = calculateFitness(bestIndividual);
        for (int i = 1; i < tournamentSize; i++) {
            int[] individual = population[random.nextInt(populationSize)];
            int fitness = calculateFitness(individual);
            if (fitness < bestFitness) {
                bestFitness = fitness;
            }
        }
        return bestIndividual;
    }

    // Crossover operation (combine two parents to produce offspring)
    public int[] crossover(int[] parent1, int[] parent2) {
        int crossoverPoint = random.nextInt(boardSize);
        int[] offspring = new int[boardSize];
        System.arraycopy(parent1, 0, offspring, 0, crossoverPoint);
        System.arraycopy(parent2, crossoverPoint, offspring, crossoverPoint, boardSize - crossoverPoint);
        return offspring;
    }

    // Run the genetic algorithm
    public List<int[]> run() {
    int generation = 0;
    int[] fitness = new int[populationSize];

    while (generation < numGenerations) {
        // Calculate fitness for the population
        for (int i = 0; i < populationSize; i++) {
            fitness[i] = calculateFitness(population[i]);
        }

        // Keep track of all perfect solutions
        for (int i = 0; i < populationSize; i++) {
            if (fitness[i] == 0) { // Perfect solution found
                if (!isDuplicateSolution(population[i])) {
                    solutions.add(population[i].clone());
                    // Print the solution when a perfect solution is found
                    System.out.println("Genetic Solution: " + Arrays.toString(population[i]));
                }
            }
        }

        // Keep track of the best individual
        int bestIndex = 0;
        for (int i = 1; i < populationSize; i++) {
            if (fitness[i] < fitness[bestIndex]) {
                bestIndex = i;
            }
        }

        // Adjust mutation rate
        adjustMutationRate(fitness[bestIndex]);

        // Create new population with elitism
        int[][] newPopulation = new int[populationSize][boardSize];
        newPopulation[0] = population[bestIndex].clone(); // Keep the best individual

        for (int i = 1; i < populationSize; i++) {
            int[] parent1 = tournamentSelection();
            int[] parent2 = tournamentSelection();
            int[] offspring = crossover(parent1, parent2);
            mutate(offspring);
            newPopulation[i] = offspring;
        }

        population = newPopulation; // Move to the next generation
        generation++;
    }

    // Print the total number of solutions found
    if (solutions.isEmpty()) {
        System.out.println("No solutions found for Genetic Algorithm.");
        System.out.println();
    } else {
        System.out.println("Total solutions found in Genetic Algorithm: " + solutions.size());
        System.out.println();
    }

    // Return all perfect solutions found
    return solutions;
}



    // Check if a solution is a duplicate
    private boolean isDuplicateSolution(int[] solution) {
        for (int[] existing : solutions) {
            if (Arrays.equals(existing, solution)) {
                return true;
            }
        }
        return false;
    }
}
