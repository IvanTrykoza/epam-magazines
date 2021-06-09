package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
import com.my.jdbc.MagazineManager;
import com.my.jdbc.entity.Category;
import com.my.jdbc.entity.Magazine;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllMagazinesCommand implements Command {

    private final Logger logger = LogManager.getLogger(ShowAllMagazinesCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("ShowAllMagazineCommand#execute");

        MagazineManager magazineManager = MagazineManager.getInstance();

        String address = "journals.jsp";

        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int recordsPerPage = 8;

        // init genre
        List<Category> categories = magazineManager.getCategories();
        req.getSession().setAttribute("categories", categories);

        ///////////////////////////////////////////////////////////////////////////////////

        List<Magazine> magazines = magazineManager.getAllMagazines(currentPage, recordsPerPage);

        req.getSession().setAttribute("magazines", magazines);

        int rows = magazineManager.getAmountOfAllMagazines();
        int noOfPages = rows / recordsPerPage;

        if (noOfPages % recordsPerPage > 0) {
            noOfPages++;
        }

        req.getSession().setAttribute("noOfPages", noOfPages);
        req.getSession().setAttribute("currentPage", currentPage);

        // detect role
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null && user.getRoleId() == 1) {
            address = "admin-edit-magazine.jsp";
            return address;
        }
        return address;
    }
}
