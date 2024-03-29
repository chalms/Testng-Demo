package org.server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.InetAddress;
import java.net.Socket;

public class Stop {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 8079);
        OutputStream out = s.getOutputStream();
        System.out.println("*** sending jetty stop request");
        out.write(("\r\n").getBytes());
        out.flush();
        s.close();
    }

}
