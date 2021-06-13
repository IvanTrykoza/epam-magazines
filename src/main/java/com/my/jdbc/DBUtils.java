package com.my.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    public static final Logger logger = LogManager.getLogger(DBUtils.class.getName());
    private static DataSource ds;

    public static void init() {
        if (ds != null) {
            throw new IllegalStateException("Cannot initialize DataSource twice");
        }
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/TestDB");
        } catch (NamingException ex) {
            logger.error("Cannot init DBUtils", ex);
            throw new IllegalStateException("Cannot obtain a data source", ex);
        }

    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            logger.error("Cannot obtain a connection", ex);
            throw new IllegalStateException("Cannot obtain a connection", ex);
        }
        return con;
    }
}
