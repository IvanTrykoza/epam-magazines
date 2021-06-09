package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.jdbc.SQLConstant.*;

public class EditUserCommand implements Command {

    private final Logger logger = LogManager.getLogger(EditUserCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {

        logger.info("AdminSettingCommand#execute");

        String address = "";

        String status = req.getParameter("setStatus");
        int userId = Integer.parseInt(req.getParameter("userId"));

        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUserById(userId);

        Integer currentPage = (Integer) req.getSession().getAttribute("currentPage");
        logger.info("currentPage ==> " + currentPage);

        if (status.equals("block")) {
            address = "controller?command=showAllUsers&currentPage=" + currentPage;
            userManager.setUserStatus(VALUE_STATUS_FALSE, user.getId());
//            user.setStatus(userManager.getActualUserStatus(user.getId()));
        }

        if (status.equals("unblock")) {
            address = "controller?command=showAllUsers&currentPage=" + currentPage;
            userManager.setUserStatus(VALUE_STATUS_TRUE, user.getId());
//            user.setStatus(userManager.getActualUserStatus(user.getId()));
        }
        return address;
    }
}
