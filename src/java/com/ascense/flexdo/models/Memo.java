package com.ascense.flexdo.models;

import com.ascense.flexdo.core.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Memo {
    private int id;
    private int userid;
    private String memoname;
    private String content;
    private Timestamp created;

    private boolean taskCached;
    private Task task;

    protected Memo(ResultSet res) {
        try {
            initialize(
                res.getInt("memoid"),
                res.getInt("userid"),
                res.getString("memoname"),
                res.getString("content"),
                res.getTimestamp("created")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Memo(int id, int userid, String memoname, String content) {
        initialize(
            id,
            userid,
            memoname,
            content,
            new Timestamp(new Date().getTime())
        );
    }

    /**
     * Initialization function for constructors to wrap
     * @param id
     * @param userid
     * @param memoname
     * @param content
     * @param created
     */
    private void initialize(int id, int userid, String memoname, String content, Timestamp created) {
        this.id = id;
        this.userid = userid;
        this.memoname = memoname;
        this.content = content;
        this.created = created;

        // if id < 0, we are creating a new memo, so no task can be attached yet
        this.taskCached = (id < 0);
        this.task = null;
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

    public void setName(String name) {
        this.memoname = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    /**
     * Set categories for Memo.
     *
     * Unlike most other Memo-methods, changes are applied directly to the database!
     *
     * @param categories Complete list of categories to be assigned
     */
    public void setCategories(int[] categories) {
        // create new args array to be passed as varargs to doQuery
        Object args[];

        // Remove old categories not in new list:
        args = new Object[categories.length + 1];
        args[0] = this.id;
        for (int i = 0; i < categories.length; ++i) {
            args[i + 1] = categories[i];
        }

        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM memo_category WHERE memoid=?");
        if (categories.length > 0) {
            query.append("AND catid NOT IN (");
            for (int i = 0; i < categories.length - 1; ++i) {
                query.append("?, ");
            }
            query.append("?) RETURNING memoid");
        }

        try {
            Database.doQuery(query.toString(), args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Get existing category ids:
        ArrayList<Integer> old_cats = new ArrayList<Integer>();
        try {
            Database.doQuery(
                Database.QueryInt.class,
                old_cats,
                "SELECT catid FROM memo_category WHERE memoid=?",
                this.id
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Add newly assigned categories:
        ArrayList<Integer> new_cats = new ArrayList<Integer>();
        for (int catid : categories) {
            if (!old_cats.contains((Integer) catid)) {
                new_cats.add(catid);
            }
        }

        if (new_cats.size() == 0) return;

        args = new Object[new_cats.size() * 2];
        for (int i = 0; i < new_cats.size(); ++i) {
            args[i * 2] = this.id;
            args[i * 2 + 1] = new_cats.get(i);
        }

        query = new StringBuilder();
        query.append("INSERT INTO memo_category (memoid, catid) VALUES ");
        for (int i = 0; i < new_cats.size() - 1; ++i) {
            query.append("(?,?), ");
        }
        query.append("(?,?) RETURNING memoid");

        try {
            Database.doQuery(query.toString(), args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setTask(Task task) {
        if (task.getMemoID() != this.id) {
            return;
        }
        this.taskCached = true;
        this.task = task;
    }

    public Task getTask() {
        if (this.taskCached == false) {
            this.task = Task.getTask(this.id);
            this.taskCached = true;
        }
        return this.task;
    }

    public boolean createMemo() {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {
            Database.doQuery(
                Database.QueryInt.class,
                ids,
                "INSERT INTO memo (userid, memoname, content, created) VALUES(?,?,?,?) RETURNING memoid",
                this.userid, this.memoname, this.content, this.created
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!ids.isEmpty()) {
            this.id = ids.get(0);
            return true;
        }
        return false;
    }

    public boolean updateMemo() {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {
            Database.doQuery(
                Database.QueryInt.class,
                ids,
                "UPDATE memo SET memoname=?, content=? WHERE memoid=? RETURNING memoid",
                this.memoname, this.content, this.id
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!ids.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean deleteMemo() {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {
            // remove any references to memo first
            Database.doQuery(
                Database.QueryInt.class,
                ids,
                "DELETE FROM memo_category WHERE memoid=? RETURNING memoid",
                this.id
            );
            // then remove the memo itself
            Database.doQuery(
                Database.QueryInt.class,
                ids,
                "DELETE FROM memo WHERE memoid=? RETURNING memoid",
                this.id
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.id = -1;
        return !ids.isEmpty();
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

    /**
     * Get a list of all memos.
     */
    public static List<Memo> getMemos(int userid) {
        ArrayList<Memo> memos = new ArrayList<Memo>();

        try {
            Database.doQuery(
                Memo.class,
                memos,
                "SELECT * FROM memo WHERE userid=?",
                userid
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return memos;
    }

    /**
     * Get a filtered list of memos.
     *
     * The returned list consists either of only memos with tasks attached,
     * or of memos without tasks attached.
     * Parameter taskMemos is used to determine filtering.
     *
     * @param taskMemos If true, search for task memos, if false, search for non-task memos
     * @return
     */
    public static List<Memo> getFilteredMemos(int userid) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        String query = "SELECT * FROM memo WHERE memoid NOT IN (SELECT memoid FROM task) AND userid=? ORDER BY memoname";

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

    public static Memo fromResultSet(ResultSet rs) {
        return new Memo(rs);
    }

    /**
     * Wrapper function for use in JSP files
     */
    public static boolean hasCategory(Memo memo, Category cat) {
        if (memo == null || cat == null) return false;

        for (Category iCat : memo.getCategories()) {
            if (iCat.getId() == cat.getId()) {
                return true;
            }
        }
        return false;
    }
}
