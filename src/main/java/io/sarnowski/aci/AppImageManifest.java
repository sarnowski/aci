package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class AppImageManifest {
    private static final String MANIFEST_VERSION = "0.1.0";
    private static final String MANIFEST_KIND = "ImageManifest";

    @JsonProperty("acVersion")
    private String acVersion = MANIFEST_VERSION;

    @JsonProperty("acKind")
    private String acKind = MANIFEST_KIND;

    @JsonProperty("name")
    private String name;

    @JsonProperty("labels")
    private List<Label> labels;

    @JsonProperty("app")
    private App app;

    @JsonProperty("dependencies")
    private List<Dependency> dependencies;

    @JsonProperty("pathWhitelist")
    private List<String> pathWhitelist;

    @JsonProperty("annotations")
    private Map<String,String> annotations;

    private AppImageManifest() {
    }

    public AppImageManifest(final String name) {
        checkArgument(!isNullOrEmpty(name), "app manifest name required");

        this.name = name;
    }

    public String getAcVersion() {
        return acVersion;
    }

    public String getAcKind() {
        return acKind;
    }

    public String getName() {
        return name;
    }

    public List<Label> getLabels() {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        return labels;
    }

    public App getApp() {
        return app;
    }

    public void setApp(final App app) {
        this.app = app;
    }

    public List<Dependency> getDependencies() {
        if (dependencies == null) {
            dependencies = new ArrayList<>();
        }
        return dependencies;
    }

    public List<String> getPathWhitelist() {
        if (pathWhitelist == null) {
            pathWhitelist = new ArrayList<>();
        }
        return pathWhitelist;
    }

    public Map<String, String> getAnnotations() {
        if (annotations == null) {
            annotations = new HashMap<>();
        }
        return annotations;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AppImageManifest that = (AppImageManifest) o;

        if (!acKind.equals(that.acKind)) return false;
        if (!acVersion.equals(that.acVersion)) return false;
        if (labels != null ? !labels.equals(that.labels) : that.labels != null) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = acVersion.hashCode();
        result = 31 * result + acKind.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (labels != null ? labels.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("acVersion", acVersion)
                .add("acKind", acKind)
                .add("name", name)
                .add("labels", labels)
                .toString();
    }
}
