package com.my.command.commandContainer.impl.user;

import com.my.command.commandContainer.Command;
import com.my.jdbc.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountInfoCommand implements Command {
    private final Logger logger = LogManager.getLogger(AccountInfoCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("AccountInfoCommand#execute");
        return "account-info.jsp";
    }
}
