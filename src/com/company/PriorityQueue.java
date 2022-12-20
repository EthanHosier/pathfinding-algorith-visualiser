package com.company;

public class PriorityQueue {

    private PQNode startNode;

    //method to add a given gridNode to the priority node
    //priority == f(n) weight
    public void add(GridNode gridNode, double priority){

        PQNode nodeToAdd = new PQNode(gridNode,priority);

        if (startNode == null){
            startNode = nodeToAdd;
        }else{

            PQNode tempNode;

            //sets nodeToAdd as new Start node if priority > start node priority
            if (priority < startNode.getPriority()){
                tempNode = startNode;
                startNode = nodeToAdd;
                startNode.setNextNode(tempNode);
            }else {
                recursiveAdd(nodeToAdd, startNode);
            }
        }


    }

    //recursive method utilised in add() method, adding value to priority queue
    private void recursiveAdd(PQNode nodeToAdd, PQNode currentNode){

        //holds node after current node
        PQNode afterCurrentNode = currentNode.getNextNode();


        if (afterCurrentNode == null){

            //adds node to front of queue
            currentNode.setNextNode(nodeToAdd);


        }else{

            //continues recursiveAdd of nodeToAdd if its priority is still larger than next node's priority
            if (nodeToAdd.getPriority() < afterCurrentNode.getPriority()){
                //inserts nodeToAdd
                currentNode.setNextNode(nodeToAdd);
                nodeToAdd.setNextNode(afterCurrentNode);


            }else{

                recursiveAdd(nodeToAdd,afterCurrentNode);

            }
        }

    }

    //'pops' first node of Priority Queue, and returns it
    public GridNode pop(){

        //makes temporary copy of start node
        PQNode tempNode = startNode;

        //new start of PQ is assigned
        startNode = startNode.getNextNode();

        return tempNode.getGridNode();

    }

    //returns boolean of whether priority queue is empty
    public boolean isEmpty(){

        if (startNode == null){
            return true;
        }else{
            return false;
        }
    }

//private PQNode class (nodes solely used in Priority Queue class)
    private class PQNode{

        //GridNode value being stored
        private GridNode gridNode;

        private double priority;
        private PQNode nextNode;


        protected PQNode(GridNode gridNode, double priority){
            this.gridNode = gridNode;
            this.priority = priority;
        }

        //getters and setters
        public void setNextNode(PQNode nextNode) {
            this.nextNode = nextNode;
        }

        public GridNode getGridNode() {
            return gridNode;
        }

        public double getPriority() {
            return priority;
        }

        public PQNode getNextNode() {
            return nextNode;
        }

    }


}
