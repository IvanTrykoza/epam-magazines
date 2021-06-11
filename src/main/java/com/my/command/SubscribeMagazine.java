package com.my.command;

import com.my.command.container.Command;
import com.my.jdbc.DBException;
import com.my.jdbc.MagazineManager;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.Magazine;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscribeMagazine implements Command {

    private final Logger logger = LogManager.getLogger(SubscribeMagazine.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {

        logger.info("AddMagazineToCart#execute");

        String address = "journals.jsp";

        MagazineManager magazineManager = MagazineManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        long magazineId = Long.parseLong(req.getParameter("magazineId"));

        User user = (User) req.getSession().getAttribute("loggedUser");
        Magazine magazine = magazineManager.getMagazineById(magazineId);
        logger.info(magazine);

        if (userManager.checkSubscription(user.getId(), magazineId)) {
            throw new DBException("You have this magazine in subscriptions");
        }

        if (user.getWallet() < magazine.getPrice()) {
            throw new DBException("User have not enough money");
        }

        userManager.updateBalance(magazine.getPrice(), user.getId());
        user.setWallet(userManager.getActualBalance(user.getId()));

        req.getSession().setAttribute("loggedUser", user);


        long millis = System.currentTimeMillis();
        java.sql.Date startDate = new java.sql.Date(millis);
        logger.info("startDate = " + startDate);

        LocalDate endDate = LocalDate.now().plusMonths(1);
        logger.info("endDate = " + startDate);

        userManager.doSubscribe(user.getId(), magazineId, startDate, Date.valueOf(endDate));

        return address;
    }
}
