package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Isolator {
    @JsonProperty("name")
    private String name;

    @JsonProperty("val")
    private String val;

    private Isolator() {
    }

    public Isolator(final String name, final String val) {
        if (name == null) throw new IllegalArgumentException("name not given");
        if (val == null) throw new IllegalArgumentException("val not given");

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
        return "Isolator{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
