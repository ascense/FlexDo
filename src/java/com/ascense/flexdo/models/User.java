package com.ascense.flexdo.models;

import com.ascense.flexdo.core.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class User {
    private int id;
    private String username;
    private String pass;
    private String salt;

    protected User(ResultSet res) {
        try {
            this.id = res.getInt("userid");
            this.username = res.getString("username");
            this.pass = res.getString("pass");
            this.salt = res.getString("salt");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User(int id, String username, String password, String salt) {
        this.id = id;
        this.username = username;
        this.pass = password;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return pass;
    }

    public String getSalt() {
        return salt;
    }

    public boolean createUser() {
        if (getUser(this.username) != null) {
            return false;
        }

        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {
            Database.doQuery(
                Database.QueryInt.class,
                ids,
                "INSERT INTO users (username, pass, salt) VALUES(?,?,?) RETURNING userid",
                this.username, this.pass, this.salt
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return !ids.isEmpty();
    }

    public static User getUser(String name) {
        ArrayList<User> users = new ArrayList<User>();

        try {
            Database.doQuery(
                User.class,
                users,
                "SELECT * FROM users WHERE username = ?",
                name
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public static List<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            Database.doQuery(User.class, users, "SELECT * FROM users");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public static User fromResultSet(ResultSet rs) {
        return new User(rs);
    }
}
