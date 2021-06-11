package com.my.jdbc;

import com.my.jdbc.entity.Category;
import com.my.jdbc.entity.Magazine;
import com.my.jdbc.entity.Subscription;
import com.my.jdbc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static com.my.jdbc.SQLConstant.*;

public class DBManager {
    public static final Logger logger = LogManager.getLogger(DBManager.class.getName());

    private final DataSource ds;

    //////////////////////////////////////////////////////////////////////

    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/TestDB");
        } catch (NamingException ex) {
            logger.error("Cannot init DBManager", ex);
            throw new IllegalStateException("Cannot init DBManager");
        }
    }

    //////////////////////////////////////////////////////////////////////

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

//    --------------------------USER METHOD ------------------------------------

    public List<User> getAllUsers(Connection con, int currentPage, int recordsPerPage) throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(FIND_ALL_USERS);
            int k = 1;
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot get users from data-base", ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return users;
    }

    public User getUserByLogin(Connection con, String userLogin) throws SQLException {
        User user = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.prepareStatement(FIND_USER_BY_LOGIN);
            psmt.setString(1, userLogin);
            rs = psmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get user by login ==> " + userLogin, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return user;
    }

    public User getUserById(Connection con, int userId) throws SQLException {
        User user = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.prepareStatement(FIND_USER_BY_ID);
            psmt.setString(1, String.valueOf(userId));
            rs = psmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get user by ID ==> " + userId, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return user;
    }

    public void createUser(Connection con, String login, String password, String userName, int userRole) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(ADD_NEW_USER);
            int k = 1;
            pstmt.setString(k++, login);
            pstmt.setString(k++, password);
            pstmt.setString(k++, userName);
            pstmt.setInt(k++, userRole);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot create user with params (login, password, userName, userRole) ==> " +
                    "(" + login + "," + password + "," + userName + "," + userRole + ")", ex);
        } finally {
            close(pstmt);
        }
    }


    public void topUpBalance(Connection con, double amountOfMoney, long userId) throws SQLException {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.prepareStatement(SET_USER_BALANCE);
            int k = 1;
            psmt.setString(k++, String.valueOf(amountOfMoney + getActualBalance(con, userId)));
            psmt.setString(k++, String.valueOf(userId));
            psmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot top up balance with params (amountOfMoney, userId) ==> " +
                    "(" + amountOfMoney + "," + userId + ")", ex);
        } finally {
            close(rs);
            close(psmt);
        }
    }

    public double getActualBalance(Connection con, long userId) throws SQLException {
        double amountOfMoney = 0;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.prepareStatement(GET_USER_BALANCE);
            psmt.setString(1, String.valueOf(userId));
            rs = psmt.executeQuery();
            if (rs.next()) {
                amountOfMoney = rs.getDouble(USER_WALLET);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get actual balance for userID ==> " + userId, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return amountOfMoney;
    }

    public void updateBalance(Connection con, double amountOfMoney, long userId) {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.prepareStatement(SET_USER_BALANCE);
            int k = 1;
            psmt.setString(k++, String.valueOf(getActualBalance(con, userId) - amountOfMoney));
            psmt.setString(k++, String.valueOf(userId));
            psmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot update balance with params (amountOfMoney, userId) ==> " +
                    "(" + amountOfMoney + "," + userId + ")", ex);
        } finally {
            close(rs);
            close(psmt);
        }
    }


    public void setUserStatus(Connection con, int status, long userId) throws SQLException {
        PreparedStatement psmt = null;
        try {
            psmt = con.prepareStatement(SET_USER_STATUS);
            int k = 1;
            psmt.setString(k++, String.valueOf(status));
            psmt.setString(k++, String.valueOf(userId));
            psmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot set status for userID ==> " + userId, ex);
        } finally {
            close(psmt);
        }
    }

    public boolean getActualUserStatus(Connection con, long userId) throws SQLException {
        boolean status = false;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.prepareStatement(GET_USER_STATUS);
            psmt.setString(1, String.valueOf(userId));
            rs = psmt.executeQuery();
            if (rs.next()) {
                status = rs.getBoolean(USER_STATUS);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get actual status for userID ==> " + userId, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return status;
    }

    public int getAmountOfAllUsers(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        int amount = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_AMOUNT_OF_ALL_USERS);
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get amount of all users", ex);
        } finally {
            close(rs);
            close(stmt);
        }
        return amount;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(USER_ID));
        user.setLogin(rs.getString(USER_LOGIN));
        user.setPassword(rs.getString(USER_PASSWORD));
        user.setName(rs.getString(USER_NAME));
        user.setRoleId(rs.getInt(USER_ROLE_ID));
        user.setStatus(rs.getBoolean(USER_STATUS));
        user.setWallet(rs.getDouble(USER_WALLET));
        return user;
    }


    public void doSubscribe(Connection con, long userId, long magazineId, Date startDate, Date endDate) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(DO_SUBSCRIPTION);
            int k = 1;
            pstmt.setLong(k++, userId);
            pstmt.setLong(k++, magazineId);
            pstmt.setDate(k++, startDate);
            pstmt.setDate(k++, endDate);
            logger.info("ps = " + pstmt);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            logger.error("Cannot create subscription with params (userId, magazineId, startDate, endDate) ==> " +
                    "(" + userId + "," + magazineId + "," + startDate + "," + endDate + ")", ex);
        } finally {
            close(pstmt);
        }
    }

    public List<Subscription> getSubscriptionsByUserId(Connection con, long userId) throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.prepareStatement(GET_SUBSCRIPTION_BY_USER_ID);
            psmt.setLong(1, userId);
            rs = psmt.executeQuery();
            while (rs.next()) {
                subscriptions.add(extractSubscription(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot get subscriptions from data-base", ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return subscriptions;
    }

    public List<Subscription> getSubscriptions(Connection con) throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_SUBSCRIPTION);
            while (rs.next()) {
                subscriptions.add(extractSubscription(rs));
            }
        } catch (SQLException ex) {
            logger.info("Cannot get subscriptions", ex);
        } finally {
            close(rs);
            close(stmt);
        }
        return subscriptions;
    }

    public void removeSubscription(Connection con, long magazineId) {
        PreparedStatement psmt = null;
        try {
            psmt = con.prepareStatement(REMOVE_SUBSCRIPTION_MAGAZINE);
            psmt.setLong(1, magazineId);
            psmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot remove subscription with magazineId ==> " + magazineId, ex);
        } finally {
            close(psmt);
        }
    }


    public boolean checkSubscription(Connection con, long userId, long magazineId) throws SQLException {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            psmt = con.prepareStatement(CHECK_SUBSCRIPTION);
            int k = 1;
            psmt.setLong(k++, userId);
            psmt.setLong(k++, magazineId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException ex) {
            logger.error("Cannot check subscription", ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return result;
    }

    private Subscription extractSubscription(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setSubId(rs.getLong(SUBSCRIPTION_ID));
        subscription.setMagazineId(rs.getLong(SUBSCRIPTION_MAGAZINE_ID));
        subscription.setMagazineName(rs.getString(MAGAZINE_NAME));
        subscription.setUserId(rs.getLong(SUBSCRIPTION_USER_ID));
        subscription.setStartDate(rs.getDate(SUBSCRIPTION_START_DATE));
        subscription.setEndDate(rs.getDate(SUBSCRIPTION_END_DATE));
        return subscription;
    }

//    --------------------------MAGAZINE METHOD ------------------------------------

    public List<Magazine> getAllMagazines(Connection con, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(FIND_ALL_MAGAZINES);
            int k = 1;
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot get all magazines", ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }

    public List<Magazine> findMagazinesByName(Connection con, String magazineName, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(FIND_MAGAZINES_BY_NAME);
            int k = 1;
            psmt.setString(k++, "%" + magazineName + "%");
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot find magazines by name ==> " + magazineName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }

    public List<Magazine> findMagazinesByCategory(Connection con, String category, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(FIND_MAGAZINES_BY_CATEGORY);
            int k = 1;
            psmt.setString(k++, category);
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot find magazines by category ==> " + category, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }


    public int getAmountOfAllMagazines(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        int amount = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_AMOUNT_OF_ALL_MAGAZINES);
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get amount of all magazines", ex);
        } finally {
            close(rs);
            close(stmt);
        }
        return amount;
    }

    public int getAmountOfMagazinesFoundByCategory(Connection con, String categoryName) {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int amount = 0;
        try {
            psmt = con.prepareStatement(GET_AMOUNT_OF_MAGAZINES_FOUND_BY_CATEGORY);
            psmt.setString(1, categoryName);
            rs = psmt.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get amount of magazines found by category ==> " + categoryName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return amount;
    }

    public int getAmountOfAllMagazinesFoundByName(Connection con, String magazineName) {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int amount = 0;
        try {
            psmt = con.prepareStatement(GET_AMOUNT_OF_MAGAZINES_FOUND_BY_NAME);
            psmt.setString(1, "%" + magazineName + "%");
            rs = psmt.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get amount of all magazines found by name ==> " + magazineName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return amount;

    }


    public List<Category> getCategories(Connection con) throws SQLException {
        List<Category> categories = new ArrayList<>();
        Statement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.createStatement();
            rs = psmt.executeQuery(GET_CATEGORY);
            while (rs.next()) {
                categories.add(extractCategory(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot get categories from data-base", ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return categories;
    }

    public void setMagazineInfo(Connection con, String magazineName, String magazineDescription, int magazineCategory, double magazinePrice, long magazineId) throws SQLException {
        PreparedStatement psmt = null;
        try {
            psmt = con.prepareStatement(SET_MAGAZINE_INFO);
            int k = 1;
            psmt.setString(k++, magazineName);
            psmt.setString(k++, magazineDescription);
            psmt.setInt(k++, magazineCategory);
            psmt.setDouble(k++, magazinePrice);
            psmt.setLong(k++, magazineId);
            psmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot set magazine info with params (name, description, category, price, magazineID) ==> " +
                    "(" + magazineName + "," + magazineDescription + "," + magazineCategory + "," + magazinePrice + "," + magazineId + ")", ex);
        } finally {
            close(psmt);
        }
    }

    public void addMagazine(Connection con, String magazineName, String magazineDescription, int magazineCategory, double magazinePrice) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(ADD_NEW_MAGAZINE);
            int k = 1;
            statement.setString(k++, magazineName);
            statement.setString(k++, magazineDescription);
            statement.setInt(k++, magazineCategory);
            statement.setDouble(k++, magazinePrice);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot add magazine to data-base with params (name, description, category, price) ==> " +
                    "(" + magazineName + "," + magazineDescription + "," + magazineCategory + "," + magazinePrice + ")", ex);
        } finally {
            close(statement);
        }
    }

    public void deleteMagazineById(Connection con, long magazineId) throws SQLException {
        PreparedStatement psmt = null;
        try {
            psmt = con.prepareStatement(DELETE_MAGAZINE);
            psmt.setLong(1, magazineId);
            psmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot delete magazine with id ==> " + magazineId, ex);
        } finally {
            close(psmt);
        }
    }

    public void setCategory(Connection con, String categoryName) throws SQLException {
        PreparedStatement psmt = null;
        try {
            psmt = con.prepareStatement(SET_CATEGORY);
            psmt.setString(1, categoryName);
            psmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot add category with name ==> " + categoryName, ex);
        } finally {
            close(psmt);
        }
    }

    public Magazine getMagazineById(Connection con, long magazineId) throws SQLException {
        Magazine magazine = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = con.prepareStatement(GET_MAGAZINE_BY_ID);
            psmt.setString(1, String.valueOf(magazineId));
            rs = psmt.executeQuery();
            if (rs.next()) {
                magazine = extractMagazine(rs);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get magazine by ID ==> " + magazineId, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazine;
    }


    public List<Magazine> sortMagazinesByNameAllRequest(Connection con, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_NAME_ALL_REQUEST);
            int k = 1;
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by name", ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }

    public List<Magazine> sortMagazinesByNameSearchRequest(Connection con, String magazineName, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_NAME_SEARCH_REQUEST);
            int k = 1;
            psmt.setString(k++, "%" + magazineName + "%");
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by name ==> " + magazineName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }

    public List<Magazine> sortMagazinesByNameCategoryRequest(Connection con, String categoryName, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_NAME_CATEGORY_REQUEST);
            int k = 1;
            psmt.setString(k++, categoryName);
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by name with category ==> " + categoryName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }


    public List<Magazine> lowHighSortMagazinesByPriceAllRequest(Connection con, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_PRICE_ALL_REQUEST_LH);
            int k = 1;
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price L ==> H", ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }

    public List<Magazine> lowHighSortMagazinesByPriceSearchRequest(Connection con, String magazineName, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_PRICE_SEARCH_REQUEST_LH);
            int k = 1;
            psmt.setString(k++, "%" + magazineName + "%");
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price L ==> H with name ==>" + magazineName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }

    public List<Magazine> lowHighSortMagazinesByPriceCategoryRequest(Connection con, String categoryName, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_PRICE_CATEGORY_REQUEST_LH);
            int k = 1;
            psmt.setString(k++, categoryName);
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price L ==> H with category ==> " + categoryName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }


    public List<Magazine> highLowSortMagazinesByPriceAllRequest(Connection con, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_PRICE_ALL_REQUEST_HL);
            int k = 1;
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price H ==> L", ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }

    public List<Magazine> highLowSortMagazinesByPriceSearchRequest(Connection con, String magazineName, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_PRICE_SEARCH_REQUEST_HL);
            int k = 1;
            psmt.setString(k++, "%" + magazineName + "%");
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price H ==> L with name ==>" + magazineName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }

    public List<Magazine> highLowSortMagazinesByPriceCategoryRequest(Connection con, String categoryName, int currentPage, int recordsPerPage) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int start;
        try {
            start = currentPage * recordsPerPage - recordsPerPage;
            psmt = con.prepareStatement(SORT_MAGAZINES_BY_PRICE_CATEGORY_REQUEST_HL);
            int k = 1;
            psmt.setString(k++, categoryName);
            psmt.setInt(k++, start);
            psmt.setInt(k++, recordsPerPage);
            rs = psmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazine(rs));
            }
        } catch (SQLException ex) {
            logger.error("Cannot sort magazines by price H ==> L with category ==> " + categoryName, ex);
        } finally {
            close(rs);
            close(psmt);
        }
        return magazines;
    }


    private Magazine extractMagazine(ResultSet rs) throws SQLException {
        Magazine magazine = new Magazine();
        magazine.setId(rs.getInt(MAGAZINE_ID));
        magazine.setName(rs.getString(MAGAZINE_NAME));
        magazine.setPrice(rs.getDouble(MAGAZINE_PRICE));
        magazine.setDescription(rs.getString(MAGAZINE_DESCRIPTION));
        magazine.setCategoryName(rs.getString(MAGAZINE_CATEGORY_NAME));
        magazine.setCategoryId(rs.getInt(MAGAZINE_CATEGORY_ID));
        return magazine;
    }

    private Category extractCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt(CATEGORY_ID));
        category.setName(rs.getString(CATEGORY_NAME));
        return category;
    }

//    -----------------------------------------------------------------------------

    public void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                logger.error("Cannot do close", e);
            }
        }
    }

    public void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException throwable) {
                logger.error("Cannot do rollback", throwable);
                throwable.printStackTrace();
            }
        }
    }



}
