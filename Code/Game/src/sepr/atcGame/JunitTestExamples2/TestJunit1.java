package sepr.atcGame.JunitTestExamples2;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

public class TestJunit1 {

   String message = "Robert";	
   MessageUtil messageUtil = new MessageUtil(message);
   
   @Test
   public void testPrintMessage() {	
      System.out.println("Inside testPrintMessage()");    
      assertEquals("Matthew", messageUtil.printMessage()); 
      // Will create a failure, as message equals "Robert", but "Matthew" is expected.
   }
}