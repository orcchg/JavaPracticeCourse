package com.github.orcchg.nogeneric.matrix;

import java.util.Arrays;

import com.github.orcchg.nogeneric.matrix.Matrix;

public class SquareMatrix extends Matrix {
  
  public SquareMatrix(int dim) {
    mElements = new double[dim][dim];
  }
  
  public SquareMatrix(double[][] array) {
    if (array.length != array[0].length) {
      throw new RuntimeException("Non-squared input array!");
    }
    mElements = new double[array.length][array.length];
    System.arraycopy(array, 0, mElements, 0, array.length);
  }
  
  public SquareMatrix(final SquareMatrix obj) {
    super(obj);
  }
  
  public static SquareMatrix identity(int dim) {
    SquareMatrix mat = new SquareMatrix(dim);
    for (int i = 0; i < dim; ++i) {
      mat.mElements[i][i] = 1.0;
    }
    return mat;
  }
  
  public SquareMatrix minor(int row, int col) {
    SquareMatrix mat = new SquareMatrix(getDims() - 1);
    int row_off = 0;
    for (int i = 0; i < getRows(); ++i) {
      if (i == row) {
        row_off = -1;
        continue;
      }
      int col_off = 0;
      for (int j = 0; j < getCols(); ++j) {
        if (j == col) {
          col_off = -1;
          continue;
        }
        mat.mElements[i + row_off][j + col_off] = mElements[i][j];
      }
    }
    return mat;
  }

  public int getDims() {
    return mElements.length;
  }

  public SquareMatrix multiplyTo(final SquareMatrix rhs) {
    if (getDims() != rhs.getDims()) {
      throw new RuntimeException("Incompatible matrices!");
    }
    SquareMatrix mat = new SquareMatrix(getDims());
    for (int i = 0; i < getRows(); ++i) {
      for (int j = 0; j < getCols(); ++j) {
        double sum = 0.0;
        for (int k = 0; k < getCols(); ++k) {
          sum += mElements[i][k] * rhs.mElements[k][j];
        }
        mat.mElements[i][j] = sum;
      }
    }
    mElements = mat.mElements;
    return this;
  }
  
  public SquareMatrix mul(final SquareMatrix rhs) {
    SquareMatrix mat = new SquareMatrix(this);
    return mat.multiplyTo(rhs);
  }
  
  public double determinant() {
    if (getDims() == 1) {
      return mElements[0][0];
    }
    if (getDims() == 2) {
      return mElements[0][0] * mElements[1][1] - mElements[0][1] * mElements[1][0];
    }
    double det = 0.0;
    for (int i = 0; i < getCols(); ++i) {
      det += mElements[0][i] * Math.pow(-1.0, i) * minor(0, i).determinant();
    }
    return det;
  }
  
  public SquareMatrix inverse() throws SingularSquareMatrixException {
    SquareMatrix mat = getInversed();
    mElements = mat.mElements;
    return this;
  }
  
  public SquareMatrix getInversed() throws SingularSquareMatrixException {
    double det = determinant();
    if (elementEquals(det, 0.0)) {
      throw new SingularSquareMatrixException("Singular matrix!");
    }
    SquareMatrix mat = new SquareMatrix(getDims());
    for (int i = 0; i < getRows(); ++i) {
      for (int j = 0; j < getCols(); ++j) {
        mat.mElements[i][j] = Math.pow(-1.0, i + j) * minor(j, i).determinant() / det;
      }
    }
    return mat;
  }
}
