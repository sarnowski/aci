package io.sarnowski.aci.fetcher;

public final class ACIFetcherConfigurer {
    private ACIFetcherConfigurer() {
    }

    public static ACIFetcherConfigurer newConfiguration() {
        return new ACIFetcherConfigurer();
    }

    // TODO configure storage system (disk storage, db storage, ...)
    // TODO configure protocols (http, bittorrent, ...)
    // TODO configure local image cache (/var/aci, ~/.aci)

    public ACIFetcher createFetcher() {
        throw new UnsupportedOperationException();
    }
}
