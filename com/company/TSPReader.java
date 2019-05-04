package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TSPReader {

    int[][] costMatrix;

    public TSPReader(String path) {
        try {
            int dim = 0;
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNext() && dim == 0) {
                if (sc.hasNextInt())
                    dim = sc.nextInt();
                else
                    sc.next();
            }
            while (sc.hasNext() && !sc.next().equals("NODE_COORD_SECTION"));
            double[][] coordinates = new double[dim][2];
            for (int i = 0; i < dim; i++){
                sc.nextInt();
                coordinates[i][0] = Double.parseDouble(sc.next());
                coordinates[i][1] = Double.parseDouble(sc.next());
            }
            this.costMatrix = createCostMatrix(coordinates);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    int[][] getCostMatrix() {
        return costMatrix;
    }

    int[][] createCostMatrix(double[][] coordinates){
        int[][] costMatrix = new int[coordinates.length][coordinates.length];
        for (int i = 0; i < costMatrix.length; i++){
            for (int j = 0; j < costMatrix.length; j++){
                costMatrix[i][j] = -1;
                if (i != j) {
                    double xdiff = coordinates[j][0] - coordinates[i][0];
                    double ydiff = coordinates[j][1] - coordinates[i][1];
                    costMatrix[i][j] = (int) Math.round(Math.sqrt(xdiff * xdiff + ydiff * ydiff));
                }
            }
        }
        return costMatrix;
    }

    void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++){
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

}
