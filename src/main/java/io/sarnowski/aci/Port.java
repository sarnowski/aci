package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class Port {
    @JsonProperty("name")
    private String name;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("port")
    private int port;

    @JsonProperty("socketActivated")
    private boolean socketActivated;

    private Port() {
    }

    public Port(final String name, final String protocol, final int port) {
        checkArgument(!isNullOrEmpty(name), "port name required");
        checkArgument(!isNullOrEmpty(protocol), "port protocol required");
        checkArgument(port > 0 && port <= 65335, "port range invalid");

        this.name = name;
        this.protocol = protocol;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getPort() {
        return port;
    }

    public boolean isSocketActivated() {
        return socketActivated;
    }

    public void setSocketActivated(final boolean socketActivated) {
        this.socketActivated = socketActivated;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Port port = (Port) o;

        if (!name.equals(port.name)) return false;

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
                .add("protocol", protocol)
                .add("port", port)
                .add("socketActivated", socketActivated)
                .toString();
    }
}
