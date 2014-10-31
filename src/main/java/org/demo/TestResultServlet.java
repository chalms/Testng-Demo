package org.demo;
import org.server.Start;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestResultServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

              // we do not set content type, headers, cookies etc.
        // resp.setContentType("text/html"); // while redirecting as
        // it would most likely result in an IllegalStateException

        // "/" is relative to the context root (your web-app name)
        RequestDispatcher view = request.getRequestDispatcher("/test-results/Surefire suite/Surefire test.html");


        // don't add your web-app name to the path
        view.forward(request, response);
    }
}
