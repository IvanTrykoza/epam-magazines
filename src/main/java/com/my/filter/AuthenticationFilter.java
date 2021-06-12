package com.my.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/controller"}, dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD})
public class AuthenticationFilter implements Filter {

    List<String> mainCommands = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mainCommands.add("login");
        mainCommands.add("register");

        mainCommands.add("showAllMagazine");
        mainCommands.add("findMagazineByName");
        mainCommands.add("sortMagazineByCategory");

        mainCommands.add("sortMagazineByName");
        mainCommands.add("sortMagazineByPriceHL");
        mainCommands.add("sortMagazineByPriceLH");

        mainCommands.add("subscribeMagazine");
        mainCommands.add("changeLocale");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession currentSession = req.getSession(false);

        boolean loggedUser = currentSession != null && currentSession.getAttribute("loggedUser") != null;

        if (req.getParameter("command") == null) {
            resp.sendRedirect(req.getContextPath() + "/mainPage.jsp");
            return;
        }

        String command = req.getParameter("command");

        if (mainCommands.contains(command) || loggedUser) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/mainPage.jsp");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
