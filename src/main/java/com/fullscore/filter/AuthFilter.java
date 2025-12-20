package com.fullscore.filter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class AuthFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)req).getSession(false);
        if(session == null) {
            ((HttpServletResponse)res).sendRedirect("index.jsp");
            return;
        }
        chain.doFilter(req, res);
    }
}