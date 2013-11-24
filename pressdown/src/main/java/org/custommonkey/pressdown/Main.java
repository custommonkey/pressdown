package org.custommonkey.pressdown;

import static org.custommonkey.pressdown.ReadableFile.readableFile;

import java.io.FileNotFoundException;

public class Main {

	public static void main(final String[] args) throws Exception {
	
	    try {
	
	        if (args.length == 0) {
	        	
	            new Pressdown().markdownToHTML(readableFile("README.md"));
	            
	        } else {
	
	            final ReadableFile xslFile = readableFile(args[0]);
	            
				final Pressdown pressdown = new Pressdown(xslFile);
	
	            for (int i = 1; i < args.length; i++) {
	                pressdown.markdownToHTML(readableFile(args[i]));
	            }
	        }
	
	    } catch(FileNotFoundException e) {
	        System.err.println("Could not find file " + e.getMessage());
	    }
	
	}

}
