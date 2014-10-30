package test.java;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

//show the use of @BeforeSuite and @BeforeTest
public class NamedExample {

  @BeforeSuite
  public void testBeforeSuite() {
    System.out.println("irregularilyNamedExample()");
  }

}