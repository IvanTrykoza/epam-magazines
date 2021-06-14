package com.my.jdbc;

import com.my.jdbc.constatants.SQLConstant;
import com.my.jdbc.entity.Magazine;
import com.my.jdbc.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

public class DBManagerTest {

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

    @Test
    public void testGetUsers() throws SQLException {
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
    public void testGetUser() throws SQLException {
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
    public void testGetMagazine() throws SQLException {
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(SQLConstant.GET_MAGAZINE_BY_ID)).thenReturn(pstmt);

        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getString("magazine_name")).thenReturn("Coach #3, March 2021");

        DBManager dbManager = DBManager.getInstance();
        Magazine magazine = dbManager.getMagazineById(con, 1);
        Assert.assertEquals("Coach #3, March 2021", magazine.getName());
        System.out.println(magazine);
    }



}
