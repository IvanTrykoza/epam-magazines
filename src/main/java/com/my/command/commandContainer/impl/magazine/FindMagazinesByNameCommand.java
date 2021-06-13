package com.my.command.commandContainer.impl.magazine;

import com.my.command.commandContainer.Command;
import com.my.jdbc.exception.DBException;
import com.my.jdbc.MagazineManager;
import com.my.jdbc.entity.Magazine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindMagazinesByNameCommand implements Command {

    private final Logger logger = LogManager.getLogger(FindMagazinesByNameCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("FindMagazineByNameCommand#execute");

        MagazineManager magazineManager = MagazineManager.getInstance();

        String address = "journals.jsp";

        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int recordsPerPage = 8;

        String magazineName;
        if (req.getParameter("magazineName") != null) {
            magazineName = req.getParameter("magazineName");
            req.getSession().setAttribute("magazineName", magazineName);
            logger.info("magazine name ==> " + magazineName);
        }

        List<Magazine> magazines = magazineManager.findMagazineByName((String) req.getSession().getAttribute("magazineName"),
                currentPage, recordsPerPage);


        if (magazines.isEmpty()) {
            throw new DBException("Please, Enter correct data.");
        }

        req.getSession().setAttribute("magazines", magazines);

        int rows = magazineManager.getAmountOfAllMagazinesFoundByName((String) req.getSession().getAttribute("magazineName"));
        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        req.getSession().setAttribute("noOfPages", nOfPages);
        req.getSession().setAttribute("currentPage", currentPage);

        return address;
    }
}
