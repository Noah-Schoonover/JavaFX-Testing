package com.treemoval.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.*;

//----------------------------------------------------------------------------------------------------------------------
// ::Forest
//
/**
 * The Forest class holds and manipulates an ArrayList of Tree objects.
 *
 * Includes methods for reading and writing to file, calculating the distance between two trees, and printing the
 * coordinates of every tree in the forest.
 *
 * @author Garrett Evans
 * @version 1.1
 *
 */
public class Forest {

    List<Tree> trees = new ArrayList<>();

    //--------------------------------------------------------------------------------------------------
    // Forest::Forest
    //
    /**
     * The default constructor currently instantiates with 20 randomly positioned trees.
     *
     * todo the default constructor should create an empty forest, and another method should
     *      be created to add random trees to it. Alternatively, we can overload the constructor
     *      to take in the number of trees and other parameters for a random forest.
     */
    public Forest() {
        for(int i = 0; i < 20; i++){
            Random rand = new Random();
            double x = rand.nextInt(10) + rand.nextDouble();
            double y = rand.nextInt(10) + rand.nextDouble();
            double z = 0;

            this.trees.add(new Tree(x, y, z));
        }
    }

    //--------------------------------------------------------------------------------------------------
    // Forest::readFromFile
    //
    /**
     * Reads tree data from a CSV file and stores it in the forest.
     *
     * todo This method should probably accept an enum value (APPEND or CLEAR)
     *      to determine whether or not to clear before adding trees from the file.
     *
     * @param path the path to the CSV file to be read
     * @throws IOException if the CSV file is not found
     */
    public void readFromFile(String path) throws IOException {

        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        this.trees.clear();

        String line;

        while ((line = br.readLine()) != null) {
            // System.out.println(line);
            String[] dets = line.split(",");
            int x = Integer.parseInt(dets[0]);
            int y = Integer.parseInt(dets[1]);
            int z = Integer.parseInt(dets[2]);
            this.trees.add(new Tree(x, y, z));
            // for (String string : dets) { System.out.println(string); }
        }

        br.close();
    }

    //--------------------------------------------------------------------------------------------------
    // Forest::distance
    //
    /**
     * Calculates the euclidean distance between two trees.
     *
     * @param a the first tree
     * @param b the second tree
     * @return the distance between trees a and b.
     */
    public static double distance(Tree a, Tree b) {
        double x1 = a.getX();
        double y1 = a.getY();
        double z1 = a.getZ();

        double x2 = b.getX();
        double y2 = b.getY();
        double z2 = b.getZ();

        return sqrt(pow(x2-x1, 2) + pow(y2-y1, 2) + pow(z2-z1, 2));
    }

    //--------------------------------------------------------------------------------------------------
    // Forest::getTree
    //
    /**
     * Returns the tree at a specified index in the forest.
     *
     * todo should handle out of bounds indexing without crashing
     *
     * @param x the index value of the desired tree
     * @return the Tree object, if found.
     */
    public Tree getTree(int x) {
        return this.trees.get(x);
    }

    //--------------------------------------------------------------------------------------------------
    // Forest::listTrees
    //
    /**
     * Outputs the coordinates of every tree in the forest.
     */
    public void listTrees() {
        int i = 0;
        for(Tree tree : this.trees) {
            i++;
            System.out.println("Tree " + i + "; " + tree);
        }
        System.out.println();
    }

    //--------------------------------------------------------------------------------------------------
    // Forest::main
    //
    /**
     * Simple forest testing; instantiates and prints a random forest, then clears the forest
     * and reads data from a file to print to console as well.
     *
     * todo Should be tested with JUnit testing instead of using main function.
     *
     * @param args none
     */
    public static void main(String[] args) {

        Forest forest = new Forest();
        forest.listTrees();
        System.out.println("The distance between the first two trees is: " +
                distance(forest.getTree(0), forest.getTree(1)) + "\n");


        try {

            forest.readFromFile("forest.txt");
            forest.listTrees();
            System.out.println("The distance between the first two trees is: " +
                    distance(forest.getTree(0), forest.getTree(1)) + "\n");

        } catch (IOException e) {

            System.out.println("Sample forest file not found!");

        }

    }

}