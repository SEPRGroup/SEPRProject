package sepr.atcGame.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
///////DO NOT CHANGE
// This class provides ability to run AATestSuite in the console only.
// Please see AATestSuite for further details :)
public class AATestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(AATestSuite.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
   }
}  