package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Category;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Categories extends AbstractServlet {
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean post)
            throws ServletException, IOException  {
        response.setContentType("text/html;charset=UTF-8");

        if (!isLoggedIn(request)) {
            response.sendRedirect("login");
            return;
        }

        int userid = getLoggedIn(request).getId();

        request.setAttribute("categories", Category.getCategories(userid));

        RequestDispatcher dispatcher = request.getRequestDispatcher("categories.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Provides the Categories-view";
    }
}
