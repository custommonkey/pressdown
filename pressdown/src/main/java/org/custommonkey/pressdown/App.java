package org.custommonkey.pressdown;

import static java.lang.System.err;

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

public class App {

	public static void main(final String[] args) throws Exception {


        if(args.length==0) {
            new App().transform("README.md");
        } else {

            final App app = new App(args[0]);

            for (int i = 1; i < args.length; i++) {
                app.transform(args[i]);
            }
        }

	}

	private final Transformer transformer;

	App() throws TransformerConfigurationException, IOException {
		this(internalXSL());
	}

	App(final String xsl) throws TransformerConfigurationException, IOException {
		this(new StreamSource(xsl));
	}

	App(final StreamSource xsl) throws TransformerConfigurationException, IOException {
		transformer = new TransformerFactoryImpl().newTransformer(xsl);
		transformer.setParameter("css", read(resource("style.css")));
	}

	private String read(final URL url) throws IOException {
	    final URLConnection conn = url.openConnection();
	    return new String(readChars(new InputStreamReader(conn.getInputStream()), conn.getContentLength()));
    }

    private static StreamSource internalXSL() throws IOException {
		return new StreamSource(resource("pressdown.xsl").openStream());
	}

    private static URL resource(final String name) {
        final URL url = App.class.getClassLoader().getResource(name);
		err.println(url);
        return url;
    }

	public void transform(final String inputFile) throws TransformerException {
        final StreamResult outputFile = outputFile(inputFile);
        err.printf("%s -> %s%n", inputFile, outputFile.getSystemId());
        transformer.transform(markdownSource(inputFile), outputFile);
	}

    private static StreamResult outputFile(final String args) {
        return new StreamResult(args + ".html");
    }

    private static SAXSource markdownSource(final String args) {
        return new SAXSource(new Ser(), new InputSource(args));
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
