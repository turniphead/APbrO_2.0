import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class StatParser {
	
	/// Instance Variables
	private String userFirstName;
	private String userLastName;
	private File rawData;
	private String[] userData;
	
	/// Methods
	/**
	 * Constructor for StatParser object, called in MainActivity
	 * @param userFirstName
	 * @param userLastName
	 * @param rawData: file to be parsed.
	 */
	public StatParser(String userFirstName, String userLastName, File rawData) {
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.rawData = rawData;
		this.userData = parseUserData(parseFile(rawData));
	}
	
	/**
	 * Uses Scanner to read file and return the line with the userData.
	 * @param spreadsheetData: raw file to be parsed
	 * @return the line matching the user's data
	 */
	private String parseFile(File spreadsheetData) {
		String data = "";
		try {
			Scanner fileScan = new Scanner(spreadsheetData);
			// Read each line of the file.
			while (fileScan.hasNextLine()) {
				String currentLine = fileScan.nextLine();
				Scanner lineScan = new Scanner(currentLine);
				lineScan.useDelimiter("\t");
				
				// Get name from line and check if this is the user we want.
				String lastName = lineScan.next(),
					   firstName = lineScan.next();
				lineScan.close();
				if (lastName.equalsIgnoreCase(this.userLastName)
						&& firstName.toLowerCase().contains(firstName.toLowerCase())) {
					data = currentLine;
				}
			}
			fileScan.close();
		} catch (FileNotFoundException e) {
			//TODO: something
		}
		return data;
	}
	
	/**
	 * Parse the line of userdata into a String array
	 * @param userData: raw userData
	 * @return: userData as a String[]
	 */
	private String[] parseUserData(String userData) {
		if (userData == "") return null;
		
		String[] _userData = new String[11];
		Scanner userDataParser = new Scanner(userData);
		userDataParser.useDelimiter("\t");
		
		// Set first element as name.
		_userData[0] = userDataParser.next() + " " + userDataParser.next();
		// Populate rest of data array.
		for (int i = 1; i < _userData.length; ++i) {
			if (userDataParser.hasNext())
				_userData[i] = userDataParser.next();
		}
		// Close Scanner.
		userDataParser.close();
		return _userData;
	}
	
	/* KVC */
	public String[] getUserData() {
		return this.userData;
	}
}
