package com.treemoval.visualizer;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

//--------------------------------------------------------------------------------------------------
// ::GroundMesh
//
/**
 * The GroundMesh class builds the 3d ground mesh
 */
public class GroundMesh extends MeshView {

    private Point[][] points;

    private int length;
    private int width;
    private static final int HORIZONTAL_SCALE = 10;
    private static final int VERTICAL_SCALE = 3;

    //--------------------------------------------------------------------------------------------------
    // GroundMesh::GroundMesh
    //
    /**
     * Creates the GroundMesh with the specified length and width
     *
     * @param length the length of the GroundMesh (number of points)
     * @param width the width of the GroundMesh (number of points)
     */
    public GroundMesh(int length, int width) {

        this.length = length;
        this.width = width;

        generatePointArray();

        buildGroundMesh();

    }

    //--------------------------------------------------------------------------------------------------
    // GroundMesh::buildGroundMesh
    //
    private void buildGroundMesh() {

        // build the mesh
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(convertPointsToRawData());
        mesh.getTexCoords().addAll(generateTextureArray());
        mesh.getFaces().addAll(generateFaceArray());

        setMesh(mesh);

        // set the material
        final PhongMaterial groundMaterial = new PhongMaterial();
        groundMaterial.setDiffuseColor(Color.web("0x4a2a00"));
        groundMaterial.setSpecularColor(Color.BLACK);

        setMaterial(groundMaterial);

    }

    //--------------------------------------------------------------------------------------------------
    // GroundMesh::generatePointArray
    //
    /**
     * Generates the point data in an array. The x/z points lie on a width * length grid.
     * The y points are currently randomized
     */
    private void generatePointArray() {

        points = new Point[width][length];

        for(int x = 0; x < width; x++) {

            for(int z = 0; z < length; z++) {

                points[x][z] = new Point(
                        x * HORIZONTAL_SCALE,
                        ThreadLocalRandom.current().nextInt(0, 3) * VERTICAL_SCALE,
                        z * HORIZONTAL_SCALE);

            }

        }

    }

    //--------------------------------------------------------------------------------------------------
    // GroundMesh::convertPointsToRawData
    //
    /**
     * Converts the 2D array of Point objects to a 1D array of points to be accepted by the TriangleMesh class
     */
    private float[] convertPointsToRawData() {

        float[] pointsRaw = new float[width*length*3];

        for (int i = 0; i < width*length; i++) {

            Point point = points[i % width][i / length];

            pointsRaw[i*3] = point.x;
            pointsRaw[i*3+1] = point.y;
            pointsRaw[i*3+2] = point.z;

        }

/*      Print raw point data for debugging
        for (int i = 0; i < width*length; i++) {
            System.out.println(pointsRaw[i*3] + ", " + pointsRaw[i*3+1] + ", " + pointsRaw[i*3+2]);
        }
*/
        return pointsRaw;

    }

    //--------------------------------------------------------------------------------------------------
    // GroundMesh::generateTextureArray
    //
    /**
     * Generates the texture array. This is currently just an array filled with zeroes since the ground mesh
     * doesn't have any texture at this point.
     */
    private float[] generateTextureArray() {

        float[] texCoords = new float[width*length*2];
        Arrays.fill(texCoords, 0);

        return texCoords;

    }

    //--------------------------------------------------------------------------------------------------
    // GroundMesh::generateFaceArray
    //
    /**
     * Generates the face array. Uses indices of the points array to describe a face.
     *
     * Note that the order of the points dictates which angle the face will be visible from.
     */
    private int[] generateFaceArray() {

        int numFaces = (width-1)*(width-1)*2;
        int[] faces = new int[numFaces*6];

        for (int i = 0, f = 0; i < width*length - width; i++) {

            if(i % width == 0) continue;    // skip the first point of every row

            faces[f*6] = faces[f*6+1] = i;
            faces[f*6+2] = faces[f*6+3] = i-1;
            faces[f*6+4] = faces[f*6+5] = i-1 + width;

            f++; // next face

            faces[f*6] = faces[f*6+1] = i;
            faces[f*6+2] = faces[f*6+3] = i-1 + width;
            faces[f*6+4] = faces[f*6+5] = i + width;

            f++; //next face

        }

        /*
        for (int i = 0; i < numFaces; i++) {
            System.out.println(faces[i*6] + ", " + faces[i*6+1] + ", " + faces[i*6+2] + ", "
                    + faces[i*6+3] + ", " + faces[i*6+4] + ", " + faces[i*6+5]);
        }
        */

        return faces;

    }

}
