package com.ascense.flexdo.models;

import com.ascense.flexdo.core.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Task {
    private int memoid;
    private int priority;
    private Timestamp closed;

    private Memo memo;

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

    public void setMemo(Memo memo) {
        this.memo = memo;
    }

    public Memo getMemo() {
        if (this.memo == null) {
            this.memo = Memo.getMemo(this.memoid);
        }
        return this.memo;
    }

    public int getMemoID() {
        return memoid;
    }

    public int getPriority() {
        return priority;
    }

    public Timestamp getClosed() {
        return closed;
    }

    public boolean isClosed() {
        if (getClosed() == null) {
            return false;
        }
        return true;
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

    public static Task fromResultSet(ResultSet rs) {
        return new Task(rs);
    }
}