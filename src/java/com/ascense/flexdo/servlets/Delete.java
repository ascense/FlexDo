package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Memo;
import com.ascense.flexdo.models.Task;
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

        Task task = null;
        Memo memo = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            task = Task.getTask(id);
            if (task != null) {
                memo = task.getMemo();
            } else {
                memo = Memo.getMemo(id);
            }
        } catch (NumberFormatException e) {}

        if (task != null) {
            task.deleteTask();
        }
        if (memo != null) {
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
