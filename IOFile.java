import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * This class works to make an instance of an I/O File that be can read from, printed to, and updated.
 * The runner for this class creates an ArrayList of IOFile Objects that can be worked at once.
 */
public class IOFile {
	public static Scanner sc = new Scanner(System.in);
	private PrintWriter pw;							// used to print final contents of ArrayList to file
	public static FileReader fr;					// used to read file
	public static BufferedReader br;				// used to read file
	
	private String fileName;
	private ArrayList<String> file = null;
	
/***********************************************************************************************/
	
	// accessor methods
	/**
	 * getFileName() -> String
	 * 
	 * method returns the file name as either the address (fileName.txt) or just the name (fileName)
	 * 
	 * @param getAddress (boolean): 
	 * 				T- returns full file address
	 * 				F - returns just the file name (without .txt)
	 * @return (String) - the file name as a String
	 */
	public String getFileName(boolean getAddress) { 
		if(getAddress)
			return fileName;
		else
			return fileName.substring(0, (fileName.indexOf(".")));
	
	}
	
/***********************************************************************************************/
	
	// constructor
	/**
	 * IOFile()
	 * 
	 * method creates a new instance of an IOFile for which a text file can be opened and read from/printed to
	 * the contents of the file given by the fileName from the user are stored to a String ArrayList
	 * 
	 * the runner for this Object Class has an IOFile ArrayList that has the ability to open and use 
	 * muliple text files
	 * 
	 * @throws IOException
	 */
	public IOFile() throws IOException {
		fileName = chooseFile();
		fr = new FileReader(fileName);
		br = new BufferedReader(fr);
		this.file = readFile();		
	}// end constructor method
	
/***********************************************************************************************/
	
	// body methods
	/**
	 * chooseFile() -> String
	 * 
	 * method prompts the user to enter the name of the file
	 * it checks whether the user specified the address type (.txt) and adds it to the return if not
	 * 
	 * @throws IOException
	 * @return String - the name of the file chosen by the user to be used throughout the program
	 */
	public String chooseFile() throws IOException {
		String f = javax.swing.JOptionPane.showInputDialog("Enter the file name: ");
		// check for unspecified address
			if(!f.contains("."))
				f += ".txt";
		
		return f;
	}//end chooseFile method
	
	
	/**
	 * readFile(ArrayList<String>) -> ArrayList<String>
	 *
	 * method reads the file and stores the contents to a String ArrayList
	 * 
	 * @throws IOException
	 * @param file - the array list that will store the contents of the file
	 * @return (ArrayList<String>)- updated array list storing the contents of the file
	 */
	public ArrayList<String> readFile() throws IOException {
		ArrayList<String> file = new ArrayList<String>();
		String line = br.readLine();	
		if(line == null) { // empty file
			file.add("");
			return file;
		}
		while(line != null) {
			file.add(line);
			line = br.readLine();
		}
		
		fr.close();
		br.close();
		return file;
	}//end of readFile method
	
	/**
	 * saveFile()
	 * 
	 * method 'saves' the file by printing the contents to the given file
	 * 
	 * @throws IOException
	 */
	public void saveFile() throws IOException {
		pw = new PrintWriter(new File(fileName));
		for(int i=0; i<file.size(); i++) {
			pw.println(file.get(i));
		}
		pw.close();
	}// end saveFile method
	
	/**
	 * displayLine()
	 * 
	 * method first checks if the file is empty
	 * 
	 * @param wholeDoc
	 */
	public void displayLine(boolean wholeDoc) {
		// check if the file is empty
		if(file.size() == 0) {
			JOptionPane.showMessageDialog(null, "The file is currently empty");
			return;
		}

		int lineNum=0;
		if(!wholeDoc) {
			lineNum = validateLineNumber();
			JOptionPane.showMessageDialog(null, (lineNum+1) + ": " + file.get(lineNum));
		}
		else {
			String out = "Contents of " + this.getFileName(false) + ":\n";
			for (int i = 0; i<file.size(); i++) {
				out += (i+1) +": " + file.get(i) + "\n";
			}
			JOptionPane.showMessageDialog(null, out);
		}
	}// end displayLine method
	
	public void printLine(boolean lineAtEnd) {
		int lineNum = 0;
		if(lineAtEnd) {
			lineNum = file.size();
		}
		else {
			lineNum = validateLineNumber();
		}
		
		String str = javax.swing.JOptionPane.showInputDialog("Enter the text you want to add to the file: ");
		file.add(lineNum, str);
		
	}// end printLine method
	
	public void changeLine() {
		String input = "";
		// get the line number
			int lineNumber = validateLineNumber();
		// display the current text
			JOptionPane.showMessageDialog(null, "This line currently holds '" + file.get(lineNumber) + "'");
			if(yesOrNo("Would you like to change the text (y/n)? ")) {
				input = javax.swing.JOptionPane.showInputDialog("Enter the text you would like to change it to: ");
				file.set(lineNumber, input);
			}
	}// end changeLine method
	
	public void deleteLine() {
		// get the line number
		int lineNumber = validateLineNumber();
		// display the current text
		JOptionPane.showMessageDialog(null, "This line currently holds '" + file.get(lineNumber) + "'");
		if(yesOrNo("Are you sure you want to remove this line (y/n)? "))
			file.remove(lineNumber);
	}// end deleteLine method
	
	/***********************************************************************************************/
	
	// validation methods
	/**
	 * validateLineNumber(ArrayList<String>) -> int
	 * 
	 * this method consumes the array list, file, and the int for the line number that the user enters
	 * the method checks that the number entered is a valid number in the range 0-the size of the array list
	 * and returns a valid number
	 *  
	 * @param file(ArrayList<String>) - the contents of the file
	 * @return (int) - the valid number that will be used as the index to change the file
	 */
	public int validateLineNumber() throws InputMismatchException{
		int lineNum=0;		
		boolean valid=false;
		
		// validate for positive integers in the range of 1-the number of lines in the file
		while(!valid) {
			String str = javax.swing.JOptionPane.showInputDialog(String.format("Enter the line number (1-%d): ", file.size()));
			lineNum = Integer.parseInt(str);
			if(lineNum<=0 || lineNum>file.size()) {
				JOptionPane.showMessageDialog(null,"Invalid line number; try again");
			}	
			else {
				valid = true;
			}
		}
		return lineNum-1;		// subtract 1 from the line number to adjust it to be the index of the ArrayList
	}//end of validateLineNumber method
	
	/**
	 * yesOrNo() -> boolean
	 * 
	 * this method consumes nothing, it asks the user to enter y/n and continues asking until the 
	 * user enter a valid input
	 * the method returns a boolean based on the input
	 * 
	 * @param text (String) - the message initially displayed to the user by the method
	 * 
	 * @return (boolean) - returns true if the user enters yes and false if they enter no
	 */
	public boolean yesOrNo(String text) {
		String input = javax.swing.JOptionPane.showInputDialog(text);
		// validate for y/n
		while(!(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"))) {
			input = javax.swing.JOptionPane.showInputDialog("You must enter y/n: ");
		}
		if(input.equalsIgnoreCase("y"))
			return true;
		
		return false;
	}// end yesOrNo method
	
	
	
}// end IOFile class
