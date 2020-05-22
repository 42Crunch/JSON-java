import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTrack;
import org.json.JSONValue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {

    public static void jsonDfsTraversal(Object root, String emptyPadding) {

        if (root instanceof JSONObject) {
            for (Map.Entry<String, Object> entry : ((JSONObject) root).toJsonMap().entrySet()) {
                String print = emptyPadding + entry.getKey() +
                        " (line = " + ((JSONTrack) entry.getValue()).getLine() +
                        " column = " + ((JSONTrack) entry.getValue()).getColumn() +
                        " offset = " + ((JSONTrack) entry.getValue()).getOffset() + "): ";
                System.out.println(print);
                jsonDfsTraversal(entry.getValue(), emptyPadding + "  ");
            }
            return;
        }

        if (root instanceof JSONArray) {
            for (Object o : (JSONArray) root) {
                jsonDfsTraversal(o, emptyPadding + "  ");
            }
            return;
        }

        // Always a leaf node for a primitive type
        if (root instanceof JSONValue) {
            System.out.println(emptyPadding + "* " + ((JSONValue) root).getValue());
        }
    }

    public static void main(String[] args) {

        String text = null;
        try {
            text = Files.readString(Paths.get("petstore.json"), StandardCharsets.UTF_8);
            // System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject(text);
        // System.out.println(object);

        jsonDfsTraversal(object, "");
    }
}
