package netoneko.basic.comments;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

import static spark.Spark.*;

class App {
    public static Dao<Comment, String> commentDao;
    private static final JsonTransformer jsonTransformer = new JsonTransformer();

    public static void initializeDatabase() {
        try {
            String databaseUrl = "jdbc:postgresql://localhost/basic_comments?user=comments&password=comments&charSet=utf8";
            ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
            commentDao = DaoManager.createDao(connectionSource, Comment.class);
            TableUtils.createTableIfNotExists(connectionSource, Comment.class);
        } catch (SQLException e) {
            e.printStackTrace();

            /**
             * There is a bug that still tries to create a sequence even if it exists
             */
            if (e.getCause() instanceof SQLException) {
                final String sqlState = ((SQLException) e.getCause()).getSQLState();
                if ("42P07".equals(sqlState)) {
                    System.err.println("Ignore sequence creation errors");
                    return;
                }
            }

            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        initializeDatabase();

        get("/health", Actions.health, jsonTransformer);
        get("/api/comments", Actions.API.index, jsonTransformer);
        post("/api/comments", Actions.API.create, jsonTransformer);
    }
}