package com.company;

import javax.swing.*;

public class UCSSolverThread extends Thread {

    private UCSSolver ucsSolver;
    private SolverFrame mainFrame;
    private SquaresPanel gui;
    private int sleepTime;

    public void initialize(char[][] charArrayGrid, int xStart, int yStart, int xEnd, int yEnd, SquaresPanel gui, SolverFrame mainFrame, int sleepTime) {

        ucsSolver = new UCSSolver(charArrayGrid,xStart,yStart,xEnd,yEnd,gui,sleepTime);
        this.mainFrame = mainFrame;
        this.gui = gui;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run(){

        boolean solved;

        gui.setDrawable(false);
        solved = ucsSolver.solve();

        if (solved) {
            ucsSolver.guiSetSolvedGrid();
        }else{
            JOptionPane.showMessageDialog(mainFrame, "Maze Has No Solutions", "No Solutions", JOptionPane.INFORMATION_MESSAGE);
        }

        gui.reValidateStartAndEndNode();
        mainFrame.enableAllButtons();
        gui.setDrawable(true);
    }



}
