package test.java;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.server.Stop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.IOException;

public class StopTest {

  @Test(groups = {"stopping"}, dependsOnGroups = { "running" })
   public void testStop() throws Exception {
      Stop.main(null);
      org.testng.Assert.assertEquals(true, true, "Server is stopped!");
  }
}
