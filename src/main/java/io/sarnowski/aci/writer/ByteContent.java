package io.sarnowski.aci.writer;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import java.io.ByteArrayInputStream;

public class ByteContent extends StreamingContent {
    public ByteContent(final TarArchiveEntry destination, final byte[] bytes) {
        super(destination, new ByteArrayInputStream(bytes));
    }
}
