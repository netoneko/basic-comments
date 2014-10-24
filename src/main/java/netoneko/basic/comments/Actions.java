package netoneko.basic.comments;

import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;

public class Actions {
    public static class Error {
        private final String status = "error";
    }

    public static class OK {
        private final String status = "ok";
    }

    public static Route health = (req, res) -> {
        res.type("application/json");

        try {
            App.commentDao.countOf();
            return new OK();
        } catch(SQLException e) {
            e.printStackTrace();
            return new Error();
        }
    };

    public static class API {
        public static Route index = (req, res) -> {
            res.type("application/json");
            try {
                return App.commentDao.queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return new Error();
            }
        };

        public static Route create = (req, res) -> {
            res.type("application/json");

            try {
                final String text = req.queryParams("text");
                final Comment comment = new Comment(text);
                App.commentDao.create(comment);
                return comment;
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return new Error();
            }
        };
    }
}
