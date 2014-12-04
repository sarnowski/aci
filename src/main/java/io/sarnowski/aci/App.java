package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    @JsonProperty("name")
    private String name;

    @JsonProperty("imageID")
    private String imageID;

    @JsonProperty("isolators")
    private List<Isolator> isolators;

    @JsonProperty("annotations")
    private Map<String,String> annotations;

    private App() {
    }

    public App(final String name, final String imageID) {
        if (name == null) throw new IllegalArgumentException("name not given");
        if (imageID == null) throw new IllegalArgumentException("imageID not given");

        this.name = name;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public String getImageID() {
        return imageID;
    }

    public List<Isolator> getIsolators() {
        if (isolators == null) {
            isolators = new ArrayList<>();
        }
        return isolators;
    }

    public Map<String, String> getAnnotations() {
        if (annotations == null) {
            annotations = new HashMap<>();
        }
        return annotations;
    }
}
