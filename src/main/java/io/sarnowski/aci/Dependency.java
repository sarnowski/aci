package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class Dependency {
    @JsonProperty("name")
    private String name;

    @JsonProperty("labels")
    private List<Label> labels;

    @JsonProperty("hash")
    private String hash;

    @JsonProperty("root")
    private String root;

    private Dependency() {
    }

    public Dependency(final String name, final String root) {
        checkArgument(!isNullOrEmpty(name), "dependency name required");
        checkArgument(!isNullOrEmpty(root), "dependency root required");

        this.name = name;
        this.root = root;
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

    public String getHash() {
        return hash;
    }

    public void setHash(final String hash) {
        this.hash = hash;
    }

    public String getRoot() {
        return root;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Dependency that = (Dependency) o;

        if (!name.equals(that.name)) return false;
        if (!hash.equals(that.hash)) return false;
        if (!root.equals(that.root)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hash.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + root.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("labels", labels)
                .add("hash", hash)
                .add("root", root)
                .toString();
    }
}
