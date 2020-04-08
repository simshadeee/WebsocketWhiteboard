package edu.cmu.andrew.sguglani;

// Figure.java
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;

public class Figure {
    private JsonObject json;

    public Figure() {
    }

    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        Json.createWriter(writer).write(json);
        return writer.toString();
    }

    public Figure(JsonObject json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }
}