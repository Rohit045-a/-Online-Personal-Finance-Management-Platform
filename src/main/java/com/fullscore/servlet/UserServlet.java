package com.fullscore.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.getWriter().println("User Servlet Working");
    }
}