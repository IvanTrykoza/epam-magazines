package com.my.listener;

import com.my.jdbc.DBUtils;
import com.my.jdbc.exception.DBException;
import com.my.jdbc.UserManager;
import com.my.jdbc.entity.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.List;


@WebListener
public class ContextListener implements ServletContextListener {
    private final Logger logger = LogManager.getLogger(ContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBUtils.init();

        Terminator terminator = new Terminator();
        terminator.start();
    }

    private class Terminator extends Thread {
        @Override
        public void run() {
            logger.info("Terminator started...");

            UserManager userManager = UserManager.getInstance();

            List<Subscription> subscriptionList;

            try {
                subscriptionList = userManager.getSubscriptions();

                long millis = System.currentTimeMillis();
                java.sql.Date today = new java.sql.Date(millis);
                logger.info("Current Date ==> " + today);

                Date terminate;

                for (Subscription subscription : subscriptionList) {

                    terminate = subscription.getEndDate();
                    logger.info("End Date from subscription table ==> " + terminate);

                    if (today.after(terminate)) {
                        userManager.removeSubscription(subscription.getMagazineId());
                    }
                }
                long timeoutToCheck = 259200000;
                synchronized (currentThread()) {
                    try {
                        Thread.currentThread().wait(timeoutToCheck);
                        run();
                    } catch (InterruptedException e) {
                        throw new DBException("Cannot check and terminate", e);
                    }
                }
            } catch (DBException e) {
                logger.info("Cannot remove subscription after 30 days");
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}