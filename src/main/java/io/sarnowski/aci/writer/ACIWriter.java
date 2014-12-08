package io.sarnowski.aci.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class ACIWriter {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final Charset MANIFEST_CHARSET = Charsets.UTF_8;

    private final String name;
    private final String version;

    private AppManifest appManifest;
    private FilesetManifest filesetManifest;

    private List<Content> contents = new ArrayList<>();

    private ACICompression compression = ACICompression.GZIP;

    private boolean buffering = true;

    private ACIWriter(final String name, final String version) {
        checkArgument(!isNullOrEmpty(name), "image name required");
        checkArgument(!isNullOrEmpty(version), "image version required");

        this.name = name;
        this.version = version;
    }

    public static ACIWriter newImage(final String name, final String version) {
        return new ACIWriter(name, version);
    }

    public ACIWriter withAppManifest(final AppManifest appManifest) {
        checkNotNull(appManifest, "app manifest required");
        this.appManifest = appManifest;
        return this;
    }

    public ACIWriter withFilesetManifest(final FilesetManifest filesetManifest) {
        checkNotNull(filesetManifest, "fileset manifest required");
        this.filesetManifest = filesetManifest;
        return this;
    }

    public ACIWriter addContent(final Content content) {
        this.contents.add(content);
        return this;
    }

    public ACIWriter addStream(final TarArchiveEntry destination, final InputStream source) {
        this.contents.add(new StreamingContent(destination, source));
        return this;
    }

    public ACIWriter addBytes(final TarArchiveEntry destination, final byte[] source) {
        destination.setSize(source.length);
        this.contents.add(new ByteContent(destination, source));
        return this;
    }

    public ACIWriter addText(final TarArchiveEntry destination, final String source) {
        destination.setSize(source.length());
        this.contents.add(new TextContent(destination, source));
        return this;
    }

    public ACIWriter addText(final TarArchiveEntry destination, final String source, final Charset charset) {
        destination.setSize(source.length());
        this.contents.add(new TextContent(destination, source, charset));
        return this;
    }

    public ACIWriter addDirectory(final TarArchiveEntry destination) {
        this.contents.add(new DirectoryContent(destination));
        return this;
    }

    public ACIWriter withCompression(final ACICompression compression) {
        this.compression = compression;
        return this;
    }

    public ACIWriter withoutBuffering() {
        this.buffering = false;
        return this;
    }

    public String writeTo(final OutputStream outputStream) throws IOException {
        checkState(appManifest != null || filesetManifest != null,
                "neither AppManifest nor a FilesetManifest is given");

        final OutputStream bufferedOutputStream;
        if (buffering) {
            bufferedOutputStream = new BufferedOutputStream(outputStream);
        } else {
            bufferedOutputStream = outputStream;
        }

        // TODO add encryption capabilities (before or after hashing/compression?) (https://github.com/coreos/rocket/issues/233)

        final OutputStream compressedOutputStream = compression.wrapStream(bufferedOutputStream);

        final HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha256(),
                compressedOutputStream);

        final TarArchiveOutputStream imageOutputStream = new TarArchiveOutputStream(hashingOutputStream);

        if (appManifest != null) {
            final String appManifestJson = JSON_MAPPER.writeValueAsString(appManifest);

            final TarArchiveEntry appManifestEntry = new TarArchiveEntry("/app");
            appManifestEntry.setSize(appManifestJson.length());

            imageOutputStream.putArchiveEntry(appManifestEntry);
            imageOutputStream.write(appManifestJson.getBytes(MANIFEST_CHARSET));
            imageOutputStream.closeArchiveEntry();
        }

        if (filesetManifest != null) {
            final String filesetManifestJson = JSON_MAPPER.writeValueAsString(filesetManifest);

            final TarArchiveEntry filesetManifestEntry = new TarArchiveEntry("/fileset");
            filesetManifestEntry.setSize(filesetManifestJson.length());

            imageOutputStream.putArchiveEntry(filesetManifestEntry);
            imageOutputStream.write(filesetManifestJson.getBytes(MANIFEST_CHARSET));
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

        // TODO writeTo file in directory - filename derived from writer
        // TODO write result of writeTo (hash) also to file

        throw new UnsupportedOperationException();
    }
}
