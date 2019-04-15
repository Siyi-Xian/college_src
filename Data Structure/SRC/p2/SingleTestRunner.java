import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Request;
import org.junit.runner.notification.Failure;
import org.junit.internal.RealSystem;

/**
  * Class to run JUnit tests on the given classes and print a list of tests failed.
  * Use: java -cp ...junit.jar:hamcrest.jar SingleTestRunner className#methodName
  * https://stackoverflow.com/questions/9288107/run-single-test-from-a-junit-class-using-command-line
  * 
  * @author Syed Turab Ali Jafri 
  * @date 06 August 2018
  */
public class SingleTestRunner{

  public static void main(String... args) throws Exception{
    String[] classAndMethod = args[0].split("#");
    Request request = Request.method(Class.forName(classAndMethod[0]),classAndMethod[1]);

    Result result = new JUnitCore().run(request);
    if(result.wasSuccessful()){
      System.out.println("SUCCESS");
      System.exit(0);
    } else {
      Failure f = result.getFailures().get(0);
      System.out.println("FAILURE");
      String exceptionName = f.getException().getClass().getSimpleName();
      String exceptionMessage = f.getException().getMessage() == null? "" : f.getException().getMessage();
      String errorMessage = exceptionName.equals("AssertionError")? "Test Failed: " + exceptionMessage :
                                                                  exceptionName + " " + exceptionMessage;        
      System.err.print(/*f.getTestHeader()+ " --> " + */errorMessage);                                           
      System.exit(1);
    }
  }
}
