package com.ascense.flexdo.servlets;

import com.ascense.flexdo.models.Category;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ListCategory extends AbstractServlet {
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

        Category cat = null;
        try {
            if (request.getParameter("id") != null) {
                int cat_id = Integer.parseInt((String) request.getParameter("id"));
                cat = Category.getCategory(cat_id);
            }
        } catch (NumberFormatException e) {}

        // verify that the user owns requested category
        if (cat != null && cat.getUserId() == userid) {
            request.setAttribute("category", cat);
        }
        request.setAttribute("categories", Category.getCategories(userid));

        if (post == false) {
            display(request, response, cat);
        } else {
            update(request, response, cat);
        }
    }

    /**
     * Display a task/memo listing for a category
     */
    private void display(HttpServletRequest request, HttpServletResponse response, Category cat)
            throws ServletException, IOException {
        if (cat == null) {
            response.sendRedirect("categories");
            return;
        }

        request.setAttribute("memos", cat.getMemos(getLoggedIn(request).getId()));

        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    /*
     * Edit existing category
     */
    private void update(HttpServletRequest request, HttpServletResponse response, Category cat)
            throws ServletException, IOException {
        String name = request.getParameter("inputName");
        // TODO: parentid

        if (cat == null) {
            // create new category
            cat = createCategory(request, name, "-1");
        }

        request.setAttribute("category", cat);
        display(request, response, cat);
    }

    private Category createCategory(HttpServletRequest request, String name, String parent) {
        int parentid = -1;
        try {
            parentid = Integer.parseInt(parent);
        } catch (NumberFormatException e) {}

        Category cat = new Category(
            -1,
            getLoggedIn(request).getId(),
            name,
            parentid
        );
        if (!cat.createCategory()) {
            request.setAttribute("errorMsg", "Luokan luominen epäonnistui!");
        }

        return cat;
    }

    @Override
    public String getServletInfo() {
        return "Provides the Categories-view";
    }
}
