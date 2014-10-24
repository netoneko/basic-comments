package netoneko.basic.comments;

import static spark.Spark.*;
import spark.*;
import spark.utils.IOUtils;

class App {
    public static void main(String[] args) {
        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("application/json");
                return "{status: \"ok\"}";
            }
        });
    }
}