package com.fullscore.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("user", req.getParameter("username"));
        res.sendRedirect("dashboard.jsp");
    }
}