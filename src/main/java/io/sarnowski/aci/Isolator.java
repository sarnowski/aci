package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class Isolator {
    @JsonProperty("name")
    private String name;

    @JsonProperty("val")
    private String val;

    private Isolator() {
    }

    public Isolator(final String name, final String val) {
        checkArgument(!isNullOrEmpty(name), "isolator name required");
        checkArgument(!isNullOrEmpty(val), "isolator val required");

        this.name = name;
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public String getVal() {
        return val;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Isolator isolator = (Isolator) o;

        if (!name.equals(isolator.name)) return false;

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
                .add("val", val)
                .toString();
    }

    public static final class Name {
        public static final String CPU_SHARES = "cpu/shares/";

        public static final String MEMORY_LIMIT = "memory/limit";

        public static final String BLOCKIO_READ_BANDWITH = "blockIO/readBandwidth";
        public static final String BLOCKIO_WRITE_BANDWITH = "blockIO/writeBandwidth";

        public static final String NETWORKIO_READ_BANDWITH = "networkIO/readBandwidth";
        public static final String NETWORKIO_WRITE_BANDWITH = "networkIO/writeBandwidth";

        public static final String PRIVATE_NETWORK = "privateNetwork";

        public static final String CAPABILITIES_BOUNDING_SET = "capabilities/boundingSet";
    }
}
