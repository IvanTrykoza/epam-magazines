package com.my.jdbc;

import com.my.jdbc.entity.Category;
import com.my.jdbc.entity.Magazine;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MagazineManager {
    private final DBManager dbManager;

    ///////////////////////////////////////////////////////////////////////////////

    private final Logger logger = LogManager.getLogger(MagazineManager.class.getName());
    private static MagazineManager instance;

    public static synchronized MagazineManager getInstance() {
        if (instance == null) {
            instance = new MagazineManager();
        }
        return instance;
    }

    private MagazineManager() {
        dbManager = DBManager.getInstance();
    }

    ///////////////////////////////////////////////////////////////////////////////


    public List<Magazine> getAllMagazines(int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.getAllMagazines(con, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get all magazines", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get all magazines", ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }

    public List<Magazine> findMagazineByName(String magazineName, int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.findMagazinesByName(con, magazineName, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot find magazines by name ==> " + magazineName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot find magazines by name: " + magazineName, ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }

    public List<Magazine> findMagazinesByCategory(String category, int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.findMagazinesByCategory(con, category, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot find magazines by category ==> " + category, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot find magazines by category: " + category, ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }


    public int getAmountOfAllMagazines() throws DBException {
        int numOfRows;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            numOfRows = dbManager.getAmountOfAllMagazines(con);
            con.commit();

        } catch (SQLException ex) {
            logger.error("Cannot get amount of all magazines", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get amount of all magazines", ex);
        } finally {
            dbManager.close(con);
        }
        return numOfRows;
    }

    public int getAmountOfMagazinesFoundByCategory(String categoryName) throws DBException {
        int numOfRows;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            numOfRows = dbManager.getAmountOfMagazinesFoundByCategory(con, categoryName);
            con.commit();

        } catch (SQLException ex) {
            logger.error("Cannot get amount of magazines found by category ==> " + categoryName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get amount of magazines found by category: " + categoryName, ex);
        } finally {
            dbManager.close(con);
        }
        return numOfRows;
    }

    public int getAmountOfAllMagazinesFoundByName(String magazineName) throws DBException {
        int numOfRows;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            numOfRows = dbManager.getAmountOfAllMagazinesFoundByName(con, magazineName);
            con.commit();

        } catch (SQLException ex) {
            logger.error("Cannot get amount of all magazines found by name ==> " + magazineName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get amount of all magazines found by name: " + magazineName, ex);
        } finally {
            dbManager.close(con);
        }
        return numOfRows;
    }


    public List<Magazine> sortMagazinesByNameAllRequest(int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.sortMagazinesByNameAllRequest(con, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by name", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by name", ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }

    public List<Magazine> sortMagazinesByNameSearchRequest(String magazineName, int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.sortMagazinesByNameSearchRequest(con, magazineName, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by name ==> " + magazineName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by name: " + magazineName, ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }

    public List<Magazine> sortMagazinesByNameCategoryRequest(String categoryName, int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.sortMagazinesByNameCategoryRequest(con, categoryName, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by name with category ==> " + categoryName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by name with category: " + categoryName, ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }


    public List<Magazine> lowHighSortMagazinesByPriceAllRequest(int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.lowHighSortMagazinesByPriceAllRequest(con, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price L ==> H", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by price L ==> H", ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }

    public List<Magazine> lowHighSortMagazinesByPriceSearchRequest(String magazineName, int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.lowHighSortMagazinesByPriceSearchRequest(con, magazineName, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price L ==> H with name ==> " + magazineName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by price L ==> H with name: " + magazineName, ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }

    public List<Magazine> lowHighSortMagazinesByPriceCategoryRequest(String categoryName, int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.lowHighSortMagazinesByPriceCategoryRequest(con, categoryName, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price L ==> H with category ==> " + categoryName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by price L ==> H with category: " + categoryName, ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }


    public List<Magazine> highLowSortMagazinesByPriceAllRequest(int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.highLowSortMagazinesByPriceAllRequest(con, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price H ==> L", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by price H ==> L", ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }

    public List<Magazine> highLowSortMagazinesByPriceSearchRequest(String magazineName, int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.highLowSortMagazinesByPriceSearchRequest(con, magazineName, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price H ==> L with name ==> " + magazineName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by price H ==> L with name: " + magazineName, ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }

    public List<Magazine> highLowSortMagazinesByPriceCategoryRequest(String categoryName, int currentPage, int recordsPerPage) throws DBException {
        List<Magazine> magazines;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazines = dbManager.highLowSortMagazinesByPriceCategoryRequest(con, categoryName, currentPage, recordsPerPage);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price L ==> H with category ==> " + categoryName, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot sort magazines by price L ==> H with category: " + categoryName, ex);
        } finally {
            dbManager.close(con);
        }
        return magazines;
    }


    public List<Category> getCategories() throws DBException {
        List<Category> categories;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            categories = dbManager.getCategories(con);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get categories from data-base", ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get categories from data-base", ex);
        } finally {
            dbManager.close(con);
        }
        return categories;
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

    public Magazine getMagazineById(long magazineId) throws DBException {
        Magazine magazine;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            magazine = dbManager.getMagazineById(con, magazineId);
            con.commit();
        } catch (SQLException ex) {
            logger.error("Cannot get magazine by ID ==> " + magazineId, ex);
            dbManager.rollback(con);
            throw new DBException("Cannot get magazine by ID: " + magazineId, ex);
        } finally {
            dbManager.close(con);
        }
        return magazine;
    }
}
