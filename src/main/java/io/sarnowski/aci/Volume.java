package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

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
        checkArgument(!isNullOrEmpty(kind), "volume kind required");

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
        return MoreObjects.toStringHelper(this)
                .add("kind", kind)
                .add("source", source)
                .add("readOnly", readOnly)
                .add("fulfills", fulfills)
                .toString();
    }
}
