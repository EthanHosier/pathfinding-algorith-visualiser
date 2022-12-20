package com.company;

//node on the grid
public abstract class GridNode {

    private GridNode parentGNode;

    //f(n) = g(n)
    protected int f;

    //g(n)
    protected int g;

    //coordinates of node within grid
    protected int xVal;
    protected int yVal;

    protected String letter;

    public GridNode(String letter, int xVal, int yVal){
        this.letter = letter;
        this.xVal = xVal;
        this.yVal = yVal;

    }



    public int getxVal() {
        return xVal;
    }

    public int getyVal() {
        return yVal;
    }

    //////

    public void setParentGNode(GridNode parentGNode){
        this.parentGNode = parentGNode;
    }


    public int getF(){
        return f;
    }

    public int getG(){
        return g;
    }

    public GridNode getParentGNode(){
        return parentGNode;
    }

}
