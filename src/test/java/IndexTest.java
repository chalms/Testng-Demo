package test.java;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.demo.Index;

public class IndexTest {


  Integer port = Index.port;

  @Test
  public void testServerPort() {

    System.out.println("First test!");

    org.testng.Assert.assertEquals(port, ((Integer) 8080));
  }
}

