package io.sarnowski.aci.fetcher;

import io.sarnowski.aci.AppContainerImage;

public interface ACIFetcher {
    AppContainerImage fetchImage(String fqn);
}
