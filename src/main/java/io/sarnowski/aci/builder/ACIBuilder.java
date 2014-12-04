package io.sarnowski.aci.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sarnowski.aci.AppManifest;
import io.sarnowski.aci.FilesetManifest;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ACIBuilder {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final String name;
    private final String version;

    private AppManifest appManifest;
    private FilesetManifest filesetManifest;

    private List<Content> contents = new ArrayList<>();

    private ACIBuilder(final String name, final String version) {
        if (name == null) throw new IllegalArgumentException("name not given");
        if (version == null) throw new IllegalArgumentException("version not given");

        this.name = name;
        this.version = version;
    }

    public static ACIBuilder newImage(final String name, final String version) {
        return new ACIBuilder(name, version);
    }

    public ACIBuilder withAppManifest(final AppManifest appManifest) {
        if (appManifest == null) throw new IllegalArgumentException("appManifest not given");
        this.appManifest = appManifest;
        return this;
    }

    public ACIBuilder withFilesetManifest(final FilesetManifest filesetManifest) {
        if (filesetManifest == null) throw new IllegalArgumentException("filesetManifest not given");
        this.filesetManifest = filesetManifest;
        return this;
    }

    public ACIBuilder add(final Content content) {
        this.contents.add(content);
        return this;
    }

    public ACIBuilder add(final String destination, final File source) {
        this.contents.add(new FileContent(destination, source));
        return this;
    }

    public String writeTo(final OutputStream outputStream) {
        final TarArchiveOutputStream image = new TarArchiveOutputStream(outputStream);

        if (appManifest != null) {
            try {
                final TarArchiveEntry appManifestEntry = new TarArchiveEntry("/app");

                image.putArchiveEntry(appManifestEntry);
                JSON_MAPPER.writeValue(image, appManifest);
                image.closeArchiveEntry();

            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        if (filesetManifest != null) {
            try {
                final TarArchiveEntry appManifestEntry = new TarArchiveEntry("/fileset");

                image.putArchiveEntry(appManifestEntry);
                JSON_MAPPER.writeValue(image, filesetManifest);
                image.closeArchiveEntry();

            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        for (final Content content: contents) {
            try {
                content.addToImage(image);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        return "sha256 hash not yet implemented";
    }
}
