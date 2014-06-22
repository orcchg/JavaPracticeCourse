package com.github.orcchg.nogeneric.matrix;

import com.github.orcchg.nogeneric.matrix.SquareMatrix;

public class AlgebraDemo {

  public static void main(String[] args) {
    SquareMatrix mat1 = new SquareMatrix(new double[][] {{1, 2, 3}, {4, 5, 6}, {2, 5, 3}});
    SquareMatrix identity = new SquareMatrix(new double[][] {{1, 0}, {0, 1}});
    SquareMatrix identity3 = new SquareMatrix(new double[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
    
    System.out.println(mat1.determinant());
    System.out.println(identity.determinant());
    System.out.println("\n");
    
    System.out.println(mat1.toString());
    System.out.println(identity.toString());
    
    System.out.println(mat1.getTransposed());
    System.out.println(identity.getTransposed());
    
    System.out.println(mat1.determinant());
    System.out.println(identity.determinant());
  }
}
