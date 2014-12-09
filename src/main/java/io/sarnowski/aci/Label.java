package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class Label {
    public static String NAME_VERSION = "version";
    public static String NAME_OS = "os";
    public static String NAME_ARCH = "arch";

    @JsonProperty("name")
    private final String name;

    @JsonProperty("val")
    private final String val;

    public Label(final String name, final String val) {
        checkArgument(!isNullOrEmpty(name), "label name required");
        checkArgument(!isNullOrEmpty(val), "label val required");

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

        final Label label = (Label) o;

        if (!name.equals(label.name)) return false;
        if (!val.equals(label.val)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + val.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("val", val)
                .toString();
    }
}
