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

        int userid = getLoggedIn(request).getId();

        request.setAttribute("categories", Category.getCategories(userid));

        RequestDispatcher dispatcher;
        if (request.getParameter("memos") != null) {
            // display memo listing
            request.setAttribute("memos", Memo.getFilteredMemos(userid));
            dispatcher = request.getRequestDispatcher("memos.jsp");

        } else if (request.getParameter("archive") != null) {
            // display closed task listing
            request.setAttribute("memos", Task.getFilteredMemos(userid, true));
            dispatcher = request.getRequestDispatcher("archive.jsp");

        } else {
            // display open task listing
            request.setAttribute("memos", Task.getFilteredMemos(userid, false));
            dispatcher = request.getRequestDispatcher("tasks.jsp");
        }

        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Provides the standard view of FlexDo";
    }
}
