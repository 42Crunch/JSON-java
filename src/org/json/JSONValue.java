package org.json;

public class JSONValue extends JSONTrack {

    private final Object value;

    public JSONValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
