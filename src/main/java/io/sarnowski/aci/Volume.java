package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Volume {
    @JsonProperty("kind")
    private String kind;

    @JsonProperty("source")
    private String source;

    @JsonProperty("readOnly")
    private boolean readOnly;

    @JsonProperty("fulfills")
    private List<String> fulfills;

    private Volume() {
    }

    public Volume(final String kind) {
        if (kind == null) throw new IllegalArgumentException("kind not given");

        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
    }

    public List<String> getFulfills() {
        if (fulfills == null) {
            fulfills = new ArrayList<>();
        }
        return fulfills;
    }

    @Override
    public String toString() {
        return "Volume{" +
                "kind='" + kind + '\'' +
                ", source='" + source + '\'' +
                ", readOnly=" + readOnly +
                ", fulfills=" + fulfills +
                '}';
    }
}
