package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        if (hash == null) throw new IllegalArgumentException("hash not given");
        if (name == null) throw new IllegalArgumentException("name not given");
        if (root == null) throw new IllegalArgumentException("root not given");

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
        return "Dependency{" +
                "hash='" + hash + '\'' +
                ", name='" + name + '\'' +
                ", root='" + root + '\'' +
                '}';
    }
}
