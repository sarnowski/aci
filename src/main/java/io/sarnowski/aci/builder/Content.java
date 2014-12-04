package io.sarnowski.aci.builder;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public abstract class Content {
    private static final String ROOTFS_PREFIX = "/rootfs";

    private final String target;

    protected Content(final String target) {
        if (target == null) throw new IllegalArgumentException("target not given");

        if (target.startsWith("/")) {
            this.target = ROOTFS_PREFIX + target;
        } else {
            this.target = ROOTFS_PREFIX + "/" + target;
        }
    }

    protected String getTarget() {
        return target;
    }

    protected void addToImage(final TarArchiveOutputStream image, final TarArchiveEntry entry,
                              Optional<InputStream> inputStream) throws IOException {

        image.putArchiveEntry(entry);
        if (inputStream.isPresent()) {
            IOUtils.copy(inputStream.get(), image);
        }
        image.closeArchiveEntry();
    }

    protected abstract void addToImage(final TarArchiveOutputStream image) throws IOException;
}
