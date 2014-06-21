package com.github.orcchg.nogeneric.matrix;

import java.util.Arrays;

public class SquareMatrix {
  private double[][] mElements;
  
  public SquareMatrix(int dim) {
    mElements = new double[dim][dim];
  }
  
  public SquareMatrix(double[][] array) {
    if (array.length != array[0].length) {
      System.err.println("Non-squared input array!");
      throw new RuntimeException();
    }
    mElements = new double[array.length][array.length];
    System.arraycopy(array, 0, mElements, 0, array.length);
  }
  
  public SquareMatrix(final SquareMatrix obj) {
    mElements = obj.mElements.clone();
  }
  
  public SquareMatrix minor(int row, int col) {
    SquareMatrix mat = new SquareMatrix(mElements.length - 1);
    int row_off = 0;
    for (int i = 0; i < mElements.length; ++i) {
      if (i == row) {
        row_off = -1;
        continue;
      }
      int col_off = 0;
      for (int j = 0; j < mElements.length; ++j) {
        if (j == col) {
          col_off = -1;
          continue;
        }
        mat.mElements[i + row_off][j + col_off] = mElements[i][j];
      }
    }
    return mat;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(mElements);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SquareMatrix other = (SquareMatrix) obj;
    if (!Arrays.deepEquals(mElements, other.mElements))
      return false;
    return true;
  }

  public double getElement(int row, int col) {
    return mElements[row][col];
  }

  public int getDims() {
    return mElements.length;
  }

  public int totalElements() {
    return getDims() * getDims();
  }

  public SquareMatrix add(final SquareMatrix rhs) {
    if (rhs.getDims() != getDims()) {
      System.err.println("Incompatible matrices!");
      throw new RuntimeException();
    }
    for (int i = 0; i < mElements.length; ++i) {
      for (int j = 0; j < mElements.length; ++j) {
        mElements[i][j] += rhs.mElements[i][j];
      }
    }
    return this;
  }
  
  public SquareMatrix add(double value) {
    for (int i = 0; i < mElements.length; ++i) {
      for (int j = 0; j < mElements.length; ++j) {
        mElements[i][j] += value;
      }
    }
    return this;
  }
  
  public SquareMatrix sum(final SquareMatrix rhs) {
    SquareMatrix mat = new SquareMatrix(this);
    return mat.add(rhs);
  }
  
  public SquareMatrix sum(double value) {
    SquareMatrix mat = new SquareMatrix(this);
    return mat.add(value);
  }

  public SquareMatrix substract(final SquareMatrix rhs) {
    return add(rhs.multiplyTo(-1.0));
  }
  
  public SquareMatrix substract(double value) {
    return add(-value);
  }
  
  public SquareMatrix diff(final SquareMatrix rhs) {
    SquareMatrix mat = new SquareMatrix(this);
    return mat.substract(rhs);
  }
  
  public SquareMatrix diff(double value) {
    SquareMatrix mat = new SquareMatrix(this);
    return mat.substract(value);
  }

  public SquareMatrix multiplyTo(final SquareMatrix rhs) {
    SquareMatrix mat = new SquareMatrix(mElements.length);
    for (int i = 0; i < mElements.length; ++i) {
      for (int j = 0; j < mElements.length; ++j) {
        double sum = 0.0;
        for (int k = 0; k < mElements.length; ++k) {
          sum += mElements[i][k] * rhs.mElements[k][j];
        }
        mat.mElements[i][j] = sum;
      }
    }
    mElements = mat.mElements;
    return this;
  }
  
  public SquareMatrix multiplyTo(double value) {
    for (int i = 0; i < mElements.length; ++i) {
      for (int j = 0; j < mElements.length; ++j) {
        mElements[i][j] *= value;
      }
    }
    return this;
  }
  
  public SquareMatrix mul(final SquareMatrix rhs) {
    SquareMatrix mat = new SquareMatrix(mElements.length);
    for (int i = 0; i < mElements.length; ++i) {
      for (int j = 0; j < mElements.length; ++j) {
        double sum = 0.0;
        for (int k = 0; k < mElements.length; ++k) {
          sum += mElements[i][k] * rhs.mElements[k][j];
        }
        mat.mElements[i][j] = sum;
      }
    }
    return mat;
  }
  
  public SquareMatrix mul(double value) {
    SquareMatrix mat = new SquareMatrix(this);
    return mat.multiplyTo(value);
  }
  
  public double determinant() {
    if (mElements.length == 1) {
      return mElements[0][0];
    }
    if (mElements.length == 2) {
      return mElements[0][0] * mElements[1][1] - mElements[0][1] * mElements[1][0];
    }
    double det = 0.0;
    for (int i = 0; i < mElements.length; ++i) {
      det += mElements[0][i] * Math.pow(-1.0, i) * minor(0, i).determinant();
    }
    return det;
  }
  
  public SquareMatrix transpose() {
    SquareMatrix mat = getTransposed();
    mElements = mat.mElements;
    return this;
  }
  
  public SquareMatrix getTransposed() {
    SquareMatrix mat = new SquareMatrix(mElements.length);
    for (int i = 0; i < mElements.length; ++i) {
      for (int j = 0; j < mElements.length; ++j) {
        mat.mElements[j][i] = mElements[i][j];
      }
    }
    return mat;
  }
  
  public SquareMatrix inverse() {
    SquareMatrix mat = getInversed();
    mElements = mat.mElements;
    return this;
  }
  
  public SquareMatrix getInversed() {
    double det = determinant();
    if (det == 0.0) {
      System.err.println("Singular matrix!");
      throw new RuntimeException();
    }
    SquareMatrix mat = new SquareMatrix(mElements.length);
    for (int i = 0; i < mElements.length; ++i) {
      for (int j = 0; j < mElements.length; ++j) {
        mat.mElements[i][j] = Math.pow(-1.0, i + j) * minor(j, i).determinant() / det;
      }
    }
    return mat;
  }
  
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < mElements.length; ++i) {
      for (int j = 0; j + 1 < mElements.length; ++j) {
        str.append(Double.toString(mElements[i][j])).append(", ");
      }
      str.append(Double.toString(mElements[i][mElements.length - 1])).append("\n");
    }
    return str.toString();
  }
}
