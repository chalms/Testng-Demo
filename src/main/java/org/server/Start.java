
package org.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.demo.WebApplication;
import org.demo.TestResultServlet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.IOException;

public class Start {

  private static Server server;

  public static void main(String[] args) throws Exception
  {
      Server server = pairServerHandler(getServer(), getDefaultHandler());
      startMonitor();
      server.start();
      server.join();
  }

  public static Server getServer() {
    return new Server(getServerPort());
  }

  public static int getServerPort() {
    return 8080;
  }

  public static int getServerSocketPort() {
    return 8079;
  }

  public static void startMonitor() {
    getMonitor().start();
  }

  public static Server pairServerHandler(Server s, AbstractHandler h) {
    s.setHandler(h);
    return s;
  }

  public static AbstractHandler getDefaultHandler() {
    return new WebApplication();
  }

  public static InetAddress getInetAddress(String name)  throws UnknownHostException {
    return InetAddress.getByName(name);
  }

  public static ServerSocket getServerSocket(String address) {
    try {
      return new ServerSocket(getServerSocketPort(), 1, getInetAddress(address));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static class MonitorThread extends Thread {

    private ServerSocket socket;
    private Boolean daemon = true;
    public Boolean started = false;

    public MonitorThread(String name, String address) {

        setDaemon(daemon);
        setName(name);
        socket = getServerSocket(address);
    }

    @Override
    public void run() {

        System.out.println("*** running jetty 'stop' thread");
        started = true;
        Socket accept;

        try {
            accept = socket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));

            reader.readLine();
            System.out.println("*** stopping jetty embedded server");

            server.stop();
            accept.close();
            socket.close();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
  }

  public static Thread getMonitor() {
    return new MonitorThread("StopMonitor", "127.0.0.1");
  }

}