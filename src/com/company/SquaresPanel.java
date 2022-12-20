package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class SquaresPanel extends JPanel {

    //stores start and endNode locations (set as -1 at start to symbolise non-existant)
    Point2D startNode = new Point2D.Double(-1,-1);
    Point2D endNode = new Point2D.Double(-1,-1);

    //stores length of each row (in squares)
    int rowLength;

    //stores length of panel side in pixels
    int panelSideSizePixels;

    //stores references to all square objects in grid
    private Square[][] gridArray;

    //stors actual grid in char form
    private char[][] charGrid;

    //stores whether grid can be changed at given time
    private boolean drawable;

    public SquaresPanel(int rowLength, int panelSideSizePixels, JFrame mainFrame){

        drawable = true;

        this.rowLength = rowLength;
        this.panelSideSizePixels = panelSideSizePixels;

        this.setLayout(new GridLayout(rowLength,rowLength,1,1));

        gridArray = new Square[rowLength][rowLength];

        charGrid = new char[rowLength][rowLength];

        //temp Square object used in grid creation
        Square tempSquare;

        //fill gridArray with 'blank' Squares
        for (int i = 0; i < rowLength; i ++){

            for (int j = 0; j < rowLength; j ++){

               tempSquare = new Square(panelSideSizePixels / rowLength);

               gridArray[i][j] = tempSquare;
               this.add(tempSquare);

               charGrid[i][j] = '0';

            }

        }

        this.setBounds(1,1,panelSideSizePixels,panelSideSizePixels);
        this.setBackground(new Color(0xDAD9CD));

        //x and y co-ords for clicking square
        int x;
        int y;

        //mouse listener for dragging mouse
        this.addMouseMotionListener(new MouseMotionListener() {

            //temp variable used in retrieving current state of given square.
            char currentState;

            int xPosition;
            int yPosition;

            @Override
            public void mouseDragged(MouseEvent e) {

                //if grid is not enabled for drawing, method exited straight away
                if (!drawable){
                    return;
                }

                yPosition = (int) Math.floor((e.getY())/ (panelSideSizePixels/rowLength));
                xPosition = (int) Math.floor((e.getX())/ (panelSideSizePixels/rowLength));

                //method exited if location clicked outside of grid
                if (!((xPosition < rowLength && xPosition >= 0) && (yPosition < rowLength && yPosition >= 0))){
                    return;
                }

                currentState = charGrid[yPosition][xPosition];

                if (currentState != 's' && currentState != 'e') {

                    //left click = black
                    if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {

                        updateSquare('X', xPosition, yPosition);

                        return;

                    }

                    //right click = white
                    if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {

                        if (currentState == 'X') {
                            updateSquare('0', xPosition, yPosition);
                        }
                        return;

                    }

                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });

        //mouse listener for clicking mouse
       this.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent e) {

               //if grid is not enabled for drawing, method exited straight away
               if (!drawable){
                   return;
               }


               int yPosition = (int) Math.floor((e.getY())/ (panelSideSizePixels/rowLength));
               int xPosition = (int) Math.floor((e.getX())/ (panelSideSizePixels/rowLength));

               //method exited if location clicked outside of grid
               if (!((xPosition < rowLength && xPosition >= 0) && (yPosition < rowLength && yPosition >= 0))){
                   return;
               }

               char currentState = charGrid[yPosition][xPosition];


               if (currentState != 's' && currentState != 'e') {

                   //left click = black
                   if (e.getButton() == 1) {
                       updateSquare('X', xPosition, yPosition);
                       return;
                   }

                   //right click = white
                   if (e.getButton() == 3) {

                       if (currentState == 'X') {
                           updateSquare('0', xPosition, yPosition);
                           return;
                       }
                   }
               }

           }

           @Override
           public void mousePressed(MouseEvent e) {}

           @Override
           public void mouseReleased(MouseEvent e) {}

           @Override
           public void mouseEntered(MouseEvent e) {}

           @Override
           public void mouseExited(MouseEvent e) {}
       });


        this.getInputMap().put(KeyStroke.getKeyStroke('s'),"startNode");
        this.getActionMap().put("startNode",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                SquaresPanel.this.setStartNode();
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke('e'),"endNode");
        this.getActionMap().put("endNode",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                SquaresPanel.this.setEndNode();
            }
        });


    }

    //method to set new start node position
    public void setStartNode(){

        //if grid is not enabled for drawing, method exited straight away
        if (!drawable){
            return;
        }

        PointerInfo a = MouseInfo.getPointerInfo();
        Point point = new Point(a.getLocation());
        SwingUtilities.convertPointFromScreen(point, this);

        int x = (int) Math.floor((point.getX())/ (panelSideSizePixels/rowLength));
        int y = (int) Math.floor((point.getY())/ (panelSideSizePixels/rowLength));

        boolean inRange = (x < rowLength && x >= 0) && (y < rowLength && y >= 0);

        if (inRange && charGrid[y][x] != 's' && charGrid[y][x] != 'e'&& charGrid[y][x] != 'X') {
            //set previous start point to blank again
            updateSquare('0', (int) startNode.getX(), (int) startNode.getY());

            updateSquare('s', x, y);

            startNode.setLocation(x, y);
        }
    }


    //method to set new end node position
    public void setEndNode(){

        //if grid is not enabled for drawing, method exited straight away
        if (!drawable){
            return;
        }

        PointerInfo a = MouseInfo.getPointerInfo();
        Point point = new Point(a.getLocation());
        SwingUtilities.convertPointFromScreen(point, this);

        int x = (int) Math.floor((point.getX())/ (panelSideSizePixels/rowLength));
        int y = (int) Math.floor((point.getY())/ (panelSideSizePixels/rowLength));

        boolean inRange = (x < rowLength && x >= 0) && (y < rowLength && y >= 0);

        if (inRange && charGrid[y][x] != 's' && charGrid[y][x] != 'e' && charGrid[y][x] != 'X' ) {
            //set previous end point to blank again
            updateSquare('0', (int) endNode.getX(), (int) endNode.getY());

            updateSquare('e', x, y);


            endNode.setLocation(x, y);
        }
    }

    //used for when UCS and A* turn end and start nodes into path nodes in traversal
    public void reValidateStartAndEndNode(){
        updateSquare('e',(int)endNode.getX(),(int)endNode.getY());
        updateSquare('s',(int)startNode.getX(), (int)startNode.getY());
    }

    public void setBlankNode(int x, int y){

        gridArray[y][x].setState('0');
        charGrid[y][x] = '0';

    }

    public void setWallNode(int x, int y){

        gridArray[y][x].setState('X');
        charGrid[y][x] = 'X';

    }

    public void setActiveNode(int x, int y){

        gridArray[y][x].setState('a');
        charGrid[y][x] = 'a';

        this.repaint();

    }



    public void setPriorityNode(int x, int y){

        gridArray[y][x].setState('p');
        charGrid[y][x] = 'p';

    }

    public void setPathNode(int x, int y){

        gridArray[y][x].setState('*');
        charGrid[y][x] = '*';

    }

    //method to update squaresPanel and charGrid
    //catches ArrayIndexOutOfBoundsException to allow for when mouse gets just dragged out of panel
    private void updateSquare(char state, int x, int y){

        try {
            charGrid[y][x] = state;
            gridArray[y][x].setState(state);

            this.repaint();

        }catch ( Exception ArrayIndexOutOfBoundsException){};

    }

    //method to clear grid
    //boolean is whether to clear start and end nodes

    public void clearGrid(boolean clearStartEnd){

                for (int i = 0; i < rowLength; i ++){
                    for(int j = 0; j < rowLength; j ++){
                        setBlankNode(j,i);
                    }
                }

                if (clearStartEnd) {

                    startNode.setLocation(-1, -1);
                    endNode.setLocation(-1, -1);

                }else {
                    updateSquare('s',(int)startNode.getX(),(int)startNode.getY());
                    updateSquare('e',(int)endNode.getX(),(int)endNode.getY());
                }

            this.repaint();
    }

    //method to remove active, priority and final-path nodes from grid.
    public void clearGridOnlyTraversalNodes(){

        char tempChar;

        for (int i = 0; i < rowLength; i ++){
            for (int j = 0; j < rowLength; j ++){
                tempChar = charGrid[i][j];
                if (tempChar == 'a' || tempChar == 'p' || tempChar == '*'){
                    setBlankNode(j,i);
                }
            }
        }

        updateSquare('s',(int)startNode.getX(),(int)startNode.getY());
        updateSquare('e',(int)endNode.getX(),(int)endNode.getY());

        this.repaint();

    }


    public char[][] getCharGridArray(){
        return charGrid;
    }

    public Point2D getStartCoordinates(){
        return startNode;
    }

    public Point2D getEndCoordinates(){
        return endNode;
    }

    public boolean hasValidStartAndEndNode(){return ((int) startNode.getX() != -1 && (int) startNode.getY() != -1) && ((int) endNode.getX() != -1 && (int) endNode.getY() != -1);}

    public void setDrawable(boolean b){
        drawable = b;
    }

    //square inner class used within SquaresPanel
    private class Square extends JLabel {

        /*stores 'state' of square


        s = start
        e = end

        0 = blank
        X = obstacle

        a = active
        p = priority
        * = path

        */

        public Square(int pixelSize){

            this.setSize(new Dimension(pixelSize - 2, pixelSize - 2));
            this.setBackground(Color.white);
            this.setOpaque(true);
            this.setFont(new Font("Sarif Sans", Font.BOLD, (int) Math.floor(pixelSize * 0.9)));

        }

        //method to update square to according colour
        private void setState(char state) {

            if (state == '0'){
                this.setBackground(Color.white);
                this.setText(null);
                return;
            }

            if (state == 'X'){
                this.setBackground(Color.black);
                this.setText(null);
                return;
            }

            if (state == 's'){
                this.setBackground(new Color(0x37CFC5));
                this.setText(" S");
                return;
            }

            if (state == 'e'){
                this.setBackground(new Color(0xCF81BB));
                this.setText(" E");
                return;
            }

            if (state == 'a'){
                this.setBackground(new Color(0x46CF22));
                return;
            }

            if (state == 'p'){
                this.setBackground(new Color(0xFFEE00));
                return;
            }

            if (state == '*'){
                this.setBackground(new Color(0xE60A00));
                return;
            }

        }
    }
}



