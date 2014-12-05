package io.sarnowski.aci.builder;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public enum ACICompression {
    NONE {
        @Override
        public OutputStream wrapStream(final OutputStream outputStream) {
            return outputStream;
        }
    },
    GZIP {
        @Override
        public OutputStream wrapStream(final OutputStream outputStream) throws IOException {
            return new GZIPOutputStream(outputStream);
        }
    },
    BZIP2 {
        @Override
        public OutputStream wrapStream(final OutputStream outputStream) throws IOException {
            return new BZip2CompressorOutputStream(outputStream);
        }
    },
    XZ {
        @Override
        public OutputStream wrapStream(final OutputStream outputStream) throws IOException {
            return new XZCompressorOutputStream(outputStream);
        }
    };

    public OutputStream wrapStream(final OutputStream outputStream) throws IOException {
        throw new UnsupportedOperationException();
    }
}
