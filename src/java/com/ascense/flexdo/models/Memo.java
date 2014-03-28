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

    public int getID() {
        return id;
    }

    public int getUserID() {
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

    public static Memo getMemo(String name) {
        ArrayList<Memo> memos = new ArrayList<Memo>();

        try {
            Database.doQuery(
                Memo.class,
                memos,
                "SELECT * FROM memo WHERE memoname = ?",
                name
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

    public static List<Memo> getMemos() {
        ArrayList<Memo> memos = new ArrayList<Memo>();

        try {
            Database.doQuery(Memo.class, memos, "SELECT * FROM memo");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return memos;
    }

    public static Memo fromResultSet(ResultSet rs) {
        return new Memo(rs);
    }
}
