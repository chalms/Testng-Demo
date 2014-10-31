package test.java;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.server.Start;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.IOException;
import org.eclipse.jetty.server.Connector;
import org.demo.WebApplication;

public class StartTest {
  public static org.eclipse.jetty.server.Server server;
  public static Thread monitor;

  @Test(groups={"get-server"})
  public static void testStart() throws Exception {
    server = Start.getServer();
    org.testng.Assert.assertEquals(server.getClass(), Server.class, "Server is running");
  }

  @Test(groups={"server-running"}, dependsOnGroups={"get-server"})
 public static void testMonitor() throws Exception {
    monitor = Start.getMonitor();
    org.testng.Assert.assertEquals("StopMonitor", monitor.getName(), "Monitor Name");
  }

  @Test(groups={"server-running"}, dependsOnGroups={"get-server"})
  public static  void testSetHandler() throws Exception {

    server.setHandler(((AbstractHandler) Start.getDefaultHandler()));

    // org.testng.Assert.assertNotSame(initialConnectors, secondaryConnectors, "Server has webhandler");
  }

  @Test(groups={"start"}, dependsOnGroups={"server-running"})
  public static void testThreadStart() throws Exception {

    monitor.start();
    server.start();

    org.testng.Assert.assertEquals(true, monitor.isAlive(), "Monitor has started");

    org.testng.Assert.assertEquals(true, (server.isStarted() || server.isStarting()), "Server has started");
  }
}

