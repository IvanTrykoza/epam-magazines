package com.my.filter;

import com.my.jdbc.DBException;
import com.my.jdbc.DBManager;
import com.my.jdbc.SQLConstant;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "GetActualStatusFilter", urlPatterns = {"/controller"}, dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD})
public class GetActualStatusFilter implements Filter {
    public static final Logger logger = LogManager.getLogger(GetActualStatusFilter.class.getName());
    List<String> mainCommands = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("GetActualStatusFilter#excute");
        mainCommands.add("logOut");
        mainCommands.add("accountInfo");
        mainCommands.add("getUsersSubscriptions");
        mainCommands.add("login");
        mainCommands.add("register");
        mainCommands.add("changeLocale");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession currentSession = req.getSession(false);

        UserManager userManager = UserManager.getInstance();

        User currentUser = (User) currentSession.getAttribute("loggedUser");

        if (currentUser != null) {
            try {
                currentUser.setStatus(userManager.getUserStatus(currentUser.getId()));
            } catch (DBException e) {
                logger.info("Cannot set status for user with userId ==> " + currentUser.getId());
            }
        }


        String blockedURL = "account-blocked.jsp";

        String requestedURL = req.getRequestURI() + "?" + req.getQueryString();
        boolean blockedPageRequested = blockedURL.equalsIgnoreCase(requestedURL);

        if (currentUser != null && !currentUser.isStatus() && !mainCommands.contains(req.getParameter("command")) && !blockedPageRequested
                && currentUser.getRoleId() != SQLConstant.ADMIN_ROLE_ID) {
            resp.sendRedirect(blockedURL);
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
