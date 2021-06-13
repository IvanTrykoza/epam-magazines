package com.my.command.commandContainer.impl.admin;

import com.my.command.commandContainer.Command;
import com.my.jdbc.AdminManager;
import com.my.jdbc.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditMagazineCommand implements Command {

    private final Logger logger = LogManager.getLogger(EditMagazineCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        logger.info("EditMagazineCommand#execute");

        String address = "";

        String action = req.getParameter("action");
        logger.info("COMMAND ==> " + action);

        long magazineId;
        String magazineName;
        String magazineDescription;
        int magazineCategory;
        double magazinePrice;

        AdminManager adminManager = AdminManager.getInstance();

        Integer currentPage = (Integer) req.getSession().getAttribute("currentPage");

        switch (action) {
            case ("edit"):
                address = "controller?command=showAllMagazine&currentPage=" + currentPage;
                magazineId = Long.parseLong(req.getParameter("magazineId"));
                magazineName = req.getParameter("magazineName");
                magazineDescription = req.getParameter("magazineDescription");
                magazineCategory = Integer.parseInt(req.getParameter("magazineCategory"));
                magazinePrice = Double.parseDouble(req.getParameter("magazinePrice"));
                logger.info("magazines param (magazineId, magazineName, description, magazineCategory, magazinePrice) ==> " +
                        "(" + magazineId + ", " + magazineName + ", " + magazineDescription + ", " + magazineCategory + ", " + magazinePrice + ")");
                adminManager.setMagazineInfo(magazineName, magazineDescription, magazineCategory, magazinePrice, magazineId);
                break;
            case ("remove"):
                magazineId = Long.parseLong(req.getParameter("magazineId"));
                logger.info("magazine id for remove ==> " + magazineId);
                adminManager.deleteMagazineById(magazineId);
                address = "controller?command=showAllMagazine&currentPage=" + currentPage;
                break;
            case ("addCategory"):
                address = "controller?command=showAllMagazine&currentPage=1";
                String categoryName = req.getParameter("categoryName");
                logger.info("new category name ==> " + categoryName);
                adminManager.setCategory(categoryName);
                break;
            case ("add"):
                address = "controller?command=showAllMagazine&currentPage=1";
                magazineName = req.getParameter("magazineName");
                magazineDescription = req.getParameter("magazineDescription");
                magazineCategory = Integer.parseInt(req.getParameter("magazineCategory"));
                magazinePrice = Double.parseDouble(req.getParameter("magazinePrice"));
                logger.info("magazines param (magazineName, description, magazineCategory, magazinePrice) ==> " +
                        "(" + magazineName + ", " + magazineDescription + ", " + magazineCategory + ", " + magazinePrice + ")");
                adminManager.addMagazine(magazineName, magazineDescription, magazineCategory, magazinePrice);
                break;
        }
        return address;
    }
}
