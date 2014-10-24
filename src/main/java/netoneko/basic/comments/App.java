package netoneko.basic.comments;

import static spark.Spark.*;

class App {
    public static void main(String[] args) {
        get("/", (req, res) -> {
            res.type("application/json");
            return "{\"status\": \"ok\"}";
        });
    }
}