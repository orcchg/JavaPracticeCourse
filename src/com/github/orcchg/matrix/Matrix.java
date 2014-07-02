package com.github.orcchg.matrix;

public interface Matrix<T> extends Comparable<Matrix<T>> {
  public T getElement(int row, int col);
  public int getRows();
  public int getCols();
  public int totalElements();
  
  public Matrix<T> sum(final Matrix<T> rhs);
  public Matrix<T> substract(final Matrix<T> rhs);
  public Matrix<T> multiplyTo(final Matrix<T> rhs);
  
  public String toString();
}
