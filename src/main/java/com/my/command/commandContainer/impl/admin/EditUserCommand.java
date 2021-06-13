package com.my.command.commandContainer.impl.admin;

import com.my.command.commandContainer.Command;
import com.my.jdbc.AdminManager;
import com.my.jdbc.exception.DBException;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.jdbc.constatants.SQLConstant.*;

public class EditUserCommand implements Command {

    private final Logger logger = LogManager.getLogger(EditUserCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {

        logger.info("EditUserCommand#execute");

        String status = req.getParameter("setStatus");
        int userId = Integer.parseInt(req.getParameter("userId"));
        logger.info("user param (id, status) ==> " + "(" + userId + ", " + status + ")");
        AdminManager adminManager = AdminManager.getInstance();

        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUserById(userId);

        Integer currentPage = (Integer) req.getSession().getAttribute("currentPage");

        String address = "controller?command=showAllUsers&currentPage=" + currentPage;

        if (status.equals("block")) {
            adminManager.setUserStatus(VALUE_STATUS_FALSE, user.getId());
        }

        if (status.equals("unblock")) {
            adminManager.setUserStatus(VALUE_STATUS_TRUE, user.getId());
        }

        return address;
    }
}
