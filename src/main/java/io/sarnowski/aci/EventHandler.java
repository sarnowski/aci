package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public class EventHandler {
    @JsonProperty("name")
    private String name;

    @JsonProperty("exec")
    private List<String> exec;

    private EventHandler() {
    }

    public EventHandler(final String name, final List<String> exec) {
        checkArgument(!isNullOrEmpty(name), "event handler name required");
        checkNotNull(exec, "event handler exec required");

        this.name = name;
        this.exec = exec;
    }

    public String getName() {
        return name;
    }

    public List<String> getExec() {
        if (exec == null) {
            exec = new ArrayList<>();
        }
        return exec;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EventHandler that = (EventHandler) o;

        if (!name.equals(that.name)) return false;

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
                .add("exec", exec)
                .toString();
    }
}
