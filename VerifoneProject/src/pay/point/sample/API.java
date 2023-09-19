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
	}
	 
	 
	 public String getCurrentInvoiceNumber(String retailerUUID) throws Exception {
	     
	     System.out.println("ClientInvoiceReport->getCurrentInvoiceNumber()");
	     
	     url = "https://lockwind.com/test/TM/GetInvoiceNumber.php";
	     obj = new URL(url);
	     con = (HttpURLConnection) obj.openConnection();
	     
	     //add request header
	     con.setRequestMethod("POST");
	     con.setRequestProperty("User-Agent", USER_AGENT);
	     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	     
	     String urlParameters = "";
	     
	     
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
	 
	 
	 
	 
	 
	 public String sendPost(String consumer_uuid, String issuer_uuid,String client_id,String client_name,String customer_code,String invoice_number,String invoice_date,String invoice_time,String invoice_currency,String total_value,String tender_value,String change_value) throws Exception {
		 	
		 	 url 			= "";
		 	 urlParameters 	= "";

		 	 url 			= "https://lockwind.com/test/TM/AddTransactionAPI.php";
			 obj 			= new URL(url);
			 con 			= (HttpURLConnection) obj.openConnection();

			 con.setRequestMethod("POST");
			 con.setRequestProperty("User-Agent", USER_AGENT);
			 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			 urlParameters =  "consumer_uuid="+ consumer_uuid +"&" ;
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
	
}
