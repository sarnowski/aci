package io.sarnowski.aci.writer;

import io.sarnowski.aci.AppContainerImage;
import io.sarnowski.aci.AppManifest;
import io.sarnowski.aci.Isolator;
import io.sarnowski.aci.reader.ACIReader;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class ACIWriterTest {

    @Test
    public void helloWorld() throws IOException {
        final AppManifest appManifest = new AppManifest("helloworld", "1.0.0", AppManifest.OS.LINUX, AppManifest.Arch.AMD64);
        appManifest.getIsolators().add(new Isolator(Isolator.Name.MEMORY_LIMIT, "1g"));

        final ByteArrayOutputStream imageOut = new ByteArrayOutputStream();

        final String hash = ACIWriter.newImage("helloworld", "1.0.0")
                .withAppManifest(appManifest)
                .addText(new TarArchiveEntry("test.txt"), "Hello World!")
                .writeTo(imageOut);

        final ByteArrayInputStream imageIn = new ByteArrayInputStream(imageOut.toByteArray());

        final AppContainerImage image = ACIReader.newImage("helloworld", "1.0.0", imageIn, hash)
                .read();


    }
}
