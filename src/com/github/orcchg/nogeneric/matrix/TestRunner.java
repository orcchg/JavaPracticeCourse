package com.github.orcchg.nogeneric.matrix;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
  public static void main(String[] argv) {
    Result result = JUnitCore.runClasses(TestMatrix.class);
    for (Failure fail : result.getFailures()) {
      System.out.println(fail.toString());
    }
  }
}
