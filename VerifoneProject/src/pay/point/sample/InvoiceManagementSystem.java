package pay.point.sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InvoiceManagementSystem  {
	 
	private final String USER_AGENT = "Mozilla/5.0";

	 private int invoiceNumber;
	 private ClientInvoiceReport http;
	 
	 private String issuer_uuid;
	 private String consumer_uuid;
	
	 
	 public void setConsumerUUID(String r_uuid) {
		 consumer_uuid = r_uuid;
	 }
	 public String getConsumerUUID() {
	 return consumer_uuid;
	 }
	
	 
	 
	 public void setRetailerUUID(String r_uuid) {
		 issuer_uuid = r_uuid;
	 }
	 public String getRetailerUUID() {
	 return issuer_uuid;
	 }
	 
	 
	 public String getEntityName(String issuer_uuid) throws Exception {

		 
			String url = "https://lockwind.com/test/TM/GetName.php";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "";
		
			urlParameters = "issuer_uuid="+ issuer_uuid +"&" ;
	     
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
			 System.out.println("InvoiceManagementSystem @ getEntityName");
			 System.out.println("Result of getName from Database:");
			System.out.println(response.toString());

			return response.toString();
			
		}
	 
	 
	 public InvoiceManagementSystem()
	 {
	     http = new ClientInvoiceReport();
	 }
	 
	 public int getIndex(String client_id){

	     int x = 0;
	     // String client_id = "100002";
	     
	     try {
	    	 	invoiceNumber = Integer.parseInt(http.getCurrentInvoiceNumber(client_id));
	    	 	x = invoiceNumber;
	     }catch(Exception e){}
	return x;
	}
	} // close class :: InvoiceManagementSystem
