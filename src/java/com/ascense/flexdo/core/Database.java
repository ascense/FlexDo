package com.ascense.flexdo.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    public static class QueryInt {
        public static Integer fromResultSet(ResultSet rs) {
            try {
                return new Integer(rs.getInt(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

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

        while (tableData.next()) {
            tables.add(tableData.getString(1));
        }

        tableData.close();
        stmnt.close();
        conn.close();

        return tables;
    }

    public static void doQuery(Class cls, List list, String query, Object... qvals)
            throws NamingException, SQLException, NoSuchMethodException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // TODO: Fix ugly hacks
        Method instantiate = null;
        if (cls != null) {
            instantiate = cls.getMethod("fromResultSet", ResultSet.class);
        }

        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(query);

            for (int i = 0; i < qvals.length; ++i) {
                ps.setObject(i + 1, qvals[i]);
            }

            rs = ps.executeQuery();

            if (cls != null && list != null) {
                while (rs.next()) {
                    list.add(instantiate.invoke(null, rs));
                }
            }

        } catch (IllegalAccessException e) {
            throw new NoSuchMethodException("fromResultSet(<ResultSet>)");
        } catch (InvocationTargetException e) {
            throw new NoSuchMethodException("fromResultSet(<ResultSet>)");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    /*
     * Conviniance wrapper for doQuery(cls, list, query, qvals)
     */
    public static void doQuery(String query, Object... qvals)
            throws NamingException, SQLException, NoSuchMethodException {
        doQuery(null, null, query, qvals);
    }
}
