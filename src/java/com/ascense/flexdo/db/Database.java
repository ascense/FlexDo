package com.ascense.flexdo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class Database {
    public static Connection getConnection()
            throws NamingException, SQLException {
        InitialContext cxt = new InitialContext();
        DataSource dataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");

        return dataSource.getConnection();
    }


    public static boolean hasConnection() {
        try {
            Connection c = getConnection();
            return c != null;
        } catch(Exception e) {
            return false;
        }
    }


    public static List<String> getTables()
            throws NamingException, SQLException {
        Connection conn = getConnection();
        String url = conn.getMetaData().getURL();

        String query = "SELECT tablename FROM pg_tables WHERE tableowner != 'postgres' ORDER BY tablename";
        ArrayList<String> tables = new ArrayList<String>();

        PreparedStatement stmnt = conn.prepareStatement(query);
        ResultSet tableData = stmnt.executeQuery();

        while (tableData.next())
            tables.add(tableData.getString(1));

        tableData.close();
        stmnt.close();
        conn.close();

        return tables;
    }
}
