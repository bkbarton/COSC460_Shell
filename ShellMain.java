package edu.frostburg.cosc460;

import java.io.IOException;

/**
 * MAIN
 * 
 * @author Benjamin Barton
 * @version 1.0, 10/02/2015
 */
public class ShellMain {
	/**
	 * this is responsible for creating then starting an instance of Shell
	 * 
	 * @param args
	 * @throws IOException
	 * 
	 */
	public static void main(String[] args) throws IOException {
		ShellHelper instance = new ShellHelper();
		instance.start();
	}
}
