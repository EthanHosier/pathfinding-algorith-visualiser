package com.company;

public class RecursiveMazeGeneratorThread extends Thread{

    private int length;
    private SquaresPanel gui;
    private SolverFrame mainFrame;

    private int sleepTime;

    public RecursiveMazeGeneratorThread(int length,SquaresPanel gui, SolverFrame mainFrame, int sleepTime){

        this.length = length;
        this.gui = gui;
        this.mainFrame = mainFrame;
        this.sleepTime = sleepTime;

    }

    @Override
    public void run() {
        gui.setDrawable(false);
        RecursiveMazeGenerator recursiveMazeGenerator = new RecursiveMazeGenerator(length,gui,sleepTime);
        recursiveMazeGenerator.generateMaze();
        mainFrame.enableAllButtons();
        gui.setDrawable(true);
    }
}
