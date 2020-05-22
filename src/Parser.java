import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Parser {

    public static void main(String[] args) {

        String text = null;
        try {
            text = Files.readString(Paths.get("petstore.json"), StandardCharsets.UTF_8);
            // System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject(text);
        System.out.println(object);

        String message = object.getString("openapi");
        System.out.println(message);
    }
}
