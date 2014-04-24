package com.ascense.flexdo.models;

import com.ascense.flexdo.core.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Task {
    private int memoid;
    private int priority;
    private Timestamp closed;

    protected Task(ResultSet res) {
        try {
            this.memoid = res.getInt("memoid");
            this.priority = res.getInt("priority");
            this.closed = res.getTimestamp("closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Task(int memoid, int priority, Timestamp closed) {
        this.memoid = memoid;
        this.priority = priority;
        this.closed = closed;
    }

    public int getMemoID() {
        return memoid;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Timestamp getClosed() {
        return closed;
    }

    public void setClosed() {
        this.closed = new Timestamp(new Date().getTime());
    }

    public boolean deleteTask() {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {
            Database.doQuery(
                Database.QueryInt.class,
                ids,
                "DELETE FROM task WHERE memoid=? RETURNING memoid",
                this.memoid
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return !ids.isEmpty();
    }

    public boolean createTask() {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {
            Database.doQuery(
                Database.QueryInt.class,
                ids,
                "INSERT INTO task (memoid, priority, closed) VALUES(?,?,?) RETURNING memoid",
                this.memoid,this.priority, this.closed
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return !ids.isEmpty();
    }

    public boolean updateTask() {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {
            Database.doQuery(
                Database.QueryInt.class,
                ids,
                "UPDATE task SET priority=?, closed=? WHERE memoid=? RETURNING memoid",
                this.priority, this.closed, this.memoid
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!ids.isEmpty()) {
            return true;
        }
        return false;
    }

    public static Task getTask(int memoid) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        try {
            Database.doQuery(
                Task.class,
                tasks,
                "SELECT * FROM task WHERE memoid = ?",
                memoid
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!tasks.isEmpty()) {
            return tasks.get(0);
        } else {
            return null;
        }
    }

    public static List<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        try {
            Database.doQuery(Task.class, tasks, "SELECT * FROM task");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }

    /*
     * Get a list of memos with tasks attached
     */
    public static List<Memo> getFilteredMemos(int userid, boolean closed) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        String query = "SELECT * FROM memo JOIN task ON memo.memoid = task.memoid WHERE memo.memoid IN (SELECT memoid FROM task)  AND ";
        // show only closed, or show only open
        query += (closed) ? "closed IS NOT NULL" : "closed IS NULL";
        query += " AND userid=? ORDER BY priority, memoname";

        try {
            Database.doQuery(
                Memo.class,
                memos,
                query,
                userid
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return memos;
    }

    public static Task fromResultSet(ResultSet rs) {
        return new Task(rs);
    }
}
