package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class ContainerRuntimeManifest {
    private static final String MANIFEST_VERSION = "0.1.0";
    private static final String MANIFEST_KIND = "ContainerRuntimeManifest";

    @JsonProperty("acVersion")
    private String acVersion = MANIFEST_VERSION;

    @JsonProperty("acKind")
    private String acKind = MANIFEST_KIND;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("apps")
    private List<App> apps;

    @JsonProperty("volumes")
    private List<Volume> volumes;

    @JsonProperty("isolators")
    private List<Isolator> isolators;

    @JsonProperty("annotations")
    private Map<String,String> annotations;

    private ContainerRuntimeManifest() {
    }

    public ContainerRuntimeManifest(final String uuid) {
        checkArgument(!isNullOrEmpty(uuid), "container runtime manifest uuid required");

        this.uuid = uuid;
    }

    public String getAcVersion() {
        return acVersion;
    }

    public String getAcKind() {
        return acKind;
    }

    public String getUuid() {
        return uuid;
    }

    public List<App> getApps() {
        if (apps == null) {
            apps = new ArrayList<>();
        }
        return apps;
    }

    public List<Volume> getVolumes() {
        if (volumes == null) {
            volumes = new ArrayList<>();
        }
        return volumes;
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

        final ContainerRuntimeManifest that = (ContainerRuntimeManifest) o;

        if (!uuid.equals(that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("acVersion", acVersion)
                .add("acKind", acKind)
                .add("uuid", uuid)
                .toString();
    }
}
