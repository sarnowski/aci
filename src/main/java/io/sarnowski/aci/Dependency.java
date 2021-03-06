package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class Dependency {
    @JsonProperty("hash")
    private String hash;

    @JsonProperty("name")
    private String name;

    @JsonProperty("root")
    private String root;

    private Dependency() {
    }

    public Dependency(final String hash, final String name, final String root) {
        checkArgument(!isNullOrEmpty(hash), "dependency hash required");
        checkArgument(!isNullOrEmpty(name), "dependency name required");
        checkArgument(!isNullOrEmpty(root), "dependency root required");

        this.hash = hash;
        this.name = name;
        this.root = root;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getRoot() {
        return root;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Dependency that = (Dependency) o;

        if (!hash.equals(that.hash)) return false;
        if (!name.equals(that.name)) return false;
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
                .add("hash", hash)
                .add("name", name)
                .add("root", root)
                .toString();
    }
}
