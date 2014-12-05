package io.sarnowski.aci.builder;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

final class StreamingContent extends Content {
    private final TarArchiveEntry destination;
    private final InputStream source;

    public StreamingContent(final TarArchiveEntry destination, final InputStream source) {
        this.destination = destination;
        this.source = source;
    }

    @Override
    protected void addToImage(final TarArchiveOutputStream image) throws IOException {
        addToImage(image, destination, Optional.of(source));
    }
}
