
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ProductManagementSystem implements Runnable {
	 
	private Thread go;
	
	private String retailer_uuid;
	
	 public ProductManagementSystem(){
	
		 retailer_uuid = "";
		 go = null;
		 System.out.println("@ProductManagementSystem-New Object from Constructor");
		 run();
		 
	 }
	 
	 public void setRetailerUUID(String r_uuid) {
		 retailer_uuid = r_uuid;
	 }
	 public String getRetailerUUID() {
	 return retailer_uuid;
	 }
	 
	 public String getProductInfoAPI(String API_GTIN_VALUE) throws Exception {
		 
		 String USER_AGENT = "Mozilla/5.0";
	     
	     String url = "https://lockwind.com/test/PIM/JAVAPOS_API.php";
	     URL obj = new URL(url);
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	     
	     //add request header
	     con.setRequestMethod("POST");
	     con.setRequestProperty("User-Agent", USER_AGENT);
	     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	     
	     String urlParameters = "";
	     
//	     String retailer_uuid = "5fd726bb58b94";
	     
	     
	     urlParameters =  "reference_code="+ API_GTIN_VALUE +"&retailer_uuid=" + retailer_uuid ;
	     
	     // Send post request
	     con.setDoOutput(true);
	     DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	     wr.writeBytes(urlParameters);
	     wr.flush();
	     wr.close();
	     
	     int responseCode = con.getResponseCode();
	     System.out.println("\nSending 'POST' request to URL : " + url);
	     System.out.println("Post parameters : " + urlParameters);
	     System.out.println("Response Code : " + responseCode);
	     
	     BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     
	     while ((inputLine = in.readLine()) != null) {
	         response.append(inputLine);
	     }
	     in.close();
	     
	     //print result
	     System.out.println(response.toString());
	     
	     return response.toString();
	     
	     
	 }
	 public String getProductInfoAPICategory(String API_GTIN_VALUE) throws Exception {

		String USER_AGENT = "Mozilla/5.0";
	     
	     String url = "https://lockwind.com/test/PIM/JAVAPOS_API_CATEGORY.php";
	     URL obj = new URL(url);
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	     
	     //add request header
	     con.setRequestMethod("POST");
	     con.setRequestProperty("User-Agent", USER_AGENT);
	     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	     
	     String urlParameters = "";
	     
//	     String retailer_uuid = "5fd726bb58b94";
	     
	     
	     urlParameters =  "reference_code="+ API_GTIN_VALUE +"&retailer_uuid=" + retailer_uuid ;
	     
	     // Send post request
	     con.setDoOutput(true);
	     DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	     wr.writeBytes(urlParameters);
	     wr.flush();
	     wr.close();
	     
	     int responseCode = con.getResponseCode();
	     System.out.println("\nSending 'POST' request to URL : " + url);
	     System.out.println("Post parameters : " + urlParameters);
	     System.out.println("Response Code : " + responseCode);
	     
	     BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     
	     while ((inputLine = in.readLine()) != null) {
	         response.append(inputLine);
	     }
	     in.close();
	     
	     //print result
		 System.out.println( "API RESPONSE: "+ response.toString());

	     return response.toString();
	 
 }
 



 public String getProductInfoAPIBrandDescription(String API_GTIN_VALUE) throws Exception {

	String USER_AGENT = "Mozilla/5.0";
	 
	 String url = "https://lockwind.com/test/PIM/JAVAPOS_API_BRAND_DESCRIPTION.php";
	 URL obj = new URL(url);
	 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
	 //add request header
	 con.setRequestMethod("POST");
	 con.setRequestProperty("User-Agent", USER_AGENT);
	 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
	 String urlParameters = "";
	 
//	     String retailer_uuid = "5fd726bb58b94";
	 
	 
	 urlParameters =  "reference_code="+ API_GTIN_VALUE +"&retailer_uuid=" + retailer_uuid ;
	 
	 // Send post request
	 con.setDoOutput(true);
	 DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	 wr.writeBytes(urlParameters);
	 wr.flush();
	 wr.close();
	 
	 int responseCode = con.getResponseCode();
	 System.out.println("\nSending 'POST' request to URL : " + url);
	 System.out.println("Post parameters : " + urlParameters);
	 System.out.println("Response Code : " + responseCode);
	 
	 BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
	 String inputLine;
	 StringBuffer response = new StringBuffer();
	 
	 while ((inputLine = in.readLine()) != null) {
		 response.append(inputLine);
	 }
	 in.close();
	 
	 //print result
	 System.out.println( "API RESPONSE: "+ response.toString());
	 
	 return response.toString();
 
}



public String getProductInfoAPIPriceRetail(String API_GTIN_VALUE) throws Exception {

	String USER_AGENT = "Mozilla/5.0";
	 
	 String url = "https://lockwind.com/test/JAVAPOS_API_PRICE_RETAIL.php";
	 URL obj = new URL(url);
	 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
	 //add request header
	 con.setRequestMethod("POST");
	 con.setRequestProperty("User-Agent", USER_AGENT);
	 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
	 String urlParameters = "";
	 
	 urlParameters =  "reference_code="+ API_GTIN_VALUE +"&retailer_uuid=" + retailer_uuid ;
	 
	 // Send post request
	 con.setDoOutput(true);
	 DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	 wr.writeBytes(urlParameters);
	 wr.flush();
	 wr.close();
	 
	 int responseCode = con.getResponseCode();
	 System.out.println("\nSending 'POST' request to URL : " + url);
	 System.out.println("Post parameters : " + urlParameters);
	 System.out.println("Response Code : " + responseCode);
	 
	 BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
	 String inputLine;
	 StringBuffer response = new StringBuffer();
	 
	 while ((inputLine = in.readLine()) != null) {
		 response.append(inputLine);
	 }
	 in.close();
	 
	 //print result
	 System.out.println( "API RESPONSE: "+ response.toString());
	 
	 return response.toString();
 
}












 public String getProductInfo(String upc) // deprecated method pricing now comes from API via lockwind.com
	 {
	     String productInfo = "";String da = "";String line = "";  StringTokenizer str = null;
	     
	     try{
	         File file = new File("datum.csv");
	         Scanner inputFile = new Scanner(file);
	         
	         line = inputFile.nextLine();
	         
	         while(inputFile.hasNextLine()  /* && !da.equalsIgnoreCase(upc)*/  ){
	             line = inputFile.nextLine();
	             str = new StringTokenizer(line,",");
	             
	             if(str.hasMoreTokens()){
	                 da = str.nextToken();
	                 
	                 if(da.equalsIgnoreCase(upc)){
	                     productInfo = line;}
	                 str.nextToken();
	                 str.nextToken();
	                 str.nextToken();
	                 str.nextToken();}}
	         inputFile.close();
	     	    
	     }
	     
	     catch(IOException e){}
	     System.out.println(productInfo);
	     
	     return productInfo;
	 }
	 public void run() {
		 System.out.println("@ProductManagementSystem.run()");		 
	 }
	 
	 public void stop() {
	    	System.out.println("@ProductManagementSystem.stop()");
	    	if(go != null)
	    	{
	    		go = null;
	    	}
	    }
	} // close class:: ProductManagementSystem