
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class Internet {

	 public Internet() {
		 
	 }
	 
	 public String checkConnection() {
	     
	     String temp = "";
	     try {
	         URL url = new URL("https://www.google.com");
	         URLConnection connection = url.openConnection();
	         connection.connect();
	         temp = "Internet connected";
	         //         System.out.println("Internet is connected");
	     } catch (MalformedURLException e) {
	         temp = "No connection available";
	         // System.out.println("Internet is not connected");
	     } catch (IOException e) {
	         temp = "No connection available";
	         //         System.out.println("Internet is not connected");
	     }
	     return temp;
	 }
	}