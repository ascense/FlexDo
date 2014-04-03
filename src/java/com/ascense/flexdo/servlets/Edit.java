package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Memo;
import com.ascense.flexdo.models.Task;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Edit extends AbstractServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        response.setContentType("text/html;charset=UTF-8");

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

            if (memo == null) {
                request.setAttribute("errorMsg", "Annettua muistiota/askaretta ei löytynyt!");
            }
        } catch (NumberFormatException e) {}

        setMemoAttrs(request, memo);
        setTaskAttrs(request, task);

        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        dispatcher.forward(request, response);
    }

    private void setMemoAttrs(HttpServletRequest request, Memo memo) {
        if (memo == null) {
            memo = new Memo(-1, getLoggedIn(request), "Uusi Askare", "", null);
        }
        request.setAttribute("title", " Muistion Muokkaus");
        request.setAttribute("id", memo.getId());
        request.setAttribute("name", memo.getName());
        request.setAttribute("content", memo.getContent());
    }

    private void setTaskAttrs(HttpServletRequest request, Task task) {
        if (task == null) {
            return;
        }
        request.setAttribute("title", "Askareen Muokkaus");
        request.setAttribute("priority", task.getPriority());
    }

    @Override
    public String getServletInfo() {
        return "Task and Memo editing views";
    }
}
