package io.sarnowski.aci.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import io.sarnowski.aci.AppImageManifest;
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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class ACIWriter {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final Charset MANIFEST_CHARSET = Charsets.UTF_8;

    private final AppImageManifest appImageManifest;

    private List<Content> contents = new ArrayList<>();
    private ACICompression compression = ACICompression.GZIP;
    private boolean buffering = true;

    private ACIWriter(final AppImageManifest appImageManifest) {
        this.appImageManifest = appImageManifest;
    }

    public static ACIWriter newImage(final AppImageManifest appImageManifest) {
        checkNotNull(appImageManifest, "app image manifest required");
        return new ACIWriter(appImageManifest);
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

        // add manifest
        final String appManifestJson = JSON_MAPPER.writeValueAsString(appImageManifest);

        final TarArchiveEntry appManifestEntry = new TarArchiveEntry("/app");
        appManifestEntry.setSize(appManifestJson.length());

        imageOutputStream.putArchiveEntry(appManifestEntry);
        imageOutputStream.write(appManifestJson.getBytes(MANIFEST_CHARSET));
        imageOutputStream.closeArchiveEntry();

        // add content
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
