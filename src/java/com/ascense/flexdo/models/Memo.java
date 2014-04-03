package com.ascense.flexdo.models;

import com.ascense.flexdo.core.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Memo {
    private int id;
    private int userid;
    private String memoname;
    private String content;
    private Timestamp created;

    protected Memo(ResultSet res) {
        try {
            this.id = res.getInt("memoid");
            this.userid = res.getInt("userid");
            this.memoname = res.getString("memoname");
            this.content = res.getString("content");
            this.created = res.getTimestamp("created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Memo(int id, int userid, String memoname, String content, Timestamp created) {
        this.id = id;
        this.userid = userid;
        this.memoname = memoname;
        this.content = content;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userid;
    }

    public String getName() {
        return memoname;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreated() {
        return created;
    }

    public List<Category> getCategories() {
        ArrayList<Category> cats = new ArrayList<Category>();


        try {
            Database.doQuery(
                Category.class,
                cats,
                "SELECT * FROM memo_category JOIN category ON category.catid = memo_category.catid WHERE memoid = ?",
                this.id
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return cats;
    }

    public static Memo getMemo(int id) {
        ArrayList<Memo> memos = new ArrayList<Memo>();

        try {
            Database.doQuery(
                Memo.class,
                memos,
                "SELECT * FROM memo WHERE memoid = ?",
                id
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!memos.isEmpty()) {
            return memos.get(0);
        } else {
            return null;
        }
    }

    /*
     * Get a list of all memos not part of a task
     */
    public static List<Memo> getMemos() {
        ArrayList<Memo> memos = new ArrayList<Memo>();

        try {
            Database.doQuery(
                Memo.class,
                memos,
                "SELECT * FROM memo WHERE memoid NOT IN (SELECT memoid FROM task)"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return memos;
    }

    public static Memo fromResultSet(ResultSet rs) {
        return new Memo(rs);
    }
}
