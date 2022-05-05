package servlets;

import ejb.MySessionBeanBean;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyHelloServlet", urlPatterns = "/hello")
public class MyHelloServlet extends HttpServlet {

    @EJB
    MySessionBeanBean bean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html><head><title> DA demo </title><head>\n<body>");
        writer.println("<h1>Hello</h1>");
        // Test
        writer.println("Test");
        writer.println("Test 2");
        writer.println("<p>The result of 15 + 32 = " + bean.doSomethingReallyDifficult(15,32) + ".</p>");
        writer.println("</body>\n</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
