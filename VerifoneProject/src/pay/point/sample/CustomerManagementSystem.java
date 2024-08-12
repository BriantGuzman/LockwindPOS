package pay.point.sample;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

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
		 
		boolean found = false;
		int i = 0;
		
		Customer customer_temp = null;
		// customer_temp = new Customer("Generic");
		customer_temp = new Customer(customer_code);
		try { 

			}catch(Exception e) {
		
				System.out.println("Error at CustomerManagementSystem.getCustomerTarget ");
		
			}
		return customer;
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

	 
	 public void loadCustomerDatabase()
	 {
	     customerList.clear();
	     
	     System.out.println("CustomerManagementSystem->loadCustomerDatabase()");
	     System.out.println("Present Working Directory:" + System.getProperty("user.dir"));

	     try{
	         line = "";

	         str = null;
	         
	         
	         file = new File("NewCustomer.csv");
	         inputFile = new Scanner(file);
	         customer = null;
	         
	         System.out.println("Entering loop");

	         while(inputFile.hasNextLine()){
		         line = inputFile.nextLine();
	             str =  new StringTokenizer(line,",");
	             
	             System.out.println(line);
	             
	             if(str.hasMoreTokens())
	             {
	                 customer = new Customer();
	                 customer.setBillToCode( str.nextToken() );
	                 customer.setBillToName(str.nextToken() );
	                 customer.setBillToPhoneNumber( str.nextToken() );
	                 customer.setBillToEmailAddress( str.nextToken() );
	                 customer.setBillToAddress( str.nextToken() );
	                 customer.setBillToCity( str.nextToken() );
	                 customer.setBillToState( str.nextToken() );
	                 customer.setBillToZipcode( str.nextToken() );
	                 customer.setBillToCountry( str.nextToken() );
	                 
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
	         System.out.println(customerList.get(i).getBillToName());
			  System.out.println(customerList.get(i).getBillToAddress());
			  System.out.println(customerList.get(i).getBillToCity());
			  System.out.println(customerList.get(i).getBillToState());
			  System.out.println(customerList.get(i).getBillToZipcode());
			  System.out.println(customerList.get(i).getBillToCountry());

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
		 test.printCustomerList();
		 System.out.println("Finished printing customer database");
		 System.exit(0);
	 }
	 
	}