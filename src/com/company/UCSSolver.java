package com.company;


import java.util.ArrayList;
import java.util.Iterator;

//ONLY WORKS WITH SQUARE MAZES
public class UCSSolver {

    //stores grid in 2d char array form
    private char[][] grid;
    private char[][] solvedGrid;

    private int rowLength;

    private int xValStart;
    private int yValStart;

    private int xValEnd;
    private int yValEnd;

    //'open list'
    private PriorityQueue openPQ;
    private EHashtable openEHashTable;

    //'closed list'
    private EHashtable closedEHashTable;

    private UCSGridNode endNode;
    private SquaresPanel gui;

    private int sleepTime;

    //constructor
    public UCSSolver(char[][] twoArrayDGrid, int xValStart, int yValStart, int xValEnd, int yValEnd, SquaresPanel gui, int sleepTime){

        this.grid = twoArrayDGrid;
        this.solvedGrid = twoArrayDGrid;

        this.rowLength = twoArrayDGrid.length;

        this.xValStart = xValStart;
        this.yValStart = yValStart;

        this.xValEnd = xValEnd;
        this.yValEnd = yValEnd;

        this.gui = gui;
        this.sleepTime = sleepTime;

        //initializing 'open' and 'closed lists'
        openPQ = new PriorityQueue();
        openEHashTable = new EHashtable(rowLength);

        closedEHashTable = new EHashtable(rowLength);

        UCSGridNode startNode = new UCSGridNode("s",xValStart,yValStart);

        //adding start node to open lists (priority is 0 for openPQ)
        openPQ.add(startNode,0);
        openEHashTable.smartAdd(startNode);

    }

    public boolean solve(){

        //node with least f value
        GridNode q;

        boolean solved;

        //repeats until 'open list' is empty
        while (!openPQ.isEmpty()){

            //'pop' q from open list
            q = openPQ.pop();
            openEHashTable.remove(q);

            //generates successors to q and adds them accordingly to open list
            solved = generateSuccessors((UCSGridNode) q);

            closedEHashTable.smartAdd(q);
            gui.setActiveNode(q.getxVal(),q.getyVal());

            //break out of loop early if solved
            if (solved == true){
                return true;
            }
        }

        return false;
    }

    //returns true if one of successors of q is end node (allows for early break)
    public boolean generateSuccessors(UCSGridNode q){

        ArrayList<UCSGridNode> successors = new ArrayList<>();

        //above:
        if (q.getyVal() != 0){
            successors.add(new UCSGridNode("p",q.getxVal(),q.getyVal() - 1));
        }

        //below:
        if (q.getyVal() != rowLength - 1){
            successors.add(new UCSGridNode("p",q.getxVal(),q.getyVal() + 1));
        }

        //left:
        if (q.getxVal() != 0){
            successors.add(new UCSGridNode("p",q.getxVal()-1,q.getyVal()));
        }

        //right:
        if (q.getxVal() != rowLength - 1){
            successors.add(new UCSGridNode("p",q.getxVal()+1,q.getyVal()));
        }

        //check for end node, set weight of node ,and remove node if is actually obstacle node

        for(Iterator<UCSGridNode> iterator = successors.iterator(); iterator.hasNext();){

            UCSGridNode gn = iterator.next();

            //set parent node of gn to q
            gn.setParentGNode(q);

            if ((gn.getxVal() == xValEnd) && (gn.getyVal() == yValEnd)){
                //end node has been found so exit early
                endNode = gn;
                return true;
            }

            gn.setF(q.getG(),xValEnd,yValEnd);

            //remove node from successors if is actually wall node;
            if ((grid[gn.getyVal()][gn.getxVal()] == 'X')){
                iterator.remove();
            }

        }

        //add successors to open list if not already in (open list or close list):

        //stores whether present in closed list
        Boolean alreadyPresentClosed;


        //stores whether node was added through smartAdd() to open hashtable
        Boolean added;

        for (UCSGridNode gn: successors){

            //checks if gn present in closed list (hashtable)
            alreadyPresentClosed = closedEHashTable.checkFor(gn);

            if (!alreadyPresentClosed){
                //smartAdds to open hashtable
                added = openEHashTable.smartAdd(gn);

                if (added == true){
                    //if added to open hashtable through smartAdd, added to open PQ
                    openPQ.add(gn,gn.getF());

                    //update gui
                    gui.setPriorityNode(gn.getxVal(),gn.getyVal());
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        //returns false here as no successor node was end node
        return false;
    }

    public void guiSetSolvedGrid(){

        gui.setPathNode(xValStart,yValStart );
        recursiveSetSolvedGrid(endNode.getParentGNode());
        gui.setPathNode(xValEnd,yValEnd);

    }

    private void recursiveSetSolvedGrid(GridNode nodeOn){

        if (!((nodeOn.getxVal() == xValStart) && (nodeOn.getyVal() == yValStart))){
            grid[nodeOn.getyVal()][nodeOn.getxVal()] = '*';
            recursiveSetSolvedGrid(nodeOn.getParentGNode());

            gui.setPathNode(nodeOn.getxVal(),nodeOn.getyVal());

            try {
                Thread.sleep(sleepTime * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private class UCSGridNode extends GridNode {

        //f(n) = g(n) + h(n) for a*
        private int h;

        public UCSGridNode(String letter, int xVal, int yVal) {
            super(letter, xVal, yVal);
        }

        public void setF(int gq, int xValEnd, int yValEnd){

            //uses Manhattan distance
            this.g = gq + 1;


            this.f = g;

        }

    }

}

