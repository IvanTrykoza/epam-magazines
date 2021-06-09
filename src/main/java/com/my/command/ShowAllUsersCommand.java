package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllUsersCommand implements Command {

    private final Logger logger = LogManager.getLogger(ShowAllUsersCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("ShowAllUsersCommand#execute");

        UserManager userManager = UserManager.getInstance();

        String address = "admin-edit-user.jsp";

        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int recordsPerPage = 10;

        List<User> users = userManager.getAllUsers(currentPage, recordsPerPage);

        req.getSession().setAttribute("users", users);

        int rows = userManager.getAmountOfAllUsers();
        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        req.getSession().setAttribute("noOfPages", nOfPages);
        req.getSession().setAttribute("currentPage", currentPage);

        return address;
    }
}