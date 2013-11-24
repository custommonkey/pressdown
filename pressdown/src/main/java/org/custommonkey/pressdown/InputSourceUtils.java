package org.custommonkey.pressdown;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import org.xml.sax.InputSource;

class InputSourceUtils {

	static char[] readChars(final InputSource in) throws IOException {
		final File file = new File(in.getSystemId());
		return readChars(in.getCharacterStream(), (int) file.length());
	}

	static String read(final URL url) throws IOException {
		final URLConnection conn = url.openConnection();
		final InputStreamReader reader = new InputStreamReader(
				conn.getInputStream());
		return new String(readChars(reader, conn.getContentLength()));
	}

	private static char[] readChars(final Reader in, final int size)
			throws IOException {
		final char[] chars = new char[size];
		in.read(chars);
		in.close();

		return chars;
	}

}