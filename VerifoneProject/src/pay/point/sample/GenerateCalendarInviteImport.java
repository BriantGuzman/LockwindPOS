package pay.point.sample;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class GenerateCalendarInviteImport {
    public static void main(String[] args) {

    	try { 
    	try {
            // Specify the path to the iCalendar (.ics) file to be attached
        	String outlookPath = "";
        	String icsFilePath = "";
            File icsFile = new File("example.ics");

            // Open the default email client and create a new email
            Desktop.getDesktop().mail();
            Process process = Runtime.getRuntime().exec(outlookPath + " /ical " + icsFilePath);
            process.waitFor();
        
        } catch (IOException e) {
            System.out.println("Error opening email client: " + e.getMessage());
        }
    	}catch(Exception e)
    	{
    		
    	}
    }
}
