package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {

    private Logger logger = LogManager.getLogger(LogOutCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("LogOutCommand#execute");
        req.getSession().removeAttribute("loggedUser");
        return "mainPage.jsp";
    }
}
