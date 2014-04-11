package com.ascense.flexdo.servlets;

import com.ascense.flexdo.core.PasswordHandler;
import com.ascense.flexdo.models.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Admin extends AbstractServlet {
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
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (!isLoggedIn(request) || getLoggedIn(request).getId() != 1) {
            response.sendRedirect("index");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");

        if (post == true) {
            update(request, response, dispatcher);
        }
        display(request, response, dispatcher);

        dispatcher.forward(request, response);
    }

    private void display(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher)
            throws ServletException, IOException {
        request.setAttribute("users", User.getUsers());
    }

    private void update(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher)
            throws ServletException, IOException {
        String user = request.getParameter("inputName");
        String pass = request.getParameter("inputPassword");

        if (user == null && pass == null) {
            dispatcher.forward(request, response);
            return;
        }

        if (user == null || "".equals(user)) {
            request.setAttribute("errorMsg", "Käyttäjän luominen epäonnistui! Et antanut käyttäjätunnusta.");
            dispatcher.forward(request, response);
            return;
        }

        request.setAttribute("inputName", user);

        if (pass == null || "".equals(pass)) {
            request.setAttribute("errorMsg", "Käyttäjän luominen epäonnistui! Et antanut salasanaa.");
            dispatcher.forward(request, response);
            return;
        }

        if (User.getUser(user) != null) {
            request.setAttribute("errorMsg", "Käyttäjänimi on jo käytössä!");
        } else {
            if (createUser(user, pass)) {
                request.setAttribute("infoMsg", "Käyttäjä luotiin onnistuneesti.");
            } else {
                request.setAttribute("errorMsg", "Käyttäjän luominen epäonnistui! Tuntematon virhe!");
            }
        }
    }

    private boolean createUser(String user, String pass) {
        PasswordHandler passHandler = new PasswordHandler();

        byte[] salt = passHandler.generateSalt();
        byte[] hash = passHandler.generateHash(pass.getBytes(), salt);

        User u = new User(
                -1,
                user,
                passHandler.toHexString(hash),
                passHandler.toHexString(salt)
        );
        return u.createUser();
    }

    @Override
    public String getServletInfo() {
        return "Handles user creation";
    }
}
