package edu.frostburg.cosc460;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class is responsible for performing appropriate shell operations
 * 
 * @author Benjamin Barton
 * @version 1.0, 10/02/2015
 */
public class ShellHelper {

	/**
	 * this will start the shell and will continue as long as while loop is set
	 * to true
	 * 
	 * @throws java.io.IOException
	 */
	public void start() throws java.io.IOException {
		// We will hold commands that will be stored into history.
		ArrayList<String> history = new ArrayList<String>();
		// String variable for user input
		String commandLine;
		// Creating the buffer
		BufferedReader console = new BufferedReader(new InputStreamReader(
				System.in));
		// Initializing string of System.getProperty("user.home")
		String file_string = System.getProperty("user.home");
		File currentDirectory = new File(file_string);
		// shell will operate while the loop is true, when set to false the
		// method start() will end
		while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();
			// put commandLine into an arrayList
			// exit if the user entered "exit"
			if (commandLine.equals("exit"))
				break;
			// If the user entered a return, just loop again
			if (commandLine.equals(""))
				continue;
			// command.get(0).startsWith("!")
			// history will not have ! commands placed into it.
			if (commandLine.charAt(0) == ('!')) {
				if (commandLine.equals("!!")) {
					// if command is !! then user wants to use the command most
					// recently used
					int last = (history.size()) - 1;
					commandLine = history.get(last);
				} else {
					// will remove ! from the beginning of string
					// will then use the number specified by the user to access
					// the element at that index in history and set commandLine
					// to the accessed element"String"
					String temp = commandLine.substring(1);
					int commandLocation = Integer.parseInt(temp);
					commandLine = history.get(commandLocation);
				}
			}
			// if history is the input then output the current contents of
			// history
			// will not add history to history
			if (commandLine.equals("history")) {
				for (int i = 0; i < history.size(); i++) {
					System.out.print(i + " ");
					System.out.println(history.get(i));
				}
				continue;
			}
			// if this part is reached then add commandLine to the history
			history.add(commandLine);
			// creating an ArrayList<String> from the commandLine.split(" ")
			List<String> command = new ArrayList<String>(
					Arrays.asList(commandLine.split(" ")));
			// if the user enters cd,start one of two cases.
			if (command.get(0).equals("cd")) {
				// if only cd is the input then set currentFile to
				// System.getProperty("user.home")
				if (command.size() == 1) {
					file_string = System.getProperty("user.home");
					currentDirectory = new File(file_string);
					continue;
				}
				// if cd and a destination is the input, set file_string
				else if (command.size() == 2) {
					try {
						file_string += "/" + (command.get(1));
						currentDirectory = new File((file_string));
					} catch (Exception e) {
						System.out.println("This file was not found");
					}
					continue;
				}
			}
			// create ProcessBuilder
			// catch exceptions
			try {
				// creating processbuilder based on command
				ProcessBuilder pb = new ProcessBuilder(command);
				// Setting the directory of pb to the currentDirectory
				pb.directory(currentDirectory);
				Process process = pb.start();
				// Get process inputStream
				InputStream myInput = process.getInputStream();
				// Output contents returned by the command
				InputStreamReader read = new InputStreamReader(myInput);
				BufferedReader buff = new BufferedReader(read);
				String line;
				// need to format in a more condensed way like regular commands
				// instead of skipping a line
				while ((line = buff.readLine()) != null)
					System.out.println(line);

			} catch (Exception e) {
				System.out.print(e + "\n");
			}
		}
		
	}
}
