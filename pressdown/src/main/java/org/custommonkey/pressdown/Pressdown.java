package org.custommonkey.pressdown;

import static java.lang.System.err;
import static org.custommonkey.pressdown.ReadableFile.readableFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;

import org.xml.sax.InputSource;

public class Pressdown {

    public static void main(final String[] args) throws Exception {

        try {

            if (args.length == 0) {
                new Pressdown().transform(readableFile("README.md"));
            } else {

                final Pressdown app = new Pressdown(readableFile(args[0]));

                for (int i = 1; i < args.length; i++) {
                    app.transform(readableFile(args[i]));
                }
            }

        } catch(FileNotFoundException e) {
            System.err.println("Could not find file " + e.getMessage());
        }

    }

    private final Transformer transformer;

    public Pressdown() throws TransformerConfigurationException, IOException {
        this(internalXSL());
    }

    public Pressdown(final ReadableFile xslFile) throws TransformerConfigurationException,
            IOException {
        this(xslFile.asStreamSource());
    }

    public Pressdown(final StreamSource xsl) throws TransformerConfigurationException,
            IOException {
        transformer = new TransformerFactoryImpl().newTransformer(xsl);
        transformer.setParameter("css", read(resourceAsURL("style.css")));
    }

    private String read(final URL url) throws IOException {
        final URLConnection conn = url.openConnection();
        return new String(readChars(
                new InputStreamReader(conn.getInputStream()),
                conn.getContentLength()));
    }

    private static StreamSource internalXSL() throws IOException {
        return new StreamSource(resourceAsURL("pressdown.xsl").openStream());
    }

    private static URL resourceAsURL(final String name) {
        final URL url = Pressdown.class.getClassLoader().getResource(name);
        err.println(url);
        return url;
    }

    public void transform(final ReadableFile inputFile) throws TransformerException {
        final StreamResult outputFile = outputFile(inputFile);
        err.printf("%s -> %s%n", inputFile, outputFile.getSystemId());
        transformer.transform(markdownSource(inputFile), outputFile);
    }

    private static StreamResult outputFile(final ReadableFile inputFile) {
        return new StreamResult(inputFile.getName() + ".html");
    }

    private static SAXSource markdownSource(final ReadableFile inputFile) {
        return new SAXSource(new SAXEventVisitor(), inputFile.asInputSource());
    }

    static char[] readChars(final InputSource in) throws IOException {
        final File file = new File(in.getSystemId());
        return readChars(new FileReader(file), (int) file.length());
    }

    private static char[] readChars(final Reader in, final int size)
            throws IOException {
        final char[] chars = new char[size];
        in.read(chars);
        in.close();

        return chars;
    };

}
