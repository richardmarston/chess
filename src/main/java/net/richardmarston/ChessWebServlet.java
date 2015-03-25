package net.richardmarston;

/**
 * Created by rich on 24/03/15.
 */
import net.richardmarston.ChessEndpointImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

@WebServlet(name="ChessWebServlet", urlPatterns={"/HelloServlet"})
public class ChessWebServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
    {
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h1>" + "Alright Geez!" + "</h1>");
    }
}