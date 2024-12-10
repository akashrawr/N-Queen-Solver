package com.nqueen;

import com.nqueen.algorithm.*;
import java.util.List;  // Import java.util.List (not java.awt.List)
import java.util.Arrays;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    public Main() throws IOException {
        JFrame frame = new JFrame("N-Queens Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DisplayPanel displayPanel = new DisplayPanel();
        ControlPanel controlPanel = new ControlPanel(displayPanel);

        frame.add(displayPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.WEST);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // DisplayPanel Class
    public class DisplayPanel extends JPanel {

        private int boardSize = 8;
        private int squareWidth;
        private final int width = 720;
        private final int height = 720;
        private boolean solved = false;
        private int[] queenPositions;
        private Image queenImage; // Image to represent the queen
        private List<int[]> allSolutions; // List to store all solutions
        private int currentSolutionIndex = 1; // Current solution index (1-based)

        public DisplayPanel() {
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.DARK_GRAY);
            squareWidth = width / boardSize;

            // Load the queen image when initializing the panel
            queenImage = loadImage();
        }

        public void solveWithBacktracking(int boardSize) {
            this.boardSize = boardSize;
            squareWidth = width / boardSize;
            solved = true;

            BackTracking.solveNQueens(boardSize);
            allSolutions = BackTracking.solutions;

            if (!allSolutions.isEmpty()) {
                queenPositions = allSolutions.get(0); // Set the first solution by default
            } else {
                queenPositions = null; // No solution found
                JOptionPane.showMessageDialog(this, "No solutions found for the given board size.", "No Solution",
                JOptionPane.WARNING_MESSAGE);
            }

            repaint();
        }

        public void solveWithGeneticAlgorithm(int boardSize) {
            this.boardSize = boardSize;
            squareWidth = width / boardSize;
            solved = true;

            Genetic geneticAlgorithm = new Genetic(boardSize, 2, 5, 500, 5000);
            allSolutions = geneticAlgorithm.run(); // Get the list of solutions

            if (!allSolutions.isEmpty()) {
                queenPositions = allSolutions.get(0).clone(); // Use the first solution and clone it
            } else {
                queenPositions = new int[boardSize];
                Arrays.fill(queenPositions, -1); // Fill with -1 if no solution is found
                JOptionPane.showMessageDialog(this, "No solutions found for the given board size.", "No Solution",
                JOptionPane.WARNING_MESSAGE);
            }

            repaint();
        }



        
        public void solveWithBruteForce(int boardSize) {
            this.boardSize = boardSize;
            squareWidth = width / boardSize;
            solved = true;

            BruteForce.solveNQueens(boardSize);
            allSolutions = BruteForce.solutions;

            if (!allSolutions.isEmpty()) {
                queenPositions = allSolutions.get(0); // Use the first solution
            } else {
                queenPositions = new int[boardSize];
                Arrays.fill(queenPositions, -1); // No solution found
                JOptionPane.showMessageDialog(this, "No solutions found for the given board size.", "No Solution",
                JOptionPane.WARNING_MESSAGE);
            }

            repaint();
        }
        
        public void solveWithBranchAndBound(int boardSize) {
            this.boardSize = boardSize;
            squareWidth = width / boardSize;
            solved = true;

            BranchAndBound.solveNQueens(boardSize);
            allSolutions = BranchAndBound.solutions;

            if (!allSolutions.isEmpty()) {
                queenPositions = allSolutions.get(0); // Use the first solution by default
            } else {
                queenPositions = null; // No solution found
                JOptionPane.showMessageDialog(this, "No solutions found for the given board size.", "No Solution",
                JOptionPane.WARNING_MESSAGE);
            }

            repaint();
        }
        
public void solveWithMonteCarlo(int boardSize) {
    this.boardSize = boardSize;
    squareWidth = width / boardSize;
    solved = true;

    // Configure Monte Carlo parameters
    int maxIterations = 10000; // Set a reasonable number of iterations
    MonteCarlo monteCarlo = new MonteCarlo(boardSize, maxIterations);

    // Solve using the Monte Carlo algorithm
    monteCarlo.solve();
    allSolutions = monteCarlo.getSolutions();

    if (!allSolutions.isEmpty()) {
        queenPositions = allSolutions.get(0); // Display the first solution
    } else {
        queenPositions = null; // No solution found
        JOptionPane.showMessageDialog(this, "No solutions found for the given board size.", "No Solution",
            JOptionPane.WARNING_MESSAGE);
    }

    repaint();
}


public void solveWithHillClimbing(int boardSize) {
    this.boardSize = boardSize;
    squareWidth = width / boardSize;
    solved = true;

    HillClimbing.solveNQueens(boardSize);
    allSolutions = HillClimbing.solutions;

    if (!allSolutions.isEmpty()) {
        queenPositions = allSolutions.get(0); // Display the first solution
    } else {
        queenPositions = null; // No solution found
        JOptionPane.showMessageDialog(this, "No solutions found for the given board size.", "No Solution",
            JOptionPane.WARNING_MESSAGE);
    }

    repaint();
}


public void solveWithSimulatedAnnealing(int boardSize) {
    this.boardSize = boardSize;
    squareWidth = width / boardSize;
    solved = true;

    SimulatedAnnealing.solveNQueens(boardSize);
    allSolutions = SimulatedAnnealing.solutions;

    if (!allSolutions.isEmpty()) {
        queenPositions = allSolutions.get(0); // Display the first solution
    } else {
        queenPositions = null; // No solution found
        JOptionPane.showMessageDialog(this, "No solutions found for the given board size.", "No Solution",
            JOptionPane.WARNING_MESSAGE);
    }

    repaint();
}

public void solveWithAntColony(int boardSize) {
    this.boardSize = boardSize;
    squareWidth = width / boardSize;
    solved = true;

    // Call the Ant Colony algorithm to solve the N-Queens problem
    AntColony.solveNQueens(boardSize);

    // Get the unique solutions found by the Ant Colony algorithm
    allSolutions = AntColony.removeDuplicates(AntColony.solutions);

    // Check if any solutions were found
    if (!allSolutions.isEmpty()) {
        queenPositions = allSolutions.get(0); // Display the first solution
   } else {
        queenPositions = null; // No solution found
        JOptionPane.showMessageDialog(this, "No solutions found for the given board size.", "No Solution",
            JOptionPane.WARNING_MESSAGE);
    }

    repaint(); // Redraw the board with the solution
}




        public void nextSolution() {
            if (allSolutions != null && currentSolutionIndex < allSolutions.size()) {
                currentSolutionIndex++;
                queenPositions = allSolutions.get(currentSolutionIndex - 1); // Get the next solution
                repaint();
            }
        }

        public void previousSolution() {
            if (allSolutions != null && currentSolutionIndex > 1) {
                currentSolutionIndex--;
                queenPositions = allSolutions.get(currentSolutionIndex - 1); // Get the previous solution
                repaint();
            }
        }

        public int getCurrentSolutionIndex() {
            return currentSolutionIndex;
        }

        public int getTotalSolutions() {
            return allSolutions == null ? 0 : allSolutions.size();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBoard(g);

            if (solved) {
                drawQueens(g);
            }
        }

        private void drawBoard(Graphics g) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    int x = j * squareWidth;
                    int y = i * squareWidth;
                    g.setColor((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                    g.fillRect(x, y, squareWidth, squareWidth);
                }
            }
        }

        public Image loadImage() {
            try {
                return ImageIO.read(this.getClass().getResource("queen.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null; // Return null if the image couldn't be loaded
        }

        public void resetBoard() {
            this.solved = false;
            this.queenPositions = null; // Clear positions of queens
            this.allSolutions = null; // Clear all solutions
            this.currentSolutionIndex = 1; // Reset the solution index
            System.out.println("----------Board Reset----------");
            System.out.println();
            repaint(); // Refresh the display
        }


        private void drawQueens(Graphics g) {
            int offset = squareWidth / 8;
            int queenLength = squareWidth * 6 / 8;

            if (queenPositions != null && queenImage != null) {
                for (int i = 0; i < boardSize; i++) {
                    if (queenPositions[i] != -1) {
                        g.drawImage(queenImage, i * squareWidth + offset, queenPositions[i] * squareWidth + offset,
                                queenLength, queenLength, null);
                    }
                }
            }
        }
    }

    // ControlPanel Class
    public static class ControlPanel extends JPanel {

        private final DisplayPanel display;
        private final JTextField boardInputField;
        private final String[] algorithmList = {
            "Backtracking", 
            "Brute Force", 
            "Branch and Bound",  
            "Genetic Algorithm", 
            "Hill Climbing",
            "Simulated Annealing",
            "Monte Carlo",
            "Ant Colony"
        };

        private final JComboBox<String> algorithmDropdown;
        private final JButton solveButton;
        private final JButton resetButton;
        private final JButton prevButton;
        private final JButton nextButton;
        private final JButton quitButton;  // Quit Button
        private final JLabel solutionLabel;

        public ControlPanel(DisplayPanel display) {
            this.display = display;
            setPreferredSize(new Dimension(300, 720));
            setBackground(Color.DARK_GRAY);
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBorder(BorderFactory.createEmptyBorder(150, 50, 150, 25));

            // Create and style the header label
            JLabel headerLabel = new JLabel("<html><u>N-Queens Solver</u></html>");
            headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            headerLabel.setForeground(Color.WHITE);  // Set text color to white for contrast
            headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the header label

            boardInputField = new JTextField(5);
            algorithmDropdown = new JComboBox<>(algorithmList);
            algorithmDropdown.setSelectedItem("Backtracking");

            JLabel sizeLabel = new JLabel("Enter Board Size (4-1000):");
            JLabel algorithmLabel = new JLabel("Choose Algorithm:");

            solveButton = new JButton("Solve");
            resetButton = new JButton("Reset");
            prevButton = new JButton("◀");
            nextButton = new JButton("▶");
            quitButton = new JButton("Quit");  // Initialize Quit Button
            solutionLabel = new JLabel("<html><body>Solution: <b>1</b> of 1</body></html>");

            prevButton.setForeground(Color.DARK_GRAY);
            nextButton.setForeground(Color.DARK_GRAY);
            quitButton.setForeground(Color.DARK_GRAY);  // Set color for Quit Button
            solutionLabel.setForeground(Color.WHITE);

            JPanel navPanel = new JPanel();
            navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            navPanel.setBackground(Color.DARK_GRAY);
            navPanel.add(prevButton);
            navPanel.add(solutionLabel);
            navPanel.add(nextButton);
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.setBackground(Color.DARK_GRAY);
            buttonPanel.add(solveButton);
            buttonPanel.add(resetButton);
            buttonPanel.add(quitButton);  // Add Quit button to the button panel

            prevButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    display.previousSolution();
                    updateSolutionLabel();
                }
            });

            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    display.nextSolution();
                    updateSolutionLabel();
                }
            });

            solveButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedAlgorithm = (String) algorithmDropdown.getSelectedItem();
        int boardSize = getBoardSize();
        if (boardSize >= 4 && boardSize <= 1000) {
            // Reset the solution index when solving
                 display.currentSolutionIndex = 1;  // Reset the solution index in the DisplayPanel


            switch (selectedAlgorithm) {
                case "Backtracking":
                    display.solveWithBacktracking(boardSize);
                    break;
                case "Brute Force":
                    display.solveWithBruteForce(boardSize);
                    break;
                case "Branch and Bound":
                    display.solveWithBranchAndBound(boardSize);
                    break;
                case "Genetic Algorithm":
                    display.solveWithGeneticAlgorithm(boardSize);
                    break;
                case "Hill Climbing":
                    display.solveWithHillClimbing(boardSize);
                    break;
                case "Simulated Annealing":
                    display.solveWithSimulatedAnnealing(boardSize);
                    break;
                case "Monte Carlo":
                    display.solveWithMonteCarlo(boardSize);
                    break;
                case "Ant Colony":
                    display.solveWithAntColony(boardSize);
                    break;
            }
            updateSolutionLabel(); // Update the solution label after solving
        } else {
            JOptionPane.showMessageDialog(display,
                    "Please enter a valid board size between 4 and 100.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
});

            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    display.resetBoard();  // Reset the board and solutions
                    solutionLabel.setText("<html><body>Solution: <b>1</b> of 1</body></html>");

                    // Clear the board size input field
                    boardInputField.setText(""); // This clears the input field for board size
                }
            });

            // Quit Button ActionListener
            quitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Exiting :)");
                    System.exit(0);  // Exit the application when the Quit button is clicked
                }
            });

            sizeLabel.setForeground(Color.WHITE);
            algorithmLabel.setForeground(Color.WHITE);
            
            add(headerLabel);  // Add the header label to the panel
            add(sizeLabel);
            add(boardInputField);
            add(Box.createRigidArea(new Dimension(0, 50)));
            add(algorithmLabel);
            add(algorithmDropdown);
            add(Box.createRigidArea(new Dimension(0, 50)));
            add(navPanel);
            add(buttonPanel);
        }

        private void updateSolutionLabel() {
            int currentIndex = display.getCurrentSolutionIndex();
            int totalSolutions = display.getTotalSolutions();
            solutionLabel.setText("<html><body>Solution: <b>" + currentIndex + "</b> of " + totalSolutions + "</body></html>");
        }

        public int getBoardSize() {
            try {
                return Integer.parseInt(boardInputField.getText());
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }
}
