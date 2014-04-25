package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Category;
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

        if (request.getParameter("memoid") != null) {
            deleteMemo((String) request.getParameter("memoid"));
        } else if (request.getParameter("catid") != null) {
            deleteCategory((String) request.getParameter("catid"));
        }

        String ref = request.getHeader("referer");
        if (ref != null && !ref.isEmpty()) {
            response.sendRedirect(ref);
        } else {
            response.sendRedirect("/FlexDo/index");
        }
    }

    private static void deleteMemo(String memoid) {
        Memo memo = null;
        try {
             memo = Memo.getMemo(Integer.parseInt(memoid));
        } catch (NumberFormatException e) {}
        if (memo == null) return;

        if (memo.getTask() != null) {
            Task task = memo.getTask();

            // if task is open, close task only, otherwise delete task and memo
            if (task.getClosed() == null) {
                task.setClosed();
                task.updateTask();
                return;
            }

            task.deleteTask();
        }
        memo.deleteMemo();
    }

    private static void deleteCategory(String catid) {
        Category cat = null;
        try {
             cat = Category.getCategory(Integer.parseInt(catid));
        } catch (NumberFormatException e) {}
        if (cat == null) return;

        // TODO: delete children

        cat.deleteCategory();
    }

    @Override
    public String getServletInfo() {
        return "Task and Memo editing views";
    }
}
