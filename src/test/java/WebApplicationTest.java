package test.java;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.server.Start;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



public class WebApplicationTest {

  @Test(groups = {"running"}, dependsOnGroups={"start"})
  public void test() throws Exception {

    String userId = "invalid";
    HttpClient client = new DefaultHttpClient();

    HttpGet mockRequest = new HttpGet("http://localhost:8080/");
    mockRequest.setHeader("http-user", userId);

    HttpResponse mockResponse = client.execute(mockRequest);
    BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));

    String line;
    String str = "";
    String testString =  "<h1>Hello World</h1>";

    while ((line = rd.readLine()) != null) str += line;
    org.testng.Assert.assertEquals(true, str.contains(testString), "Website Output Correct!");
  }
}
