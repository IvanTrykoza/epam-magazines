package com.my.jdbc;

import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminManager {

    private final DBManager dbManager;

    ///////////////////////////////////////////////////////////////////////////////

    private final Logger logger = LogManager.getLogger(AdminManager.class.getName());
    private static AdminManager instance;

    public static synchronized AdminManager getInstance() {
        if (instance == null) {
            instance = new AdminManager();
        }
        return instance;
    }

    private AdminManager() {
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

    public void setCategory(String categoryName) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.setCategory(con, categoryName);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot add category with name ==> " + categoryName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot add category with name: " + categoryName, ex);
        } finally {
            dbManager.close(con);
        }
    }

    public void addMagazine(String magazineName, String magazineDescription, int magazineCategory, double magazinePrice) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.addMagazine(con, magazineName, magazineDescription, magazineCategory, magazinePrice);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot add magazine to data-base with params (name, description, category, price) ==> " +
                    "(" + magazineName + "," + magazineDescription + "," + magazineCategory + "," + magazinePrice + ")", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot add magazine to data-base", ex);
        } finally {
            dbManager.close(con);
        }
    }

    public void setMagazineInfo(String magazineName, String magazineDescription, int magazineCategory, double magazinePrice, long magazineId) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.setMagazineInfo(con, magazineName, magazineDescription, magazineCategory, magazinePrice, magazineId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot set magazine info with params (name, description, category, price, magazineID) ==> " +
                    "(" + magazineName + "," + magazineDescription + "," + magazineCategory + "," + magazinePrice + "," + magazineId + ")", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot set magazine info for magazineId: " + magazineId, ex);
        } finally {
            dbManager.close(con);
        }
    }

    public void deleteMagazineById(long magazineId) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            dbManager.deleteMagazineById(con, magazineId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot delete magazine with id ==> " + magazineId, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot delete magazine with id: " + magazineId, ex);
        } finally {
            dbManager.close(con);
        }
    }
}
