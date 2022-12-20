package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SolverFrame extends JFrame implements ActionListener {

    //stores rowLength (in number of squares)
    private int rowLength;

    //stores grid of squares
    private SquaresPanel grid;

    //buttons references
    private JButton solveButton;
    private JButton generateRecursiveMazeButton;
    private JButton generateRandomMazeButton;
    private JButton clearButton;

    //combo box for selecting which algorithm
    private JComboBox algorithmComboBox;

    //JSlider for selecting speed (for maze traversal)
    private JSlider speedSlider;

    //stores all buttons in gui for easy access (for enabling / disabling)
    private ArrayList<JButton> allButtons = new ArrayList<>();

    //stores length of grid in pixels
    int gridPixelLength;

    int frameWidth;
    int frameHeight;


    public SolverFrame(int rowLength){

        this.setTitle("UCS + Greedy BFS + A* Visualizer");

        this.setLayout(null);

        frameHeight = 700;
        frameWidth = 900;

        gridPixelLength = (int) Math.floor(660 / rowLength) * rowLength;

        this.rowLength = rowLength;

        //format solverFrame dimensions etc
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - (100 + frameWidth));
        int y = (int) ((dimension.getHeight() - frameHeight) / 2);
        this.setLocation(x, y);

        grid = new SquaresPanel(rowLength, gridPixelLength,this);

        this.add(grid);

        this.setResizable(false);
        this.setSize(frameWidth,frameHeight);

        solveButton = new JButton("Solve"){{

            this.setBounds(frameWidth - 175, frameHeight - 150,  100,60 );
            this.setFont(new Font("Sarif Sans", Font.BOLD, 15));
            this.setFocusable(false);

            this.addActionListener(SolverFrame.this);

        }};

        generateRecursiveMazeButton = new JButton("Recursive Maze"){{
            this.setBounds(frameWidth - 180,  180, 110,40 );
            this.setMargin(new Insets(5,5,5,5));
            this.setFont(new Font("Sarif Sans", Font.BOLD, 12));
            this.setFocusable(false);

            this.addActionListener(SolverFrame.this);
        }};

        generateRandomMazeButton = new JButton("Random Maze"){{
            this.setBounds(frameWidth - 180,  230, 110,40 );
            this.setMargin(new Insets(5,5,5,5));
            this.setFont(new Font("Sarif Sans", Font.BOLD, 12));
            this.setFocusable(false);

            this.addActionListener(SolverFrame.this);
        }};

        clearButton = new JButton("Clear Grid"){{

            this.setBounds(frameWidth - 175, 65,  100,60 );
            this.setFont(new Font("Sarif Sans", Font.BOLD, 13));
            this.setFocusable(false);

            this.addActionListener(SolverFrame.this);

        }};

        String[] algorithms = {"Uniform Cost Search", "Greedy Best-First Search", "A* Search"};
        algorithmComboBox = new JComboBox(algorithms){{
            this.setBounds(frameWidth - 190, 320,150,40);
            this.setFocusable(false);
            this.addActionListener(SolverFrame.this);
        }};


        JLabel speedSliderLabel = new JLabel(){{
            this.setBounds(frameWidth - 185, 390, 150, 50);
            this.setFont(new Font("Sarif Sans", Font.BOLD, 12));
            this.setText("Select Traversal Speed:");
        }};

        speedSlider = new JSlider(1,3,2){{
            this.setBounds(frameWidth - 190, 430, 150, 50);
            this.setPaintTicks(true);
            this.setMajorTickSpacing(1);
            this.setPaintLabels(true);
            this.setFocusable(false);

        }};

        //add buttons to arrayList
        allButtons.add(solveButton);
        allButtons.add(generateRecursiveMazeButton);
        allButtons.add(generateRandomMazeButton);
        allButtons.add(clearButton);

        //add buttons to panel
        this.add(solveButton);
        this.add(generateRecursiveMazeButton);
        this.add(generateRandomMazeButton);
        this.add(clearButton);
        this.add(algorithmComboBox);
        this.add(speedSlider);
        this.add(speedSliderLabel);

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    //method to disable all buttons within frame
    private void disableAllButtons(){
        for (JButton b : allButtons){
            b.setEnabled(false);
        }
        algorithmComboBox.setEnabled(false);

    }

    //method to disable all buttons within frame.
    public void enableAllButtons(){
        for (JButton b : allButtons){
            b.setEnabled(true);
        }
        algorithmComboBox.setEnabled(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == solveButton) {

            if (! grid.hasValidStartAndEndNode()){
                JOptionPane.showMessageDialog(this, "Please Select a Valid Start and End Node", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double sleepTime;
            int speedVal = speedSlider.getValue();

            //setting sleep time
            if (rowLength > 20) {
                sleepTime = (1 + (3 - speedVal) * 4) + ((3 + (3 - speedVal) * 12) * (101 - rowLength) / 81);
            }else{
                sleepTime = 36;
            }

            disableAllButtons();
            grid.clearGridOnlyTraversalNodes();

            char[][] charGridArray = grid.getCharGridArray();

            if (algorithmComboBox.getSelectedItem().toString() == "Uniform Cost Search" ) {

                int ucsSleepTime = (int) Math.round(sleepTime - (sleepTime / 2));

                UCSSolverThread u = new UCSSolverThread();
                u.initialize(charGridArray, (int) grid.getStartCoordinates().getX(), (int) grid.getStartCoordinates().getY(), (int) grid.getEndCoordinates().getX(), (int) grid.getEndCoordinates().getY(), grid, this, ucsSleepTime);
                u.start();

                return;

            }
                if (algorithmComboBox.getSelectedItem().toString() == "A* Search") {

                    AStarSolverThread a = new AStarSolverThread();
                    a.initialize(charGridArray, (int) grid.getStartCoordinates().getX(), (int) grid.getStartCoordinates().getY(), (int) grid.getEndCoordinates().getX(), (int) grid.getEndCoordinates().getY(), grid, this, (int) sleepTime);
                    a.start();
                    return;

                }

                if (algorithmComboBox.getSelectedItem().toString() == "Greedy Best-First Search") {

                    GreedyBFSSolverThread g = new GreedyBFSSolverThread();
                    g.initialize(charGridArray, (int) grid.getStartCoordinates().getX(), (int) grid.getStartCoordinates().getY(), (int) grid.getEndCoordinates().getX(), (int) grid.getEndCoordinates().getY(), grid, this, (int) sleepTime);
                    g.start();
                    return;
                }

            }


        if (e.getSource() == generateRecursiveMazeButton){

            int sleepTime = 30;

            if (rowLength > 20) {
                sleepTime = (int) Math.ceil(1 + 28 * (101 - rowLength) / 81);
            }


            disableAllButtons();

            grid.clearGrid(true);

            RecursiveMazeGeneratorThread recursiveMazeGeneratorThread = new RecursiveMazeGeneratorThread(rowLength,grid, this, sleepTime);
            recursiveMazeGeneratorThread.start();

            return;
        }

        if (e.getSource() == generateRandomMazeButton){

            disableAllButtons();

            int sleepTime = 7;

            if (rowLength > 20) {
                sleepTime = (int) Math.ceil(1 + 7 * (101.0 - rowLength) / 81.0);
            }

            grid.clearGrid(false);

            RandomMazeGeneratorThread randomMazeGeneratorThread = new RandomMazeGeneratorThread(rowLength,grid,this,sleepTime);
            randomMazeGeneratorThread.start();

            return;

        }

        if (e.getSource() == clearButton){

            grid.clearGrid(false);
            enableAllButtons();
            return;
        }

        if (e.getSource() == algorithmComboBox){
            grid.clearGridOnlyTraversalNodes();
            enableAllButtons();
        }

    }
}
