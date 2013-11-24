package org.custommonkey.pressdown;

import static org.custommonkey.pressdown.ReadableFile.readableFile;

import java.io.FileNotFoundException;

public class Main {

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

}
