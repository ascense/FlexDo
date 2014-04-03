package com.ascense.flexdo.servlets;

import com.ascense.flexdo.core.PasswordHandler;
import com.ascense.flexdo.models.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Login extends AbstractServlet {

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
        HttpSession session = request.getSession();
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if (request.getParameter("logout") != null) {
            session.removeAttribute("loggedIn");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");

        if (user == null && pass == null) {
            dispatcher.forward(request, response);
            return;
        }

        if (user == null || "".equals(user)) {
            request.setAttribute("errorMsg", "Kirjautuminen epäonnistui! Et antanut käyttäjätunnusta.");
            dispatcher.forward(request, response);
            return;
        }

        request.setAttribute("username", user);

        if (pass == null || "".equals(pass)) {
            request.setAttribute("errorMsg", "Kirjautuminen epäonnistui! Et antanut salasanaa.");
            dispatcher.forward(request, response);
            return;
        }

        User u = User.getUser(user);

        if (u != null) {
            PasswordHandler passHandler = new PasswordHandler();
            if (passHandler.verifyPassword(pass, u.getSalt(), u.getPassword())) {
                session.setAttribute("loggedIn", new Integer(u.getID()));

                response.sendRedirect("index");
                return;
            }
        }

        request.setAttribute("errorMsg", "Kirjautuminen epäonnistui! Antamasi tunnus tai salasana on väärä. ");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles login and logout";
    }
}
