package io.sarnowski.aci;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    @JsonProperty("name")
    private String name;

    @JsonProperty("exec")
    private List<String> exec;

    private EventHandler() {
    }

    public EventHandler(final String name, final List<String> exec) {
        if (name == null) throw new IllegalArgumentException("name not given");
        if (exec == null) throw new IllegalArgumentException("exec not given");

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
        return "EventHandler{" +
                "name='" + name + '\'' +
                ", exec=" + exec +
                '}';
    }
}
