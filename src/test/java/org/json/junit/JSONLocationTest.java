package org.json.junit;

import org.json.*;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.*;

public class JSONLocationTest {

    private static void jsonDfsTraversal(Object root, Set<String> result, boolean hasLocation) {

        if (root instanceof JSONObject) {
            for (Map.Entry<String, Object> entry : ((JSONObject) root).toJsonMap().entrySet()) {
                if (entry.getValue() instanceof LocationHolder) {
                    if (hasLocation) {
                        result.add(entry.getKey() + " " + ((LocationHolder) entry.getValue()).getLocation());
                    }
                    else {
                        assert ((LocationHolder) entry.getValue()).getLocation() == null;
                    }
                }
                jsonDfsTraversal(entry.getValue(), result, hasLocation);
            }
        }
        else if (root instanceof JSONArray) {
            int index = 0;
            for (Object o : (JSONArray) root) {
                if (o instanceof LocationHolder) {
                    if (hasLocation) {
                        result.add("array [" + index + "] " + ((LocationHolder) o).getLocation());
                    }
                    else {
                        assert ((LocationHolder) o).getLocation() == null;
                    }
                }
                jsonDfsTraversal(o, result, hasLocation);
                index++;
            }
        }
        else {
            String msg = (root instanceof JSONValue) ? "+" + ((JSONValue) root).getValue() : "+" + root;
            if (hasLocation) {
                result.add(msg);
            }
            else {
                assert !(root instanceof JSONValue);
            }
        }
    }

    private String getTextFromFile(String fileName) throws IOException {

        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileName);
        assert resourceAsStream != null;

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(
                resourceAsStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }

    private static void print(Set<String> items) {
        System.out.println(items.size());
        for (String s : items) {
            System.out.println("assertTrue(items.contains(\"" + s +"\"));");
        }
    }

    @Test
    public void jsonObjectTrickyWithLocation2() throws IOException {

        JSONObject object = JSONObject.parseWithLocation(getTextFromFile("test1.json"));

        JSONLocation location = object.getLocation();
        assertTrue((location != null) && location.toString().contains("<line 0, column 2, offset (0, 1)>"));

        Set<String> items = new HashSet<>();
        jsonDfsTraversal(object, items, true);

        assertEquals(29, items.size());
        assertTrue(items.contains("array [3] <line 7, column 6, offset (108, 109)>"));
        assertTrue(items.contains("+ID"));
        assertTrue(items.contains("swagger <line 1, column 11, offset (5, 12)>"));
        assertTrue(items.contains("+10"));
        assertTrue(items.contains("array [4] <line 8, column 6, offset (117, 118)>"));
        assertTrue(items.contains("+12"));
        assertTrue(items.contains("array [2] <line 13, column 8, offset (179, 186)>"));
        assertTrue(items.contains("array [1] <line 12, column 6, offset (166, 170)>"));
        assertTrue(items.contains("array [1] <line 5, column 9, offset (86, 88)>"));
        assertTrue(items.contains("enum <line 4, column 10, offset (68, 72)>"));
        assertTrue(items.contains("array [0] <line 11, column 6, offset (154, 158)>"));
        assertTrue(items.contains("array [4] <line 15, column 6, offset (208, 218)>"));
        assertTrue(items.contains("type <line 3, column 10, offset (46, 50)>"));
        assertTrue(items.contains("+5678989080"));
        assertTrue(items.contains("+1"));
        assertTrue(items.contains("+4"));
        assertTrue(items.contains("+6"));
        assertTrue(items.contains("+true"));
        assertTrue(items.contains("abc <line 10, column 9, offset (140, 143)>"));
        assertTrue(items.contains("array [5] <line 8, column 14, offset (125, 127)>"));
        assertTrue(items.contains("array [0] <line 5, column 6, offset (83, 84)>"));
        assertTrue(items.contains("+false"));
        assertTrue(items.contains("array [2] <line 6, column 8, offset (97, 99)>"));
        assertTrue(items.contains("+xxxxxxx"));
        assertTrue(items.contains("array [3] <line 14, column 6, offset (195, 200)>"));
        assertTrue(items.contains("+string"));
        assertTrue(items.contains("+2.0"));
        assertTrue(items.contains("+1.02"));
        assertTrue(items.contains("paymentForm <line 2, column 15, offset (25, 36)>"));
    }

    @Test
    public void jsonObjectTrickyWithLocation() throws IOException {

        JSONObject object = JSONObject.parseWithLocation(getTextFromFile("jsonpointer-testdoc-crlf.json"));

        JSONLocation location = object.getLocation();
        assertTrue((location != null) && location.toString().contains("<line 1, column 17, offset (17, 18)>"));

        Set<String> items = new HashSet<>();
        jsonDfsTraversal(object, items, true);

        assertTrue(items.contains("+val"));
        assertTrue(items.contains("+baz"));
        assertTrue(items.contains("c%d <line 9, column 7, offset (82, 85)>"));
        assertTrue(items.contains(" <line 24, column 8, offset (287, 287)>"));
        assertTrue(items.contains("a/b <line 8, column 7, offset (70, 73)>"));
        assertTrue(items.contains("e^f <line 10, column 7, offset (94, 97)>"));
        assertTrue(items.contains("foo <line 2, column 7, offset (22, 25)>"));
        assertTrue(items.contains("+bar"));
        assertTrue(items.contains("array [0] <line 20, column 10, offset (249, 252)>"));
        assertTrue(items.contains(" <line 7, column 4, offset (61, 61)>"));
        assertTrue(items.contains("+0"));
        assertTrue(items.contains("i\\j <line 12, column 8, offset (119, 122)>"));
        assertTrue(items.contains("+1"));
        assertTrue(items.contains("g|h <line 11, column 7, offset (106, 109)>"));
        assertTrue(items.contains("+2"));
        assertTrue(items.contains("key <line 17, column 9, offset (180, 183)>"));
        assertTrue(items.contains("other~key <line 18, column 15, offset (201, 210)>"));
        assertTrue(items.contains("+3"));
        assertTrue(items.contains("+4"));
        assertTrue(items.contains("m~n <line 15, column 7, offset (154, 157)>"));
        assertTrue(items.contains("+5"));
        assertTrue(items.contains("+value"));
        assertTrue(items.contains("+6"));
        assertTrue(items.contains("+7"));
        assertTrue(items.contains("+Some other value"));
        assertTrue(items.contains("+8"));
        assertTrue(items.contains("array [1] <line 5, column 6, offset (48, 51)>"));
        assertTrue(items.contains("obj <line 16, column 7, offset (166, 169)>"));
        assertTrue(items.contains("  <line 14, column 5, offset (144, 145)>"));
        assertTrue(items.contains("+empty key of an object with an empty key"));
        assertTrue(items.contains("array [0] <line 4, column 6, offset (37, 40)>"));
        assertTrue(items.contains("subKey <line 25, column 14, offset (342, 348)>"));
        assertTrue(items.contains("another/key <line 19, column 19, offset (223, 234)>"));
        assertTrue(items.contains("k\"l <line 13, column 8, offset (132, 135)>"));
        assertTrue(items.contains(" <line 23, column 6, offset (274, 274)>"));
    }

    @Test
    public void jsonObjectDefault() throws IOException {

        JSONObject object = new JSONObject(getTextFromFile("params.json"));
        JSONLocation location = object.getLocation();
        assert location == null;
        jsonDfsTraversal(object, null, false);
    }

    @Test
    public void jsonObjectWithLocation() throws IOException {

        JSONObject object = JSONObject.parseWithLocation(getTextFromFile("params.json"));

        JSONLocation location = object.getLocation();
        assertTrue((location != null) && location.toString().contains("<line 0, column 2, offset (0, 1)>"));

        Set<String> items = new HashSet<>();
        jsonDfsTraversal(object, items, true);

        assertEquals(45, items.size());
        assertTrue(items.contains("+path"));
        assertTrue(items.contains("version <line 41, column 13, offset (810, 817)>"));
        assertTrue(items.contains("+1.0.0"));
        assertTrue(items.contains("required <line 10, column 22, offset (146, 154)>"));
        assertTrue(items.contains("array [0] <line 9, column 11, offset (131, 132)>"));
        assertTrue(items.contains("array [0] <line 3, column 6, offset (42, 46)>"));
        assertTrue(items.contains("swagger <line 1, column 11, offset (5, 12)>"));
        assertTrue(items.contains("+OK"));
        assertTrue(items.contains("description <line 30, column 25, offset (599, 610)>"));
        assertTrue(items.contains("get <line 7, column 11, offset (89, 92)>"));
        assertTrue(items.contains("type <line 23, column 18, offset (450, 454)>"));
        assertTrue(items.contains("array [2] <line 21, column 11, offset (405, 406)>"));
        assertTrue(items.contains("+one"));
        assertTrue(items.contains("basePath <line 37, column 12, offset (684, 692)>"));
        assertTrue(items.contains("in <line 25, column 16, offset (509, 511)>"));
        assertTrue(items.contains("host <line 36, column 8, offset (662, 666)>"));
        assertTrue(items.contains("schemes <line 2, column 11, offset (25, 32)>"));
        assertTrue(items.contains("+two"));
        assertTrue(items.contains("description <line 39, column 17, offset (717, 728)>"));
        assertTrue(items.contains("required <line 22, column 22, offset (420, 428)>"));
        assertTrue(items.contains("title <line 40, column 11, offset (789, 794)>"));
        assertTrue(items.contains("name <line 24, column 18, offset (480, 484)>"));
        assertTrue(items.contains("+xkcd.com"));
        assertTrue(items.contains("responses <line 28, column 19, offset (553, 562)>"));
        assertTrue(items.contains("type <line 17, column 18, offset (313, 317)>"));
        assertTrue(items.contains("array [1] <line 15, column 11, offset (268, 269)>"));
        assertTrue(items.contains("info <line 38, column 8, offset (703, 707)>"));
        assertTrue(items.contains("+/"));
        assertTrue(items.contains("200 <line 29, column 15, offset (578, 581)>"));
        assertTrue(items.contains("in <line 13, column 16, offset (233, 235)>"));
        assertTrue(items.contains("+three"));
        assertTrue(items.contains("+true"));
        assertTrue(items.contains("name <line 12, column 18, offset (206, 210)>"));
        assertTrue(items.contains("+Webcomic of romance, sarcasm, math, and language."));
        assertTrue(items.contains("name <line 18, column 18, offset (343, 347)>"));
        assertTrue(items.contains("required <line 16, column 22, offset (283, 291)>"));
        assertTrue(items.contains("type <line 11, column 18, offset (176, 180)>"));
        assertTrue(items.contains("in <line 19, column 16, offset (370, 372)>"));
        assertTrue(items.contains("+XKCD"));
        assertTrue(items.contains("paths <line 5, column 9, offset (56, 61)>"));
        assertTrue(items.contains("parameters <line 8, column 20, offset (106, 116)>"));
        assertTrue(items.contains("/hello <line 6, column 12, offset (71, 77)>"));
        assertTrue(items.contains("+string"));
        assertTrue(items.contains("+http"));
        assertTrue(items.contains("+2.0"));
    }
}
