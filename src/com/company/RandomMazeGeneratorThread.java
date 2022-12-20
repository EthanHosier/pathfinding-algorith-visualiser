package com.company;

import java.util.Random;

public class RandomMazeGeneratorThread extends Thread{

    private int length;
    private SquaresPanel gui;
    private SolverFrame mainFrame;
    private int sleepTime;

    public RandomMazeGeneratorThread(int length, SquaresPanel gui, SolverFrame mainFrame,int sleepTime){

        this.length = length;
        this.gui = gui;
        this.mainFrame = mainFrame;
        this.sleepTime = sleepTime;

    }

    @Override
    public void run() {
        gui.setDrawable(false);
        RandomMazeGenerator randomMazeGenerator = new RandomMazeGenerator(length,gui,sleepTime);
        randomMazeGenerator.generateGrid();
        mainFrame.enableAllButtons();
        gui.setDrawable(true);
    }

    public class RandomMazeGenerator {

        private int length;

        private SquaresPanel gui;

        private char[][] grid;

        private int sleepTime;

        public RandomMazeGenerator(int length, SquaresPanel gui, int sleepTime) {

            this.length = length;
            this.gui = gui;

            grid = gui.getCharGridArray();
            this.sleepTime = sleepTime;

        }

        public void generateGrid() {

            Random random = new Random();

            char tempChar;

            int numWalls = 0;

            for (int i = 0; i < length; i++) {

                for (int j = 0; j < length; j++) {

                    tempChar = grid[i][j];

                    if ((tempChar != 's' && tempChar != 'e') && random.nextInt(9) < 3) {

                        gui.setWallNode(j, i);

                        gui.repaint();
                        numWalls ++;

                        if (numWalls % 3 == 0) {
                            try {

                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }


            }


        }

    }
}