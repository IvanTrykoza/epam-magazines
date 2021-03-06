package com.my.command.commandContainer.impl.user;

import com.my.command.commandContainer.Command;
import com.my.jdbc.exception.DBException;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToUpBalanceCommand implements Command {
    private final Logger logger = LogManager.getLogger(ToUpBalanceCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("ToUpBalance#execute");

        String address;

        double amount;
        try {
            amount = Double.parseDouble(req.getParameter("amount"));
            logger.info("amount money ==> " + amount);
        } catch (NumberFormatException ex) {
            throw new DBException("Please, Enter correct value for balance", ex);
        }

        User user = (User) req.getSession().getAttribute("loggedUser");

        UserManager userManager = UserManager.getInstance();

        if (amount > 0) {
            address = "account-info.jsp";
            userManager.topUbBalance(amount, user.getId());
            user.setWallet(userManager.getActualBalance(user.getId()));
            req.getSession().setAttribute("loggedUser", user);
            return address;
        }
        throw new DBException("Incorrect value. Please enter correct data");
    }
}
