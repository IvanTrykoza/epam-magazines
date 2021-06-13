package com.my.filter;

import com.my.jdbc.constatants.SQLConstant;
import com.my.jdbc.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/controller"}, dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD})
public class AuthorizationFilter implements Filter {

    List<String> adminCommand = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        adminCommand.add("adminUserSetting");
        adminCommand.add("adminMagazineSetting");
        adminCommand.add("showAllUsers");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession currentSession = req.getSession(false);
        if (currentSession == null || currentSession.getAttribute("loggedUser") == null) {
            chain.doFilter(request, response);
        } else {
            authorize(request, response, chain);
        }
    }

    private void authorize(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User currentUser = (User) req.getSession().getAttribute("loggedUser");
        boolean isAdmin = currentUser.getRoleId() == SQLConstant.ADMIN_ROLE_ID;

        if (request.getParameter("command") == null) {
            resp.sendRedirect(req.getContextPath() + "mainPage.jsp");
            return;
        }
        String command = request.getParameter("command");
        if (isAdmin || !adminCommand.contains(command)) {
            chain.doFilter(request, response);
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
