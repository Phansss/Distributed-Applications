package servlets;

import ejb.CourseServiceBean;
import ejb.MySessionBean;
import ejb.MySessionBean;
import entities.CourseEntity;
import entities.PersonEntity;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.List;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyHelloServlet", urlPatterns = "/hello")
public class MyHelloServlet extends HttpServlet {

    @EJB
    MySessionBean bean;

    @EJB
    CourseServiceBean courseServiceBean;
    @PersistenceContext(unitName = "DADemoPU")
    EntityManager em;



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PRINT MSG: HTML");

        courseServiceBean.getAllCourses().forEach(courseEntity -> {
            System.out.println(courseEntity.getName());
        });

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html><head><title> DA demo </title><head>\n<body>");
        writer.println("<h1>Hello</h1>");
        // Test
        writer.println("Test");
        writer.println("Test 2");
        writer.println("<p>The result of 15 + 32 = " + bean.doSomethingReallyDifficult(15,32) + ".</p>");



        writer.println("<ul>");
        for (PersonEntity p: bean.findPersonByName("Pieter")) {
            writer.println("<li>" + p.getEmail() + "</li>");
        }
        writer.println("</ul>");
        writer.println("</body>\n</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}