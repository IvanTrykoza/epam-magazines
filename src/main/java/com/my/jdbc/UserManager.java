package com.my.jdbc;

import com.my.jdbc.entity.User;

import java.sql.Connection;
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

    public List<User> getAllUsers(int currentPage, int recordsPerPage) throws DBException {
        List<User> users;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            users = dbManager.getAllUsers(con, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get users from data-base", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get users from data-base", ex);
        } finally {
            dbManager.close(con);
        }
        return users;
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

    public void setUserStatus(int status, long userId) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.setUserStatus(con, status, userId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot set status for userID ==> " + userId, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot set status for userID: " + userId, ex);
        } finally {
            dbManager.close(con);
        }
    }

    public boolean getActualUserStatus(long userId) throws DBException {
        boolean status;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            status = dbManager.getActualUserStatus(con, userId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get actual status for userID ==> " + userId, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get actual status for userID: " + userId, ex);
        } finally {
            dbManager.close(con);
        }

        return status;
    }

    public Integer getAmountOfAllUsers() throws DBException {
        int numOfRows;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            numOfRows = dbManager.getAmountOfAllUsers(con);
            con.commit();

        } catch (SQLException ex) {
            logger.error("Cannot get amount of all users", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get amount of all users", ex);
        } finally {
            dbManager.close(con);
        }
        return numOfRows;

    }
}
