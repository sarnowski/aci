package io.sarnowski.aci.builder;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * TODO implement PathContent for adding single files or whole directories recursivly and also read the unix file permissions
 */
public abstract class Content {
    private static final String ROOTFS_PREFIX = "rootfs";

    protected void addToImage(final TarArchiveOutputStream imageOutputStream, final TarArchiveEntry entry,
                              Optional<InputStream> inputStream) throws IOException {

        if (entry.getName().startsWith("/")) {
            entry.setName(ROOTFS_PREFIX + entry.getName());
        } else {
            entry.setName(ROOTFS_PREFIX + "/" + entry.getName());
        }

        imageOutputStream.putArchiveEntry(entry);
        if (inputStream.isPresent()) {
            IOUtils.copy(inputStream.get(), imageOutputStream);
        }
        imageOutputStream.closeArchiveEntry();
    }

    protected abstract void addToImage(final TarArchiveOutputStream image) throws IOException;
}
