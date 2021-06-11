package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
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
        logger.info("LoinCommand#execute");
        String address;

        String login = req.getParameter("login");
        logger.info("login ==> " + login);

        String password = req.getParameter("password");
        logger.info("password ==> " + password);

        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUserByLogin(login);

        logger.info("user ==> " + user);

        if (user != null && password.equals(user.getPassword())) {
            address = "mainPage.jsp";
            req.getSession().setAttribute("loggedUser", user);
            return address;
        }


        throw new DBException("Incorrect login or Password! Please, enter correct data");
    }
}
