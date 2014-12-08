package io.sarnowski.aci.reader;

import io.sarnowski.aci.AppContainerImage;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class ACIReader {
    private final String name;
    private final String version;
    private final InputStream inputStream;
    private final String hash;

    private String fqn;
    private URI sourceImageFile;

    private ACIReader(final String name, final String version, final InputStream inputStream,
                     final String hash) {
        this.name = name;
        this.version = version;
        this.inputStream = inputStream;
        this.hash = hash;
    }

    public static ACIReader newImage(final String name, final String version, final InputStream inputStream,
                                     final String hash) {
        return new ACIReader(name, version, inputStream, hash);
    }

    public ACIReader withFqn(final String fqn) {
        checkArgument(!isNullOrEmpty(fqn), "fqn required");
        this.fqn = fqn;
        return this;
    }

    public ACIReader withSourceImageFile(final URI sourceImageFile) {
        checkNotNull(sourceImageFile, "sourceImageFile required");
        this.sourceImageFile = sourceImageFile;
        return this;
    }

    // TODO make decryption key provider configurable

    public AppContainerImage read() {
        // TODO do all parsing magic of stream to tarball
        final TarArchiveInputStream imageStream = new TarArchiveInputStream(inputStream);

        final AppContainerImage image = new AppContainerImage(name, version, imageStream, hash);

        image.setFqn(Optional.ofNullable(fqn));
        image.setSourceImageFile(Optional.ofNullable(sourceImageFile));

        return image;
    }
}
