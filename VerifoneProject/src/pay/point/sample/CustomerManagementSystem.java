package pay.point.sample;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CustomerManagementSystem extends Service {
	 
	 
	 List<Customer> customerList = new ArrayList<Customer>();
	 
	 private File file;
	 private Scanner inputFile;
	 private String line;
	 private StringTokenizer str;
	 
	 private Customer customer;

	 private ElectronicDocument document;
	 
	 
	 public CustomerManagementSystem() {
		 inputFile 	= null;
		 line 		= "";
		 file 		= null;
		 customer 	= null;
		 
	 }
	 public CustomerManagementSystem(Customer head){
	     customerList.add(head);
	 }
	 
	 public Customer getCustomerTarget(String customer_code)
	 {
		// This method should be documented and implemented
		 
		int i = 0;
		boolean found = false;
		Customer customer_temp = null;
		
		while(i < customerList.size() ) {
			
			customer_temp = customerList.get(i);
			if(customer_temp.getBillToCode().equalsIgnoreCase(customer_code))
			{
				i = customerList.size();
			}
			else { 
				i++;
			}
			System.out.println(i);
		}
		
		try { 

			}catch(Exception e) {
		
				System.out.println("Error at CustomerManagementSystem.getCustomerTarget ");
		
			}
		return customer_temp;
	 }


	 public String getCustomerBillToCode(String customer,String two)
	 {
		 boolean not_found = false;
		 int i = 0;
		 String temp = "";
		 
		 while(not_found == false && i < customerList.size() )
		 {
			 customerList.get(i).getBillToCode();
			 i++;
		 }
		 return temp;
	 }

	 public String getCustomerBillToAddress(String customer_code)
	 {
		 boolean found = false;
		 int i = 0;
		 String temp = "";
		 
		 Customer customer_temp = null;
		 customer_temp = customer;
		 while( found == false && i < customerList.size() )
		 {
			if(customer.getBillToCode() == customer_code){
				customer_temp = customer;
				found = true;
			}
//			 customerList.get(i).getCustomerBillToAddress();
			 i++;
			 customer_temp = customer.getNext();
			}
		 temp = customer_temp.getBillToAddress();
		 customer = customer_temp;
		 return temp;
	 }

	 
	 
	 /*
	 public void loadCustomerDatabase() {


	     customerList.clear();

		 
		    try {
	            // URL of the resource to fetch
	            URL url = new URL("https://lockwind.com/test/JAVAPOS_API_GET_CUSTOMER_DATABASE.php");

	            // Open connection to the URL
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setDoOutput(true);

	            // Add POST parameters
	            String urlParameters = "client_id=" + URLEncoder.encode("27", "UTF-8");

	            // Send POST request
	            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
	            out.writeBytes(urlParameters);
	            out.flush();
	            out.close();

	            // Check for successful response
	            int responseCode = connection.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {

	                // Read the response from the input stream
	                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                String line;
	                List<Customer> customers = new ArrayList<>();

	                // Parse each line
	                while ((line = in.readLine()) != null) {
	                    StringTokenizer str = new StringTokenizer(line, ",");
	                    
	                    if (str.hasMoreTokens()) {
		                    customer = null;
	                        Customer customer = new Customer();
	                        customer.setBillToCode(str.nextToken());
	                        System.out.println(customer.getBillToCode());
	                        
	                        customer.setBillToName(str.nextToken());
	                        System.out.println(customer.getBillToName());
	                        
	                        customer.setBillToPhoneNumber(str.nextToken());
	                        System.out.println(customer.getBillToPhoneNumber());
	                        
	                        customer.setBillToEmailAddress(str.nextToken());
	                        System.out.println(customer.getBillToEmailAddress());
	                        
	                        customer.setBillToAddress(str.nextToken());
	                        System.out.println(customer.getBillToAddress());

	                        customer.setBillToCity(str.nextToken());
	                        System.out.println(customer.getBillToCity());
	                        
	                        customer.setBillToState(str.nextToken());
	                        System.out.println(customer.getBillToState());
	                        
	                        customer.setBillToZipcode(str.nextToken());
	                        System.out.println(customer.getBillToZipcode());
	                        
	                        customer.setBillToCountry(str.nextToken());
	                        System.out.println(customer.getBillToCountry());

	                        
	                        this.add(customer);
	                        System.out.println(customer.toString());
	                        // Optionally, print the customer object
	                        
	                    }
	                }

	                // Close the input stream
	                in.close();

	                // Output the number of customers processed
	                System.out.println("Processed " + customers.size() + " customers.");
	            } else {
	                System.out.println("POST request failed with response code: " + responseCode);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		 
		 */
		 
		 
		 

	 
	 public void loadCustomerDatabase()
	 {
	     customerList.clear();
	     
	     System.out.println("CustomerManagementSystem->loadCustomerDatabase()");
	     System.out.println("Present Working Directory:" + System.getProperty("user.dir"));

	     try{
	         line = "";

	         str = null;
	         
	         
	         file = new File("./NewCustomer.csv");
	         inputFile = new Scanner(file);
	         customer = null;
	         
	         System.out.println("Entering loop");

	         while(inputFile.hasNextLine()){
		         line = inputFile.nextLine();
	             str =  new StringTokenizer(line,",");
	             
	             System.out.println(line);
	             String x = "";
	             
	             if(str.hasMoreTokens())
	             {
	                 customer = new Customer();
	                 
	                 x = str.nextToken();
	                 customer.setBillToCode( x );
	                 customer.setShipToCode( x );
	                 
	                 x = str.nextToken();
	                 customer.setBillToName(x);
	                 customer.setShipToName(x);

	                 x = str.nextToken();
	                 customer.setBillToPhoneNumber( x );
	                 customer.setShipToPhoneNumber( x );
	                 
	                 x = str.nextToken();
	                 customer.setBillToEmailAddress( x );
	                 customer.setShipToEmailAddress( x );

	                 x = str.nextToken();
	                 customer.setBillToAddress( x );
	                 customer.setShipToAddress( x );

	                 x = str.nextToken();
	                 customer.setBillToCity( x );
	                 customer.setShipToCity( x );

	                 x = str.nextToken();
	                 customer.setBillToState( x );
	                 customer.setShipToState( x );
	                 
	                 x = str.nextToken();
	                 customer.setBillToZipcode( x );
	                 customer.setShipToZipcode( x );
	                 
	                 x = str.nextToken();
	                 customer.setBillToCountry( x );
	                 customer.setShipToCountry( x );
	                 
	                 this.add(customer);
	                 // System.out.println(customer.toString());
	                 
	             }
	         }
	     }catch(IOException e) {
	    	 System.out.println(e.toString() + " NewCustomer.csv" );
	     }
	         inputFile.close();
	             
	 
	 }
	    
	     
	 public void add(Customer c)
	 {
	     customerList.add(c);
	 }
	 
	
	 
	 
	 
	 
	 
	 
	 public void printCustomerList()
	 {
	     
	     for(int i = 0; i < customerList.size(); i++) {
	    	 
	          System.out.print(customerList.get(i).getBillToName());
	          System.out.print(" ");
			  System.out.print(customerList.get(i).getBillToAddress());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getBillToCity());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getBillToState());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getBillToZipcode());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getBillToCountry());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getBillToEmailAddress());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getBillToPhoneNumber());

	          System.out.print(customerList.get(i).getShipToName());
	          System.out.print(" ");
			  System.out.print(customerList.get(i).getShipToAddress());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getShipToCity());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getShipToState());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getShipToZipcode());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getShipToCountry());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getShipToEmailAddress());
		      System.out.print(" ");
			  System.out.print(customerList.get(i).getShipToPhoneNumber());
			  System.out.println(" ");

	     }
	 }
	 
	 public String[] printComboBoxList(){

	     String[] list = new String[customerList.size()];
	     
	     for(int i = 0; i < customerList.size(); i++) {
	             list[i] = customerList.get(i).getBillToCode();
	             
	     }
	     return list;
	 }
	 public String getCustomerBillToName(String customerBillToCode)
	 {
		 String temp = "";
		 
		 String[] list = new String[customerList.size()];
	     
		 int i = 0;
	     while( i < customerList.size() ) {
	    	 
	    	 // Run the loop and if the customer bill to code is the same as the customer code provided then provide the customer bill to name.
	    	 // complete this code as of 9 14 2022
	    	 if(customerList.get(i).getBillToCode().equalsIgnoreCase(customerBillToCode))
	    	 {
	    		 temp = customerList.get(i).getBillToName();
	    	 }
	             list[i] = customerList.get(i).getBillToCode();
	             i++;
	     }
	     return temp;
	 }
	 
	 public static void main(String[] args)
	 {
		 CustomerManagementSystem test = new CustomerManagementSystem();
		 
		 test.loadCustomerDatabase();
		 
		 System.out.println( " ************************ ");
/*		 Customer temp = test.getCustomerTarget("ALITA");
		 
		 System.out.println ( temp.getBillToName() );
		 System.out.println ( temp.getBillToCode() );
		 System.out.println ( temp.getBillToAddress() );
		 System.out.println ( temp.getBillToCity() );
		 System.out.println ( temp.getBillToState() );
		 System.out.println ( temp.getBillToZipcode() );
		 System.out.println ( temp.getBillToCountry() );
		 System.out.println ( temp.getBillToPhoneNumber() );
		 System.out.println ( temp.getBillToEmailAddress() );
	 
		 // test.printCustomerList();
		 
		  * */
		 test.printCustomerList();
		System.out.println("CustomerManagementSystem -> Completed Action -> Load Customer Database");
		 System.out.println( " ************************ ");

		 System.exit(0);
	 }
	 
	}