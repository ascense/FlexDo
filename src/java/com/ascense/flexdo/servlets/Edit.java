package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Category;
import com.ascense.flexdo.models.Memo;
import com.ascense.flexdo.models.Task;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Edit extends AbstractServlet {
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

        // set shared attributes
        request.setAttribute("memo", memo);
        request.setAttribute("categories", Category.getCategories(getLoggedIn(request).getId()));

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

    /*
     * Edit existing task/memo
     */
    private void update(HttpServletRequest request, HttpServletResponse response, Memo memo)
            throws ServletException, IOException {
        String name = request.getParameter("inputName");
        String content = request.getParameter("inputContent");
        String priority = request.getParameter("inputPriority");

        if (memo == null) {
            memo = createMemo(request, name, content, priority);
        } else {
            updateMemo(request, memo, name, content, priority);
        }

        String[] categories = request.getParameterValues("inputCategories");
        int catids[] = new int[categories.length];
        try {
            for (int i = 0; i < categories.length; ++i) {
                catids[i] = Integer.parseInt(categories[i]);
            }
            memo.setCategories(catids);
        } catch (NumberFormatException e) {
            // TODO: Error handling
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
            content
        );
        memo.createMemo();

        if (priority != null) {
            Task task;
            try {
                task = new Task(memo.getId(), Integer.parseInt(priority), null);
            } catch (NumberFormatException e) {
                task = new Task(memo.getId(), 1, null);
                if (priority.length() != 0) {
                    request.setAttribute("errorMsg", "Askaareen prioriteetin täytyy olla kokonaisluku");
                }
            }
            task.createTask();
            memo.setTask(task);
        }

        setMemoAttrs(request, memo);

        return memo;
    }

    private void updateMemo(HttpServletRequest request, Memo memo, String name, String content, String priority) {
        if (memo != null) {
            if (memo.getTask() != null && memo.getTask().getClosed() != null) {
                request.setAttribute("errorMsg", "Suljettua askaretta ei voida muokata!");
                return;
            }

            memo.setName(name);
            memo.setContent(content);

            if (priority != null) {
                try {
                    memo.getTask().setPriority(Integer.parseInt(priority));
                    memo.getTask().updateTask();
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMsg", "Askaareen prioriteetin täytyy olla kokonaisluku");
                }
            }

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
        request.setAttribute("title", " Muistion Muokkaus");

        if (memo == null) {
            request.setAttribute("id", -1);
            request.setAttribute("inputName", "Uusi Muistio");
            return;
        }
        request.setAttribute("id", memo.getId());
        request.setAttribute("inputName", memo.getName());
        request.setAttribute("inputContent", memo.getContent());

        if (memo.getTask() != null) {
            // override title
            request.setAttribute("title", "Askareen Muokkaus");
            request.setAttribute("inputPriority", memo.getTask().getPriority());
            if (memo.getTask().getClosed() != null) {
                request.setAttribute("closed", true);
                request.setAttribute("infoMsg", "Askare on suljettu, muutoksien tekeminen ei ole sallittua.");
            } else {
                request.setAttribute("closed", false);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Task and Memo editing views";
    }
}
