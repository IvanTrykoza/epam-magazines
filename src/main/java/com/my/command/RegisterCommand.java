package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
import com.my.jdbc.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.jdbc.SQLConstant.*;

public class RegisterCommand implements Command {

    private final Logger logger = LogManager.getLogger(RegisterCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException, DBException {
        logger.info("RegisterCommand#execute");

        String address = "mainPage.jsp";

        UserManager userManager = UserManager.getInstance();

        String name = req.getParameter("name");
        logger.info("name ==> " + name);

        String login = req.getParameter("login");
        logger.info("login ==> " + login);

        String password = req.getParameter("password");
        logger.info("password ==> " + password);

        if (userManager.getUserByLogin(login) != null) {
            throw new DBException("This user already exist!");
        }

        userManager.createUser(login, password, name, CUSTOMER_ROLE_ID);

        return address;
    }
}
