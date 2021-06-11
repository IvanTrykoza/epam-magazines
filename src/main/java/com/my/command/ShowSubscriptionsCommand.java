package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.Subscription;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowSubscriptionsCommand implements Command {

    private final Logger logger = LogManager.getLogger(ShowSubscriptionsCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("ShowSubscriptionsCommand#execute");

        String address = "account-subscription.jsp";

        UserManager userManager = UserManager.getInstance();

        User user = (User) req.getSession().getAttribute("loggedUser");

        if (user != null) {
            List<Subscription> subscriptions = userManager.getSubscriptionsByUserId(user.getId());
            req.getSession().setAttribute("subscriptions", subscriptions);
        }

        return address;
    }
}
