package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Memo;
import com.ascense.flexdo.models.Task;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Edit extends AbstractServlet {
    /*
     * Edit existing task/memo
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean post)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (!isLoggedIn(request)) {
            response.sendRedirect("login");
            return;
        }

        Memo memo = null;
        try {
             memo = Memo.getMemo(Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException e) {}

        if (post == false) {
            display(request, response, memo);
        } else {
            update(request, response, memo);
        }
    }

    /**
     * Display the edit page for the given memo/task
     *
     * @param request
     * @param response
     * @param memo Memo to display for editing
     * @param task Task if a task is attached to memo, otherwise null
     * @throws ServletException
     * @throws IOException
     */
    private void display(HttpServletRequest request, HttpServletResponse response, Memo memo)
            throws ServletException, IOException {
        if (memo == null) {
            request.setAttribute("errorMsg", "Annettua muistiota/askaretta ei löytynyt!");
        }

        setMemoAttrs(request, memo);

        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        dispatcher.forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response, Memo memo)
            throws ServletException, IOException {
        String name = request.getParameter("inputName");
        String content = request.getParameter("inputContent");
        String priority = request.getParameter("inputPriority");

        if (memo == null) {
            createMemo(request, name, content, priority);
        } else {
            updateMemo(request, memo, name, content, priority);
        }

        if (name == null || name.isEmpty()) {
            request.setAttribute("errorMsg", "Askareella/muistiolla täytyy olla nimi!");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        dispatcher.forward(request, response);
    }

    private Memo createMemo(HttpServletRequest request, String name, String content, String priority) {
        Memo memo = new Memo(
            -1,
            getLoggedIn(request).getId(),
            name,
            content,
            new Timestamp(new Date().getTime())
        );
        memo.createMemo();

        if (priority != null) {
            Task task;
            try {
                task = new Task(memo.getId(), Integer.parseInt(priority), null);
            } catch (NumberFormatException e) {
                task = new Task(memo.getId(), 1, null);
                request.setAttribute("errorMsg", "Askaareen prioriteetin täytyy olla kokonaisluku");
            }
            task.createTask();
            memo.setTask(task);
        }

        setMemoAttrs(request, memo);

        return memo;
    }

    private void updateMemo(HttpServletRequest request, Memo memo, String name, String content, String priority) {
        if (memo != null) {
            memo.setName(name);
            memo.setContent(content);

            setMemoAttrs(request, memo);

            if (memo.updateMemo()) {
                request.setAttribute("infoMsg", "Askare/muistio päivitetty");
                return;
            }
        }
        request.setAttribute("errorMsg", "Päivitys epäonnistui!");
    }

    /**
     * Set page display attributes for a memo.
     *
     * If a task is attached to the memo, also set task attributes.
     *
     * @param request
     * @param memo Memo to display
     */
    private void setMemoAttrs(HttpServletRequest request, Memo memo) {
        if (memo == null) {
            request.setAttribute("title", " Muistion Muokkaus");
            request.setAttribute("id", -1);
            request.setAttribute("name", "Uusi Muistio");
            return;
        }
        request.setAttribute("title", " Muistion Muokkaus");
        request.setAttribute("id", memo.getId());
        request.setAttribute("name", memo.getName());
        request.setAttribute("content", memo.getContent());

        if (memo.getTask() != null) {
            request.setAttribute("title", "Askareen Muokkaus");
            request.setAttribute("priority", memo.getTask().getPriority());
        }
    }

    @Override
    public String getServletInfo() {
        return "Task and Memo editing views";
    }
}
