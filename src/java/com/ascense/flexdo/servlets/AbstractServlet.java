package com.ascense.flexdo.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public abstract class AbstractServlet extends HttpServlet {

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
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse respons, boolean post)
            throws ServletException, IOException;

    protected Integer getLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            return (Integer) (session.getAttribute("loggedIn"));
        } catch (ClassCastException e) {
            return null;
        }
    }

    protected boolean isLoggedIn(HttpServletRequest request) {
        if (getLoggedIn(request) != null) {
            return true;
        }

        return false;
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }

        processRequest(request, response, false);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }

        processRequest(request, response, true);
    }
}
