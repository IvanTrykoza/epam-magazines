package com.my.command.commandContainer.impl.user;

import com.my.command.commandContainer.Command;
import com.my.jdbc.exception.DBException;
import com.my.jdbc.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.jdbc.constatants.SQLConstant.*;

public class RegisterCommand implements Command {

    private final Logger logger = LogManager.getLogger(RegisterCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException, DBException {
        logger.info("RegisterCommand#execute");

        String address = "mainPage.jsp";

        UserManager userManager = UserManager.getInstance();

        String name = req.getParameter("name");

        String login = req.getParameter("login");

        String password = req.getParameter("password");

        logger.info("Register param (name, login, password) ==> " +
                "(" + name + ", " + login + ", " + password + ")");

        if (userManager.getUserByLogin(login) != null) {
            throw new DBException("This user already exist!");
        }

        userManager.createUser(login, password, name, CUSTOMER_ROLE_ID);

        return address;
    }
}
