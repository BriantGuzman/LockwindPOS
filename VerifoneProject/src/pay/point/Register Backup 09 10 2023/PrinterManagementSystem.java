
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.print.*;

public class PrinterManagementSystem {
	 
	 
	 List<Printer> printerList = new ArrayList<Printer>();
	 
	 private File file;
	 private Scanner inputFile;
	 private String line;
	 private StringTokenizer str;
	 
	 private Customer customer;
	 
	 
	 public PrinterManagementSystem() {
		 inputFile = null;
		 line = "";
		 file = null;
		 customer = null;
		 
	 }
	 public PrinterManagementSystem(Printer head){
	     printerList.add(head);
	 }
	 
	 public void loadPrinterDatabase()
	 {
	     printerList.clear();
	     
	     System.out.println("PrinterManagementSystem->loadPrinterDatabase()");

	     PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
	     System.out.println("Number of print services: " + printServices.length);

	     Printer temp = new Printer();
	     
	     for (PrintService printer : printServices) {
	    	 
		       System.out.println("Printer: " + printer.getName());
		       temp = new Printer("","","",printer.getName() );
		       add(temp );
	     }
	     
	     
	     
	     /*
	     try{
	         line = "";

	         str = null;
	         
	         file = new File("src/main/java/lockwind/com/NewCustomer.csv");
	         inputFile = new Scanner(file);
	         customer = null;
	         
	         System.out.println("Entering loop");

	         while(inputFile.hasNextLine()){
		         line = inputFile.nextLine();
	             str =  new StringTokenizer(line,",");
	             
	             if(str.hasMoreTokens())
	             {
	                 customer = new Customer();
	                 customer.setCustomerCode( str.nextToken() );
	                 customer.setCustomerName(str.nextToken() );
	                 customer.setCustomerPhoneNumber( str.nextToken() );
	                 customer.setCustomerEmail( str.nextToken() );
	                 add(customer);
	                 System.out.println(customer.toString());
	                 
	             }
	         }
	     }catch(IOException e) {
	    	 System.out.println(e.toString() );
	     }
         inputFile.close();
	             
	 */
	 }
	     
	     
	 public void add(Printer c)
	 {
	     printerList.add(c);
	 }
	 
	
	 
	 
	 
	 
	 
	 
	 public void printPrinterList()
	 {
	     
	     for(int i = 0; i < printerList.size(); i++) {
	         System.out.println(printerList.get(i).getPrinterName());
	     }
	 }
	 
	 public String[] printComboBoxList(){

	     String[] list = new String[printerList.size()];
	     
	     for(int i = 0; i < printerList.size(); i++) {
	             list[i] = printerList.get(i).getPrinterName();
	     }
	     return list;
	 }
	 
	 public static void main(String[] args)
	 {
		 PrinterManagementSystem test = new PrinterManagementSystem();
		 
		 test.loadPrinterDatabase();
		 test.printPrinterList();
		 System.out.println("Finished printing Printer database");
		 System.exit(0);
	 }
	 
	}