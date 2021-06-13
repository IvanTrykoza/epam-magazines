package com.my.command.commandContainer.impl;

import com.my.command.commandContainer.Command;
import com.my.jdbc.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocaleCommand implements Command {
    private final Logger logger = LogManager.getLogger(ChangeLocaleCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("ChangeLocaleCommand#execute");

        String locale = req.getParameter("locale");

        req.getSession().setAttribute("locale", locale);

        logger.info("locale => " + locale);

        return "mainPage.jsp";
    }
}
