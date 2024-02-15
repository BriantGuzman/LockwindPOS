//Last Update: 3/26/2022

package pay.point.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class API {

	 private final String USER_AGENT = "Mozilla/5.0";
	// private String response;

	 private String url;
	 private String urlParameters;
	 private String inputLine;
	 private StringBuffer response_buffer;
	 
	 private URL obj;
	 private HttpURLConnection con;

	 private int responseCode;

	 private DataOutputStream wr; 
	 
	 private BufferedReader in;
	 
	 public API() {
		 
	 }
	
	 
	 public static double getDaysBetween(LocalDate start, LocalDate end) {
	        return ChronoUnit.DAYS.between(start, end);
	    }
	     public double calculatePercentage(LocalDate initial_date, LocalDate end_date, double initial_value,double final_value) {
	         
	         
	         // Calculate total years
	         double totalYears = getDaysBetween(initial_date,end_date);

	         // Calculate percentage increase
	         double percentageIncrease = ((final_value - initial_value) / initial_value) * 100;

	         // Calculate CAGR
	         double cagr = Math.pow((final_value / initial_value), (1.0 / totalYears)) - 1;

	         // Output the results
	         System.out.printf("Percentage Increase: %.2f%%\n", percentageIncrease);
	         System.out.printf("Average annual percentage increase (CAGR) over %d years: %.2f%%\n", totalYears, cagr * 100);
	         
	         return cagr * 100;
	     }
	 

	 
	 public String addCustomerAPI(Customer c,String cient_id)
	 {
		 String temp = "";
		 return temp;
		 /*			
		 	url = "https://lockwind.com/test/TM/AddCustomerAPI.php";
		 	urlParameters = "";
			response_buffer = new StringBuffer();

		 	try {
		 	
		 	 obj = new URL(url);
			 con = (HttpURLConnection) obj.openConnection();

			 con.setRequestMethod("POST");
			 con.setRequestProperty("User-Agent", USER_AGENT);
			 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			 urlParameters =  "customer_code="+ c.getCustomerBillToCode() +"&" ; 
			 urlParameters += "customer_name="+ c.getCustomerBillToName() +"&" ;
		     urlParameters += "customer_phone_number="+ c.getCustomerBillToPhoneNumber() +"&" ;
		     urlParameters += "customer_email="+ c.getCustomerBillToEmailAddress() +"&" ;
		 	 con.setDoOutput(true);
			 wr = new DataOutputStream(con.getOutputStream());
			 wr.writeBytes(urlParameters);
			 wr.flush();
			 wr.close();

			responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			while ((inputLine = in.readLine()) != null) { response_buffer.append(inputLine); }
			in.close();
		 	} catch(Exception e) {}
		 	
		 	return response_buffer.toString();
		 	*/
	}
	 
	 public void updateLineItemStatus(String uuid[],String action[]) {
		 try {
		 
			 this.sendPost("https://lockwind.com/test/TM/AddTransactionLineItemAPIv2.php", uuid,action);
			 for(int i = 0; i < uuid.length; i++)
			 {
			 System.out.println("updateLineItemStatus:" + url + " " + uuid[i]  +" " + action[i]);
			 }
		 }catch(Exception e) { e.printStackTrace(); };
	 }
	 // Factory method/function for reducing API code for each different message API request.
	 public String sendPost(String url, String key[], String value[]) throws Exception { 
		 System.out.println("API->sendPost(String url, String key[], String value[])");
	     
	     obj = new URL(url);
	     con = (HttpURLConnection) obj.openConnection();
	     
	     //add request header
	     con.setRequestMethod("POST");
	     con.setRequestProperty("User-Agent", USER_AGENT);
	     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	     
	     String urlParameters = "";
	     
	     for(int i = 0; i < key.length; i++) { urlParameters +=  key[i] + "="+ value[i] +"&" ; }

	     // Send post request
	     con.setDoOutput(true);
	     wr = new DataOutputStream(con.getOutputStream());
	     wr.writeBytes(urlParameters);
	     wr.flush();
	     wr.close();
	     
	     responseCode = con.getResponseCode();
	     System.out.println("\nSending 'POST' request to URL : " + url);
	     System.out.println("Post parameters : " + urlParameters);
	     System.out.println("Response Code : " + responseCode);
	     
	     in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
	     response_buffer = new StringBuffer();
	     
	     while ((inputLine = in.readLine()) != null) {
	         response_buffer.append(inputLine);
	     }
	     in.close();
	     
	     //print result
//	     System.out.println(response_buffer.toString());
	     
	     return response_buffer.toString();
	 }
	 
	 public String getTransactionUUID(String issuerUUID, String invoice_number) throws Exception { 
		 
		   	 System.out.println("API->getTransactionUUID()");
		     url = "https://lockwind.com/test/TM/GetTransactionUUID.php";
		     
		     String[] key = {"issuer_uuid","invoice_number"};
		     String[] value = {issuerUUID, invoice_number};

		     this.sendPost(url,key,value);
		     
		     
		     return response_buffer.toString();
	 }
	 
	 public String getCurrentInvoiceNumber(String issuerUUID) throws Exception {
	     
	     System.out.println("API->getCurrentInvoiceNumber()");
	     
	     url = "https://lockwind.com/test/TM/GetInvoiceNumber.php";
	     obj = new URL(url);
	     con = (HttpURLConnection) obj.openConnection();
	     
	     //add request header
	     con.setRequestMethod("POST");
	     con.setRequestProperty("User-Agent", USER_AGENT);
	     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	     
	     String urlParameters = "";
	     
	     
	     urlParameters =  "issuer_uuid="+ issuerUUID +"&" ;
	     
	     // Send post request
	     con.setDoOutput(true);
	     wr = new DataOutputStream(con.getOutputStream());
	     wr.writeBytes(urlParameters);
	     wr.flush();
	     wr.close();
	     
	     responseCode = con.getResponseCode();
	     System.out.println("\nSending 'POST' request to URL : " + url);
	     System.out.println("Post parameters : " + urlParameters);
	     System.out.println("Response Code : " + responseCode);
	     
	     in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
	     response_buffer = new StringBuffer();
	     
	     while ((inputLine = in.readLine()) != null) {
	         response_buffer.append(inputLine);
	     }
	     in.close();
	     
	     //print result
	     System.out.println(response_buffer.toString());
	     
	     return response_buffer.toString();
	     
	 }

	 public String IncrementInvoiceNumber(String retailerUUID) throws Exception {
	     
	     System.out.println("API->IncrementInvoiceNumber()");
	     
	     url = "https://lockwind.com/test/TM/IncrementInvoiceNumber.php";
	     obj = new URL(url);
	     con = (HttpURLConnection) obj.openConnection();
	     
	     //add request header
	     con.setRequestMethod("POST");
	     con.setRequestProperty("User-Agent", USER_AGENT);
	     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	     
	     urlParameters = "";
	     
	     
	     urlParameters =  "uuid="+ retailerUUID +"&" ;
	     
	     // Send post request
	     con.setDoOutput(true);
	     wr = new DataOutputStream(con.getOutputStream());
	     wr.writeBytes(urlParameters);
	     wr.flush();
	     wr.close();
	     
	     responseCode = con.getResponseCode();
	     System.out.println("\nSending 'POST' request to URL : " + url);
	     System.out.println("Post parameters : " + urlParameters);
	     System.out.println("Response Code : " + responseCode);
	     
	     in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
	     
	     response_buffer = new StringBuffer();
	     
	     while ((inputLine = in.readLine()) != null) {
	         response_buffer.append(inputLine);
	     }
	     in.close();
	     
	     //print result
	     System.out.println(response_buffer.toString());
	     
	     return response_buffer.toString();
	     
	 }
	 
	 
	 
	 
	 
	 public String sendPost(String uuid, String consumer_uuid, String issuer_uuid,String client_id,String client_name,String customer_code,String invoice_number,String invoice_date,String invoice_time,String invoice_currency,String total_value,String tender_value,String change_value) throws Exception {
		 	
		 	 url 			= "";
		 	 urlParameters 	= "";

		 	 url 			= "https://lockwind.com/test/TM/AddTransactionAPI.php";
			 obj 			= new URL(url);
			 con 			= (HttpURLConnection) obj.openConnection();

			 con.setRequestMethod("POST");
			 con.setRequestProperty("User-Agent", USER_AGENT);
			 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			 urlParameters =  "uuid="+ uuid +"&" ;
			 urlParameters +=  "consumer_uuid="+ consumer_uuid +"&" ;
			 urlParameters += "issuer_uuid="+ issuer_uuid +"&" ;
		     urlParameters += "client_id="+ client_id +"&" ;
		     urlParameters += "client_name="+ client_name.trim() +"&" ;
		     urlParameters += "customer_code="+ customer_code +"&" ;
		     urlParameters += "invoice_number="+ invoice_number +"&" ;
		     urlParameters += "invoice_date="+ invoice_date +"&";
		     urlParameters += "invoice_time="+ invoice_time +"&" ;
		     urlParameters += "invoice_currency="+ invoice_currency +"&";
		     urlParameters += "total_value="+ total_value +"&" ;
		     urlParameters += "tender_value="+ tender_value +"&";
		     urlParameters += "change_value="+ change_value +"";

		     con.setDoOutput(true);
			 wr = new DataOutputStream(con.getOutputStream());
			 wr.writeBytes(urlParameters);
			 wr.flush();
			 wr.close();

			 responseCode = con.getResponseCode();
			 System.out.println("\nSending 'POST' request to URL : " + url);
			 System.out.println("Post parameters : " + urlParameters);
			 System.out.println("Response Code : " + responseCode);

			 in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
			 response_buffer= new StringBuffer();

			 while ((inputLine = in.readLine()) != null) {
				response_buffer.append(inputLine);
			 }
			 in.close();

			 return response_buffer.toString();

		}
	
	 
	 

	// Send the line item information to the Lockwind Cloud	 
	 public String sendProductPostAPILineItem( 
			 
		 
			 String reference_code, String quantity, String category,String description, 
			 String price, String subtotal, String tax, String discount, 
			 String onhand, String line_item_id, String document_number, String transaction_type_status, 
			 String pos_uuid, String issuer_uuid, String location_uuid, String transaction_uuid ) throws Exception {
		 	
		 // package and send data to Lockwind Cloud. (No calculations or API requests except 1)
		 	 url 			= "";
		 	 urlParameters 	= "";

		 	 url 			= "https://lockwind.com/test/TM/AddTransactionLineItemAPIv2.php";
			 obj 			= new URL(url);
			 con 			= (HttpURLConnection) obj.openConnection();

			 con.setRequestMethod("POST");
			 con.setRequestProperty("User-Agent", USER_AGENT);
			 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			 urlParameters += "uuid="+ line_item_id +"&" ;			 // in MySQL Table JAVAPOS_TRANSACTION
			 urlParameters += "transaction_type="+ "ELECTRONIC_DOCUMENT" +"&" ;			 // in MySQL Table JAVAPOS_TRANSACTION
			 urlParameters += "transaction_type_value="+ "LINE_ITEM" +"&" ;			 // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "transaction_type_status="+ transaction_type_status +"&"; // in MySQL Table JAVAPOS_TRANSACTION

		     
		     urlParameters += "reference_code="+ reference_code +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "quantity="+ quantity +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "category="+ category +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "description="+ description +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "price="+ price +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "subtotal="+ subtotal +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "tax="+ tax +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "discount="+ discount +"&"; // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "onhand="+ onhand +"&";  // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "line_item_id="+ line_item_id +"&"; // in MySQL Table JAVAPOS_TRANSACTION

		     urlParameters += "document_number="+ document_number +"&"; // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "pos_uuid="+ pos_uuid +"&"; // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "issuer_uuid="+ issuer_uuid +"&"; // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "location_uuid="+ location_uuid +"&"; // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "transaction_uuid="+ transaction_uuid +"&"; // in MySQL Table JAVAPOS_TRANSACTION
		     
		     
		     
		     con.setDoOutput(true);
			 wr = new DataOutputStream(con.getOutputStream());
			 wr.writeBytes(urlParameters);
			 wr.flush();
			 wr.close();

			 responseCode = con.getResponseCode();
			 System.out.println("\nSending 'POST' request to URL : " + url);
			 System.out.println("Post parameters : " + urlParameters);
			 System.out.println("Response Code : " + responseCode);

			 in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
			 response_buffer= new StringBuffer();

			 while ((inputLine = in.readLine()) != null) {
				response_buffer.append(inputLine);
			 }
			 in.close();

			 return response_buffer.toString();

		}
	 

 public String getUUID( ) throws Exception {
		 	
		 	 url 			= "";
		 	 urlParameters 	= "";

		 	 url 			= "https://lockwind.com/test/uuid.php";
			 obj 			= new URL(url);
			 con 			= (HttpURLConnection) obj.openConnection();

			 con.setRequestMethod("POST");
			 con.setRequestProperty("User-Agent", USER_AGENT);
			 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			 urlParameters =  "new_uuid=&" ; // in MySQL Table JAVAPOS_TRANSACTION
			 
		     con.setDoOutput(true);
			 wr = new DataOutputStream(con.getOutputStream());
			 wr.writeBytes(urlParameters);
			 wr.flush();
			 wr.close();

			 responseCode = con.getResponseCode();
			 
			 in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
			 response_buffer= new StringBuffer();

			 while ((inputLine = in.readLine()) != null) {
				response_buffer.append(inputLine);
			 }
			 in.close();

			 System.out.println("API->getUUID() \t\t + " + response_buffer.toString() );
			 return response_buffer.toString();

		}
	 
	 

	 
	 public String sendProductPostAPI( // Send the line item information to the Lockwind Cloud
			 
			 String consumer_uuid, String issuer_uuid,String client_id,String client_name,
			 String customer_code,String invoice_number,String invoice_date,String invoice_time,
			 String invoice_currency,String total_value,String tender_value,String change_value,
			 String transaction_type,String transaction_type_value,
			 String reference_code, String quantity, String category,String description, 
			 String price, String subtotal, String tax, String discount, String onhand, String line_item_id

			 ) throws Exception {
		 	
		 	 url 			= "";
		 	 urlParameters 	= "";

		 	 url 			= "https://lockwind.com/test/TM/AddTransactionLineItemAPI.php";
			 obj 			= new URL(url);
			 con 			= (HttpURLConnection) obj.openConnection();

			 con.setRequestMethod("POST");
			 con.setRequestProperty("User-Agent", USER_AGENT);
			 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			 urlParameters =  "consumer_uuid="+ consumer_uuid +"&" ; // in MySQL Table JAVAPOS_TRANSACTION
			 urlParameters += "issuer_uuid="+ issuer_uuid +"&" ;	// in MySQL Table JAVAPOS_TRANSACTION		 
			 urlParameters += "client_id="+ client_id +"&" ;// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "client_name="+ client_name.trim() +"&" ;// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "customer_code="+ customer_code +"&" ;// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "invoice_number="+ invoice_number +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "invoice_date="+ invoice_date +"&"; // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "invoice_time="+ invoice_time +"&" ;// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "invoice_currency="+ invoice_currency +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "total_value="+ total_value +"&" ;// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "tender_value="+ tender_value +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "change_value="+ change_value +"";// in MySQL Table JAVAPOS_TRANSACTION

			 urlParameters += "transaction_type="+ "INVOICE" +"&" ;			 // in MySQL Table JAVAPOS_TRANSACTION
			 urlParameters += "transaction_type_value="+ "PAID" +"&" ;			 // in MySQL Table JAVAPOS_TRANSACTION
		     
		     
		     urlParameters += "reference_code="+ reference_code +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "quantity="+ quantity +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "category="+ category +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "description="+ description +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "price="+ price +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "subtotal="+ subtotal +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "tax="+ tax +"&";// in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "discount="+ discount +"&"; // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "onhand="+ onhand +"&";  // in MySQL Table JAVAPOS_TRANSACTION
		     urlParameters += "line_item="+ line_item_id +"&"; // in MySQL Table JAVAPOS_TRANSACTION

		     
		     
		     con.setDoOutput(true);
			 wr = new DataOutputStream(con.getOutputStream());
			 wr.writeBytes(urlParameters);
			 wr.flush();
			 wr.close();

			 responseCode = con.getResponseCode();
			 System.out.println("\nSending 'POST' request to URL : " + url);
			 System.out.println("Post parameters : " + urlParameters);
			 System.out.println("Response Code : " + responseCode);

			 in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
			 response_buffer= new StringBuffer();

			 while ((inputLine = in.readLine()) != null) {
				response_buffer.append(inputLine);
			 }
			 in.close();

			 return response_buffer.toString();

		}
	
	 
}
