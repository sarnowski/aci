package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class AppManifest {
    private static final String MANIFEST_VERSION = "0.1.0";
    private static final String MANIFEST_KIND = "AppManifest";

    @JsonProperty("acVersion")
    private String acVersion = MANIFEST_VERSION;

    @JsonProperty("acKind")
    private String acKind = MANIFEST_KIND;

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private String version;

    @JsonProperty("os")
    private String os;

    @JsonProperty("arch")
    private String arch;

    @JsonProperty("exec")
    private List<String> exec;

    @JsonProperty("user")
    private String user;

    @JsonProperty("group")
    private String group;

    @JsonProperty("eventHandlers")
    private List<EventHandler> eventHandlers;

    @JsonProperty("environment")
    private Map<String,String> environment;

    @JsonProperty("mountPoints")
    private List<MountPoint> mountPoints;

    @JsonProperty("ports")
    private List<Port> ports;

    @JsonProperty("isolators")
    private List<Isolator> isolators;

    @JsonProperty("annotations")
    private Map<String,String> annotations;

    private AppManifest() {
    }

    public AppManifest(final String name, final String version, final String os, final String arch) {
        checkArgument(!isNullOrEmpty(name), "app manifest name required");
        checkArgument(!isNullOrEmpty(version), "app manifest version required");
        checkArgument(!isNullOrEmpty(os), "app manifest os required");
        checkArgument(!isNullOrEmpty(arch), "app manifest arch required");

        this.name = name;
        this.version = version;
        this.os = os;
        this.arch = arch;
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

    public String getVersion() {
        return version;
    }

    public String getOs() {
        return os;
    }

    public String getArch() {
        return arch;
    }

    public List<String> getExec() {
        if (exec == null) {
            exec = new ArrayList<>();
        }
        return exec;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public List<EventHandler> getEventHandlers() {
        if (eventHandlers == null) {
            eventHandlers = new ArrayList<>();
        }
        return eventHandlers;
    }

    public Map<String, String> getEnvironment() {
        if (environment == null) {
            environment = Maps.newHashMap();
        }
        return environment;
    }

    public List<MountPoint> getMountPoints() {
        if (mountPoints == null) {
            mountPoints = new ArrayList<>();
        }
        return mountPoints;
    }

    public List<Port> getPorts() {
        if (ports == null) {
            ports = new ArrayList<>();
        }
        return ports;
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

        AppManifest that = (AppManifest) o;

        if (!acKind.equals(that.acKind)) return false;
        if (!acVersion.equals(that.acVersion)) return false;
        if (!arch.equals(that.arch)) return false;
        if (!name.equals(that.name)) return false;
        if (!os.equals(that.os)) return false;
        if (!version.equals(that.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = acVersion.hashCode();
        result = 31 * result + acKind.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + os.hashCode();
        result = 31 * result + arch.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("acVersion", acVersion)
                .add("acKind", acKind)
                .add("name", name)
                .add("version", version)
                .add("os", os)
                .add("arch", arch)
                .toString();
    }

    public static final class OS {
        public static final String LINUX = "linux";
        // TODO more standards? (https://github.com/coreos/rocket/issues/234)
    }

    public static final class Arch {
        public static final String AMD64 = "amd64";
        public static final String I386 = "i386";
        // TODO more standards? (https://github.com/coreos/rocket/issues/234)
    }
}
