package com.treemoval.data;

//----------------------------------------------------------------------------------------------------------------------
// ::Tree
//
/**
 * The Tree class holds euclidean coordinates for the location of a tree along with various helper functions.
 *
 * @author Garrett Evans
 * @version 1.1
 */
public class Tree {

    private double x;
    private double y;
    private double z;
    private int cut = -1;   // todo this should be an enum


    //--------------------------------------------------------------------------------------------------
    // Tree::Tree
    //
    /**
     * This constructor instantiates a new tree at the origin of the euclidean space.
     */
    public Tree() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    //--------------------------------------------------------------------------------------------------
    // Tree::Tree
    //
    /**
     * This constructor instantiates a new tree with specified coordinates.
     *
     * todo can we use default parameter values here?
     *
     * @param x the x coordinate of the tree
     * @param y the y coordinate of the tree
     * @param z the z coordinate (elevation) of the tree
     */
    public Tree(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //--------------------------------------------------------------------------------------------------
    // Tree::toString
    //
    /**
     * Overrides the inherited toString method to print coordinates.
     *
     * @return a formatted string of the x, y, and z coordinates.
     */
    @Override
    public String toString() {
        return "x: " + getX() + ", y: " + getY() + ", z: " + getZ();
    }

    //--------------------------------------------------------------------------------------------------
    // Tree Getters and Setters
    //

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getZ() { return z; }
    public void setZ(double z) { this.z = z; }

    public int getCut() {return cut;}

}
