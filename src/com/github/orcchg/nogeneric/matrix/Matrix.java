package com.github.orcchg.nogeneric.matrix;

import java.util.Arrays;

public class Matrix {
  protected static final double epsilon = 1e-11;
  protected double[][] mElements;
  
  protected Matrix() {
    mElements = null;
  }
  
  public Matrix(int rows, int cols) {
    mElements = new double[rows][cols];
  }
  
  public Matrix(double[][] array) {
    mElements = new double[array.length][array[0].length];
    System.arraycopy(array, 0, mElements, 0, array.length);
  }
  
  public Matrix(final Matrix obj) {
    mElements = obj.mElements.clone();
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
    Matrix other = (Matrix) obj;
    if (getRows() != other.getRows() ||
        getCols() != other.getCols()) {
      throw new RuntimeException("Incompatible matrices!");
    }
    for (int i = 0; i < getRows(); ++i) {
      for (int j = 0; j < getCols(); ++j) {
        if (!elementEquals(mElements[i][j], other.mElements[i][j])) {
          return false;
        }
      }
    }
    return true;
  }
  
  public final int getRows() {
    return mElements.length;
  }
  
  public final int getCols() {
    return mElements[0].length;
  }
  
  public double getElement(int row, int col) {
    return mElements[row][col];
  }
  
  public void setElement(int row, int col, double value) {
    mElements[row][col] = value;
  }
  
  public final int totalElements() {
    return getRows() * getCols();
  }
  
  public Matrix add(final Matrix rhs) {
    if (rhs.getCols() != getCols() ||
        rhs.getRows() != getRows()) {
      throw new RuntimeException("Incompatible matrices!");
    }
    for (int i = 0; i < getRows(); ++i) {
      for (int j = 0; j < getCols(); ++j) {
        mElements[i][j] += rhs.mElements[i][j];
      }
    }
    return this;
  }
  
  public Matrix add(double value) {
    for (int i = 0; i < getRows(); ++i) {
      for (int j = 0; j < getCols(); ++j) {
        mElements[i][j] += value;
      }
    }
    return this;
  }
  
  public Matrix sum(final Matrix rhs) {
    Matrix mat = new Matrix(this);
    return mat.add(rhs);
  }
  
  public Matrix sum(double value) {
    Matrix mat = new Matrix(this);
    return mat.add(value);
  }

  public Matrix substract(final Matrix rhs) {
    return add(rhs.multiplyTo(-1.0));
  }
  
  public Matrix substract(double value) {
    return add(-value);
  }
  
  public Matrix diff(final Matrix rhs) {
    Matrix mat = new Matrix(this);
    return mat.substract(rhs);
  }
  
  public Matrix diff(double value) {
    Matrix mat = new Matrix(this);
    return mat.substract(value);
  }
  
  public Matrix multiplyTo(double value) {
    for (int i = 0; i < getRows(); ++i) {
      for (int j = 0; j < getCols(); ++j) {
        mElements[i][j] *= value;
      }
    }
    return this;
  }
  
  public Matrix mul(double value) {
    Matrix mat = new Matrix(this);
    return mat.multiplyTo(value);
  }
  
  public Matrix multiplyTo(final Matrix rhs) {
    if (getCols() != rhs.getRows()) {
      throw new RuntimeException("Incompatible matrices!");
    }
    Matrix mat = new Matrix(rhs.getRows(), rhs.getCols());
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
  
  public Matrix mul(final Matrix rhs) {
    Matrix mat = new Matrix(this);
    return mat.multiplyTo(rhs);
  }
  
  public Matrix transpose() {
    Matrix mat = getTransposed();
    mElements = mat.mElements;
    return this;
  }
  
  public Matrix getTransposed() {
    Matrix mat = new Matrix(getCols(), getRows());
    for (int i = 0; i < getRows(); ++i) {
      for (int j = 0; j < getCols(); ++j) {
        mat.mElements[j][i] = mElements[i][j];
      }
    }
    return mat;
  }
  
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < getRows(); ++i) {
      for (int j = 0; j + 1 < getCols(); ++j) {
        if (elementEquals(mElements[i][j], 0.0)) {
          mElements[i][j] = Math.abs(mElements[i][j]);
        }
        str.append(String.format("%9.4f", mElements[i][j])).append(", ");
      }
      str.append(String.format("%9.4f", mElements[i][getCols() - 1])).append("\n");
    }
    return str.toString();
  }
  
  protected boolean elementEquals(double lhs, double rhs) {
    return Math.abs(lhs - rhs) < SquareMatrix.epsilon;
  }
}
