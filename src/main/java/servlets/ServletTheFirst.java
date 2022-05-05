package servlets;

import ejb.MySessionBean;
import entities.PersonEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletTheFirst", urlPatterns = "/DisApp")
public class ServletTheFirst extends HttpServlet {

    @EJB
    MySessionBean bean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html><head><title>Test Title</title></head>\n<body >"); //style="background-color: rgba(255, 99, 71, 0.5)"
        for(PersonEntity p: bean.findPersonByName("Daan"))
        {
            writer.println("<h1> Welcome " + p.getName() + "</h1><hr>");
        }

        writer.println("<div class=\"row\">\n");
        writer.println("<div class=\"column\" style=\"width: 25%; float: left\">");

        // What goes inside the column
        writer.println("<h2>Menu</h2>");
        writer.println("<ul>");
        writer.println("<li><a href=# >Your Courses</a></li>");
        writer.println("<li><a href=# >Your Ratings</a></li>");
        writer.println("<li><a href=# >Your Professors</a></li>");
        writer.println("</ul>");

        writer.println("</div>\n ");
        writer.println("<div class=\"column\" style=\"width: 50%; float: left;\">");

        // What goes inside the column
        writer.println("<h2>Newly Added</h2>");
        writer.println("<ul>");
        writer.println("<p>Result: " + bean.addNumbers(1, 2) + "</p>");
        writer.println("</ul>");

        writer.println("</div>\n ");
        writer.println("<div class=\"column\" style=\"width: 25%; float: left\">");

        // What goes inside the column
        writer.println("<h2>New Review</h2>");

        writer.println("</div>\n ");
        writer.println("</div><br>");

        writer.println("</body>\n</html>");
        writer.println("Test");
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
