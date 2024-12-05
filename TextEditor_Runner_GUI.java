/*
 * JavaTextEditor.java
 * Names: Andrew Golias, Patrick Conley, Jack Mueller
 * Usernames: goliasa1, conleyp1, muellerj14
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * Names: Andrew Golias, Patrick Conley, Jack Mueller
 * Usernames: goliasa1, conleyp1, muellerj14
 * 
 * Expansion of Final Project: Java Text Editor
 * 
 * CPS 150 Algorithms & Programming I 
 * 
 * 	Description:
 * 		- This program runs a text editor with ability to open a file, save it, display the file and a user
 * 			specified line, add a line and a line at a user specified line number, change a line, delete
 * 			a line, choose from multiple files and end the program 
 * 
 * 	Bug Report:
 * 		- This program works properly for any .txt file and any input is covered by the
 * 			appropriate error message and prompt to re enter the input
 * 		- Input Mismatch is currently not handled, if the user enters a Sting instead of a number,
 * 			the program should throw an Exception
 */

public class TextEditor_Runner_GUI {

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		// declare variables
			int choice=0;
			int currFileNum = 0;
			ArrayList<IOFile> allFiles = new ArrayList<IOFile>();
			
		// display opening message
			JOptionPane.showMessageDialog(null, "First, select a file to perform a given list of actions on the text in the file",
					 "Welcome to the Text Editor", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(null, "At any time, Select 1 to open a new file, 9 to switch between files, or 10 to close out of a file",
					 "Welcome to the Text Editor", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(null, "Select 2-8 to perform the given actions on the currently selected file",
					 "Welcome to the Text Editor", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(null, "Select 11 to quit the program",
					 "Welcome to the Text Editor", JOptionPane.INFORMATION_MESSAGE);

			
		try {	
			
			do {
				// if no file is open; set choice so user must add a file
					if(!filesInList(allFiles)) {
						choice = 1;
					}
			// determine what to do based on user's choice
				switch(choice) {
			
			// open a text document
				case 1: 
					// choose the file with JFileChooser
					allFiles.add(new IOFile());
					if(!fileIsUnique(allFiles, currFileNum)) {
						allFiles.remove(allFiles.size()-1);
					}
					break;
					
			// save the text document
				case 2:
					allFiles.get(currFileNum).saveFile();
					break;
			// display the document
				case 3:
					allFiles.get(currFileNum).displayLine(true);
					break;
					
			// display a given line
				case 4:
					allFiles.get(currFileNum).displayLine(false);;
					break;
				
			// add a new line at the end
				case 5:
					allFiles.get(currFileNum).printLine(true);
					break;
			// insert a line at a given line
				case 6:
					allFiles.get(currFileNum).printLine(false);
					break;
				
			// change a given line
				case 7:
					allFiles.get(currFileNum).changeLine();
					break;
					
			// delete a given line
				case 8:
					allFiles.get(currFileNum).deleteLine();
					break;
					
			// go to files list
				case 9: 
					if(!filesInList(allFiles)) {
						JOptionPane.showMessageDialog(null, "There are no files currently open");
					}
					else {
						currFileNum = showFileList(allFiles);	// change current file to index of selection
					}
					break;
			
			// remove a file from the list
				case 10:
					if(allFiles.size() < 1) {
						JOptionPane.showMessageDialog(null, "There are no files currently open");
					}
					else {
						removeFileFromList(allFiles, currFileNum);
					}
					if(filesInList(allFiles)) {
						currFileNum = showFileList(allFiles);
					}
					break;
					
				default:	// case 11
					break;
					
				}// end switch	
				
			// get the user's choice
				choice = getChoice();
			}// end do loop
			while(choice != 11);
		}// end try
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}	
		
	}//end main method
	
	/**
	 * method returns true if the size of the ArrayList is greater than 0
	 * 			returns false otherwise
	 * 
	 * @param a (ArrayList<IOFile>) - the list of opened files
	 * @return (boolean)
	 */
	public static boolean filesInList(ArrayList<IOFile> a) {
		if(a.size() > 0) {
			return true;
		}
		return false;
	}// end filesInList method

	/**
	 * fileIsUnique() -> boolean
	 * 
	 * method checks that the file being opened is already open
	 * 
	 * @param f
	 * @param curr
	 * @return (boolean):
	 * 			- True: if there is no matching file name already in the list
	 * 			- False: if there is a file with the name entered already (prevents duplication)
	 */
	public static boolean fileIsUnique(ArrayList<IOFile> f, int curr) {
		for(int i=0; i < f.size(); i++) {
			if(i == curr)
				continue;
			if(f.get(curr).getFileName(true).equals(f.get(i).getFileName(true))) {
				return false;
			}
		}
		
		return true;
	}// end fileIsUnique


	/**
	 * getOption() -> int
	 * 
	 * this method consumes nothing, it asks the user to enter the choice 1-9 and returns a valid input
	 * 
	 * @return (int) the user choice 1-9 for the action they want to carry out
	 */
	public static int getChoice() throws InputMismatchException{
		boolean valid=false;
		do {
			//display menu
			String str = javax.swing.JOptionPane.showInputDialog("Main Menu:\nEnter your choice (1-9): \n"
						+ "1. Open a text document\n"
						+ "2. Save a text document\n"
						+ "3. Display the document\n"
						+ "4. Display a line in the document at a given line number\n"
						+ "5. Add a new line to the end of the document\n"	
						+ "6. Insert a new line prior to a line at a given line number\n"
						+ "7. Change a line at a given line number to new text\n"
						+ "8. Delete a line at a given line number\n"
						+ "9. Go to opened file list\n"
						+ "10. Remove an opened file from the list (save first)\n"
						+ "11. End the program");
			//validate for integers in range
				int choice = Integer.parseInt(str);
				if(choice >=1 && choice <=11)
					return choice;
		}while(!valid);
		
		return 0;	// not reached because loop will cause either valid input or an error
				
				
	}//end getChoice method
	
	/**
	 * showFileList() -> int
	 * 
	 * method shows all the files open and gets the user's choice for which file to choose
	 * 
	 * @param allFiles (ArrrayList<IOFile>) - the list of all open files
	 * @return (int) - the index of the file the user chooses to open
	 */
	public static int showFileList(ArrayList<IOFile> allFiles) {
		boolean valid=false;
		do {
			String output="Which file do you want to work in:\n";
			for(int i=0; i < allFiles.size(); i++) {
				output += (i+1) + ": " + allFiles.get(i).getFileName(true) + "\n";
			}
			String str = javax.swing.JOptionPane.showInputDialog(output);
			int choice = Integer.parseInt(str);
			if(choice >= 1 && choice <= allFiles.size()) {
				return choice - 1;	// offset by 1 to account for indexing
			}
		}while(!valid);
		
		return 0;
	}// end showFileList method
	
	/**
	 * removeFileFromList()
	 * 
	 * method shows file list and gets their choice for which file they want to remove
	 * it asks the user if they are sure 
	 * 
	 * @param f (ArrayList<IOFile>) - list of all open files
	 * @param curr (int) - index of file currently working in
	 */
	public static void removeFileFromList(ArrayList<IOFile> f, int curr) {
		int choice = showFileList(f);
		if(f.get(curr).yesOrNo("Are you sure you want to remove " + f.get(choice).getFileName(true) + " from the list?"
				+ "\nIf the file has not been saved, the contents will not be updated (y/n):")) {
			f.remove(choice);
		}
	}// end removeFileFromList method
	
			
	
	
}//end class
