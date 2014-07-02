package com.github.orcchg.matrix;

public final class Vector<T> implements Matrix<T> {
  private Object[] mElements;
  
  public Vector(final T[] array) {
    mElements = new Object[array.length];
    System.arraycopy(array, 0, mElements, 0, array.length);
  }

  @Override
  public int compareTo(Matrix<T> o) {
    // DoubleODO Auto-generated method stub
    return 0;
  }

  @Override
  public T getElement(int row, int col) {
    // DoubleODO Auto-generated method stub
    return null;
  }

  @Override
  public int getRows() {
    // DoubleODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getCols() {
    // DoubleODO Auto-generated method stub
    return 0;
  }

  @Override
  public int totalElements() {
    // DoubleODO Auto-generated method stub
    return 0;
  }

  @Override
  public Matrix<T> sum(Matrix<T> rhs) {
    // DoubleODO Auto-generated method stub
    return null;
  }

  @Override
  public Matrix<T> substract(Matrix<T> rhs) {
    // DoubleODO Auto-generated method stub
    return null;
  }

  @Override
  public Matrix<T> multiplyTo(Matrix<T> rhs) {
    // DoubleODO Auto-generated method stub
    return null;
  }

}
