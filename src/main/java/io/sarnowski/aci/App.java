package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

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
        checkArgument(!isNullOrEmpty(name), "app name required");
        checkArgument(!isNullOrEmpty(imageID), "app imageID required");

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
            annotations = Maps.newHashMap();
        }
        return annotations;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final App app = (App) o;

        if (!name.equals(app.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("imageID", imageID)
                .toString();
    }
}
