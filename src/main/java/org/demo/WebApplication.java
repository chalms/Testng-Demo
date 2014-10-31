package org.demo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.server.Start;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import javax.servlet.RequestDispatcher;

public class WebApplication extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException
    {
        RequestDispatcher view = request.getRequestDispatcher("/index.html");

        baseRequest.setHandled(true);

        // don't add your web-app name to the path
        view.forward(request, response);

}
}