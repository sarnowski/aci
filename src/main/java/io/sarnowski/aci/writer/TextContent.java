package io.sarnowski.aci.writer;

import com.google.common.base.Charsets;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import java.nio.charset.Charset;

public class TextContent extends ByteContent {
    public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

    public TextContent(final TarArchiveEntry destination, final String text, final Charset charset) {
        super(destination, text.getBytes(charset));
    }

    public TextContent(final TarArchiveEntry destination, final String text) {
        this(destination, text, DEFAULT_CHARSET);
    }
}
