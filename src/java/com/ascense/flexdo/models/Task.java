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

    public boolean isClosed() {
        if (getClosed() == null) {
            return false;
        }
        return true;
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
                "UPDATE task SET priority=? WHERE memoid=? RETURNING memoid",
                this.priority, this.memoid
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

    public static Task fromResultSet(ResultSet rs) {
        return new Task(rs);
    }
}
