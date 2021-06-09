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

public class SortMagazineByPriceHLCommand implements Command {

    private final Logger logger = LogManager.getLogger(SortMagazineByPriceHLCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {

        logger.info("SortMagazineByPriceHLCommand#execute");

        MagazineManager magazineManager = MagazineManager.getInstance();

        String address = "journals.jsp";

        String prevRequest;
        if (req.getParameter("prevRequest") != null && !req.getParameter("prevRequest").equals("sortMagazineByPriceLH")
                && !req.getParameter("prevRequest").equals("sortMagazineByName") && !req.getParameter("prevRequest").equals("sortMagazineByPriceHL")) {
            prevRequest = req.getParameter("prevRequest");
            req.getSession().setAttribute("prevRequest", prevRequest);
        }

        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int recordsPerPage = 8;

        int rows = 0;

        List<Magazine> magazines = null;

        switch ((String) req.getSession().getAttribute("prevRequest")) {
            case ("showAllMagazine"):
                magazines = magazineManager.highLowSortMagazinesByPriceAllRequest(currentPage, recordsPerPage);
                rows = magazineManager.getAmountOfAllMagazines();
                break;
            case ("findMagazineByName"):
                String magazineName = (String) req.getSession().getAttribute("magazineName");
                magazines = magazineManager.highLowSortMagazinesByPriceSearchRequest(magazineName, currentPage, recordsPerPage);
                rows = magazineManager.getAmountOfAllMagazinesFoundByName(magazineName);
                break;
            case ("sortMagazineByCategory"):
                String categoryName = (String) req.getSession().getAttribute("categoryName");
                magazines = magazineManager.highLowSortMagazinesByPriceCategoryRequest(categoryName, currentPage, recordsPerPage);
                rows = magazineManager.getAmountOfMagazinesFoundByCategory(categoryName);
                break;
        }

        req.getSession().setAttribute("magazines", magazines);

        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        req.getSession().setAttribute("noOfPages", nOfPages);
        req.getSession().setAttribute("currentPage", currentPage);

        return address;
    }
}
