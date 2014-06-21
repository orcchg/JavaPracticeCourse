package com.github.orcchg.matrix;

public final class SquareMatrix<T> implements Matrix<T> {
  private Object[][] mElements = null;
  
  public SquareMatrix(T[][] elements) {
    mElements = new Object[elements.length][elements[0].length];
    System.arraycopy(elements, 0, mElements, 0, elements.length);
  }
  
  @Override
  public int compareTo(Matrix<T> arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public T getElement(int row, int col) {
    return null;
  }

  @Override
  public int getRows() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getCols() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int totalElements() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Matrix<T> sum(Matrix<T> rhs) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Matrix<T> substract(Matrix<T> rhs) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Matrix<T> multiplyTo(Matrix<T> rhs) {
    // TODO Auto-generated method stub
    return null;
  }

}
