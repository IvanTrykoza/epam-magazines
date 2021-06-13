package com.my.command.commandContainer.impl.user;

import com.my.command.commandContainer.Command;
import com.my.jdbc.exception.DBException;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthorizationCommand implements Command {

    private final Logger logger = LogManager.getLogger(AuthorizationCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("AuthorizationCommand#execute");
        String address;

        String login = req.getParameter("login");

        String password = req.getParameter("password");

        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUserByLogin(login);

        logger.info("Authorization param (login, password) ==> " +
                "(" + login + ", " + password + ")");
        logger.info("Found user ==> " + user);


        if (user != null && password.equals(user.getPassword())) {
            address = "mainPage.jsp";
            req.getSession().setAttribute("loggedUser", user);
            return address;
        }

        throw new DBException("Incorrect login or Password! Please, enter correct data");
    }
}
