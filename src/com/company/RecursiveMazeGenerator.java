package com.company;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class RecursiveMazeGenerator {

    //creates a square maze, so each side is length, ONLY FOR ODD SIZE MAZES
    int length;

    char grid[][];

    private Point2D startPoint;
    private Point2D endPoint = new Point2D.Double();

    private int sleepTime1;
    private int sleepTime2;

    //reference to square panel
    private SquaresPanel gui;

    public RecursiveMazeGenerator(int length, SquaresPanel gui, int sleepTime){

        this.length = length;
        this.gui = gui;
        this.sleepTime1 = sleepTime;
        this.sleepTime2 = (int) Math.ceil(sleepTime * (13.0/30.0));
        generateInitialGrid();

    }

    private void generateInitialGrid(){

        startPoint = new Point2D.Double(0,0);


        grid = new char[length][length];

        //first column is square then wall then square etc
        //second column is all walls

        //x
        for (int i = 0; i < length; i ++){

            //y
            for (int j = 0; j < length; j ++){

                if (i % 2 == 0){

                    if(j % 2 == 0){
                        grid[j][i] = 'u';
                        gui.setBlankNode(i,j);
                    }else{
                        grid[j][i] = 'X';
                        gui.setWallNode(i,j);
                    }

                }else{
                    grid[j][i] = 'X';
                    gui.setWallNode(i,j);
                }
                gui.repaint();

            }


            try {
                Thread.sleep(sleepTime1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void generateMaze(){

    recursiveGeneration(startPoint);

    }

    //takes x and y positions of node in question
    private void recursiveGeneration(Point2D point2D){

        Random rand = new Random();
        Point2D randPoint;

        ArrayList<Point2D> unvisitedNeighbours = new ArrayList<>();

        grid[(int)point2D.getY()][(int)point2D.getX()] = '0';
        gui.setBlankNode((int)point2D.getX(),(int)point2D.getY());

        gui.repaint();

        try {
            Thread.sleep(sleepTime2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //these are updated each time
        endPoint.setLocation(point2D);

        unvisitedNeighbours = getUnvisitedNeighbours(point2D);


        while (!unvisitedNeighbours.isEmpty()){
            randPoint = unvisitedNeighbours.get((int)Math.floor(Math.random()*(unvisitedNeighbours.size())));

            removeWallBetween(point2D,randPoint);
            recursiveGeneration(randPoint);
            unvisitedNeighbours = getUnvisitedNeighbours(point2D);

        }

    }

    private ArrayList<Point2D> getUnvisitedNeighbours(Point2D point2D){

        ArrayList<Point2D> unvisitedNeighbours = new ArrayList<>();

        int x = (int)point2D.getX();
        int y = (int)point2D.getY();

        //left
        if(x > 1){

            if (grid[y][x - 2] == 'u'){
                unvisitedNeighbours.add(new Point2D.Double(x-2,y));
            }

        }


        //above
        if (y > 1){

            if (grid[y-2][x] == 'u'){
                unvisitedNeighbours.add(new Point2D.Double(x,y-2));
            }

        }

        //right
        if( x < length - 2){
            if(grid[y][ x + 2] == 'u'){
                unvisitedNeighbours.add(new Point2D.Double(x + 2, y));

            }
        }

        //below
        if (y < length - 2 ){
            if(grid[y + 2][ x] == 'u'){
                unvisitedNeighbours.add(new Point2D.Double(x, y + 2));

            }
        }

        return unvisitedNeighbours;

    }

    private void removeWallBetween(Point2D point1, Point2D point2){

        grid[(int)(point1.getY() + point2.getY()) /2 ][(int)(point1.getX() + point2.getX()) / 2] = '0';
        gui.setBlankNode((int)(point1.getX() + point2.getX()) / 2,(int)(point1.getY() + point2.getY()) /2 );

    }

}
