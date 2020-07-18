import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {

    public static void jsonDfsTraversal(Object root, String emptyPadding) {

        if (root instanceof JSONObject) {
            for (Map.Entry<String, Object> entry : ((JSONObject) root).toJsonMap().entrySet()) {
                String msg = emptyPadding + entry.getKey();
                if (entry.getValue() instanceof LocationHolder) {
                    msg += " " + ((LocationHolder) entry.getValue()).getLocation();
                }
                System.out.println(msg);
                jsonDfsTraversal(entry.getValue(), emptyPadding + "  ");
            }
        }
        else if (root instanceof JSONArray) {
            int index = 0;
            for (Object o : (JSONArray) root) {
                String msg = emptyPadding + "array [" + index + "]";
                if (o instanceof LocationHolder) {
                    msg += " " + ((LocationHolder) o).getLocation();
                }
                System.out.println(msg);
                jsonDfsTraversal(o, emptyPadding + "  ");
                index++;
            }
        }
        else {
            String msg = emptyPadding;
            if (root instanceof JSONValue) {
                msg += "+ " + ((JSONValue) root).getValue();
            }
            else {
                msg += "+ " + root;
            }
            System.out.println(msg);
        }
    }

    public static void main(String[] args) {
        try {
            String text = Files.readString(Paths.get("c.json"), StandardCharsets.UTF_8);
            //JSONObject object = new JSONObject(text);
            JSONObject object = JSONObject.parseWithLocation(text);
            jsonDfsTraversal(object, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
