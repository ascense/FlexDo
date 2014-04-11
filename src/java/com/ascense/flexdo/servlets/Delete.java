package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Memo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Delete extends AbstractServlet {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean post)
            throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("login");
            return;
        }

        Memo memo = null;
        try {
             memo = Memo.getMemo(Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException e) {}

        if (memo != null) {
            if (memo.getTask() != null) {
                memo.getTask().deleteTask();
            }
            memo.deleteMemo();
        }

        String ref = request.getHeader("referer");
        if (ref != null && !ref.isEmpty()) {
            response.sendRedirect(ref);
        } else {
            response.sendRedirect("/FlexDo/index");
        }
    }

    @Override
    public String getServletInfo() {
        return "Task and Memo editing views";
    }
}
