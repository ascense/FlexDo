package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Category;
import com.ascense.flexdo.models.Memo;
import com.ascense.flexdo.models.Task;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FlexDo extends AbstractServlet {

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

        request.setAttribute("categories", Category.getCategories());

        if (request.getParameter("memos") != null) {
            memosView(request, response);
        } else {
            tasksView(request, response);
        }
    }

    private void tasksView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tasks", Task.getTasks());

        RequestDispatcher dispatcher = request.getRequestDispatcher("tasks.jsp");
        dispatcher.forward(request, response);
    }

    private void memosView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("memos", Memo.getMemos());

        RequestDispatcher dispatcher = request.getRequestDispatcher("memos.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Provides the standard view of FlexDo";
    }
}
