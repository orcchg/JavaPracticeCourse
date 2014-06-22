package com.github.orcchg.nogeneric.matrix;

public class SingularSquareMatrixException extends Exception {
  private String mMessage;
  
  public SingularSquareMatrixException(String string) {
    mMessage = string;
  }
  
  @Override
  public final String getMessage() {
    return mMessage;
  }

  private static final long serialVersionUID = 1L;
}
