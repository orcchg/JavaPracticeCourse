package com.github.orcchg.nogeneric.matrix;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.orcchg.nogeneric.matrix.Matrix;
import com.github.orcchg.nogeneric.matrix.SquareMatrix;

public class TestMatrix {
  private static double epsilon = 1e-11;
  private static Random rng = new Random();
  
  private Matrix lhs = null;
  private Matrix rhs = null;
  
  private SquareMatrix identity = null;
  private SquareMatrix square_lhs = null;
  private SquareMatrix square_rhs = null;
  
  @Before
  public void setUp() {
    int rows = Math.abs(rng.nextInt()) % 10 + 1;
    int cols = Math.abs(rng.nextInt()) % 10 + 1;
    int dim = Math.abs(rng.nextInt() % 10) + 1;
    
    lhs = new Matrix(rows, cols);
    rhs = new Matrix(rows, cols);
    
    double multiplier = rng.nextInt(10) + 1;
    
    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        lhs.setElement(i, j, rng.nextDouble() * multiplier);
        rhs.setElement(i, j, rng.nextDouble() * multiplier);
      }
    }
    
    identity = SquareMatrix.identity(dim);
    square_lhs = new SquareMatrix(dim);
    square_rhs = new SquareMatrix(dim);
    for (int i = 0; i < dim; ++i) {
      for (int j = 0; j < dim; ++j) {
        square_lhs.setElement(i, j, rng.nextDouble() * multiplier);
        square_rhs.setElement(i, j, rng.nextDouble() * multiplier);
      }
    }
  }
  
  @After
  public void tearDown() {
  }
  
  @Test
  public void testAdditionValue() {
    double value = rng.nextDouble() * (rng.nextInt(10) + 1);
    Matrix mat = new Matrix(lhs);
    mat.add(value);
    assertNotNull("Null matrix after add()", mat);
    Matrix aux_mat = lhs.sum(value);
    assertNotNull("Null matrix after sum()", aux_mat);
    assertTrue("Addition malfunctions", mat.equals(aux_mat));
  }
  
  @Test
  public void testSubstractionValue() {
    double value = rng.nextDouble() * (rng.nextInt(10) + 1);
    Matrix mat = new Matrix(lhs);
    mat.substract(value);
    assertNotNull("Null matrix after substract()", mat);
    Matrix aux_mat = lhs.diff(value);
    assertNotNull("Null matrix after diff()", aux_mat);
    assertTrue("Substraction malfunctions", mat.equals(aux_mat));
  }
  
  @Test
  public void testMultiplicationValue() {
    double value = rng.nextDouble() * (rng.nextInt(10) + 1);
    Matrix mat = new Matrix(lhs);
    mat.multiplyTo(value);
    assertNotNull("Null matrix after multiplyTo()", mat);
    Matrix aux_mat = lhs.mul(value);
    assertNotNull("Null matrix after mul()", aux_mat);
    assertTrue("Multiplication malfunctions", mat.equals(aux_mat));
  }
  
  @Test
  public void testMultiplicationMatrix() {
    SquareMatrix mat = new SquareMatrix(square_lhs);
    mat.multiplyTo(identity);
    assertTrue("Matrix multiplyTo() malfunctions", mat.equals(square_lhs));
    SquareMatrix aux_mat = new SquareMatrix(square_rhs);
    aux_mat.multiplyTo(identity);
    assertTrue("Matrix multiplyTo() malfunctions", aux_mat.equals(square_rhs));
  }
  
  @Test
  public void testDeterminant() {
    double identity_det = identity.determinant();
    assertEquals("Identity determinant is not equal to 1", 1.0, identity_det, epsilon);
  }
  
  @Test
  public void testTransposition() {
    Matrix mat = identity.getTransposed();
    assertEquals("Rows not equal", mat.getRows(), identity.getRows());
    assertEquals("Cols not equal", mat.getCols(), identity.getCols());
    for (int i = 0; i < mat.getRows(); ++i) {
      for (int j = 0; j < mat.getCols(); ++j) {
        assertEquals("Elements not equal", mat.getElement(i, j), identity.getElement(i, j), epsilon);
      }
    }
    
    double[][] values = new double[][] {{1, 2, 3, 4, 5, 6, 7},
                                        {8, 9, 10, 11, 12, 13, 14},
                                        {15, 16, 17, 18, 19, 20, 21},
                                        {22, 23, 24, 25, 26, 27, 28}};
    
    double[][] tr_values = new double[][] {{1, 8, 15, 22},
                                           {2, 9, 16, 23},
                                           {3, 10, 17, 24},
                                           {4, 11, 18, 25},
                                           {5, 12, 19, 26},
                                           {6, 13, 20, 27},
                                           {7, 14, 21, 28}};
    
    Matrix arr_mat = new Matrix(values);
    Matrix arr_mat_tr = arr_mat.getTransposed();
    Matrix tr_arr_mat = new Matrix(tr_values);
    assertTrue("Transposition malfunctions with preset arrays", arr_mat_tr.equals(tr_arr_mat));
  }
  
  @Test
  public void testInverseMatrix() {
    try {
      SquareMatrix mat_lhs = square_lhs.getInversed();
      mat_lhs.multiplyTo(square_lhs);
      assertTrue("Inversion malfunctions", mat_lhs.equals(identity));
    } catch(SingularSquareMatrixException expected) {
      // RNG may generate singular matrix, it is expected
    }
  }
}
