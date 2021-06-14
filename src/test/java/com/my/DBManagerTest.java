package com.my;

import com.my.jdbc.DBManager;
import com.my.jdbc.DBUtils;
import com.my.jdbc.constatants.SQLConstant;
import com.my.jdbc.entity.Category;
import com.my.jdbc.entity.Magazine;
import com.my.jdbc.entity.Subscription;
import com.my.jdbc.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class DBManagerTest {

    private static final double DELTA = 1e-15;
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private MockedStatic<DBUtils> dbUtils;


    @Before
    public void setUp() {
        con = Mockito.mock(Connection.class);
        stmt = Mockito.mock(Statement.class);
        pstmt = Mockito.mock(PreparedStatement.class);
        rs = Mockito.mock(ResultSet.class);

        dbUtils = Mockito.mockStatic(DBUtils.class);
        dbUtils.when(DBUtils::getConnection).thenReturn(con);
    }

    @After
    public void tearDown() {
        dbUtils.close();
    }

    /////////////////////////////////////////////////////////

    @Test
    public void testGetAllMagazines() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.FIND_ALL_MAGAZINES)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getString("magazine_id")).thenReturn("1").thenReturn("3").thenReturn("4");

        DBManager dbManager = DBManager.getInstance();
        List<Magazine> magazines = dbManager.getAllMagazines(con, 1, 3);
        Assert.assertEquals(3, magazines.size());
        System.out.println(magazines);
    }

    @Test
    public void testGetUserByLogin() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.FIND_USER_BY_LOGIN)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getString("login")).thenReturn("admin");

        DBManager dbManager = DBManager.getInstance();
        User user = dbManager.getUserByLogin(con, "admin");
        Assert.assertEquals("admin", user.getLogin());
        System.out.println(user);
    }

    @Test
    public void testGetUserById() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.FIND_USER_BY_ID)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getString("login")).thenReturn("admin");

        DBManager dbManager = DBManager.getInstance();
        User user = dbManager.getUserById(con, 1);
        Assert.assertEquals("admin", user.getLogin());
        System.out.println(user);
    }

    @Test
    public void testGetMagazineById() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.GET_MAGAZINE_BY_ID)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getString("magazine_name")).thenReturn("Coach #3, March 2021");

        DBManager dbManager = DBManager.getInstance();
        Magazine magazine = dbManager.getMagazineById(con, 1);
        Assert.assertEquals("Coach #3, March 2021", magazine.getName());
        System.out.println(magazine);
    }

    @Test
    public void testSetUserStatus() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.SET_USER_STATUS)).thenReturn(pstmt);

        Mockito.when(pstmt.executeUpdate()).thenReturn(1);
        DBManager dbManager = DBManager.getInstance();

        int resultRows = dbManager.setUserStatus(con, SQLConstant.VALUE_STATUS_FALSE, 5);
        Assert.assertEquals(1, resultRows);
    }

    @Test
    public void testGetUserStatus() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.GET_USER_STATUS)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getBoolean("status")).thenReturn(true);

        DBManager dbManager = DBManager.getInstance();
        Assert.assertTrue(dbManager.getUserStatus(con, 1));
    }

    @Test
    public void testCreateUser() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.ADD_NEW_USER)).thenReturn(pstmt);

        Mockito.when(pstmt.executeUpdate()).thenReturn(1);
        DBManager dbManager = DBManager.getInstance();

        int resultRows = dbManager.createUser(con, "login", "login", "Ivan Ivanov", 1);
        Assert.assertEquals(1, resultRows);
    }

    @Test
    public void testGetActualBalance() throws SQLException {

        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.GET_USER_BALANCE)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getDouble("wallet")).thenReturn(60D);

        DBManager dbManager = DBManager.getInstance();

        Assert.assertEquals(60, dbManager.getActualBalance(con, 1), DELTA);
    }

    @Test
    public void testDoSubscribe() throws SQLException {

        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.DO_SUBSCRIPTION)).thenReturn(pstmt);


        Mockito.when(pstmt.executeUpdate()).thenReturn(1);
        DBManager dbManager = DBManager.getInstance();

        long millis = System.currentTimeMillis();
        java.sql.Date startDate = new java.sql.Date(millis);

        LocalDate endDate = LocalDate.now().plusMonths(1);

        int resultRows = dbManager.doSubscribe(con, 1, 1, startDate, Date.valueOf(endDate));
        Assert.assertEquals(1, resultRows);
    }

    @Test
    public void testGetSubscriptions() throws SQLException {

        Mockito.when(stmt.executeQuery(SQLConstant.GET_SUBSCRIPTION)).thenReturn(rs);
        Mockito.when(con.createStatement()).thenReturn(stmt);

        Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getString("sub_id")).thenReturn("1").thenReturn("2").thenReturn("3");

        DBManager dbManager = DBManager.getInstance();
        List<Subscription> subscriptions = dbManager.getSubscriptions(con);
        Assert.assertEquals(3, subscriptions.size());
        System.out.println(subscriptions);
    }

    @Test
    public void testGetSubscriptionsByUserId() throws SQLException {

        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.GET_SUBSCRIPTION_BY_USER_ID)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getString("sub_id")).thenReturn("1").thenReturn("2").thenReturn("3");

        DBManager dbManager = DBManager.getInstance();
        List<Subscription> subscriptions = dbManager.getSubscriptionsByUserId(con, 1);
        Assert.assertEquals(3, subscriptions.size());
        System.out.println(subscriptions);
    }

    @Test
    public void testFindMagazinesByName() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.FIND_MAGAZINES_BY_NAME)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getString("magazine_name")).thenReturn("a").thenReturn("s").thenReturn("d");

        DBManager dbManager = DBManager.getInstance();
        List<Magazine> magazines = dbManager.findMagazinesByName(con, "asd", 1, 8);
        Assert.assertEquals(3, magazines.size());
        System.out.println(magazines);
    }

    @Test
    public void testFindMagazinesByCategory() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.FIND_MAGAZINES_BY_CATEGORY)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getString("magazine_name")).thenReturn("a").thenReturn("s").thenReturn("d");

        DBManager dbManager = DBManager.getInstance();
        List<Magazine> magazines = dbManager.findMagazinesByCategory(con, "asd", 1, 8);
        Assert.assertEquals(3, magazines.size());
        System.out.println(magazines);
    }

    @Test
    public void testGetCategories() throws SQLException {
        Mockito.when(stmt.executeQuery(SQLConstant.GET_CATEGORY))
                .thenReturn(rs);
        Mockito.when(con.createStatement()).thenReturn(stmt);


        Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getInt("category_id")).thenReturn(1).thenReturn(2).thenReturn(3);

        DBManager dbManager = DBManager.getInstance();
        List<Category> categories = dbManager.getCategories(con);
        Assert.assertEquals(3, categories.size());
        System.out.println(categories);
    }

    @Test
    public void testSetMagazineInfo() throws SQLException{
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.SET_MAGAZINE_INFO)).thenReturn(pstmt);

        Mockito.when(pstmt.executeUpdate()).thenReturn(1);
        DBManager dbManager = DBManager.getInstance();

        int resultRows = dbManager.setMagazineInfo(con, "name", "description", 1,20, 1);
        Assert.assertEquals(1, resultRows);
    }

    @Test
    public void testAddMagazine() throws SQLException{
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.ADD_NEW_MAGAZINE)).thenReturn(pstmt);

        Mockito.when(pstmt.executeUpdate()).thenReturn(1);
        DBManager dbManager = DBManager.getInstance();

        int resultRows = dbManager.addMagazine(con, "name", "description", 1,20);
        Assert.assertEquals(1, resultRows);
    }

    @Test
    public void setCategory() throws SQLException{
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.SET_CATEGORY)).thenReturn(pstmt);

        Mockito.when(pstmt.executeUpdate()).thenReturn(1);
        DBManager dbManager = DBManager.getInstance();

        int resultRows = dbManager.setCategory(con, "asdf");
        Assert.assertEquals(1, resultRows);
    }

}
