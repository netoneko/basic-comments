package netoneko.basic.comments;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

import static spark.Spark.*;

class App {
    private static Dao<Comment, String> commentDao;
    private static final JsonTransformer jsonTransformer = new JsonTransformer();

    public static void dbInit() throws SQLException {
        String databaseUrl = "jdbc:postgresql://localhost/basic_comments?user=comments&password=comments&charSet=utf8";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        commentDao = DaoManager.createDao(connectionSource, Comment.class);
        TableUtils.createTable(connectionSource, Comment.class);
    }

    private static class Error {
        private final String status = "error";
    }

    private static class OK {
        private final String status = "ok";
    }

    public static void main(String[] args) {
        try {
            dbInit();
        } catch (SQLException e) {
            e.printStackTrace();
//            System.exit(-1);
        }

        get("/", (req, res) -> {
            res.type("application/json");
            return new OK();
        }, jsonTransformer);

        get("/api/comments", (req, res) -> {
            res.type("application/json");
            try {
                return commentDao.queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return new Error();
            }
        }, jsonTransformer);

        post("/api/comments", (req, res) -> {
            res.type("application/json");

            try {
                final String text = req.queryParams("text");
                final Comment comment = new Comment(text);
                commentDao.create(comment);
                return comment;
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return new Error();
            }
        }, jsonTransformer);
    }
}