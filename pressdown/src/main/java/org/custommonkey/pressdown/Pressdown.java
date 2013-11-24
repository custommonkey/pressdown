package org.custommonkey.pressdown;

import static java.lang.System.err;
import static org.custommonkey.pressdown.InputSourceUtils.read;

import java.io.*;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;

public class Pressdown {

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

    private static StreamSource internalXSL() throws IOException {
        return new StreamSource(resourceAsURL("pressdown.xsl").openStream());
    }

    private static URL resourceAsURL(final String name) {
        final URL url = Pressdown.class.getClassLoader().getResource(name);
        err.println(url);
        return url;
    }

    public void markdownToHTML(final ReadableFile inputFile) throws TransformerException {
        final StreamResult outputFile = outputFile(inputFile);
        err.printf("%s -> %s%n", inputFile, outputFile.getSystemId());
        transformer.transform(markdownSource(inputFile), outputFile);
    }

    private StreamResult outputFile(final ReadableFile inputFile) {
        return new StreamResult(inputFile.getName() + ".html");
    }

    private SAXSource markdownSource(final ReadableFile inputFile) {
        return new SAXSource(new SAXEventVisitor(), inputFile.asInputSource());
    }


}
