package com.company;

import javax.swing.*;

public class AStarSolverThread extends Thread {

    private AStarSolver aStarSolver;
    private SolverFrame mainFrame;
    private SquaresPanel gui;

    public void initialize(char[][] charArrayGrid, int xStart, int yStart, int xEnd, int yEnd, SquaresPanel gui, SolverFrame mainFrame, int sleepTime) {

         aStarSolver = new AStarSolver(charArrayGrid,xStart,yStart,xEnd,yEnd,gui,sleepTime);
         this.mainFrame = mainFrame;
         this.gui = gui;

    }

    @Override
    public void run(){

        boolean solved;

        gui.setDrawable(false);
        solved = aStarSolver.solve();

        if (solved) {
            aStarSolver.guiSetSolvedGrid();
        }else{
            JOptionPane.showMessageDialog(mainFrame, "Maze Has No Solutions", "No Solutions", JOptionPane.INFORMATION_MESSAGE);
        }

        gui.reValidateStartAndEndNode();
        mainFrame.enableAllButtons();
        gui.setDrawable(true);

    }

}
