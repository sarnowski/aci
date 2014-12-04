package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        if (name == null) throw new IllegalArgumentException("name not given");
        if (protocol == null) throw new IllegalArgumentException("protocol not given");
        if (port < 0 || port > 65335) throw new IllegalArgumentException("invalid port");

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
        return "Port{" +
                "name='" + name + '\'' +
                ", protocol='" + protocol + '\'' +
                ", port=" + port +
                ", socketActivated=" + socketActivated +
                '}';
    }
}
