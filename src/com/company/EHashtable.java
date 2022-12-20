package com.company;


public class EHashtable {

    //stores first listNode of each sub list per bucket
    private ListNode[] buckets;

    //constructor
    public EHashtable(int length){
        buckets = new ListNode[2*(length)-1];
    }

    //method to remove a given gridNode from the EHastable
    public void remove(GridNode gridNode){

        int sum = gridNode.getxVal() + gridNode.getyVal();

        ListNode lNodeOn = buckets[sum];

        //removes node within bucket at start of list if node is node looking for
        if (lNodeOn.getGridNode() == gridNode){
            buckets[sum] = lNodeOn.getNextLNode();
        }

        else {

            boolean removed = false;

            //iterates through each lNode, checking if next node is the grid node looking for, and removes it if it is.
            while (removed == false){

                //if node after lNodeOn is node to remove, 'removes' it
                if (lNodeOn.getNextLNode().getGridNode() == gridNode){
                    lNodeOn.setNextListNode(lNodeOn.getNextLNode().getNextLNode());
                    removed = true;
                }
                else {
                    lNodeOn = lNodeOn.getNextLNode();
                }

            }

        }

    }

    //method to check if given gridNode is in EHashtable, and add it if it isnt.
    //returns true if node was added
    //returns false if node was not added (was already present in EHashtable)
    public boolean smartAdd(GridNode gridNode) {

        int sum = gridNode.getxVal() + gridNode.getyVal();

        //node to start from is node at bucket
        ListNode firstLNode = buckets[sum];

        //adds lNode to bucket (start of list) if list is empty
        if (firstLNode == null) {
            buckets[sum] = new ListNode(gridNode);
            return true;
        }

        return recursiveSmartAdd(firstLNode,gridNode);

        }

    //used in EHashtable.smartAdd()
    //returns true if node was added
    //returns false if node was not added (was already present in EHashtable)
    private boolean recursiveSmartAdd(ListNode nodeOn, GridNode gridNodeToAdd){

        GridNode tempGN = nodeOn.getGridNode();

        if ((tempGN.getxVal() == gridNodeToAdd.getxVal()) && (tempGN.getyVal() == gridNodeToAdd.getyVal())){

            //update parent node if new g value is smaller than g val before
            if (tempGN.getG() > gridNodeToAdd.getG()){
                tempGN.setParentGNode(gridNodeToAdd.getParentGNode());
            }

            //node is present therefore not added
            return false;
        }

        //if have reached end of list, adds this node to end of list
        if (nodeOn.getNextLNode() == null){
            nodeOn.setNextListNode(new ListNode(gridNodeToAdd));
            return true;
        }


        else{
            return recursiveSmartAdd(nodeOn.getNextLNode(),gridNodeToAdd);
        }

    }

    public boolean checkFor(GridNode gridNode){

        int sum = gridNode.getxVal() + gridNode.getyVal();

        ListNode firstLNode = buckets[sum];

        //instantly returns false if bucket pointer is null
        if (firstLNode == null){
            return false;
        }

        return recursiveCheckFor(firstLNode,gridNode);

    }


    //used in EHashtable.checkFor()
    //returns true if is present, false if isn't
    private boolean recursiveCheckFor(ListNode lNodeOn, GridNode gridNodeCheckingFor ){

        GridNode tempGN = lNodeOn.getGridNode();

        //if current GN is required GN, returns true
        if ((tempGN.getxVal() == gridNodeCheckingFor.getxVal()) && (tempGN.getyVal() == gridNodeCheckingFor.getyVal())){
            return true;
        }

        //if no more LNodes in list after this node, therefore GN looking for is not present
        if (lNodeOn.getNextLNode() == null){
            return false;
        }

        return recursiveCheckFor(lNodeOn.getNextLNode(), gridNodeCheckingFor);

    }

    //internal class ListNode only used within EHashtable class
    private class ListNode{

        //gridNode stored within ListNode
        private GridNode gridNode;

        //pointerToNextNode
        private ListNode nextListNode;

        protected ListNode(GridNode gridNode){

            this.gridNode = gridNode;

        }

        //getters + setters
        public GridNode getGridNode() {
            return gridNode;
        }

        public ListNode getNextLNode() {
            return nextListNode;
        }

        public void setNextListNode(ListNode listNode) {
            this.nextListNode = listNode;
        }
    }

}
