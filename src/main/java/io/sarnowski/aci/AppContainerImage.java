package io.sarnowski.aci;

import com.google.common.base.MoreObjects;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.net.URI;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public class AppContainerImage {
    private final String name;
    private final String version;

    private final TarArchiveInputStream files;
    private final String hash;

    private Optional<String> fqn = Optional.empty();
    private Optional<URI> sourceImageFile = Optional.empty();

    private Optional<AppManifest> appManifest = Optional.empty();
    private Optional<FilesetManifest> filesetManifest = Optional.empty();

    public AppContainerImage(final String name, final String version, final TarArchiveInputStream files,
                             final String hash) {

        checkArgument(!isNullOrEmpty(name), "app container image name");
        checkArgument(!isNullOrEmpty(version), "app container image version");
        checkNotNull(files, "app container image files");
        checkArgument(!isNullOrEmpty(hash), "app container image hash");

        this.name = name;
        this.version = version;
        this.files = files;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public TarArchiveInputStream getFiles() {
        return files;
    }

    public String getHash() {
        return hash;
    }

    public Optional<String> getFqn() {
        return fqn;
    }

    public void setFqn(final Optional<String> fqn) {
        checkNotNull(fqn, "fqn required");
        this.fqn = fqn;
    }

    public Optional<URI> getSourceImageFile() {
        return sourceImageFile;
    }

    public void setSourceImageFile(final Optional<URI> sourceImageFile) {
        checkNotNull(sourceImageFile, "sourceImageFile required");
        this.sourceImageFile = sourceImageFile;
    }

    public Optional<AppManifest> getAppManifest() {
        return appManifest;
    }

    public void setAppManifest(final Optional<AppManifest> appManifest) {
        checkNotNull(appManifest, "appManifest required");
        this.appManifest = appManifest;
    }

    public Optional<FilesetManifest> getFilesetManifest() {
        return filesetManifest;
    }

    public void setFilesetManifest(final Optional<FilesetManifest> filesetManifest) {
        checkNotNull(filesetManifest, "filesetManifest required");
        this.filesetManifest = filesetManifest;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AppContainerImage that = (AppContainerImage) o;

        if (!hash.equals(that.hash)) return false;
        if (!name.equals(that.name)) return false;
        if (!version.equals(that.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + hash.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("version", version)
                .add("hash", hash)
                .add("fqn", fqn)
                .add("sourceImageFile", sourceImageFile)
                .add("appManifest", appManifest)
                .add("filesetManifest", filesetManifest)
                .toString();
    }
}
