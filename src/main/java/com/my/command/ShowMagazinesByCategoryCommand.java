package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
import com.my.jdbc.MagazineManager;
import com.my.jdbc.entity.Magazine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowMagazinesByCategoryCommand implements Command {

    private final Logger logger = LogManager.getLogger(ShowMagazinesByCategoryCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("ShowMagazineByCategoryCommand#execute");

        MagazineManager magazineManager = MagazineManager.getInstance();

        String address = "journals.jsp";

        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int recordsPerPage = 8;

        String categoryName;
        if (req.getParameter("categoryName") != null) {
            categoryName = req.getParameter("categoryName");
            req.getSession().setAttribute("categoryName", categoryName);
        }

        List<Magazine> magazines = magazineManager.findMagazinesByCategory((String) req.getSession().getAttribute("categoryName"),
                currentPage, recordsPerPage);

        req.getSession().setAttribute("magazines", magazines);

        int rows = magazineManager.getAmountOfMagazinesFoundByCategory((String) req.getSession().getAttribute("categoryName"));
        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        req.getSession().setAttribute("noOfPages", nOfPages);
        req.getSession().setAttribute("currentPage", currentPage);

        return address;
    }
}
