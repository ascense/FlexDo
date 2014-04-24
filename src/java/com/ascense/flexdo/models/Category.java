package com.ascense.flexdo.models;

import com.ascense.flexdo.core.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Category {
    private int id;
    private int userid;
    private String catname;
    private int parentid;

    protected Category(ResultSet res) {
        try {
            this.id = res.getInt("catid");
            this.userid = res.getInt("userid");
            this.catname = res.getString("catname");
            this.parentid = res.getInt("parentid");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category(int id, int userid, String catname, int parentid) {
        this.id = id;
        this.userid = userid;
        this.catname = catname;
        this.parentid = parentid;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userid;
    }

    public String getName() {
        return catname;
    }

    public int getParentId() {
        return parentid;
    }

    public Category getParent() {
        if (parentid != 0) {
            return Category.getCategory(parentid);
        }
        return null;
    }

    /**
     * Get a list of all memos in this category.
     */
    public List<Memo> getMemos(int userid) {
        ArrayList<Memo> memos = new ArrayList<Memo>();

        try {
            Database.doQuery(
                Memo.class,
                memos,
                "SELECT * FROM memo_category JOIN memo ON memo_category.memoid = memo.memoid WHERE catid=? AND userid=?",
                this.id,
                userid
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return memos;
    }

    public static Category getCategory(int id) {
        ArrayList<Category> cats = new ArrayList<Category>();

        try {
            Database.doQuery(
                Category.class,
                cats,
                "SELECT * FROM category WHERE catid = ?",
                id
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!cats.isEmpty()) {
            return cats.get(0);
        } else {
            return null;
        }
    }

    public static List<Category> getCategories(int userid) {
        ArrayList<Category> cats = new ArrayList<Category>();

        try {
            Database.doQuery(
                Category.class,
                cats,
                "SELECT * FROM category WHERE userid=?",
                userid
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return cats;
    }

    public static Category fromResultSet(ResultSet rs) {
        return new Category(rs);
    }
}
