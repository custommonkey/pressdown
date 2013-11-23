package org.custommonkey.pressdown;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;

public class ReadableFile extends File {

	private static final long serialVersionUID = -4783309504397419263L;

	public static ReadableFile readableFile(String name) throws FileNotFoundException {
		final ReadableFile file = new ReadableFile(name);
		if (!file.canRead()) {
			throw new FileNotFoundException(name);
		}
		return file;
	}

	private ReadableFile(String name) {
		super(name);
	}

	StreamSource asStreamSource() {
		return new StreamSource(this);
	}

	InputSource asInputSource() {
		return new InputSource(this.getName());
	}
}