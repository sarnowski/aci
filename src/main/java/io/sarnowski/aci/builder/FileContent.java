package io.sarnowski.aci.builder;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

final class FileContent extends Content {
    private final File source;

    public FileContent(final String target, final File source) {
        super(target);
        this.source = source;
    }

    @Override
    protected void addToImage(final TarArchiveOutputStream image) throws IOException {
        // TODO check for file or directory and travers recusrivly
        final TarArchiveEntry entry = new TarArchiveEntry(source, getTarget());

        image.putArchiveEntry(entry);
        // TODO copy file content to stream
        image.closeArchiveEntry();
    }
}
