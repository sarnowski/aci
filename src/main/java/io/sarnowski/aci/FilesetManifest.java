package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FilesetManifest {
    private static final String MANIFEST_VERSION = "0.1.0";
    private static final String MANIFEST_KIND = "FilesetManifest";

    @JsonProperty("acVersion")
    private String acVersion = MANIFEST_VERSION;

    @JsonProperty("acKind")
    private String acKind = MANIFEST_KIND;

    @JsonProperty("name")
    private String name;

    @JsonProperty("dependencies")
    private List<Dependency> dependencies;

    @JsonProperty("files")
    private List<String> files;

    private FilesetManifest() {
    }

    public FilesetManifest(final String name) {
        if (name == null) throw new IllegalArgumentException("name not given");

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

    public List<Dependency> getDependencies() {
        if (dependencies == null) {
            dependencies = new ArrayList<>();
        }
        return dependencies;
    }

    public List<String> getFiles() {
        if (files == null) {
            files = new ArrayList<>();
        }
        return files;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final FilesetManifest that = (FilesetManifest) o;

        if (!acKind.equals(that.acKind)) return false;
        if (!acVersion.equals(that.acVersion)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = acVersion.hashCode();
        result = 31 * result + acKind.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FilesetManifest{" +
                "acVersion='" + acVersion + '\'' +
                ", acKind='" + acKind + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
