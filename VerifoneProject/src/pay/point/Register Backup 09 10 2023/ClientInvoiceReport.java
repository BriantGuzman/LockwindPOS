
//Last Update: 3/26/2022

//THIS CLASS SENDS AN INVOICE REPORT TO THE LOCKWIND DOMAIN FOR STORAGE IN THE DATABASE AND FORWARD TO BACKUP SMTP EMAIL ADDRESSES FOR ARCHIVING.

//import javax.net.ssl.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;

public class ClientInvoiceReport {
 
	private final String USER_AGENT = "Mozilla/5.0";
	 
 public ClientInvoiceReport() { }
 
 
 
 public String getCurrentInvoiceNumber(String client_id) throws Exception {
     
	 String response = "-1";
	 
	 /**
     System.out.println("ClientInvoiceReport->getCurrentInvoiceNumber()");
     
     String url = "https://lockwind.com/test/TM/GetInvoiceNumber.php";
     URL obj = new URL(url);
     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
     
     //add request header
     con.setRequestMethod("POST");
     con.setRequestProperty("User-Agent", USER_AGENT);
     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
     
     String urlParameters = "";
     
     
     urlParameters =  "client_id="+ client_id +"&" ;
     
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
     
     BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
     String inputLine;
     StringBuffer response = new StringBuffer();
     
     while ((inputLine = in.readLine()) != null) {
         response.append(inputLine);
     }
     in.close();
     
     //print result
     System.out.println(response.toString());
     	  * 
	  */

     return response;
//     return response.toString();
     
 }

 public String IncrementInvoiceNumber(String client_id) throws Exception {
     
     System.out.println("ClientInvoiceReport->IncrementInvoiceNumber()");
     
     String url = "https://lockwind.com/test/PIM/IncrementInvoiceNumber.php";
     URL obj = new URL(url);
     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
     
     //add request header
     con.setRequestMethod("POST");
     con.setRequestProperty("User-Agent", USER_AGENT);
     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
     
     String urlParameters = "";
     
     
     urlParameters =  "client_id="+ client_id +"&" ;
     
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
     
     BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
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
 
 
 
 


	public String sendPost(String consumer_uuid, String issuer_uuid,String client_id,String client_name,String customer_code,String invoice_number,String invoice_date,String invoice_time,String invoice_currency,String total_value,String tender_value,String change_value) throws Exception {

		String url = "https://lockwind.com/test/TM/AddTransactionAPI.php";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		String urlParameters = "";
	
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

     
     /*
     */
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

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
//		 IncrementInvoiceNumber(client_id);
		
		//print result
		return response.toString();

	}
 
 
 
 /*
 public void sendProductPost(String client_id, String client_name, String invoice_number, String gtin, String quantity, String description, String price, String subtotal, String tax, String discount, String on_hand) throws Exception {
     
     System.out.println("ClientInvoiceReport->sendProductPost()");

     String url = "http://lockwind.com/test/PIM/ClientProductReport.php";
     URL obj = new URL(url);
     HttpURLConnection con = (HttpURLConnection) obj.openConnection();

     //add request header
     con.setRequestMethod("POST");
     con.setRequestProperty("User-Agent", USER_AGENT);
     con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
     
     String urlParameters = "";
     
     String product_uuid = "UUID";
     product_gtin = gtin;
     product_description = description;
     product_price = price;
     product_subtotal = subtotal;
     product_tax = tax;
     product_discount = discount;
     product_on_hand = on_hand;
     
     urlParameters =  "client_id="+ client_id +"&" ;
     urlParameters += "client_name="+ client_name +"&" ;
     urlParameters += "invoice_number="+ invoice_number +"&" ;
     urlParameters += "product_uuid="+ product_uuid +"&" ;
     
     urlParameters += "product_gtin="+ product_gtin +"&" ;
     urlParameters += "quantity="+ quantity +"&";
     urlParameters += "product_description="+ product_description +"&" ;
     urlParameters += "product_price="+ product_price +"&";
     urlParameters += "product_subtotal="+ product_subtotal +"&" ;
     urlParameters += "product_tax="+ product_tax +"&";
     urlParameters += "product_discount="+ product_discount +"&";
     urlParameters += "product_on_hand="+ product_on_hand +"&";
     
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
     
     BufferedReader in = new BufferedReader(
                                            new InputStreamReader(con.getInputStream()));
     String inputLine;
     StringBuffer response = new StringBuffer();
     
     while ((inputLine = in.readLine()) != null) {
         response.append(inputLine);
     }
     in.close();
     
     //print result
     System.out.println(response.toString());
     
    
     
 }

 */


 public static void main(String[] args)
 {

     // ClientInvoiceReport test = new ClientInvoiceReport();
     
     try {
         // test.getInvoiceNumber("5d45815dc71dd");
     }
     catch(Exception e){}
     
 }



}
