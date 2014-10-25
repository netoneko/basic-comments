package netoneko.basic.comments;

import org.junit.Test;

import java.util.Map;

import com.google.gson.Gson;
import org.junit.AfterClass;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * TestResponse and request methods courtesy of msharhag@github
 * https://github.com/mscharhag/blog-examples/blob/master/sparkdemo/src/test/java/com/mscharhag/sparkdemo/UserControllerIntegrationTest.java
 */

public class AppTest {
    @BeforeClass
    public static void beforeClass() {
        Configuration.ENV = "test";
        App.main(null);
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void newCommentShouldBeCreated() {
        TestResponse res = request("POST", "/api/comments?text=Doge");
        Map<String, String> json = res.json();

        assertEquals(200, res.status);
        assertEquals("Doge", json.get("text"));
        assertNotNull(json.get("createdAt"));
        assertNotNull(json.get("id"));
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {
        public final String body;
        public final int status;
        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }
        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }
}