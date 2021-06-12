package com.my.jdbc;

import com.my.jdbc.entity.Subscription;
import com.my.jdbc.entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class UserManager {

    private final DBManager dbManager;
    private Logger logger = LogManager.getLogger(UserManager.class.getName());

    ///////////////////////////////////////////////////////////////////////////////
    private static UserManager instance;

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager() {
        dbManager = DBManager.getInstance();
    }

    ///////////////////////////////////////////////////////////////////////////////

    public List<Subscription> getSubscriptionsByUserId(long userId) throws DBException {
        List<Subscription> subscriptions;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            subscriptions = dbManager.getSubscriptionsByUserId(con, userId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get subscriptions from data-base", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get subscriptions from data-base", ex);
        } finally {
            dbManager.close(con);
        }
        return subscriptions;
    }

    public List<Subscription> getSubscriptions() throws DBException {
        List<Subscription> subscriptions;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            subscriptions = dbManager.getSubscriptions(con);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get subscriptions", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get subscriptions", ex);
        } finally {
            dbManager.close(con);
        }
        return subscriptions;
    }

    public User getUserById(int userId) throws DBException {
        User user;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            user = dbManager.getUserById(con, userId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get user by ID ==> " + userId, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get user by ID: " + userId, ex);
        } finally {
            dbManager.close(con);
        }
        return user;
    }

    public User getUserByLogin(String userLogin) throws DBException {
        User user;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            user = dbManager.getUserByLogin(con, userLogin);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get user by login ==> " + userLogin, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get user by login: " + userLogin, ex);
        } finally {
            dbManager.close(con);
        }
        return user;
    }

    public void createUser(String login, String password, String userName, int userRole) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.createUser(con, login, password, userName, userRole);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot create user with params (login, password, userName, userRole) ==> " +
                    "(" + login + "," + password + "," + userName + "," + userRole + ")", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot create user", ex);
        } finally {
            dbManager.close(con);
        }
    }

    public void topUbBalance(double amountOfMoney, long userId) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.topUpBalance(con, amountOfMoney, userId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot top up balance with params (amountOfMoney, userId) ==> " +
                    "(" + amountOfMoney + "," + userId + ")", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot top up balance", ex);
        } finally {
            dbManager.close(con);
        }
    }

    public double getActualBalance(long userId) throws DBException {
        double balance;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            balance = dbManager.getActualBalance(con, userId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get actual balance for userID ==> " + userId, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get actual balance for userID: " + userId, ex);
        } finally {
            dbManager.close(con);
        }
        return balance;
    }

    public void removeSubscription(long magazineId) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.removeSubscription(con, magazineId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot remove subscription with magazineId ==> " + magazineId, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot remove subscription with magazineId ==> " + magazineId, ex);
        } finally {
            dbManager.close(con);
        }
    }

    public void updateBalance(double amountOfMoney, long userId) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.updateBalance(con, amountOfMoney, userId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot update balance with params (amountOfMoney, userId) ==> " +
                    "(" + amountOfMoney + "," + userId + ")", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot update balance", ex);
        } finally {
            dbManager.close(con);
        }
    }

    public void doSubscribe(long userId, long magazineId, Date startDate, Date endDate) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.doSubscribe(con, userId, magazineId, startDate, endDate);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot create subscription with params (userId, magazineId, startDate, endDate) ==> " +
                    "(" + userId + "," + magazineId + "," + startDate + "," + endDate + ")", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot create subscription", ex);
        } finally {
            dbManager.close(con);
        }
    }

    public boolean checkSubscription(long userId, long magazineId) throws DBException {
        Connection con = null;
        boolean result;
        try {
            con = dbManager.getConnection();
            result = dbManager.checkSubscription(con, userId, magazineId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot check subscription", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot check subscription", ex);
        } finally {
            dbManager.close(con);
        }
        return result;
    }

    public boolean getUserStatus(long userId) throws DBException {
        boolean status;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            status = dbManager.getUserStatus(con, userId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get status for userID ==> " + userId, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get status for userID: " + userId, ex);
        } finally {
            dbManager.close(con);
        }

        return status;
    }

}
