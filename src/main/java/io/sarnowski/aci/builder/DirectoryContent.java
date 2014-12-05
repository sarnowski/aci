package io.sarnowski.aci.builder;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.IOException;
import java.util.Optional;

final class DirectoryContent extends Content {
    private final TarArchiveEntry destination;

    public DirectoryContent(final TarArchiveEntry destination) {
        this.destination = destination;
    }

    @Override
    protected void addToImage(final TarArchiveOutputStream image) throws IOException {
        addToImage(image, destination, Optional.empty());
    }
}
