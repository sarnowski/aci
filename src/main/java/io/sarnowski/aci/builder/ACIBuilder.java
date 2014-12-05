package io.sarnowski.aci.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import io.sarnowski.aci.AppManifest;
import io.sarnowski.aci.FilesetManifest;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class ACIBuilder {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final String name;
    private final String version;

    private AppManifest appManifest;
    private FilesetManifest filesetManifest;

    private List<Content> contents = new ArrayList<>();

    private ACICompression compression = ACICompression.GZIP;

    private boolean buffering = true;

    private ACIBuilder(final String name, final String version) {
        checkArgument(!isNullOrEmpty(name), "image name required");
        checkArgument(!isNullOrEmpty(version), "image version required");

        this.name = name;
        this.version = version;
    }

    public static ACIBuilder newImage(final String name, final String version) {
        return new ACIBuilder(name, version);
    }

    public ACIBuilder withAppManifest(final AppManifest appManifest) {
        checkNotNull(appManifest, "app manifest required");
        this.appManifest = appManifest;
        return this;
    }

    public ACIBuilder withFilesetManifest(final FilesetManifest filesetManifest) {
        checkNotNull(filesetManifest, "fileset manifest required");
        this.filesetManifest = filesetManifest;
        return this;
    }

    public ACIBuilder addContent(final Content content) {
        this.contents.add(content);
        return this;
    }

    public ACIBuilder addStream(final TarArchiveEntry destination, final InputStream source) {
        this.contents.add(new StreamingContent(destination, source));
        return this;
    }

    public ACIBuilder addDirectory(final TarArchiveEntry destination) {
        this.contents.add(new DirectoryContent(destination));
        return this;
    }

    public ACIBuilder withCompression(final ACICompression compression) {
        this.compression = compression;
        return this;
    }

    public ACIBuilder withoutBuffering() {
        this.buffering = false;
        return this;
    }

    public String writeTo(final OutputStream outputStream) throws IOException {
        final OutputStream bufferedOutputStream;
        if (buffering) {
            bufferedOutputStream = new BufferedOutputStream(outputStream);
        } else {
            bufferedOutputStream = outputStream;
        }

        final OutputStream compressedOutputStream = compression.wrapStream(bufferedOutputStream);

        // TODO add encryption capabilities (before or after hashing?)

        final HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha256(),
                compressedOutputStream);

        final TarArchiveOutputStream imageOutputStream = new TarArchiveOutputStream(hashingOutputStream);

        if (appManifest != null) {
            final TarArchiveEntry appManifestEntry = new TarArchiveEntry("/app");

            imageOutputStream.putArchiveEntry(appManifestEntry);
            JSON_MAPPER.writeValue(imageOutputStream, appManifest);
            imageOutputStream.closeArchiveEntry();
        }

        if (filesetManifest != null) {
            final TarArchiveEntry appManifestEntry = new TarArchiveEntry("/fileset");

            imageOutputStream.putArchiveEntry(appManifestEntry);
            JSON_MAPPER.writeValue(imageOutputStream, filesetManifest);
            imageOutputStream.closeArchiveEntry();
        }

        for (final Content content : contents) {
            content.addToImage(imageOutputStream);
        }

        imageOutputStream.flush();

        return hashingOutputStream.hash().toString();
    }

    public void writeTo(final Path directory) {
        checkArgument(Files.isDirectory(directory), "given path is not a directory");

        // TODO writeTo file in directory - filename derived from builder
        // TODO write result of writeTo (hash) also to file

        throw new UnsupportedOperationException();
    }
}
