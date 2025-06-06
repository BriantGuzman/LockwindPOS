//Last Update: 4/25/2025

package pay.point.sample;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.*;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.border.CompoundBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;

import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import javax.swing.table.DefaultTableModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.table.*;
import javax.swing.Timer;

import java.time.format.DateTimeFormatter;

import static java.text.DateFormat.FULL;
import static java.text.DateFormat.LONG;
import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.SHORT;
import static java.util.Locale.FRANCE;
import static java.util.Locale.GERMANY;
import static java.util.Locale.UK;
import static java.util.Locale.US;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.URI;
import javax.print.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;

// Classes below are required for integration with Verifone Payment Terminal, simplify later to remove duplicates.
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

// import jakarta.xml.bind.DatatypeConverter;	
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
 import javax.xml.bind.DatatypeConverter;
 import java.lang.reflect.Method;



//---------------------------------------------------- CLASS REGISTER : THIS IS THE MAIN CLASS
public class Register  extends JFrame implements ActionListener,FocusListener {

	  // private RegisterVerifone 	register_verifone;
	  private APIVerifone 								verifone;
	  private Invoice 								 	invoice;

	  String invoice_directory 					= 		"./target/classes/lockwind/com/outbound_invoice/";
      String invoice_file_extension_txt 		= 		".txt";

	  private Locale[] locale        			=       {US,UK,GERMANY,FRANCE}  ;
	  private int[]    styles        			=       {FULL,LONG,MEDIUM,SHORT};
	  
	  
      private String[] paymentMethods 			= 		new String[] {"CASH","CREDIT CARD/APPLE PAY","ON ACCOUNT","CHECK"};
      private String transaction_payment_method = 		"";
	  
	  private String[] column_header 			= 		{"UPC","QTY","CATEGORY","DESCRIPTION","PRICE","SUBTOTAL","TAX","DISCOUNT","ONHAND","LINE ITEM"};
	  private int[]    column_width  			= 		{135,55,200,100,55,85,55,75,75,75};

      private String[] electronic_document_transaction_type = new String[] { "RECEIPT", "INVOICE","CREDIT NOTE","DEBIT NOTE","PAYMENT RECEIPT","ASN","CONFIRM RECEIPT","PURCHASE ORDER", "PURCHASE ORDER ACK","TEXT MESSAGE"}; // Add on 9/6/23 to allow the system to store the transaction type of a given electronic document and transfer it out once tender is completed.
	  private JComboBox<String> electronic_document_transaction_type_selected; // Added on 9/6/23 to allow the customer to select a transaction type

	  private int i, j, row, col;
//	  private int invoiceNumber;
	  private double tendered, subtotal, totaltaxes, change, discount, transaction_tender_amount;

	  private double TAX_RATE 					= 		0.08975; // This is only temporary, it must be fixed.
	  private int table_row_count				= 		40;
	  private int table_col_count				= 		10;

	  private JComboBox<String> 						payment_method;
	  private JComboBox<String> 						default_printer_receipt; // Added 4/23/2022 for printing simplicity
	  private JComboBox<String> 						default_printer_invoice; // Added 4/23/2022 for printing simplicity
	  private JComboBox<String> 						default_printer_display; // Added 4/23/2022 for printing simplicity
	  
	  private JLabel 						 			default_printer_receipt_label;		 // Added 4/23/2022 for printing simplicity
	  private JLabel 						 			default_printer_invoice_label;		 // Added 4/23/2022 for printing simplicity
	  private JLabel 						 			default_printer_display_label;		 // Added 4/23/2022 for printing simplicity

	  private JTextArea  						 		addenda;
	  private JMenuBar 						 			menuBar;
	  
	  // Program Specific Components
	  private Internet 						 			internet;
	  private ERP 						 				erp;
	  private ERP 						 				program;
	  private Date 						 				today;
	  private String 						 			client_id, client_name;
	  private API 						 				http;
	  
	  // Variables for transaction calculations
	  private String 									url_response, error_message;

	  // ENGINES
	  private ProductManagementSystem 	product_management_system;
	  private InvoiceManagementSystem 	invoice_management_system;
	  private CustomerManagementSystem 	customer_management_system;
	  private PrinterManagementSystem 	printer_management_system;

	  private ValidationPlatform validator;
	  private QTY inventory_manager;

	  private TableManager table_manager;
	  private FormatManager format_manager;
	  private NumberFormat formatter;
	  private SimpleDateFormat simpDate;
	  private DateFormat fmt;

	  private FileWriter file;
	  private PrintWriter outputFile;
	  
	// UI Specific components
	  
	  private Dimension screenSize;
	  private double width;
	  private double height;
	  private int w;
	  private int h;
	  
	  private SpringLayout springLayout;
	  private SpringLayout panelLayout;
	  private SpringLayout layout;

	  private JFrame  frame;
	  private JPanel  panel;
	  private JPanel  tablePanel;
	  private JPanel  bottomPanel;

	  private DefaultTableModel model;
	  private JTable  table;
      private JScrollPane table_scroll_pane;
      private JTable  rowTable ;
      
      // UI LABELS FOR TRANSACTION HEADER DATA
	  private JLabel  internetLabel;
	  private JLabel  verifoneLabel;
	  private JLabel  timeLabel;
	  private JLabel  invoiceNumberLabel;
	  private JLabel  subtotalLabel;
	  private JLabel  taxesLabel;
	  private JLabel  discountLabel;
	  private JLabel  totalLabel;
	  private JLabel  tenderLabel;
	  private JLabel  changeLabel;
	  private JLabel  invoiceNumberLabelDescription;
	  private JLabel  subtotalLabelDescription;
	  private JLabel  taxesLabelDescription;
	  private JLabel  discountLabelDescription;
	  private JLabel  totalLabelDescription;
	  private JLabel  tenderLabelDescription;
	  private JLabel  changeLabelDescription;
	  private JLabel  retailerUUIDDescription;
	  private JLabel  account_name_label;
	  private JLabel  storeName;
	  private JLabel  storePhoneNumber;
	  private JLabel  storeFaxNumber;
	  private JLabel  label;
	  private JLabel  label_addenda; // added on 4/9/2022
	  
      // UI LABELS FOR ELECTRONIC DOCUMENT TRANSACTION DATA - BILLTO
	  private JLabel label_bill_to_customer_name;
	  private JLabel label_bill_to_customer_name_data;
	  private JLabel label_bill_to_customer_address;
	  private JLabel label_bill_to_customer_address_data;
	  private JLabel label_bill_to_customer_city;
	  private JLabel label_bill_to_customer_city_data;
	  private JLabel label_bill_to_customer_state;
	  private JLabel label_bill_to_customer_state_data;
	  private JLabel label_bill_to_customer_zipcode;
	  private JLabel label_bill_to_customer_zipcode_data;
	  private JLabel label_bill_to_customer_country;
	  private JLabel label_bill_to_customer_country_data;	  
	  private JLabel label_bill_to_customer_phone_number;
	  private JLabel label_bill_to_customer_phone_number_data;
	  private JLabel label_bill_to_customer_email_address;
	  private JLabel label_bill_to_customer_email_address_data;

      // UI LABELS FOR ELECTRONIC DOCUMENT TRANSACTION DATA - SHIPTO
	  private JLabel label_ship_to_customer_name;
	  private JLabel label_ship_to_customer_name_data;
	  private JLabel label_ship_to_customer_address;
	  private JLabel label_ship_to_customer_address_data;
	  private JLabel label_ship_to_customer_city;
	  private JLabel label_ship_to_customer_city_data;
	  private JLabel label_ship_to_customer_state;
	  private JLabel label_ship_to_customer_state_data;
	  private JLabel label_ship_to_customer_zipcode;
	  private JLabel label_ship_to_customer_zipcode_data;
	  private JLabel label_ship_to_customer_country;
	  private JLabel label_ship_to_customer_country_data;
	  private JLabel label_ship_to_customer_phone_number;
	  private JLabel label_ship_to_customer_phone_number_data;
	  private JLabel label_ship_to_customer_email_address;
	  private JLabel label_ship_to_customer_email_address_data;

	  // UI BUTTONS
	  private JButton button_tender;
	  private JButton button_tender_digital;
	  private JButton button_tender_on_account;
	  private JButton b,c,d,e,f,g,z,zz;
	  private JButton bx001; // customers
	  private JButton bx002; // suppliers
	  private JButton bx003; // update
	  private JButton bx004; // price_change
	  private JButton bx005; // new_stock
	  private JButton bx006; // inventory
	  private JButton bx007; // Verifone Manager
//	  private JButton bx008; // Verifone Manager
	  private JButton pim_button;
	  private JButton salesReport;
	  
	  private JComboBox<String> transaction_type;
	  private JComboBox<String> transaction_type_value;
	  private JComboBox<String> account_name_input;
	  
	  private JTextField tender_amount;

	  // User Specific Components
	  private String customer_selected; // This is used as a variable that is used to select the customer from the account name combobox for fulfillment. 
	  private String retailerUUID;
	  private String posUUID;
	  private String da;
	  private int index;
	  private Ali ali;	  

	  private ElectronicDocument record = null;
	  private ElectronicDocumentLineItemManager line_item_manager;
	  private ElectronicDocumentLineItem line_item;
	  
	  private double total;
	  
	  private Font 		font1,font2,font3;
      private Timer 	timer;

      private String 	account_selected;
      private String 	response;
      private int 		in;
      private String 	productName;
      private String 	store_print_name;
      private int 		item_count;

      
	  // ADVANCED COMMUNICATIONS & SIGNALS
	  private Document POSStatus;
	  private Element statusElement;
	
	  private String 	mac_label = "Register1"; // This is the name of the point of sale that is identifiable by Verifone.
	  private boolean 	registerStatus;
	  private boolean 	sessionInProgress;
	  private String 	address;
	  private int 		port;
	  private int 		secondary_port;
	  private int 		invoice_number;
	  KeyFactory	 	kf;
	  PrivateKey 		privateKey;
	  PublicKey 		publicKey;
	  KeyPair 			keypair; 
		
	  private String macLabel = "";
	  private byte[] macKey = new byte[0];
	  private int counter = 0;
		
	  Random generator = null;
	  String entryCode = "";
		

	  private static final String PUBLIC_KEY = 
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAugflCYHdNLX1PK+2JpedLoL4JdwpapkpwoSIYOhBF9FFri+roRTqPyyosLFGMMnO5l65z9YY1cQYSENWfhLvPROD2Oruyl1k2wSYWT+23wTB0jJjA4ktk7Q2cErNzMNiLLP0tB3rOYJHxC1HjskKBmkblF5ZDeCNzVyeEdF37zfCDD5bBIjPSpmLgH1swDQIvpULhwhmyf1AaJX+oaaCQgu6wxrbP17auMJzAjhddwUgIbkCiAEcYu8fwyTXQWFcQtfA3nufCITAcI7jmtxrXKqKWgZ23oIgvmIM1y9l6Bp9QT8MvDn63wfj54fyOW5Jb66G19x/xVGF5lH68qPErwIDAQAB";
	  private static final String PRIVATE_KEY = 
				"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6B+UJgd00tfU8r7Yml50ugvgl3ClqmSnChIhg6EEX0UWuL6uhFOo/LKiwsUYwyc7mXrnP1hjVxBhIQ1Z+Eu89E4PY6u7KXWTbBJhZP7bfBMHSMmMDiS2TtDZwSs3Mw2Iss/S0Hes5gkfELUeOyQoGaRuUXlkN4I3NXJ4R0XfvN8IMPlsEiM9KmYuAfWzANAi+lQuHCGbJ/UBolf6hpoJCC7rDGts/Xtq4wnMCOF13BSAhuQKIARxi7x/DJNdBYVxC18Dee58IhMBwjuOa3GtcqopaBnbegiC+YgzXL2XoGn1BPwy8OfrfB+Pnh/I5bklvrobX3H/FUYXmUfryo8SvAgMBAAECggEAMmmxR8JJj98/dhKn6g1sKw6S8K+ZCao4Bt6jlp9aBHpRx8JjYGOqlzQjAr8HpnEKAKPq9seuMz/Q1MRqy/+VlZeUQ1RnIa/thOzZ3FXH2OgRHkVJT8v87eoIVqXu326TTEn4Jld1R0Bm8mLS4X7ZmKMjNjHbMEeKJfzTWUDKn6imPU5/mkJIoCVNi1CM+A8QTxoCFhWzdxHj5GCExAzQJdTFDHLEDygZOjX5iGSRenYYc5dxLutroWc8a9XPuftPIBooPCYAsRbUYUE16bXosQ38aO8lU3E1tyCD0nj0sq1Oiiuw/UJmFizj9pJUzCJpWDgk4wqzOffOf2gI+5rhAQKBgQDfkOUUp0Qzo1h/MJ49BGqSLZZ1pl/UEsGhVeV2RKx7TukOq0Lj9tChAnhouA6GsjR9AG8AWiZCN4ZkMlRVHAAppkk/6WFE4FHkmvWlUmNsNdwY5z4Ww5tggywg4j9WPif5FjWfvq7V/UYvl24mJ1XChAh3HGOX674DPfyLBgvfWwKBgQDVBPeIMs4FtVo4WoIfdx3A2xvbwd9TVsCJiLV5iXcy9ksbPVLif7tO5ZR63GkevVNc4PHWupaqA3f8VCBe3M12e6xdlH59zXPRVubczK8pBqcKt84qj7yk5Y6OgiFQEp/u16G2M7FhagQ14J2N0pGHdYtZmn+A694c2HD98LBkPQKBgQC7Wgiv0zCzeXrrM8oX5kCM+ckyFNgPuBwuYPZns0s8Frf2RA1NTwQtOg2/7Ca4OFUGQDvFdsbDDRcBlq/XlxyHysNt3N1XxAi85CNhhPaus0AcWoVMvGXUbninohJj6rjC5BrSIRERYSvVLDjxnlsfJFiXwOGxaayVuPePZeTDKwKBgQCtX+mHtLHx+3R+wUt/CJfyy2KVLenyDn2OcvIhBT07ATKH7RV0u7lbsYdzp8j29+jNg1fSCPNvVHtnp6DhFJ01fdsAH0gEZB+Llks4Em/N2FhEZO0rvuku3Jd2bXtnjIEXB/HaNaB9RKhAoZwaPfOsaIMOXqy/5TlWCOOOC0PFkQKBgGB7PkgYnGg0R610g0JaXJJ3kaeGnZYJapXgUF6ZDKz6IQI1ufhyQ29tqSxSW2GydLQRFFr4pXEEoYCGGO5KVkP5bGe/3Hyj4O82JiOkwFyVtNp3yjhPFbwlOzgQnAHEbmpidLTBP3THDtqNWGb1U586pNuxDpIMQrGR9Gva0OzK";

	  private ElectronicDocument 	electronic_document;
      private static final int 		PRODUCT_NAME_COLUMN = 3;
      private JOptionPane 			t;

	  private Document 	request;
	  private Element 	docElement;
		
	  private Document 	responseXml;
	  private Element 	responseDocElement;
		
	  private String 	encryptedMACKey;
	  private byte[] 	macKeyBase64Decoded;
	  private Cipher 	cipher;
		
	  private String 	nextCounter;
	  private String 	mac;
		
	  private String 	temp;

	  
	  private JButton saveTable;
	  private JButton loadTable;
	  
	  
	  private static void restartApplication() {
	        try {
	            // Get the current Java runtime
	            String java = System.getProperty("java.home") + "/bin/java";
	            
	            // Get the current classpath
	            String classPath = System.getProperty("java.class.path");
	            
	            // Get the current class name
	            String className = Register.class.getName();
	            
	            // Build the command to restart the application
	            ProcessBuilder processBuilder = new ProcessBuilder(
	                    java, "-cp", classPath, className
	            );
	            
	            // Start the new process
	            processBuilder.inheritIO();
	            Process process = processBuilder.start();
	            
	            // Exit the current process
	            System.exit(0);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  
	  
	  
	  public void printMethods() { 
    	  Class c = java.lang.Thread.class;
//          Method[] methods = c.getMethods();
          Method[] methods2 = c.getDeclaredMethods();
          System.out.println("The public methods of the java.lang.Thread class are: ");
          for (int i = 0; i < methods2.length; i++) {
             System.out.println(methods2[i].getName() );
          }
      }
      
      public void loadShoppingCart() { 
    	  
    	  System.out.println("Loading shopping cart for Register");
    	  
    	  try { 
    	  File 		file 		= new File("shopping_cart.txt");
    	  Scanner 	inputFile 	= new Scanner(file);
    	  String 	line 		= "";
    	  StringTokenizer str 	= null;
    	  
    	  String temp = "";
    	  int i = 0;
    	  int j = 0;
    	  
    	  while(inputFile.hasNextLine()) { 
    		  
    		  line = inputFile.nextLine();
    		  
    		  str = new StringTokenizer(line,",");
    		  
			  for( i = 0; i < table_manager.getColumnCount();i++) {
				  
				  while(str.hasMoreTokens()) { 
					  
					  temp = str.nextToken();
					  
					  table_manager.setData(table, j, i, temp);
	    			  System.out.println( "Loading shopping cart" + temp );
	    	  }
			  }
			  j++;
    	  }
    	  }catch(Exception e) { } 
      }
      
      public void saveShoppingCart( int i ) { 
    	  
    	  System.out.println("Function Action: saveShoppingCart -> row: " + i);
    	  
    	  String[] temp = null;
    	  
    	  temp = new String[table.getColumnCount()*2];
          
    	  j = 0;
    	  
          for(int k = 0; k < table.getColumnCount(); k++) { // Per column  
        	  if(table_manager.getData(table,i,j) == null) { // Column value is null 
        		  temp[k] = "";
        // temp[i] = String.valueOf("");
      	// System.out.println("NULL VALUE FOUND AT TABLE: " + i + "," + j);

        	} else { 
      		
      		temp[k] = table_manager.getData(table,i,j).toString();
      	
      	}
      	j++;
      	System.out.println("LINE ITEM COLUMN VALUE: " + j + " : " + temp[k]);
          }
      }
      
      
	  public void setConstructorValues() {
			// action: organize elements, Initialize components,

		  	invoice                						= null;
		  	font1                						= null;
		  	font2                						= null;
		  	font3                						= null;
		  	
		  	width                						= 0.00;
		    height               						= 0.00;
		    w                       					= 0;
		    h                       					= 0;
		    transaction_tender_amount 					= 0.00;
//		    invoiceNumber           					= 0;

		    screenSize        							= null;
		    internet                    				= null;
		    internetLabel               				= null;	
		    verifoneLabel               				= null;	
		    
		    frame                       				= null;
		    panel                       				= null;
		    tablePanel                  				= null;
		    bottomPanel									= null;
		    model                       				= null;
		    springLayout                				= null;
		  
		    totalLabel                  				= null;
		    taxesLabel                  				= null;
		    discountLabel             				   	= null;
		    storeName            						= null;
		    storePhoneNumber     						= null;
		    storeFaxNumber       						= null;
		    label                						= null;
		    fmt                         				= null;
		    program                     				= null;
		    simpDate   									= null;
		    account_name_input         				 	= null;
		    account_name_label          				= null;
		    bx001                  						= null;
		    bx002                   					= null;
		    bx003                   					= null;
		    bx004                   					= null;
		    bx005                   					= null;
		    bx006                   					= null;
		    bx007                   					= null;
//		    bx008                   					= null;
		    
		    button_tender               			    = null;
		    button_tender_digital               		= null;
		    button_tender_on_account               		= null;
		    z                   						= null;
		    zz                  						= null;
		    pim_button									= null;
		    
		    formatter 				  					= null;
		    fmt 						 			 	= null;
		    inventory_manager 	  	  					= null;
		    table_manager							  	= null;
		    format_manager 			  					= null;
		    ali						  					= null;
		    
		    payment_method								= null;
			transaction_payment_method 					= null;
	    	tender_amount		 						= null;
	    	addenda										= null;
	    	label_addenda								= null;
	    	
	    	label_bill_to_customer_name 				= null;
	    	label_bill_to_customer_name_data 			= null;
	    	
	    	label_ship_to_customer_name 				= null;
	    	label_ship_to_customer_name_data 			= null;

	    	label_bill_to_customer_address 				= null;
	    	label_bill_to_customer_address_data 		= null;
	    	
	    	
	    	label_ship_to_customer_address 				= null;
	    	label_ship_to_customer_address_data 		= null;

	    	label_bill_to_customer_city 				= null;
	    	label_bill_to_customer_city_data 			= null;

	    	label_ship_to_customer_city 				= null;
	    	label_ship_to_customer_city_data			= null;
	    		  
	    	label_bill_to_customer_state 				= null;
	    	label_bill_to_customer_state_data 			= null;
	    	
	    	label_ship_to_customer_state 				= null;
	    	label_ship_to_customer_state_data 			= null;

	    	label_bill_to_customer_zipcode 				= null;
	    	label_bill_to_customer_zipcode_data 		= null;

	    	label_ship_to_customer_zipcode 				= null;
	    	label_ship_to_customer_zipcode_data 		= null;
	    		  
	    	label_bill_to_customer_country 				= null;
	    	label_bill_to_customer_country_data 		= null;

	    	label_ship_to_customer_country 				= null;
	    	label_ship_to_customer_country_data 		= null;
	    		  
	    	label_bill_to_customer_phone_number 		= null;
	    	label_ship_to_customer_phone_number 		= null;

	    	label_bill_to_customer_phone_number_data 	= null;
	    	label_ship_to_customer_phone_number_data	= null;

	    	label_bill_to_customer_email_address 		= null;
	    	label_ship_to_customer_email_address 		= null;

	    	label_bill_to_customer_email_address_data 	= null;
	    	label_ship_to_customer_email_address_data	= null;

	    	
	    	default_printer_receipt_label				= null;
	    	default_printer_invoice_label				= null;
	    	default_printer_display_label				= null;
	    	
	    	transaction_type							= null;
	    	transaction_type_value						= null;
	    	menuBar										= null;

			line_item									= null;
			electronic_document_transaction_type 		= null;
			electronic_document_transaction_type_selected = null;
	  
			verifone 									= null;
			registerStatus 								= false;
			kf  										= null;
			privateKey 									= null;
			publicKey 									= null;
			keypair 									= null;
			
			request 									= null;
			docElement 									= null;
			
			encryptedMACKey 							= "";
			macKeyBase64Decoded 						= null;
			cipher 										= null;

			nextCounter 								= "";
			mac 										= "";
			
			temp 										= "";
			formatter 									= null;
			
			electronic_document							= null;
		    
			account_selected 							= null;
		    t 											= null;
		     
		    response 									= "";
		    in 											= 0;
		    productName 		 						= "";
		    store_print_name 	 						= "";
		    item_count 			 						= 0;
		    
		    saveTable									= null;
		    loadTable									= null; 
			error_message 								= "";			
		    
//		    register_verifone							= null;

	  }
	  
	  public void setComponentDefaultValues() {
		  
		  	// REGISTER SET COMPONENT DEFAULT VALUES

//		  	register_verifone							= new RegisterVerifone();
//		    verifone									= new APIVerifone();

		  
		  	// -> LoadRetailerUUID();
		  	// -> posUUID(); // -> This value should be stored locally on the machine
		  
		  	retailerUUID                   				= "5d4de950d4f69";
		    posUUID                   					= "65cbcf67af4bb65cbcf67af508";
			client_id                   				= "5d4de950d4f69";
			
		    http                        				= new API();
			validator 									= new ValidationPlatform();
			product_management_system   				= new ProductManagementSystem();
		    invoice_management_system   				= new InvoiceManagementSystem();
		    customer_management_system 					= new CustomerManagementSystem();
		    printer_management_system 					= new PrinterManagementSystem();
		    inventory_manager 		  					= new QTY();
		    ali											= new Ali();
		    internet                    				= new Internet();
		    
			erp                       					= new ERP();
//			record 										= new ElectronicDocument();
		  	
		  	//		    model                       				= new DefaultTableModel(table_row_count,table_col_count);
		    model 										= new DefaultTableModel(table_row_count, table_col_count) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make the cell in the third column (index 2) not editable
                    return column != 2;
                }
            };

		    table_manager				    			= new TableManager(column_header,column_width);
		    format_manager								= new FormatManager();
		    formatter 									= new DecimalFormat("#0.00");
		    simpDate                    				= new SimpleDateFormat("hh:mm:ss a");
		    font1										= new Font("Times New Roman", Font.BOLD, 15);
			font2										= new Font("Tahoma",Font.BOLD,12);
		    font3										= new Font("Times New Roman", Font.BOLD, 20);
		    
		    table                       				= new JTable(model);
		    today                       				= new Date();
		    
		  	frame                       				= new JFrame();
		  	panel                       				= new JPanel();
		  	tablePanel                 		 			= new JPanel();			
		  	bottomPanel									= new JPanel();
		  	
		    timeLabel                   				= new JLabel("");
		  	internetLabel              					= new JLabel("");
		  	verifoneLabel              					= new JLabel("");
		    subtotalLabel            		  		 	= new JLabel("");
		    taxesLabel                			  		= new JLabel("");
		    totalLabel               		  	 		= new JLabel("");
		    tenderLabel                 				= new JLabel("");
		    changeLabel                 				= new JLabel("");
		    discountLabel                 				= new JLabel("");
		    invoiceNumberLabel            				= new JLabel("");
		    invoiceNumberLabelDescription 				= new JLabel("");  
		    retailerUUIDDescription 					= new JLabel("");
		    subtotalLabelDescription    				= new JLabel("");
		    taxesLabelDescription       				= new JLabel("");
		    totalLabelDescription       				= new JLabel("");
		    tenderLabelDescription      				= new JLabel("");
		    changeLabelDescription      				= new JLabel("");
		    discountLabelDescription	  				= new JLabel("");
		    account_name_label          				= new JLabel("");
		    storeName									= new JLabel("");
			storePhoneNumber							= new JLabel("");
			storeFaxNumber								= new JLabel("");
			label_addenda								= new JLabel("");
			
			default_printer_receipt_label				= new JLabel("");
			default_printer_invoice_label				= new JLabel("");
			default_printer_display_label				= new JLabel("");
			
			label_bill_to_customer_name 				= new JLabel("");
			label_bill_to_customer_name_data 			= new JLabel("");
			label_bill_to_customer_address 				= new JLabel("");
			label_bill_to_customer_address_data			= new JLabel("");
			label_bill_to_customer_city 				= new JLabel("");
			label_bill_to_customer_city_data			= new JLabel("");
			label_bill_to_customer_state 	    		= new JLabel("");
			label_bill_to_customer_state_data 	    	= new JLabel("");
			label_bill_to_customer_zipcode 				= new JLabel("");
			label_bill_to_customer_zipcode_data 		= new JLabel("");
			label_bill_to_customer_country				= new JLabel("");
			label_bill_to_customer_country_data			= new JLabel("");
			label_bill_to_customer_phone_number 		= new JLabel("");
			label_bill_to_customer_phone_number_data 	= new JLabel("");
			label_bill_to_customer_email_address 		= new JLabel("");
			label_bill_to_customer_email_address_data 	= new JLabel("");

			label_ship_to_customer_name 				= new JLabel("");
			label_ship_to_customer_name_data 			= new JLabel("");
			label_ship_to_customer_address 				= new JLabel("");
			label_ship_to_customer_address_data			= new JLabel("");
			label_ship_to_customer_city 				= new JLabel("");
			label_ship_to_customer_city_data			= new JLabel("");
			label_ship_to_customer_state 	    		= new JLabel("");
			label_ship_to_customer_state_data 	    	= new JLabel("");
			label_ship_to_customer_zipcode 				= new JLabel("");
			label_ship_to_customer_zipcode_data 		= new JLabel("");
			label_ship_to_customer_country				= new JLabel("");
			label_ship_to_customer_country_data			= new JLabel("");
			label_ship_to_customer_phone_number 		= new JLabel("");
			label_ship_to_customer_phone_number_data 	= new JLabel("");
			label_ship_to_customer_email_address 		= new JLabel("");
			label_ship_to_customer_email_address_data 	= new JLabel("");
			
		    salesReport				    				= new JButton("");  
		    bx001                          				= new JButton("");
		    bx002                          				= new JButton("");
		    bx003                          				= new JButton("");
		    bx004                          				= new JButton("");
		    bx005                          				= new JButton("");
		    bx006                          				= new JButton("");
		    bx007                          				= new JButton("");
//		    bx008                          				= new JButton("");
		    saveTable									= new JButton("Save Invoice");
			loadTable									= new JButton("Load Invoice");

		    button_tender              				 	= new JButton("");
		    button_tender_digital              			= new JButton("");
		    button_tender_on_account              			= new JButton("");
  		    pim_button				  				 	= new JButton("");
		    
		    springLayout                				= new SpringLayout();
		    panelLayout    								= new SpringLayout();
		    layout                      				= new SpringLayout();
		    
		    tender_amount								= new JTextField(10);
		    addenda										= new JTextArea(4,3);
		    
			customer_management_system					. loadCustomerDatabase();
		    printer_management_system					. loadPrinterDatabase();
		    account_name_input  						= new JComboBox<String>( customer_management_system.printComboBoxList() );
		   
		    
		    default_printer_receipt						= new JComboBox<String>( printer_management_system.printComboBoxList() );
		    default_printer_invoice						= new JComboBox<String>( printer_management_system.printComboBoxList() );
		    default_printer_display						= new JComboBox<String>( printer_management_system.printComboBoxList() );
		    payment_method 								= new JComboBox(paymentMethods);

		    table										. setRowHeight(25);
		    table										. setCellSelectionEnabled(true);
		    table										. setColumnSelectionInterval(0,0);
		    table										. setRowSelectionInterval(0,0);
		    validator									. setRowCount(table_row_count);
			validator									. setColumnCount(table_col_count);

			line_item 								= new ElectronicDocumentLineItem();

			
			// Compile time errands
			System.out.println("Compile time functions being loaded");


			invoice										= new Invoice( retailerUUID,posUUID,client_id );
						
			System.out.println("SetComponentDefaultValues: -> retailer UUID" + retailerUUID);
			System.out.println("SetComponentDefaultValues: -> pos UUID" + posUUID);
			System.out.println("SetComponentDefaultValues: -> client ID" + client_id);
			
			
			invoice										. setInvoiceDefaultValues();
			
			 if(invoice.getInvoiceNumber().equalsIgnoreCase("-401") ) {
				 
			 }else {
					invoice. setTransactionUUID( invoice.getIssuerUUID() );			
			        System.out.println("Register -> Invoice Number: " + invoice.getInvoiceNumber() + " -> UUID: " + invoice.getTransactionUUID() );
			 }

	        
	        
			product_management_system	  				. setRetailerUUID( invoice.getIssuerUUID() );
		    invoice_management_system	  				. setRetailerUUID( invoice.getIssuerUUID() );
	        invoice_management_system					. setConsumerUUID( invoice.getConsumerUUID() );

  		    this.setUIDefaultValues();
			this.setComponentTextValues();
			this.setComponentFontValues();
			this.setColorScheme();
			this.setComponentDimensionValues();
		    this.setActionListener();
		    this.setVerifoneDefaultValues();
	  }
	  
	  public void setUIDefaultValues() {
			fmt                         				= DateFormat.getDateInstance(styles[3], locale[0]);
			screenSize                  				= Toolkit.getDefaultToolkit().getScreenSize();
		    width                       				= screenSize.getWidth();
		    height                      				= screenSize.getHeight();
		    w                           				= (int)(width);
		    h                           				= (int)(height);
		    
		  	frame										. setTitle("TRANSACTIONS PANEL");
		  	account_name_input							. setName("account_name_input");
		  	
		  	account_name_input							. addActionListener(this);
		  	account_name_input							. setEditable(true);
		    fmt                         				= DateFormat.getDateInstance(styles[3], locale[0]);
			
		  	frame										. setLayout(springLayout);
		  	panel										. setLayout(panelLayout);
		  	tablePanel									. setLayout(new BorderLayout());
		    bottomPanel									. setLayout(panelLayout);

	  }
	  public String getVerifoneDeviceAddress(String pos_uuid) {
		  
		  String temp = "";
		  try {
		  temp = http.getVerifoneDeviceAddress(posUUID);
		  }catch(Exception e) {
			  
			  e.printStackTrace();
		  }
		  return temp;
		  
	  }
	  public void setVerifoneDefaultValues() {
		  	address										= getVerifoneDeviceAddress(posUUID);
		  	
//		   	address 									= "192.168.50.198"; // Update this to be set depending on the POS network and verifone IP address per client.
 		    port 										= 5015;
 		    secondary_port 								= 5016;
 		
 		    generator 									= new Random();
 			entryCode 									= String.valueOf(generator.nextInt(9999));
 		
 			try { 
 			
 			kf 											= KeyFactory.getInstance("RSA");

 			// This statement requires the java.xml.bind jar package
 			privateKey									= kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(PRIVATE_KEY)));
 			publicKey 									= kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(PUBLIC_KEY)));
 			keypair 									= new KeyPair(publicKey, privateKey);

 		}catch(Exception e) { }
 
	  }
	  
	
	  
	  public void setComponentTextValues() {
		    bx001										. setName("customers");
		    bx002										. setName("suppliers");
		    bx003										. setName("update");
		    bx004										. setName("price_change");
		    bx005										. setName("new_stock");
		    bx006										. setName("inventory");
		    bx007										. setName("verifone_manager");
//		    bx008										. setName("extra_button");
		    saveTable									. setName("save_table");
		    loadTable									. setName("load_table");
		    
		    button_tender								. setName("tender");
		    button_tender_digital						. setName("tender_digital");
		    button_tender_on_account					. setName("tender_on_account");
		    
			pim_button									. setName("pim_button");
			salesReport									. setName("sales_report_button");
			payment_method								. setName("payment_method");
			tender_amount								. setName("tender_amount");
			addenda										. setName("addenda ");
			label_addenda								. setName("Label Addenda ");
			
			
		  	bx001										. setText("Customers"); // 1st Column of buttons, 1st from the top
			bx002										. setText("Suppliers"); // 1st Column of buttons, 2nd from the top
			bx003										. setText("Update"); // 1st Column of buttons, 3rd from the top
			bx004										. setText("Price Change"); // 2nd Column of buttons, 1nd from the top
			bx005										. setText("New Stock"); //  2nd Column of buttons, 2nd from the top
			bx006										. setText("Add Inventory"); // 2nd Column of buttons, 3rd from the top
			bx007										. setText("Verifone Manager"); // 2nd Column of buttons, 3rd from the top
			// bx008										. setText("Extra button"); // 2nd Column of buttons, 3rd from the top
			saveTable									. setText("Park");
			loadTable									. setText("Load");
			
			
			salesReport									. setText("Sales Report"); // 3rd Column of buttons, 2nd from the top 
			button_tender								. setText("Cash"); // 3rd Column of buttons, 1st from the top
			button_tender_digital						. setText("CC/DC"); // 3rd Column of buttons, 1st from the top
			button_tender_on_account					. setText("On Account"); // 3rd Column of buttons, 1st from the top

			pim_button									. setText("PIM"); // 3rd Column of buttons, 3rd from the top
		    account_name_label							. setText("Customer");		    
		    subtotalLabelDescription					. setText("Sub-Total:");
			taxesLabelDescription						. setText("Tax:");
			totalLabelDescription						. setText("Total:");
			tenderLabelDescription						. setText("Tender:");
			changeLabelDescription						. setText("Change:");
			discountLabelDescription					. setText("Discount:");
			subtotalLabel								. setText("$ 0.00");
			taxesLabel									. setText("$ 0.00");
			totalLabel									. setText("$ 0.00");
			tenderLabel									. setText("$ 0.00");
			changeLabel									. setText("$ 0.00");
			discountLabel								. setText("$ 0.00");
			label_addenda								. setText("Addenda");
			default_printer_receipt_label				. setText("Receipt: ");
			default_printer_invoice_label				. setText("Invoice: ");
			default_printer_display_label				. setText("Display: ");
			
			label_bill_to_customer_name					. setText( invoice.getBillToCustomerNameLabel() );
			label_bill_to_customer_name_data			. setText( invoice.getBillToCustomerNameData() );
			label_bill_to_customer_address				. setText( invoice.getBillToCustomerAddressLabel() );
			label_bill_to_customer_address_data			. setText( invoice.getBillToCustomerAddressData() );
			label_bill_to_customer_city					. setText( invoice.getBillToCustomerCityLabel() );
			label_bill_to_customer_city_data			. setText( invoice.getBillToCustomerCityData() );
			label_bill_to_customer_state				. setText( invoice.getBillToCustomerStateLabel() );
			label_bill_to_customer_state_data			. setText( invoice.getBillToCustomerStateData() );
			label_bill_to_customer_zipcode				. setText( invoice.getBillToCustomerZipcodeLabel() );
			label_bill_to_customer_zipcode_data			. setText( invoice.getBillToCustomerZipcodeData() );
			label_bill_to_customer_country				. setText( invoice.getBillToCustomerCountryLabel() );
			label_bill_to_customer_country_data			. setText( invoice.getBillToCustomerCountryData() );
			label_bill_to_customer_phone_number			. setText( invoice.getBillToCustomerPhoneNumberLabel() );
			label_bill_to_customer_phone_number_data	. setText( invoice.getBillToCustomerPhoneNumberData() );
			label_bill_to_customer_email_address		. setText( invoice.getBillToCustomerEmailAddressLabel() );
			label_bill_to_customer_email_address_data	. setText( invoice.getBillToCustomerEmailAddressData() );
			label_ship_to_customer_name					. setText( invoice.getShipToCustomerNameLabel() );
			label_ship_to_customer_name_data			. setText( invoice.getShipToCustomerNameData() );
			label_ship_to_customer_address				. setText( invoice.getShipToCustomerAddressLabel());
			label_ship_to_customer_address_data			. setText( invoice.getShipToCustomerAddressData() );
			label_ship_to_customer_city					. setText( invoice.getShipToCustomerCityLabel() );
			label_ship_to_customer_city_data			. setText( invoice.getShipToCustomerCityData() );
			label_ship_to_customer_state				. setText( invoice.getShipToCustomerStateLabel() );
			label_ship_to_customer_state_data			. setText( invoice.getShipToCustomerStateData()  );
			label_ship_to_customer_zipcode				. setText( invoice.getShipToCustomerZipcodeLabel() );
			label_ship_to_customer_zipcode_data			. setText( invoice.getShipToCustomerZipcodeData() );
			label_ship_to_customer_country				. setText( invoice.getShipToCustomerCountryLabel() );
			label_ship_to_customer_country_data			. setText( invoice.getShipToCustomerCountryData() );
			label_ship_to_customer_phone_number			. setText( invoice.getShipToCustomerPhoneNumberLabel() );
			label_ship_to_customer_phone_number_data	. setText( invoice.getShipToCustomerPhoneNumberData() );
			label_ship_to_customer_email_address		. setText( invoice.getShipToCustomerEmailAddressLabel()  );
			label_ship_to_customer_email_address_data	. setText( invoice.getShipToCustomerEmailAddressData() );
			storeName                 					. setText( invoice.getStoreName());
			storePhoneNumber          					. setText( invoice.getStorePhoneNumber());
			storeFaxNumber            					. setText( invoice.getStoreFaxNumber());  
	  }
	  
	  public void setComponentFontValues() {

		  storeName									. setFont(font1);
			storePhoneNumber							. setFont(font1);
			storeFaxNumber								. setFont(font1);
			subtotalLabel								. setFont(font1);
			taxesLabel									. setFont(font1);
			totalLabel									. setFont(font1);
	        // totalLabel									. setFont(new Font("Times New Roman", Font.BOLD, 30));
	        // totalLabel									.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(null,"",0,0,new Font("Times New Roman", Font.BOLD, 20), Color.BLUE), totalLabel.getBorder()));

			tenderLabel									. setFont(font1);
			changeLabel									. setFont(font1);
			discountLabel								. setFont(font1);
			salesReport									. setFont(font1);

			bx001										. setFont(font2);
			bx002										. setFont(font2);
			bx003										. setFont(font2);
			bx004										. setFont(font2);
			bx005										. setFont(font2);
			bx006										. setFont(font2);
			bx007										. setFont(font2);
//			bx008										. setFont(font2);
			button_tender								. setFont(font3);
			button_tender_digital						. setFont(font3);
			button_tender_on_account					. setFont(font3);
			saveTable								. setFont(font3);
			loadTable								. setFont(font3);
			pim_button									. setFont(font2);
	  }
	  
	  public void setColorScheme() {
		  	panel										. setBackground(Color.decode("#F0F0F0"));
		    bottomPanel									. setBackground(Color.decode("#F0F0F0"));
		    tablePanel									. setBackground(Color.decode("#000000"));
			bx001										. setBackground(Color.WHITE);
		    bx002										. setBackground(Color.WHITE);
			bx003										. setBackground(Color.WHITE);
			bx003										. setBackground(new Color(59,89,112));
			  
			panel										. setForeground(Color.decode("#FFFFFF"));
		    storeName									. setForeground(Color.decode("#000000"));
		    storePhoneNumber							. setForeground(Color.decode("#000000"));
		    storeFaxNumber								. setForeground(Color.decode("#000000"));
		    bx001										. setForeground(Color.BLACK);
		    bx002										. setForeground(Color.BLACK);
		    bx003										. setForeground(new Color(59,89,12));
		    bx003										. setForeground(Color.BLACK);
		    bx004										. setForeground(Color.decode("#FF0000"));
		    bx005										. setForeground(Color.BLUE);
		    bx006										. setForeground(Color.BLUE);
		    bx007										. setForeground(Color.BLUE);
//		    bx008										. setForeground(Color.BLUE);
		    button_tender								. setBackground(Color.decode("#FFFFFF"));
			button_tender								. setForeground(Color.BLACK);
			button_tender.setFocusPainted(false);
			button_tender.setOpaque(true);
			 button_tender.setContentAreaFilled(true);
//		        button_tender.setBorderPainted(false);

			 
			    button_tender_digital								. setBackground(Color.decode("#FFFFFF"));
				button_tender_digital								. setForeground(Color.BLACK);
				button_tender_digital.setFocusPainted(false);
				button_tender_digital.setOpaque(true);
				 button_tender_digital.setContentAreaFilled(true);

				 
				    button_tender_on_account			. setBackground(Color.decode("#FFFFFF"));
					button_tender_on_account						. setForeground(Color.BLACK);
					button_tender_on_account.setFocusPainted(false);
					button_tender_on_account.setOpaque(true);
					button_tender_on_account.setContentAreaFilled(true);

			 
			 
		        // Remove the border for simplicity (optional)
		    saveTable								. setBackground(Color.decode("#FFFFFF"));
			saveTable								. setForeground(Color.BLACK);
			saveTable.setFocusPainted(false);
			saveTable.setOpaque(true);
		    loadTable								. setBackground(Color.decode("#FFFFFF"));
			loadTable								. setForeground(Color.BLACK);
			loadTable.setFocusPainted(false);
			loadTable.setOpaque(true);
		    
			pim_button									. setForeground(Color.BLACK);
			
	  }
	  
	  public void setComponentDimensionValues() {
		    frame							.setPreferredSize(new Dimension(w,h-30));
		    panel							.setPreferredSize(new Dimension(w,250));
			tablePanel						.setPreferredSize(new Dimension(w,550));
			bottomPanel						.setPreferredSize(new Dimension(w,200));

			bx001							.setPreferredSize(new Dimension(130,30));
			bx002							.setPreferredSize(new Dimension(130,30));
			bx003							.setPreferredSize(new Dimension(130,30));
			bx004							.setPreferredSize(new Dimension(130,30));
			bx005							.setPreferredSize(new Dimension(130,30));
			bx006							.setPreferredSize(new Dimension(130,30));
			bx006							.setPreferredSize(new Dimension(130,30));
			bx007							.setPreferredSize(new Dimension(130,30));
			
			button_tender					.setPreferredSize(new Dimension(130,65)); 
			button_tender_digital			.setPreferredSize(new Dimension(130,65)); 
			button_tender_on_account		.setPreferredSize(new Dimension(130,65)); 
			
			pim_button						.setPreferredSize(new Dimension(130,30));
		    salesReport						.setPreferredSize(new Dimension(130,30));
		    addenda							.setPreferredSize(new Dimension(200,100));
		    label_addenda					.setPreferredSize(new Dimension(130,30));
		    saveTable						.setPreferredSize(new Dimension(130,65));
			loadTable						.setPreferredSize(new Dimension(130,65));

			payment_method					.setPreferredSize(new Dimension(150,30));
		    tender_amount					.setPreferredSize(new Dimension(150,30));
		    tender_amount					.setHorizontalAlignment(SwingConstants.CENTER);
			
	  }
	  public void setActionListener() {
		    bx001							.addActionListener(this);
		    bx002							.addActionListener(this);
		    bx003							.addActionListener(this);
		    bx004							.addActionListener(this);
		    bx005							.addActionListener(this);
		    bx006							.addActionListener(this);
		    bx007							.addActionListener(this);
//		    bx008							.addActionListener(this);
		    
		    button_tender					.addActionListener(this);
		    button_tender_digital			.addActionListener(this);
		    button_tender_on_account		.addActionListener(this);
		    saveTable						.addActionListener(this);
		    loadTable						.addActionListener(this);

		    salesReport						.addActionListener(this);
		    pim_button						.addActionListener(this);
		    payment_method					.addActionListener(this);
		    tender_amount					.addActionListener(this);
		    addenda							.addFocusListener(this);

	  }
	  
	  public void setUILayout() {
		  
		  springLayout.putConstraint(SpringLayout.NORTH, panel,0,SpringLayout.NORTH, frame);
		  springLayout.putConstraint(SpringLayout.NORTH, tablePanel,200,SpringLayout.NORTH, frame);
		  springLayout.putConstraint(SpringLayout.SOUTH, bottomPanel,0,SpringLayout.SOUTH, frame);


		  panelLayout.putConstraint(panelLayout.NORTH, timeLabel,00,layout.NORTH, panel);
		  

		  panelLayout.putConstraint(panelLayout.NORTH, account_name_input,70,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, account_name_label,75,layout.NORTH, panel);
		  
		  panelLayout.putConstraint(panelLayout.NORTH, payment_method,100,panelLayout.NORTH, panel);
  	      panelLayout.putConstraint(panelLayout.NORTH, tender_amount,130,panelLayout.NORTH, panel);

		  
		  panelLayout.putConstraint(panelLayout.NORTH, storeName,0,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, storePhoneNumber,15,layout.NORTH, bottomPanel);
		  // panelLayout.putConstraint(panelLayout.NORTH, storeFaxNumber,30,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, internetLabel,30,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, verifoneLabel,45,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, retailerUUIDDescription,60,layout.NORTH, bottomPanel);

		  
		   int label_north = 20;
		   int label_west = 30;
		   
		  panelLayout.putConstraint(panelLayout.NORTH, invoiceNumberLabel,label_north-10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, subtotalLabel,label_north+10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, taxesLabel,label_north+30,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, totalLabel,label_north+50,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, tenderLabel,label_north+70,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, changeLabel,label_north+90,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, discountLabel,label_north+110,layout.NORTH, panel);
		  
		  panelLayout.putConstraint(panelLayout.NORTH, invoiceNumberLabelDescription,label_north-10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, subtotalLabelDescription,label_north+10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, taxesLabelDescription,label_north+30,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, totalLabelDescription,label_north+50,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, tenderLabelDescription,label_north+70,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, changeLabelDescription,label_north+90,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, discountLabelDescription,label_north+110,layout.NORTH, panel);
		  		  
		  panelLayout.putConstraint(panelLayout.NORTH, bx001,5,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx002,30,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx003,55,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx004,80,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx005,105,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx006,130,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx007,130,layout.NORTH, panel);
//		  panelLayout.putConstraint(panelLayout.NORTH, bx008,200,layout.NORTH, panel);
		  
		  
		  
		  panelLayout.putConstraint(panelLayout.NORTH, button_tender,5,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, button_tender_digital,5,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, button_tender_on_account,5,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, saveTable,5,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, loadTable,5,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, salesReport,30,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, pim_button,55,layout.NORTH, panel);


  	      panelLayout.putConstraint(panelLayout.NORTH, addenda, 35, panelLayout.NORTH, panel);
          panelLayout.putConstraint(panelLayout.NORTH, label_addenda, 0, panelLayout.NORTH, panel);

		  
		  
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_receipt,00,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_invoice,30,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_display,60,layout.NORTH, bottomPanel);

		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_receipt_label,3,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_invoice_label,33,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_display_label,63,layout.NORTH, bottomPanel);

  	      
		  springLayout.putConstraint(SpringLayout.WEST, panel,0,SpringLayout.WEST, frame);
		  springLayout.putConstraint(SpringLayout.WEST, tablePanel,000,SpringLayout.WEST, frame);
		  springLayout.putConstraint(SpringLayout.WEST, bottomPanel,000,SpringLayout.WEST, frame);
		  
		  panelLayout.putConstraint(panelLayout.WEST, storeName,30,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, storePhoneNumber,30,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, storeFaxNumber,30,layout.WEST, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, timeLabel,30,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, account_name_input,105,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, account_name_label,35,layout.WEST, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, invoiceNumberLabelDescription,label_west+300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, subtotalLabel,label_west+300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, taxesLabel,label_west+300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, totalLabel,label_west+300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, tenderLabel,label_west+300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, changeLabel,label_west+300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, discountLabel,label_west+300,layout.WEST, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, invoiceNumberLabel,label_west+220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, subtotalLabelDescription,label_west+220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, taxesLabelDescription,label_west+220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, totalLabelDescription,label_west+220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, tenderLabelDescription,label_west+220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, changeLabelDescription,label_west+220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, discountLabelDescription,label_west+220,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, payment_method,30,panelLayout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, tender_amount,30,panelLayout.WEST, panel);

		  
		  panelLayout.putConstraint(panelLayout.WEST, internetLabel,30,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, verifoneLabel,30,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, retailerUUIDDescription,30,layout.WEST, panel);

		  
		  panelLayout.putConstraint(panelLayout.WEST, bx001,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx002,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx003,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx004,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx005,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx006,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx007,550,layout.WEST, panel);
///		  panelLayout.putConstraint(panelLayout.WEST, bx008,550,layout.WEST, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, button_tender,600,layout.WEST, bottomPanel);
		  panelLayout.putConstraint(panelLayout.WEST, button_tender_digital,750,layout.WEST, bottomPanel);
		  panelLayout.putConstraint(panelLayout.WEST, button_tender_on_account,900,layout.WEST, bottomPanel);
		  panelLayout.putConstraint(panelLayout.WEST, saveTable,1050,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, loadTable,1200,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, salesReport,550,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, pim_button,550,layout.WEST, panel);
		  

		  
		  int bill_to_north 		= 10;
		  int bill_to_north_data 	= 30;

		  // SHIP TO COMPONENTS
		  int bill_to_west 			= 700; 
		  int bill_to_west_data 	= 700;// original 970
		  int north_push = 25;

		  // label_bill_to_customer_name		.setFont(new Font("Times New Roman", Font.BOLD, 10));
	      // label_bill_to_customer_name		.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(null,"",0,0,new Font("Times New Roman", Font.BOLD, 10), Color.BLUE), label.getBorder()));

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_name,10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_name_data,10+north_push,layout.NORTH, panel);
		  
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_address,35,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_address_data,35+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_city,60,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_city_data,60+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_state,85,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_state_data,85+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_zipcode,110,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_zipcode_data,110+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_country,135,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_country_data,135+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_email_address,160,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_email_address_data,85+north_push,layout.NORTH, panel); // 160

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_phone_number,185,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_phone_number_data,110+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_name,bill_to_west,layout.WEST, panel);// 970
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_name_data,bill_to_west,layout.WEST, panel); // 970

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_address,bill_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_address_data,bill_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_city,bill_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_city_data,bill_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_state,bill_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_state_data,bill_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_zipcode,bill_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_zipcode_data,bill_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_country,bill_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_country_data,bill_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_email_address,bill_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_email_address_data,bill_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_phone_number,bill_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_phone_number_data,bill_to_west_data,layout.WEST, panel);

		  // BILL TO COMPONENTS
		  int ship_to_west 		= 900; // 1150
		  int ship_to_west_data = 900;
		  int addenda_west 		= 1100;
		  
		  
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_name,10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_name_data,10+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_address,35,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_address_data,35+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_city,60,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_city_data,60+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_state,85,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_state_data,85+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_zipcode,110,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_zipcode_data,110+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_country,135,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_country_data,135+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_email_address,160,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_email_address_data,85+north_push,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_phone_number,185,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_phone_number_data,110+north_push,layout.NORTH, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_name,ship_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_name_data,ship_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_address,ship_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_address_data,ship_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_city,ship_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_city_data,ship_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_state,ship_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_state_data,ship_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_zipcode,ship_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_zipcode_data,ship_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_country,ship_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_country_data,ship_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_email_address,ship_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_email_address_data,ship_to_west_data,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_phone_number,ship_to_west,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_phone_number_data,ship_to_west_data,layout.WEST, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_receipt,275,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_invoice,275,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_display,275,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, default_printer_receipt_label,225,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_invoice_label,225,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_display_label,225,layout.WEST, panel);
		  
		  
		  panelLayout.putConstraint(panelLayout.WEST, addenda, addenda_west, panelLayout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_addenda, addenda_west, panelLayout.WEST, panel);

	  	}
	  
	  
	  public void setUIComponent() {
		  
		  // Add UI Elements
		  bottomPanel.add(storeName);
		  bottomPanel.add(storePhoneNumber);
//		  bottomPanel.add(storeFaxNumber);
		  bottomPanel.add(internetLabel);
		  bottomPanel.add(verifoneLabel);
		  bottomPanel.add(retailerUUIDDescription);

		  
		  panel.add(bx001);
		  panel.add(bx002);
		  panel.add(bx003);
		  panel.add(bx004);
		  panel.add(bx005);
		  panel.add(bx006);
		  panel.add(bx007);
//		  panel.add(bx008);
		  
		  
		  bottomPanel.add(button_tender);
		  bottomPanel.add(button_tender_digital);
		  bottomPanel.add(button_tender_on_account);
		  bottomPanel.add(saveTable);
		  bottomPanel.add(loadTable);
		  
		  panel.add(account_name_input);
		  panel.add(account_name_label);

		// Made adjustment 
		  panel.add(totalLabel);
		  panel.add(subtotalLabel);
		  panel.add(taxesLabel);
		  panel.add(tenderLabel);
		  panel.add(changeLabel);
		  panel.add(discountLabel);

		  panel.add(invoiceNumberLabel);
		  panel.add(invoiceNumberLabelDescription);
		  panel.add(subtotalLabelDescription);
		  panel.add(taxesLabelDescription);
		  panel.add(totalLabelDescription);
		  panel.add(tenderLabelDescription);
		  panel.add(changeLabelDescription);
		  panel.add(discountLabelDescription);

		  
		  panel.add(timeLabel);
		  panel.add(salesReport);
		  panel.add(pim_button);

		  panel.add(label_bill_to_customer_name);
		  panel.add(label_bill_to_customer_name_data);
 		  //panel.add(label_bill_to_customer_address);
		  panel.add(label_bill_to_customer_address_data);
		  //panel.add(label_bill_to_customer_city);
		  panel.add(label_bill_to_customer_city_data);
		  // panel.add(label_bill_to_customer_state);
		  // panel.add(label_bill_to_customer_state_data);
		  // panel.add(label_bill_to_customer_zipcode);
		  //panel.add(label_bill_to_customer_zipcode_data);
		  //panel.add(label_bill_to_customer_country);
		  // panel.add(label_bill_to_customer_country_data);
		  //panel.add(label_bill_to_customer_email_address);
		  panel.add(label_bill_to_customer_email_address_data);
		  //panel.add(label_bill_to_customer_phone_number);
		  panel.add(label_bill_to_customer_phone_number_data);
		  
		  panel.add(label_ship_to_customer_name);
		  panel.add(label_ship_to_customer_name_data);
		  //panel.add(label_ship_to_customer_address);
		  panel.add(label_ship_to_customer_address_data);
		  //panel.add(label_ship_to_customer_city);
		  panel.add(label_ship_to_customer_city_data);
		  //panel.add(label_ship_to_customer_state);
		  //panel.add(label_ship_to_customer_state_data);
		 // panel.add(label_ship_to_customer_zipcode);
		  //panel.add(label_ship_to_customer_zipcode_data);
		 // panel.add(label_ship_to_customer_country);
		 //panel.add(label_ship_to_customer_country_data);
		 // panel.add(label_ship_to_customer_email_address);
		  panel.add(label_ship_to_customer_email_address_data);
		  //panel.add(label_ship_to_customer_phone_number);
		  panel.add(label_ship_to_customer_phone_number_data);
		  panel.add(addenda);
	      panel.add(label_addenda);

	      panel.add(payment_method);
	      panel.add(tender_amount);

	      
	      //	      bottomPanel.add(label_addenda);

//	      bottomPanel.add(addenda);
		  
		  bottomPanel.add(default_printer_receipt);
		  bottomPanel.add(default_printer_invoice);
		  bottomPanel.add(default_printer_display);

		  bottomPanel.add(default_printer_receipt_label);
		  bottomPanel.add(default_printer_invoice_label);
		  bottomPanel.add(default_printer_display_label);

		  
		  
	  }
	  

	  
	  public void buildTopPanel() {
		    
		  try {
		    	client_name						= invoice.getStoreName();
		        retailerUUIDDescription			. setText( invoice.getIssuerUUID() );
		  } catch(Exception e) {			System.out.println(  e.toString( )); }  
		        

		  timeLabel						.setFont(new Font("Times New Roman", Font.BOLD, 30));
		  timeLabel						.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(null,fmt.format(today),0,0,new Font("Times New Roman", Font.BOLD, 20), Color.BLUE), timeLabel.getBorder()));
		      		          
		        ActionListener taskPerformer = new ActionListener() {
		        	public void actionPerformed(ActionEvent evt) {
		        		
		              simpDate          = new SimpleDateFormat("hh:mm:ss a");
		              today 			= new Date();
		              timeLabel			. setText(simpDate.format(today) );
		              internetLabel		. setText( internet.checkConnection() );
		              
		              if( internetLabel.getText().equalsIgnoreCase("No Internet Available" ) ){	  			
		            	  
		            	  internetLabel.setBackground(Color.decode("#000000"));		              
				  		  internetLabel.setForeground(Color.decode("#FF0000"));
		              }

		          
		        }};
		        
		        timer = new Timer(1000, taskPerformer);
		        timer .start();

	            verifoneLabel		. setText( "Verifone Status: " + String.valueOf(registerStatus)  );

		        
		        System.out.println("Register -> buildTopPanel()");
		        System.out.println("Register -> registerStatus : " + String.valueOf(registerStatus));
		  try {
		              invoiceNumberLabel.setText(  "Invoice #:" );
		              
	            	  try { 

		              switch(invoice.getInvoiceNumber()) {
		              			case "":
				            		  error_message = "Invoice Number Error: Check Internet Connection";
				            		  System.out.println("Invoice Number Format Error:" + error_message);
		              			break;
		              			default:
		  			              invoiceNumberLabelDescription.setText( invoice.getInvoiceNumber() );		            	  
						          invoiceNumberLabelDescription.setFont(new Font("Times New Roman", Font.BOLD, 15));
						          invoiceNumberLabelDescription.setForeground(Color.decode("#0000FF"));
						          break;
		              }
	            	  }catch(NumberFormatException e) { System.out.println("Invoice Number Format Exception: " + e.toString() ); }
		          }catch(Exception e) { System.out.println(e.toString());}
	  }
	  
	  public void buildTable()
	  {
		// organize Customer Specific default values -----------------------------------------------------------------------
		    for( int column_index = 0; column_index < table.getColumnModel().getColumnCount(); column_index++)
		    {
		        table.getColumnModel().getColumn( column_index ).setHeaderValue( column_header[ column_index ] );
		        
		        if( column_header[ column_index] . equalsIgnoreCase("DESCRIPTION") != true)
		        {
		            table.getColumnModel().getColumn( column_index ).setMinWidth( column_width[ column_index] );
		            table.getColumnModel().getColumn( column_index ).setMaxWidth( column_width[ column_index] );
		        }
		    }
		    		table.setDefaultRenderer(Object.class, new TableCellRenderer(){
		        
		    		private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();

		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,column);
		            
		    if (row%2 == 0){c.setBackground(Color.decode("#F0F0B6"));} /* Original Color: #F0F0B6 */
		    else {c.setBackground(Color.decode("#F0F0A0"));} /* Original Color: #F0F0A0 */
		    return c;
		        }});
	  }
	  
	  


	  public JMenuBar createMenuBar() {

		  	JMenuBar menuBar;
	        JMenu menu, submenu;
	        JMenuItem menuItem;
	        JRadioButtonMenuItem rbMenuItem;
	        JCheckBoxMenuItem cbMenuItem;
	 
	        //Create the menu bar.
	        menuBar = new JMenuBar();
	 
	        // Menu Name
	        menu = new JMenu(" Lockwind POS ");	        
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription("Lockwind POS");

	        menuItem = new JMenuItem("Tender Transaction",KeyEvent.VK_T);
			menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));
			
	        menuItem.setName("TenderTransaction");
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_7, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription(" Tender Transaction ");
			menuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ev) { tenderAction(0); } });
			menu.add(menuItem);


	        
	        // Menu Item located on dropdown list
			 // Add a call to the function to save the transaction details into a structured file.
			 // This action should create a parked transaction in an XML file that can then be recovered later.
			 // Each parked transaction should have an document number and machine ID in order to be processed.
	        menuItem = new JMenuItem("Park Transaction",KeyEvent.VK_C); // No ParkTransaction Module built yet
	        menuItem.setName("ParkTransaction");
	        menuItem.addActionListener(this);
			menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) { 
				//JOptionPane.showMessageDialog(null,"Parking Transaction"); 
				System.out.println("User Action: Parking Transaction");}});
			// this.parkTransaction();
   		   menuItem.setBackground(Color.decode("#FFFF00"));
		   menuItem.setForeground(Color.decode("#000000"));  
	       menu.add(menuItem);
	        
			// No Copy Last Transaction Module built yet
	        menuItem 			= new JMenuItem("Copy Last Transaction",KeyEvent.VK_C);
	        menuItem			. setName("CopyLastTransaction");
	        menuItem.setBackground(Color.decode("#FFFF00"));
			menuItem.setForeground(Color.decode("#000000"));  
	        menuItem			. addActionListener(this);
	        menu				. add(menuItem);
	        	        
	        menuItem = new JMenuItem("Add Invoice Comment",KeyEvent.VK_D);
	        menuItem.setName("AddInvoiceComment");
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_8, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription("add invoice comment");
			menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) { 
				//JOptionPane.showMessageDialog(null,"Parking Transaction"); 
				addenda.requestFocus();
				}});
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Restart Program",KeyEvent.VK_C);
	        menuItem.setName("RestartProgram");
	   		menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));  
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Refresh Program",KeyEvent.VK_C);
	        menuItem.setName("RefreshProgram");
	   		menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));  
	        menuItem.addActionListener(this);
			menuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ev) { System.out.println("User Action: Clear Register"); clearRegister(); } });
			menu.add(menuItem);

	        menuItem = new JMenuItem("Exit Program",KeyEvent.VK_C);
	        menuItem.setName("ExitProgram");
	        menuItem.addActionListener(this);
	        
			menuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ev) { System.out.println("User Action: Shut down Pont Of Sale System"); System.exit(0); }});
			menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));  
			menu.add(menuItem);
	        menuBar.add(menu);

	        
	        menu = new JMenu("Receipts Management");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription( "View the Receipts Management Menu");
	        
	        menuItem = new JMenuItem("Sales Report",KeyEvent.VK_C);
	        menuItem.setName("SalesReport");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("View the Sales Report");
	        menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) { 
					//JOptionPane.showMessageDialog(null,"Parking Transaction"); 
					addenda.requestFocus();
					}});
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Find Receipt",KeyEvent.VK_C);
	        menuItem.setName("FindReceipt");
	        menu.getAccessibleContext().setAccessibleDescription( "Find a receipt");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuItem = new JMenuItem("Reprint Receipt",KeyEvent.VK_C);
	        menuItem.setName("ReprintReceipt");
	        menu.getAccessibleContext().setAccessibleDescription( "Reprint a receipt");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        menuBar.add(menu);

	        
	        menu = new JMenu("Inventory Management");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription("Inventory Management");

	        menuItem = new JMenuItem("Products",KeyEvent.VK_C);
	        menuItem.setName("Products");
	        menu.getAccessibleContext().setAccessibleDescription("View Products");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Services",KeyEvent.VK_C);
	        menuItem.setName("Services");
	        menu.getAccessibleContext().setAccessibleDescription( "View Services");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Rentals",KeyEvent.VK_C);
	        menuItem.setName("Rentals");
	        menu.getAccessibleContext().setAccessibleDescription( "View Rentals ");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        
	        menuItem = new JMenuItem("New Stock",KeyEvent.VK_C);
	        menuItem.setName("NewStock");
	        menu.getAccessibleContext().setAccessibleDescription( "Add new Stock");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Price Change",KeyEvent.VK_C);
	        menuItem.setName("PriceChange");
	        menu.getAccessibleContext().setAccessibleDescription( "Price Change");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);


	        menuItem = new JMenuItem("Add Inventory",KeyEvent.VK_C);
	        menuItem.setName("AddInventory");
	        menu.getAccessibleContext().setAccessibleDescription( "Add Inventory");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Product Catalogue",KeyEvent.VK_C);
	        menuItem.setName("ProductCatalogue");
	        menu.getAccessibleContext().setAccessibleDescription("View the Product Catalogue");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

			menuItem = new JMenuItem("PIM System",KeyEvent.VK_C);
	        menu.getAccessibleContext().setAccessibleDescription( "View the PIM System");
	        menuItem.setName("PIMSystem");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuBar.add(menu);

	        // ---->Continue adding the accessible descriptions below per menu item @9/15/23 7:04 AM - Briant Guzman  also continue optimizing program below this point.
	        
	        menu = new JMenu("Accounts Receivable");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription( "Accounts Receivable");
	        menuItem.addActionListener(this);
	    	        
	        menuItem = new JMenuItem("Customer Management System",KeyEvent.VK_C);
	        menuItem.setName("CustomerManagementSystem");
	        menu.getAccessibleContext().setAccessibleDescription( "Customer Management System");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Review Customer Pricing",KeyEvent.VK_C);
	        menuItem.setName("ReviewCustomerPricing");
	        menu.getAccessibleContext().setAccessibleDescription( "Review Customer Pricing");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Customer Invoices",KeyEvent.VK_C);
	        menuItem.setName("InvoicesAR");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription( "Customer Invoices");
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Record Payment",KeyEvent.VK_C);
	        menuItem.setName("PaymentRemittance");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("Payment Remittance");
	        menu.add(menuItem);
	        menuBar.add(menu);

	        
	        menu = new JMenu("Accounts Payable");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription("Accounts Payable");

	        menuItem = new JMenuItem("Vendor Management System",KeyEvent.VK_C);
	        menuItem.setName("VendorManagementSystem");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("Vendor Management System");
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Vendor Catalogs",KeyEvent.VK_C);
	        menuItem.setName("Vendor Catalogs");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("Vendor Catalogues");
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Purchase Orders",KeyEvent.VK_C);
	        menuItem.setName("PurchaseOrders");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("Purchase Orders");
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Invoices to Pay",KeyEvent.VK_C);
	        menuItem.setName("InvoicesAP");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("Vendor Invoices");
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Advance Shipping Notices",KeyEvent.VK_C);
	        menuItem.setName("ASNs");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("Advance Shipping Notices");
	        menu.add(menuItem);
	        menuBar.add(menu);

	        menu = new JMenu("User Management");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription("User Management");
	        
	        // This menu item will open the user management system.
	        menuItem = new JMenuItem("Users & Permissions",KeyEvent.VK_C);
	        menuItem.setName("UserPermissions");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription( "User Permissiosn");
	        menu.add(menuItem);
	        
	        // This menu item will open the group management system.
	        menuItem = new JMenuItem("Groups & Permissions",KeyEvent.VK_C);
	        menuItem.setName("GroupPermissions");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("Group Permissions");
	        menu.add(menuItem);
	        
	        menuBar.add(menu);

	        
	        menu = new JMenu("Help");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menuItem = new JMenuItem("Contact Customer Support",KeyEvent.VK_C);
	        menuItem.setName("ContactCustomerSupport");
	        menu.getAccessibleContext().setAccessibleDescription("Contact Customer Support");
	        menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));  
		    menuItem.addActionListener(this);
			menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) { try { Desktop.getDesktop().browse(new URI("https://lockwind.com/customer_support.php" )); }  catch (Exception e1) { e1.printStackTrace(); } } });
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Platform Training",KeyEvent.VK_C);
	        menuItem.setName("PlatformTraining");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription( "Platform Training");
	        menu.add(menuItem);

	        
	        menuItem = new JMenuItem("About Lockwind POS",KeyEvent.VK_C);
	        menuItem.setName("About");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription("About Lockwind POS");
	        menu.add(menuItem);

	        menuBar.add(menu);

	        menu = new JMenu("Alerts");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription( "Alerts");
	        menuBar.add(menu);


/*
	        //a group of JMenuItems
	        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
//	        menuItem = new JMenuItem("A text-only menu item",KeyEvent.VK_T);
	 

	        menuItem = new JMenuItem("Vendors",KeyEvent.VK_V);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_2, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Product Information System",KeyEvent.VK_P);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_3, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Price Management System",KeyEvent.VK_X);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_4, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Add Inventory",KeyEvent.VK_I);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_5, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
	        menu.add(menuItem);

	        

	        
	        //a group of radio button menu items
	        menu.addSeparator();
	        ButtonGroup group = new ButtonGroup();
	 
	        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
	        rbMenuItem.setSelected(true);
	        rbMenuItem.setMnemonic(KeyEvent.VK_R);
	        group.add(rbMenuItem);
	        menu.add(rbMenuItem);
	 
	        rbMenuItem = new JRadioButtonMenuItem("Another one");
	        rbMenuItem.setMnemonic(KeyEvent.VK_O);
	        group.add(rbMenuItem);
	        menu.add(rbMenuItem);
	 
	        //a group of check box menu items
	        menu.addSeparator();
	        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
	        cbMenuItem.setMnemonic(KeyEvent.VK_C);
	        menu.add(cbMenuItem);
	 
	        cbMenuItem = new JCheckBoxMenuItem("Another one");
	        cbMenuItem.setMnemonic(KeyEvent.VK_H);
	        menu.add(cbMenuItem);
	 
	        //a submenu
	        menu.addSeparator();
	        submenu = new JMenu("A submenu");
	        submenu.setMnemonic(KeyEvent.VK_S);
	 
	        menuItem = new JMenuItem("An item in the submenu");
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(
	                KeyEvent.VK_2, ActionEvent.ALT_MASK));
	        submenu.add(menuItem);
	 
	        menuItem = new JMenuItem("Another item");
	        submenu.add(menuItem);
	        menu.add(submenu);
	 
	        //Build second menu in the menu bar.
	        menu = new JMenu("Another Menu");
	        menu.setMnemonic(KeyEvent.VK_N);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "This menu does nothing");
	        menuBar.add(menu);
	 */
	        return menuBar;
	    }
	  
	  
	  
//frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(".\\upc.png"))); 
//DOES NOT WORK FOR JAR FILE? BUT IT WORKS IN THE CLASS FILE
//-----------------------------------------------------------------------------------------------
	    

//		CONSTRUCTOR
public Register(){
	
	
	setConstructorValues();
    setComponentDefaultValues();
    buildTable();
    buildTopPanel();
    setUILayout();
    setUIComponent();
    buildActionListener();
    
    ImageIcon img = new ImageIcon("./qr.png");
    frame.setIconImage(img.getImage());
	frame.setJMenuBar(createMenuBar());

	rowTable = new RowNumberTable(table);
  	rowTable.setPreferredSize(new Dimension(500,500));

	table_scroll_pane = new JScrollPane(table);
	
	table_scroll_pane.setRowHeaderView(rowTable);
	table_scroll_pane.setCorner(JScrollPane.UPPER_LEFT_CORNER,rowTable.getTableHeader());
	
	tablePanel.add(table_scroll_pane,BorderLayout.CENTER);
	tablePanel.add(new JScrollPane(),BorderLayout.EAST);
	tablePanel.add(new JScrollPane(table),BorderLayout.CENTER);
	
	table.setFillsViewportHeight(true);
	
	frame.add(panel);
	frame.add(tablePanel);
	frame.add(bottomPanel);
	
	frame.pack();
	frame.setVisible(true);

	table.requestFocus();
	table.changeSelection(0,0, false,false);

	System.out.println("Column:count - > "  + table.getModel().getColumnCount());

	// loadShoppingCart();
	
	// Print names of columns assigned to table model
//	for(int i = 0; i < table.getModel().getColumnCount(); i++){
//		System.out.println(table.getColumnName(i)); }
	
	
	// Temporarily disabled in order to complete it correctly. on 9/18/2023.	

/*
			System.out.println("Register-> Session with Verifone Device");
			try { 
			  register_verifone.registerPOS();
			  registerStatus = true;
			System.out.println( Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ); // returns mac_label
}catch(Exception e){
System.out.println( "Failed to register POS, please register manually: " + e.toString() );
}
			

/* 
	line_item_table = new JTable();	
	table_scroll_pane = new JScrollPane(line_item_table);
	table_scroll_pane.setRowHeaderView(line_item_table);
	table_scroll_pane.setCorner(JScrollPane.UPPER_LEFT_CORNER,line_item_table.getTableHeader());
	tablePanel.add(table_scroll_pane);
*/
	//rowTable = new RowNumberTable(table);
	//
//	tablePanel.add(table_scroll_pane,BorderLayout.CENTER);
	//tablePanel.add(line_item_table,BorderLayout.CENTER);
	
  	
	// rowTable.setBackground(Color.decode("#000000"));
	

//	tablePanel.add(table_scroll_pane,BorderLayout.CENTER);
	

	
  	/*

 	
  	table_scroll_pane.setRowHeaderView(rowTable);
  	 table_scroll_pane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
  	 	    rowTable.getTableHeader());
  	
	//	tablePanel.add(new JScrollPane(),BorderLayout.EAST);
  	// 	tablePanel.add(new JScrollPane(table),BorderLayout.CENTER);
  	
//	table.setFillsViewportHeight(true);
	
  	
   

 	*/

  
}

/*
public void RegisterVerifone() { 

	
	System.out.println("Register-> Session with Verifone Device");
    
	if(registerStatus == false) // Start Verifone Session
		try {

	
		  register_verifone.registerPOS();
		  Thread.sleep(1000);
		  registerStatus = true;
			System.out.println( Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ); // returns mac_label

	
		} catch(Exception e) { System.out.println("Error at Register set default values " + e.toString()); }
	else {
		System.out.println( "Failed to register POS, please register manually: " + e.toString() );
	} 
	
}
*/

public void updateRow(JTable table, int i){
	//if(table_manager.getData(table,i,0).toString().equalsIgnoreCase("") || table_manager.getData(table, i, 0) == null) {
	if (table_manager.getData(table, i, 0) == null || table_manager.getData(table, i, 0).toString().trim().equalsIgnoreCase("")) {
		System.out.println("Register->updateRow():ErrorCode:null at GTIN;");
	}
	else {
	table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); // Update Subtotal for this row
	table_manager.setData( table,i,6,table_manager.getTax(table,i)); // Update taxes for this row
}
}

public void clearRegister() {
	
    account_name_input.setSelectedIndex(0);

    subtotalLabel	.setText("$ 0.00");
	taxesLabel		.setText("$ 0.00");
	totalLabel		.setText("$ 0.00");
	tenderLabel		.setText("$ 0.00");
	changeLabel		.setText("$ 0.00");
	discountLabel	.setText("$ 0.00");
	tender_amount	.setText("");
	addenda			.setText("");

	table_manager	.clearTable(table);
	table			.changeSelection(0,0,false,false);
	table			.requestFocus();
	
  
  }
  


/*
public void restartApplication() {
    try {
        // Get the current Java binary path
        String javaBin = System.getProperty("java.home") + "/bin/java";
        // Get the jar file location or class path
        String jarPath = new java.io.File(
                this.getClass().getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI())
                .getPath();

        // Build the command to restart the program
        ProcessBuilder processBuilder = new ProcessBuilder(javaBin, "-cp", jarPath, this.getClass().getName());
        processBuilder.start();

        // Close the current program
        System.exit(0);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
*/





public void editTableCell() {
		table.editCellAt(row, col);
        Component editor = table.getEditorComponent();
        editor.requestFocusInWindow();			  

}

public void buildActionListener() {

	InputMap inputMap 		= table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	ActionMap actionMap 	= table.getActionMap();

	//----------------------------------------------------------F1 GTIN UPDATE ACTION PROC
		String GTINAction = "GTINAction";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), GTINAction); // When they press F1 it makes the cell editable
		actionMap.put(GTINAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) { editTableCell(); }});
	//---------------------------------------------------------F1 GTIN UPDATE ACTION END 



	//----------------------------------------------------------F2 QTY UPDATE ACTION PROC
		String qtyAction = "qtyAction";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), qtyAction);
		actionMap.put(qtyAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) { updateQTY(); } });
	//---------------------------------------------------------F2 QTY UPDATE ACTION END 


	//----------------------------------------------------------F3 DESCRIPTION UPDATE ACTION PROC
		String descriptionAction = "descriptionAction";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), descriptionAction);
		actionMap.put(descriptionAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) { updateDescription(); }});
	//---------------------------------------------------------F3 PRICE UPDATE ACTION END 

	//----------------------------------------------------------F4 PRICE UPDATE ACTION PROC
		String priceAction = "priceAction";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), priceAction);
		actionMap.put(priceAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) { updatePrice(); }});
	//---------------------------------------------------------F4 PRICE UPDATE ACTION END 

	//----------------------------------------------------------F5 TAX ACTION PROC
		String taxAction = "taxAction";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), taxAction);
		actionMap.put(taxAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) { updateTax(); }});
	//---------------------------------------------------------F5 TAX ACTION END 

	//----------------------------------------------------------F6 DISCOUNT UPDATE ACTION PROC
		String discountAction = "discountAction";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), discountAction);
		actionMap.put(discountAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) { updateDiscount(); }});
	//---------------------------------------------------------F6 DISCOUNT UPDATE ACTION END 

	//----------------------------------------------------------F12 DISCOUNT UPDATE ACTION PROC
		String tenderAction = "tenderAction";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), tenderAction);
		actionMap.put(tenderAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) { tenderAction(0.00); }});
	//---------------------------------------------------------F12 DISCOUNT UPDATE ACTION END 	
		
		
	//----------------------------------------------------------DELETE KEY ACTION PROC
		String DELETE = "Delete";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), DELETE);
		actionMap.put(DELETE, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {
		int i = 0; 
		int j = 0;
		
		
		i = table.getSelectedRow();
		j = table.getSelectedColumn();

		if(table.isEditing()){table.getCellEditor().stopCellEditing();}

		table_manager.clearRow(table,i);
		if(invoice.getTransactionTenderValue() == null)
 		{
 			tenderLabel.setText(	"$ 0.00");
 			changeLabel.setText(	"$ 0.00");
 		}else {
// 		tenderLabel.setText(	"$ " + formatter.format(Double.parseDouble( invoice.getTransactionTenderValue() 	))); // Set value to UI Labeloip=q1		
// 		changeLabel.setText(	"$ " + formatter.format(Double.parseDouble( invoice.getTransactionChangeValue() 	))); // Set value to UI Labeloip=q1		
 		}
 		
		refreshTotal(table,0.00,0.00);
		
		

		// JOptionPane.showMessageDialog(null,"Refreshing Total Now");

		/*
		 * register_verifone -> removeLineItem
		if(registerStatus == true){
		try { 
			register_verifone.removeLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
			String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
		}catch (Exception e1) {
			System.out.println("Register - delete key action error: " + "cannot connect to register" + e1.toString() );
			  } }else {}
		*/
		
			table.changeSelection(i,0, false,false);
		}});
	//---------------------------------------------------------DELETE KEY ACTION END 

	//----------------------------------------------------------
		
		KeyStroke enterKey 		= KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		inputMap				. put(enterKey, "Action.enter");
		actionMap				. put("Action.enter", new AbstractAction() {

		public void actionPerformed(ActionEvent evt) {
		
		table					. putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		if (table.isEditing()){table.getCellEditor().stopCellEditing();}
		int i,j 								= 0; 
		double discount, discountPrice, st 		= 0.00;
		String productInfo 						= "";
		String inputGTIN  						= "";
		
		i 										= table.getSelectedRow();
		j 										= table.getSelectedColumn();
		line_item 								= new ElectronicDocumentLineItem();
		line_item								. setTaxRate(TAX_RATE);
		
        System.out.println("Register -> Invoice Number: " + invoice.getInvoiceNumber() + " -> UUID: " + invoice.getTransactionUUID() );

		if(j== 0)  { 
			
			// Column: UPC
			// JOptionPane.showMessageDialog(null,"Register.Enter Key Action Proc - Updating GTIN");
			// JOptionPane.showMessageDialog(null,table_manager.getData(table,i,j).toString());
		
			if(table_manager.getData(table, i, 0) == null || table_manager.getData(table, i, 0).toString().equalsIgnoreCase(""))
			{ JOptionPane.showMessageDialog(null,"Error: GTIN is null"); }
			else {
		
		 System.out.println("Enter Key Action Proc: Column " + j);
		 inputGTIN				 				= table_manager.getData(table,i,0).toString(); // COL 0: GTIN
		 table_manager							. setData(table,i,1,"1"); // COL 1: QTTY
 
		 try { productInfo = product_management_system.getProductInfoAPICategory(inputGTIN); } // COL 2: CATEGORY
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN"); } 
		 table_manager.setData(table,i,2,productInfo);

		 try { productInfo = product_management_system.getProductInfoAPIBrandDescription(inputGTIN); } // COL 3: DESCRIPTION
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN"); }
		 table_manager.setData(table,i,3,productInfo);

		 try { productInfo = product_management_system.getProductInfoAPIPriceRetail(inputGTIN); } // COL 4: PRICE
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN Retail Price"); }
		  table_manager.setData(table,i,4,productInfo); // PRICE RETAIL
		 }
		}
		// Column: QTY
		if(j == 1){		updateQTY();} // Column: Quantity
		if(j == 2){ 	JOptionPane.showMessageDialog(null, "Category cannot be edited"); } // Column: Category  
		if(j == 3){  	updateDescription(); } // Column: Description
	    if(j == 4){  	updatePrice(); table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); } // Column: Price
	    if(j == 5){ 	table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); } // Column: Subtotal
	    if(j == 6){		updateTax(); table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); } // Column: Tax
	    if(j == 7){  	updateDiscount(); table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); } // Column: Discount
	    if(j == 8){  	updateDiscount(); } // Column: OnHand
	      
	    if(table_manager.getData(table, i, 0) == null || table_manager.getData(table, i, 0).toString().equalsIgnoreCase(""))
		{ JOptionPane.showMessageDialog(null,"Error: GTIN is null"); }
		else {
	
	    line_item.setUPC			(table_manager.getData	( table,i,0).toString() );
		line_item.setQTY			(Double.parseDouble		( table_manager.getData(table,i,1).toString() ) );
		line_item.setCategory		(table_manager.getData	( table,i,2).toString() );
		line_item.setDescription	(table_manager.getData	( table,i,3).toString() );
		line_item.setRetailPrice	(Double.parseDouble		( table_manager.getData(table,i,4).toString() ) );
		line_item.getSubtotal();
		line_item.getTaxes();
		line_item.setLineItemCount(i);
		line_item.setScanned();
		line_item.setInvoiceNumber( invoice.getInvoiceNumber()  );
		invoice.addLineItem(line_item);
		table_manager.setData( table,i,5,String.valueOf(line_item.getSubtotal() ) );
		table_manager.setData( table,i,6,String.valueOf(line_item.getTaxes() ) );

		// table_manager.setData( table,i,5,table_manager.getSubTotal(table,i));
		
//		updateRow(table,i);		  
		refreshTotal(table,0.00,0.00);
		System.out.println("**********------------->>>>>> line item row: " + i + ";");			
		System.out.println( "Electronic Document: " + invoice.toString() + "");
		subtotalLabel.setText( 	"$ " + formatter.format(Double.parseDouble( invoice.getTransactionSubTotal() 		))); // Set value to UI Label
 		taxesLabel.setText( 	"$ " + formatter.format(Double.parseDouble( invoice.getTransactionTaxesTotal() 		))); // Set value to UI Label
 		totalLabel.setText(		"$ " + formatter.format(Double.parseDouble( invoice.getTransactionTotal() 			))); // Set value to UI Label
 		discountLabel.setText(	"$ " + formatter.format(Double.parseDouble( invoice.getTransactionDiscountTotal() 	))); // Set value to UI Labeloip=q1		

 		if(invoice.getTransactionTenderValue() == null)
 		{
 			tenderLabel.setText(	"$ 0.00");
 			changeLabel.setText(	"$ 0.00");
 		}else {
// 		tenderLabel.setText(	"$ " + formatter.format(Double.parseDouble( invoice.getTransactionTenderValue() 	))); // Set value to UI Labeloip=q1		
// 		changeLabel.setText(	"$ " + formatter.format(Double.parseDouble( invoice.getTransactionChangeValue() 	))); // Set value to UI Labeloip=q1		
 		}
 		

 		try {
 			if(table_manager.getData(table, i,9) == null || table_manager.getData(table, i,9).toString().equalsIgnoreCase("") ) {
		            String line_item_uuid = http.getUUID();
			  		line_item.setUUID(line_item_uuid);
			  		table_manager.setData( table,i,9,line_item.getUUID());	            	
	            }else { System.out.println("Enter key action proc: line item already assigned" ); }
	
 		updateLineItemUUID(i);
 	 	uploadLineItem(i);
// 	 	saveShoppingCart(i);
 		table.changeSelection(i+1,0, false,false);
		table.requestFocus();
		
		
		}catch(Exception e) {
				System.out.println("Error: Code Blue: uploading line item to Lockwind cloud ");
				System.out.println( e.toString() );
			}
		}
	    
		
	 
	  //	Part of register_verifone code -> review and consolidate 
	  
	  
	if(registerStatus == false){ try {  
//		 register_verifone.registerPOS(); 
	//	 registerStatus = true; 

	System.out.println("Hardware Error: Verifone Device is not connected."); 
	
	} catch(Exception a1) {  } }

	if(registerStatus == true){
			
			  try { 

			String temp = "";
			System.out.println("New Message from Verifone: ");
			temp = Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ;

			JOptionPane.showMessageDialog( null, Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ); // returns mac_label
			mac_label =  temp; // returns mac_label
		//	register_verifone.startSession();
        
	  		System.out.println("New Message from Verifone: ");
	  		temp = Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ;
            System.out.println(temp);
            sessionInProgress = true;

			JOptionPane.showMessageDialog( null, Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) );  // return Session Started

          //  register_verifone.addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())), String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;

			System.out.println("New Message from Verifone: ");
			temp = Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ;
	        System.out.println(temp);

			} catch(Exception e){
				  
			System.out.println("Error starting session: " + e.toString() );
			try {
//			register_verifone.startSession();
			}catch(Exception ee) {}

			try { 
			//	  			  addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
			//				String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				
			if( table_manager.getData(table, i, 0).toString().equalsIgnoreCase("") ) 
				{
					
				// register_verifone.addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())), String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				}
				else { 
					// register_verifone.overrideLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())), String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				}

				} catch(Exception exx){
			JOptionPane.showMessageDialog( null, exx.toString() +  " : " + Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) );
				try { 
				//	register_verifone.addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
			//		String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
		//			register_verifone.overrideLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
		//			String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				} catch(Exception exe){
			JOptionPane.showMessageDialog( null, exe.toString() +  " : " + Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) );
} } } } else {  System.out.println("Register Enter Key Action Error: Credit card payment terminal cannot be reached"); } } });





/*
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "Session Started"))) { 
//			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
			JOptionPane.showMessageDialog(null,"SESSION STARTED AND IN PROGRESS");

		}
		else {
		
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));

	  			  addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;

		}


		JOptionPane.showMessageDialog(null,  " Current Response " + Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", null) );

		if( Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", null).toString().equalsIgnoreCase("SESSION in progress") ) {
//		JOptionPane.showMessageDialog(null, "Session in Progress: " + sessionInProgress);
			System.out.println("Register-> Adding a line item to Verifone Device");
	
		sessionInProgress = true;

		throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error: Session in Progress "));

		}else{
		JOptionPane.showMessageDialog(null,  " Current Response " + Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", null) );

		sessionInProgress = true;
		JOptionPane.showMessageDialog(null, "Session in Progress: " + sessionInProgress);

			System.out.println("Register-> Adding a line item to Verifone Device");
			addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
*/   	
   	/*			  sessionInProgress = false;
   				  sessionInProgress = true;
 			System.out.println("Register-> Adding a line item to Verifone Device");
			addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				  Thread.sleep(2000);
*/

	

//			addLineItem(invoice.getTransactionSubTotal(),invoice.getTransactionTaxesTotal(),formatter.format(invoice.getTransactionTotal()),table_manager.getData(table, i, 0).toString(), "",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() );




	//-----------------------------------------------------------------------------ENTER KEY END PROC

	  
	  //-----------------------------------------------------------------------------MOUSE LISTENER PROC

	  
	table.addMouseListener(new java.awt.event.MouseAdapter() {
	@Override
	public void mouseClicked(java.awt.event.MouseEvent evt) {
	
	row = table.rowAtPoint(evt.getPoint());
	col = table.columnAtPoint(evt.getPoint());
	      
	if (row >= 0 && col >= 0) {
	if(table_manager.getData(table,row,col) == null || table_manager.getData(table,row,col).toString().equalsIgnoreCase("") ) {
	if(col == 0){ editTableCell(); }
	if(col == 1) { JOptionPane.showMessageDialog(null,"Action at Quantity Column = product is null"); }
	// if(col == 6) {  updateDiscount(); }
	}
	else
	{
	  if( col == 0) { editTableCell(); }
	  if( col == 1) { updateQTY();}
	  if( col == 2) {} // updateCategory(); -- implement this function to retrieve the category according to the UPC/GTIN provided.
	  if( col == 3) { updateDescription(); }
	  if( col == 4) { updatePrice(); }
	  if( col == 5) { table.changeSelection(row,0,false,false); } 
	  if( col == 6) { updateTax(); }
	  if( col == 7) { updateDiscount(); }
	}
	updateRow(table,row);		  
	refreshTotal(table,0.00,0.00);
	table.requestFocus();
	table.changeSelection(row,0, false,false);
	}
	  
	}});

	frame.addWindowListener(new java.awt.event.WindowAdapter() {
	@Override
	public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		if (JOptionPane.showConfirmDialog(frame,"Are you sure to close this window?", "Really Closing?", 
		JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		
			System.out.println("Terminating POS System");

			try { 
//				  register_verifone.unregisterPOSAll();
				  Thread.sleep(1000);
			}catch(Exception e){
				System.out.println("error could not unregister POS");
			}
			System.exit(0);
	  }
	  else{
	      
	  }
	  
	}});
}


public void focusGained(FocusEvent e) {

	if( e.getSource() instanceof JTable) { }
	if( e.getSource() instanceof JTextArea) {
		JTextArea temp = null;
		temp = (JTextArea) e.getSource();
		
		if( temp.getName().equalsIgnoreCase( "addenda") ) { System.out.println("Reading Addenda Information: "); }
	}
}
public void focusLost(FocusEvent e) {
	if( e.getSource() instanceof JTextArea) {
		JTextArea temp = null;
		temp = (JTextArea) e.getSource();
		
		if( temp.getName().equalsIgnoreCase( "addenda") ) {
			System.out.print("Addenda Information");
			System.out.println(": " + temp.getText() );
		}
	}
}








  public void actionPerformed(ActionEvent e) {

	  if( e.getSource() instanceof JMenuItem){

    	  JMenuItem menu_item = null;
    	  menu_item = (JMenuItem) e.getSource();
    	  
    	  System.out.println( " Menu Item Selected::::: " + menu_item.getName()  );
    	  
    	  if(menu_item.getName().equalsIgnoreCase("Customers")) { JOptionPane.showMessageDialog(null,"Opening CRM"); }

    	  if(menu_item.getName().equalsIgnoreCase("ParkTransaction")) { saveTable(); }
    	  if(menu_item.getName().equalsIgnoreCase("CopyLastTransaction")) { loadInvoice(); }
    	  if(menu_item.getName().equalsIgnoreCase("AddInvoiceComment")) { addenda.requestFocus(); } 
    	  if(menu_item.getName().equalsIgnoreCase("SalesReport")) { 

    	   

    	  Object temp = null;
    	  row = 0;
    	  
    	  do {
    		  
    		  temp = table_manager.getData(table, row, 0);
    		  updateLineItemUUID(row);
    		  uploadLineItem(row);	
    		  row++; 	
    		  System.out.println("Updating Line Item with new UUID" + row);
    	  
    	  }while( temp != null);
    	  }

    		  
    	  
    	  
    	  
    	  

		  if(menu_item.getName().equalsIgnoreCase("ProductCatalogue")) {
			JOptionPane.showMessageDialog(null,"Opening Product Menu");
			RestaurantMenuGUI test = new RestaurantMenuGUI(); }

    	  if(menu_item.getName().equalsIgnoreCase("SalesReport")) { 
    		  
    		  menu_item.addActionListener(new ActionListener() {
    		
    			  public void actionPerformed(ActionEvent ev) { try { Desktop.getDesktop().browse(new URI("https://lockwind.com/test/SalesReport.php" )); }  catch (Exception e1) { e1.printStackTrace(); } } }); 
    		  
    	  }
    	  if(menu_item.getName().equalsIgnoreCase("About")) { 

    		   try { Desktop.getDesktop().browse(new URI("https://www.lockwind.com/#about_us" )); }  catch (Exception e1) { e1.printStackTrace(); }
    	  
    	  }

	  
	  }
	  
      if( e.getSource() instanceof JComboBox){
          
          JComboBox temp = (JComboBox) e.getSource();
          
          if( temp.getName().equalsIgnoreCase("account_name_input"))
          {
              customer_selected = account_name_input.getSelectedItem().toString();
			  
              System.out.println("Customer Selected = " + customer_selected  );
			  JOptionPane.showMessageDialog(null,"Selected Account: " + account_name_input.getSelectedItem() );

              Customer customer = null;
			  customer = customer_management_system.getCustomerTarget(customer_selected);

			  
			  
			  // Fix the lines below and make sure that the invoices data are being set by the transaction.

			  System.out.println("-------------------------------------------------");
			  System.out.println( "@@ Printing our customer bill to data. " );
			  System.out.println(customer.getBillToName() );
			  System.out.println(customer.getBillToAddress());
			  System.out.println(customer.getBillToCity());
			  System.out.println(customer.getBillToState());
			  System.out.println(customer.getBillToZipcode());
			  System.out.println(customer.getBillToCountry());
			  System.out.println(customer.getBillToEmailAddress());
			  System.out.println(customer.getBillToPhoneNumber());
			  System.out.println( "@@ Printing our customer ship to data. " );
			  System.out.println(customer.getShipToName() );
			  System.out.println(customer.getShipToAddress());
			  System.out.println(customer.getShipToCity());
			  System.out.println(customer.getShipToState());
			  System.out.println(customer.getShipToZipcode());
			  System.out.println(customer.getShipToCountry());
			  System.out.println(customer.getShipToEmailAddress());
			  System.out.println(customer.getShipToPhoneNumber());
			  System.out.println("-------------------------------------------------");
			  
			  
			  
			  invoice.setBillToCustomerNameData( customer.getBillToName() );
			  invoice.setBillToCustomerCodeData( customer.getBillToCode() );
			  invoice.setBillToCustomerAddressData(customer.getBillToAddress());
           	  invoice.setBillToCustomerCityData(customer.getBillToCity());
           	  invoice.setBillToCustomerStateData(customer.getBillToState());
           	  invoice.setBillToCustomerZipcodeData(customer.getBillToZipcode());
           	  invoice.setBillToCustomerCountryData(customer.getBillToCountry());
              invoice.setBillToCustomerEmailAddressData(customer.getBillToEmailAddress());
              invoice.setBillToCustomerPhoneNumberData(customer.getBillToPhoneNumber() );

              
			  invoice.setShipToCustomerNameData( customer.getShipToName() );
			  invoice.setShipToCustomerCodeData( customer.getShipToCode() );
			  invoice.setShipToCustomerAddressData(customer.getShipToAddress());
           	  invoice.setShipToCustomerCityData(customer.getShipToCity());
           	  invoice.setShipToCustomerStateData(customer.getShipToState());
           	  invoice.setShipToCustomerZipcodeData(customer.getShipToZipcode());
           	  invoice.setShipToCustomerCountryData(customer.getShipToCountry());
              invoice.setShipToCustomerEmailAddressData(customer.getShipToEmailAddress());
              invoice.setShipToCustomerPhoneNumberData(customer.getShipToPhoneNumber() );

              
              
           	  label_bill_to_customer_name_data.setText( customer.getBillToName() );
			  label_bill_to_customer_address_data.setText( invoice.getBillToCustomerAddressData() );
              label_bill_to_customer_city_data.setText( invoice.getBillToCustomerCityData() + ", " + invoice.getBillToCustomerStateData() + ", " + invoice.getBillToCustomerZipcodeData()+ ", " + invoice.getBillToCustomerCountryData() );              
              
              // label_bill_to_customer_state_data.setText( invoice.getBillToCustomerCountryData() );
              // label_bill_to_customer_state_data.setText( invoice.getBillToCustomerStateData() );
  //            label_bill_to_customer_zipcode_data.setText( invoice.getBillToCustomerZipcodeData() );
              label_bill_to_customer_country_data.setText( invoice.getBillToCustomerCountryData() );
              label_bill_to_customer_phone_number_data.setText( invoice.getBillToCustomerPhoneNumberData() );
              label_bill_to_customer_email_address_data.setText( invoice.getBillToCustomerEmailAddressData());


           	  label_ship_to_customer_name_data.setText( customer.getShipToName() );
			  label_ship_to_customer_address_data.setText( invoice.getShipToCustomerAddressData() );
              label_ship_to_customer_city_data.setText( invoice.getShipToCustomerCityData() + ", " + invoice.getShipToCustomerStateData() + ", " + invoice.getShipToCustomerZipcodeData()+ ", " + invoice.getShipToCustomerCountryData() );

              label_ship_to_customer_country_data.setText( invoice.getShipToCustomerCountryData() );
              label_ship_to_customer_phone_number_data.setText( invoice.getShipToCustomerPhoneNumberData() );
              label_ship_to_customer_email_address_data.setText( invoice.getShipToCustomerEmailAddressData());

              
              
//			  invoice.setShipToCustomerCodeData(customer_management_system.getCustomerShipToName(customer_selected));
//			  invoice.setShipToCustomerAddressData(customer_management_system.getCustomerShipToAddress(customer_selected));
//			  invoice.setShipToCustomerCityData(customer_management_system.getCustomerShipToName(customer_selected));
//			  invoice.setShipToCustomerStateData(customer_management_system.getCustomerShipToName(customer_selected));
//			  invoice.setShipToCustomerZipcodeData(customer_management_system.getCustomerShipToName(customer_selected));
//			  invoice.setShipToCustomerCountryData(customer_management_system.getCustomerShipToName(customer_selected));
 



			  
			  //               customer_management_system.getCustomerBillToName( customer_selected ) ;
              
              // This must be updated to reflect all of the customer information in the customer fields. For both bill to and ship to.
              /*
              		  
              		 
              label_ship_to_customer_name_data.setText( customer_management_system.getCustomerShipToName( customer_selected ) );
              label_ship_to_customer_address_data.setText( customer_management_system.getCustomerShipToAddress( customer_selected ) );
              label_ship_to_customer_city_data.setText( customer_management_system.getCustomerShipToCity( customer_selected ) );
              label_ship_to_customer_state_data.setText( customer_management_system.getCustomerShipToState( customer_selected ) );
              label_ship_to_customer_zipcode_data.setText( customer_management_system.getCustomerShipToZipcode( customer_selected ) );
              label_ship_to_customer_country_data.setText( customer_management_system.getCustomerShipToCountry(customer_selected )  );
              label_ship_to_customer_email_address_data.setText( customer_management_system.getCustomerShipToEmailAddress( customer_selected ) );
              label_ship_to_customer_phone_number_data.setText( customer_management_system.getCustomerPhoneNumber( customer_selected ) );
             */
              
              table.requestFocus();
              table.changeSelection(0,0, false,false);
              
              
          }
          else{}
          
          if( temp.getName().equalsIgnoreCase("payment_method")) {
        	  
            	System.out.println("Enter Key pressed");
            	System.out.println(payment_method.getSelectedItem() + " selected");
            	transaction_payment_method = payment_method.getSelectedItem().toString();
				invoice.setPaymentMethod(transaction_payment_method);
				System.out.println( "TransactionPaymentMethod:" + invoice.getPaymentMethod() +";");
            	tender_amount.requestFocus();
          
      }
      }
      
      
      if(e.getSource() instanceof JTextField )
      {
          
          JTextField temp = (JTextField) e.getSource();
          
          if( temp.getName().equalsIgnoreCase("tender_amount"))
          {
        	    transaction_tender_amount = Double.parseDouble(String.valueOf(tender_amount.getText()) );
                System.out.println("Tender Amount: " + transaction_tender_amount);
                
                tenderAction(transaction_tender_amount);
        	  
          }else {}
          
          
          if( temp.getName().equalsIgnoreCase("account_name_input")){ JOptionPane.showMessageDialog(null,"Action Source: " + temp.getName() ) ; }
          else{ }
      }
      
      if(e.getSource() instanceof JButton )
      {
          JButton temp = (JButton) e.getSource();
          

          if( temp.getName().equalsIgnoreCase("save_table")) { saveTable(); }

          
          if( temp.getName().equalsIgnoreCase("load_table")) { loadTable(); }
          
          
          if( temp.getName().equalsIgnoreCase("customers"))
          {

              NewCustomer test = new NewCustomer();
              test.setScreenSize();
              test.setFrame();
              test.setComponentDefaultValues();
              test.finishFrame();

          }
          if( temp.getName().equalsIgnoreCase("suppliers"))
          {
              
              NewSupplier test = new NewSupplier();
              test.setScreenSize();
              test.setFrame();	
              test.setComponentDefaultValues();
              test.finishFrame();
              
          }

          if( temp.getName().equalsIgnoreCase("reports"))
          {
              JOptionPane.showMessageDialog(null,"Button Source: " + temp.getName() ) ;
          }

          if( temp.getName().equalsIgnoreCase("update"))
          {
              // account_name_input = new JComboBox<String>( customer_management_system.printComboBoxList() );
              // account_name_input.removeAllItems();
              //customer_management_system.resetCustomerDatabase();
              customer_management_system.loadCustomerDatabase();
              customer_management_system.printCustomerList();
              // account_name_input.setModel(null);
              account_name_input.setModel(new DefaultComboBoxModel( customer_management_system.printComboBoxList() ));

          }

          if( temp.getName().equalsIgnoreCase("price_change"))
          {

//            	  try { Desktop.getDesktop().browse(new URI("http://lockwind.com/test/PIM/price_change.php?retailer_uuid=" + retailerUUID )); } 
              int row = 0; int col = 0;
              row = table.getSelectedRow();
              col = 0;
//              col = table.getSelectedColumn();

              try { 
                  if( table_manager.getData(table,row,col).toString() != "")
                  {
                	  String x = table_manager.getData(table,row,col).toString();
                	  
                	  try { Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php?retailer_uuid=" + retailerUUID + "&reference_code="+x+"&user_action=price_change" )); } 
                	  catch (Exception e1) { e1.printStackTrace(); }
                  }else {}
            	  
              }catch(NullPointerException no_product_available)
              {
            	  JOptionPane.showMessageDialog(null,"Please scan / enter product barcode to launch price change.");
            	  System.out.println(no_product_available.toString() );
              
              }
        	  
          }

          
          if( temp.getName().equalsIgnoreCase("pay_online"))
          {

            	  try { Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pay?retailer_uuid=" + retailerUUID )); } 
            	  catch (Exception e1) { e1.printStackTrace(); }
          }
          
          if( temp.getName().equalsIgnoreCase("verifone_manager"))
          {

      		//String verifone_device_address = "192.168.50.198";
        	  try { 
    		String verifone_device_address = http.getVerifoneDeviceAddress(posUUID);
    		System.out.println();
    		System.out.println("New Verifone Device Address:\n\n *** " + verifone_device_address + " *** " );
      		int verifone_main_port = 5015;
    		int verifone_secondary_port = 5016; 

      		SessionManager client = new SessionManager(verifone_device_address, verifone_main_port,verifone_secondary_port);
    		client.setVisible(true);

        	  }
        	  catch(Exception exxx)
        	  {
        		  exxx.printStackTrace();
        	  }
        	  
      		
          }
          
          
          if( temp.getName().equalsIgnoreCase("new_stock"))
          {
        	  
//     	  try { Desktop.getDesktop().browse(new URI("http://lockwind.com/test/PIM/price_change.php?retailer_uuid=" + retailerUUID )); } 
          row = 0; col = 0;
          row = table.getSelectedRow();
          col = 0;

          if(table_manager.getData(table, row, col) != null)  {
    	  try { 
//        	  String x = table_manager.getData(table,row,col).toString();
//    		  Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php?retailer_uuid=" + retailerUUID + "&reference_code=" + x ));
    		  Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php?retailer_uuid=" + retailerUUID ));
          } catch(Exception e2) {

        	  try { 
        		  Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php?retailer_uuid=" + retailerUUID ));
        	  } 
        	  catch (Exception e1) { e1.printStackTrace(); }

        	  e2.printStackTrace();
          }
          }
          else { 
        	  System.out.println("Opening new stock window because table manager is null;");
        	  try { 
        		  Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php?retailer_uuid=" + retailerUUID ));
  
        	  } catch (Exception e1) { e1.printStackTrace(); }

          }
        	  
          }
          if( temp.getName().equalsIgnoreCase("inventory")) {
              QTY test = new QTY();
              test.setScreenSize();
              test.setFrame();
              test.setComponentDefaultValues();
              test.finishFrame();            }
          
          if( temp.getName().equalsIgnoreCase("sales_report_button"))
          {
        	  try { 
        	      Desktop.getDesktop().browse(new URI("https://lockwind.com/test/SalesReport.php"));
        		  // downloadAPILineItem();
        	    } catch (Exception e1) {
        	        e1.printStackTrace();
        	    }
          }
          
          if( temp.getName().equalsIgnoreCase("pim_button"))
          {
        	  try {
		      		Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php"));
        		} catch (Exception e1) {
        	        e1.printStackTrace();
        	    }
          }
          
          if( temp.getName().equalsIgnoreCase("tender")){
              if(table.isEditing()){table.getCellEditor().stopCellEditing();}
              tenderAction(0.00); 
              // 1/24/24 ->Modified the behavior of this block to use the new createElectronicDocumentMethod for regression testing.
              // createElectronicDocument();
      }
          if( temp.getName().equalsIgnoreCase("tender_cash")){
              if(table.isEditing()){table.getCellEditor().stopCellEditing();}
              tenderAction(0.00);
      }

      }
      }

  
  
  private void saveInvoice() {
          
    	  File file = new File(System.getProperty("user.home") + "/invoice/" + invoice.getInvoiceNumber() + ".txt");
          try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
        	  DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        	  Vector<Vector> dataVector = tableModel.getDataVector();
              oos.writeObject(dataVector);
              JOptionPane.showMessageDialog(this, "Table saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
          } catch (IOException ex) {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(this, "Error saving table.", "Error", JOptionPane.ERROR_MESSAGE);
          }
      }
  private void loadInvoice() {
      
          File file = new File(System.getProperty("user.home") + "/invoice/" + (Integer.parseInt(invoice.getInvoiceNumber() ) - 1) + ".txt");
         
          try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
              Vector<Vector<Object>> dataVector = (Vector<Vector<Object>>) ois.readObject();
              DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
              tableModel.setRowCount(0);
              for (Vector<Object> row : dataVector) {
                  tableModel.addRow(row);
              }
              JOptionPane.showMessageDialog(this, "Table loaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
          } catch (IOException | ClassNotFoundException ex) {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(this, "Error loading table.", "Error", JOptionPane.ERROR_MESSAGE);
          }
           table_manager.clearTableColumn(table,9); 
          // clear line item UUID -> reload with new values for the line items.
      }
      
  	
  private void saveTable() {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/invoice/parked/"));
      
      int option = fileChooser.showSaveDialog(this);
      if (option == JFileChooser.APPROVE_OPTION) {
          
    	  File file = fileChooser.getSelectedFile();
          try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
        	  DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        	  Vector<Vector> dataVector = tableModel.getDataVector();
              oos.writeObject(dataVector);
              JOptionPane.showMessageDialog(this, "Table saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
          } catch (IOException ex) {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(this, "Error saving table.", "Error", JOptionPane.ERROR_MESSAGE);
          }
      }
  }

  private void loadTable() {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/invoice/parked/"));
      
      int option = fileChooser.showOpenDialog(this);
      if (option == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
         
          try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
              Vector<Vector<Object>> dataVector = (Vector<Vector<Object>>) ois.readObject();
              DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
              tableModel.setRowCount(0);
              for (Vector<Object> row : dataVector) {
                  tableModel.addRow(row);
              }
              JOptionPane.showMessageDialog(this, "Table loaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
          } catch (IOException | ClassNotFoundException ex) {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(this, "Error loading table.", "Error", JOptionPane.ERROR_MESSAGE);
          }
      }
      table_manager.clearTableColumn(table,9);

  }  
  
  
  
public void actionGTIN() {
  
    String temp = "";

    if(table.isEditing()){table.getCellEditor().stopCellEditing();}
	  
	  i = 0;
	  j = 0;
	  
	  i = table.getSelectedRow();
	  j = table.getSelectedColumn();

	  try { 
        temp = table_manager.getData(table,i,j).toString();        
        System.out.println("Method started: actionGTIN: " + temp);
        
	  }
	  catch(NullPointerException null_pointer) { }
}

  public void updateOnHand(JTable table){

	  int i = 0;
	  int qty_sold = 0;
	  int qty_current = 0;
	  int qty_on_hand = 0;
  
	  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
  
	  i = table.getSelectedRow();
	  //  j = table.getSelectedColumn();
  
	  inventory_manager = new QTY(false);
  
	  qty_sold = inventory_manager.getQtySold( table_manager.getData(table,i,0).toString() );
	  qty_current =  inventory_manager.getCurrentQty( table_manager.getData(table,i,0).toString() );
	  qty_on_hand = qty_current - qty_sold;
  	  table_manager.setData(table,i,7,String.valueOf( qty_on_hand ) );

}

 public double getTableSubTotalValue(){ return table_manager.getColumnTotal(table,5) ; } // 6/26/23 Updated value to column 5 per new jTable design including category
 public double getTableTaxValue(){ return table_manager.getColumnTotal(table,6); } // 6/26/23 Updated value to column 6 per new jTable design including category
 public double getTableDiscountValue() { return table_manager.getColumnTotal(table,7); }  // 6/26/23 Updated value to column 6 per new jTable design including category
 
 public void refreshTableSubtotalValue(){
	
	  System.out.println("TableManager->@refreshTableSubtotalValue()");
	  double sub_total_value  = 0.00;
	  sub_total_value = this.getTableSubTotalValue(); 
	  invoice.setTransactionSubTotal(String.valueOf( sub_total_value ));
	 
	  
}
public void refreshTableTaxvalue(){
	  System.out.println("TableManager->@refreshTableTaxValue()");
	  double tax_value        = 0.00;
	  tax_value       = this.getTableTaxValue();
	  invoice.setTransactionTaxesTotal( String.valueOf( tax_value ));

}
public void refreshTableDiscountValue(){
	  System.out.println("TableManager->@refreshTableDiscountValue()");
	  double discount_value   = 0.00;
	  discount_value  = this.getTableDiscountValue();
	  invoice.setTransactionDiscountTotal( String.valueOf(discount_value ));

}
  
public void refreshTableTotalValue(){
	  System.out.println("TableManager->@refreshTableTotalValue()");
	  double total_value      = 0.00;
	  total_value     = ( this.getTableSubTotalValue() + this.getTableTaxValue() ) - this.getTableDiscountValue();
	  invoice.setTransactionTotal( formatter.format(String.valueOf(total_value) ) );

}

public void refreshTotal()
{
  
  System.out.println("TableManager->@refreshTotal()");
  
  subtotalLabel.setText( "$ "+ formatter.format( Double.parseDouble(invoice.getTransactionSubTotal() ))); // Set value to UI Label
  taxesLabel.setText( "$ "+ formatter.format( Double.parseDouble(invoice.getTransactionTaxesTotal() ))); // Set value to UI Label
  totalLabel.setText( "$ "+ formatter.format( Double.parseDouble(invoice.getTransactionTotal() ))); // Set value to UI Label
  discountLabel.setText( "$ "+ formatter.format( Double.parseDouble(invoice.getTransactionDiscountTotal() ))); // Set value to UI Label
  
  
// verifone.endSession();
}





public void readElectronicDocument() {  
	System.out.println(" Register->@readElectronicDocument() "); }

public void updateElectronicDocument() {  
	System.out.println(" Register->@updateElectronicDocument() "); }

public void deleteElectronicDocument() {  
	System.out.println(" Register->@deleteElectronicDocument() "); }

public void saveElectronicDocument() {  
	System.out.println(" Register->@saveElectronicDocument() "); }

public void sendElectronicDocument() {  
        System.out.println("Process Activation: Register->@sendElectronicDocument()-> Uploading to Lockwind Cloud:");	
}

public void loadElectronicDocument() {  
	System.out.println(" Register->@loadElectronicDocument() "); }


  public void refreshTotal(JTable table, double tender_value, double change_value) {
  
  System.out.println("TableManager->@refreshTotal(table,double,double)");
  
  double sub_total_value  			= 0.00;
  double tax_value        			= 0.00;
  double total_value      			= 0.00;
  double discount_value   			= 0.00;
  String tender_value_string 		= "";
  String change_value_string 		= "";
  String sub_total_value_string 	= "";
  String tax_value_string 			= "";
  String total_value_string 		= "";
  String discount_value_string 		= "";

  sub_total_value = table_manager.getColumnTotal(table,5) ; // 6/26/23 Updated value to column 5 per new jTable design including category
  tax_value       = table_manager.getColumnTotal(table,6);  // 6/26/23 Updated value to column 6 per new jTable design including category
  discount_value  = table_manager.getColumnTotal(table,7);  // 6/26/23 Updated value to column 7 per new jTable design including category
  total_value     = (sub_total_value + tax_value) - discount_value;

  invoice.setTransactionSubTotal( String.valueOf(sub_total_value));
  invoice.setTransactionTaxesTotal( String.valueOf(tax_value));
  invoice.setTransactionDiscountTotal( String.valueOf(discount_value));
  invoice.setTransactionTotal( String.valueOf(total_value));

  sub_total_value_string 	= formatter.format( Double.parseDouble(invoice.getTransactionSubTotal() ) );
  tax_value_string 			= formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal() ) );
  total_value_string 		= formatter.format( Double.parseDouble(invoice.getTransactionTotal()) );
  discount_value_string 	= format_manager.formatDoubleUS( Double.parseDouble(invoice.getTransactionDiscountTotal() ) );
  tender_value_string 		= formatter.format(tender_value);
  change_value_string 		= formatter.format(change_value);

  subtotalLabel						. setText( "$ "+  sub_total_value_string); // Set value to UI Label
  taxesLabel						. setText( "$ "+  tax_value_string ); // Set value to UI Label
  totalLabel						. setText( "$ "+  total_value_string ); // Set value to UI Label
  discountLabel						. setText( "$ "+  discount_value_string ); // Set value to UI Label
  // tenderLabel						. setText( "$ " + invoice.getTransactionTenderValue() );
  // changeLabel						. setText( "$ " + invoice.getTransactionChangeValue() );

  													 
//  subtotalLabel						. setText( "$ "+ formatter.format(Double.parseDouble(  invoice.getTransactionSubTotal() ))); // Set value to UI Label
//  taxesLabel						. setText( "$ "+ formatter.format(Double.parseDouble(  invoice.getTransactionTaxesTotal() ))); // Set value to UI Label
//  totalLabel						. setText( "$ "+ formatter.format(Double.parseDouble(  invoice.getTransactionTotal() ))); // Set value to UI Label
//  discountLabel						. setText( "$ "+ formatter.format(Double.parseDouble(  invoice.getTransactionDiscountTotal() ))); // Set value to UI Label
  

  
  System.out.println(" Register.refreshTotal() ");
  System.out.println( "-> sub_total_value = " 	+ invoice.getTransactionSubTotal() );
  System.out.println(" -> tax_value = " 		+ invoice.getTransactionTaxesTotal() );
  System.out.println(" -> discount_value = " 	+ invoice.getTransactionDiscountTotal() );
  System.out.println(" -> total_value = " 		+ invoice.getTransactionTotal() );
  System.out.println(" -> tender_value = " 		+ invoice.getTransactionTenderValue() );

  
}
  public void saveTableToReceipt(JTable table,String client_id){   
	  
  
	  System.out.println("Register->@saveTableToReceipt()");
	  // CreateReceipt test      = new CreateReceipt(); -> Does this class exist? if not then delete line
      FileWriter file         		= null;
      PrintWriter outputFile  		= null;
      String da               		= "";
      String productName 			= "";      
      int invoiceNumber       	  	= 0;
      int in 						= 0;
      SimpleDateFormat simpDate   	= null;
      Date 		 today				= null;
      DateFormat fmt              	= null;

      double tendered             	= 0.00;
      double subtotal             	= 0.00;
      double totaltaxes           	= 0.00;
      double total                	= 0.00; 
      double change               	= 0.00;
      double discount             	= 0.00;
//      double TAX_RATE             	= 0.08975;

      Locale[]   locale         	= {US,UK,GERMANY,FRANCE};
      int[]      styles        		= {FULL,LONG,MEDIUM,SHORT};
      String[]   styleNames     	= {"FULL","LONG","MEDIUM","SHORT"};
      fmt                         	= null;

      today                 		= new Date();
      fmt                   		= DateFormat.getDateInstance(styles[3], locale[0]);
      simpDate             			= new SimpleDateFormat("hh:mm:ss a");

      subtotal 						= table_manager.getColumnTotal(table,5);
      totaltaxes 					= table_manager.getColumnTotal(table,6);
      discount 						= table_manager.getColumnTotal(table,7);
      
      total 						= subtotal 	+ totaltaxes;
      total 						= total 	- discount;
      change 						= tendered 	- total;

      
      try { invoiceNumber = Integer.parseInt(invoice.getInvoiceNumber() ); }catch(Exception e){}
      
      
	  try {
		    
		 
	        System.out.println("Register:saveTableToReceipt -> Invoice Number: " + invoice.getInvoiceNumber() + " -> UUID: " + invoice.getTransactionUUID() );
		
		invoice										.setDirectory("./");
		invoice										.setTransactionType("Invoice");
		invoice										.setTransactionDate(fmt.format(today));
		invoice										.setTransactionTime(simpDate.format(today));

//		invoice										.setIssuerUUID( invoice.getIssuerUUID() );
//		invoice										.setLocationUUID( invoice.getLocationUUID() );

		// invoice										.setTransactionUUID(invoice.getIssuerUUID(),invoice.getInvoiceNumber());

		invoice										.setConsumerUUID(invoice.getConsumerUUID() );		
		invoice										.setLabelIssuerAddressData( invoice.getStoreAddress()  +  " " + invoice.getStoreSecondAddress() );
		invoice										.setLabelIssuerPhoneNumberData( invoice.getStorePhoneNumber() );
		invoice										.setLabelIssuerFaxNumberData( invoice.getStoreFaxNumber() );
		
		invoice										.setLabelIssuerAddress("Address: ");
		invoice										.setLabelIssuerPhoneNumber("TEL: ");
		invoice										.setLabelIssuerFaxNumberData("Fax: ");
		invoice										.setLabelIssuerCity("City: ");
		invoice										.setLabelIssuerCountry("Country: ");
		
		invoice										.setBillToCustomerCodeData( account_name_input.getSelectedItem().toString() );
		invoice										.setStoreName( invoice_management_system.getEntityName(invoice.getIssuerUUID()) );
		invoice										.setLabelIssuerPhoneNumberData("+1 212 740 4652");
		invoice										.setBillToCustomerCodeData( account_name_input.getSelectedItem().toString() );
		invoice										.setStorePhoneNumber( invoice.getLabelIssuerPhoneNumberData() );
		invoice										.setLabelIssuerFaxNumberData("+1 347 808 5425");
		
		invoice										.setBillToCustomerNameData( account_name_input.getSelectedItem().toString() );
		invoice										.setBillToCustomerEmailAddressData( account_name_input.getSelectedItem().toString() );
		invoice										.setBillToCustomerPhoneNumberData( account_name_input.getSelectedItem().toString() );
		
		System.out.println("************************************************");
	//	System.out.println(invoice.toString());
		
		}catch(Exception e) {
			
			System.out.println("Error: " + e.toString() );
			
		}

      
      try{
          
//          file            		= 	  new FileWriter("INV saveTableToReceipt "+ String.valueOf(invoiceNumber) +".txt");
          file            		= 	  new FileWriter("INV saveTableToReceipt "+ invoice.getInvoiceNumber() +".txt");
          outputFile      		= 	  new PrintWriter(file);



          outputFile.println    ("----------------------------------------");
          /*
          outputFile.println        (erp.getStoreName())        ;
          outputFile.println(erp.getStoreAddress() + " " + erp.getStoreSecondAddress());
          outputFile.println(""+erp.getStorePhoneNumber()+ "      " +erp.getStoreFaxNumber());
          outputFile.println();
          outputFile.println("Hello World from Briant Guzman\n");
          //outputFile.println("CODE:  CASH");
          */
          
          outputFile.println(invoice.getStoreName() );
          outputFile.println(invoice.getBillToCustomerAddressData() 		+ " " 		+ invoice.getBillToCustomerAddressData());
          outputFile.println(""+ invoice.getBillToCustomerPhoneNumberData() + "      "  + invoice.getBillToCustomerFaxNumberData());
          outputFile.println();
          
          
          outputFile.println(fmt.format(today) + "    " + simpDate.format(today) + "   Invoice No: " + invoiceNumber);
          outputFile.println("CUSTOMER CODE: " 			+ account_name_input.getSelectedItem() );
          outputFile.println("CUSTOMER Name: " 			+ account_name_input.getSelectedItem() );
          outputFile.println("CUSTOMER Phone Number: " 	+ account_name_input.getSelectedItem() );
          outputFile.println("CUSTOMER Email: " 		+ account_name_input.getSelectedItem() );
          outputFile.println("NAME: ");
          outputFile.println("REG:   REGISTER 1");
          outputFile.println("----------------------------------------");
          outputFile.println(" QTY UPC                 PRICE  SUBTOTAL");
          outputFile.println(" DESCRIPTION                            ");

          
          while(table_manager.getData(table,in,0) != null && (!table_manager.getData(table,in,0).toString().equals("")) ){
              
              da = " ";
              if(table_manager.getData(table,in,1)!= null){da = da + format_manager.increaseLength(table_manager.getData(table,in,1).toString(),3);}
              if(table_manager.getData(table,in,0)!= null){da = da + format_manager.increaseLength(table_manager.getData(table,in,0).toString(),19);}
              
              if(table_manager.getData(table,in,2)!= null){
            	  
                  productName = table_manager.getData(table,in,2).toString();
                  productName = format_manager.increaseLength(productName,43);
                  productName = " "+ productName.substring(0,42);}
              
              if(table_manager.getData(table,in,4)!= null){
            	  
                  da = da + format_manager.increaseLength(table_manager.getData(table,in,3).toString(),10);
                  da = da + format_manager.increaseLength(table_manager.getData(table,in,4).toString(),4);}
              
              outputFile.println(da);
              outputFile.println(productName+"\n");
                            
              da = "";
              in++;
          }
          
          
          /*
          String amount = JOptionPane.showInputDialog("Please Enter Amount Given");
          tendered = Double.parseDouble(	record.getTransactionTenderValue() );
          subtotal = Double.parseDouble( 	record.getTransactionTotal() );
          totaltaxes = Double.parseDouble( 	record.getTransactionTaxesTotal() );
          discount = Double.parseDouble(	record.getTransactionDiscountTotal() );
          // NumberFormat formatter = new DecimalFormat("#0.00");

          //
          try {
              ClientInvoiceReport http = new ClientInvoiceReport();
              http.setInformation(account_name_input.getSelectedItem().toString(),String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD",formatter.format(total),formatter.format(tendered),formatter.format(change));
              
              in = 0;
          	
              ClientInvoiceReport http = new ClientInvoiceReport();
          	  
          	  JOptionPane.showMessageDialog(null, "ITEM COUNT: " + item_count);
              System.out.println("UPLOADING PRODUCT:");
              http.sendProductPost(client_id,client_name,String.valueOf(invoiceNumber),table_manager.getData(table,in,0).toString(),table.getData(table,in,1).toString(),getData(table,in,2).toString(),getData(table,in,3).toString(),getData(table,in,4).toString(),getData(table,in,5).toString(),getData(table,in,6).toString(),getData(table,in,7).toString() );

				Thread.sleep(1000);

          
          
          }catch(Exception e){}
          
           */
          

          
          outputFile.println("                         SUB TOTAL $ "+ formatter.format(subtotal));
          outputFile.println("                         SALES TAX $ "+ formatter.format(totaltaxes));
          if(discount != 0.00) { 
          outputFile.println("                         DISCOUNT  $ "+ formatter.format(discount));} else{}
          outputFile.println("                         TOTAL     $ "+ formatter.format(total) + "\n");
          outputFile.println("                         TENDERED  $ " + formatter.format(tendered));
          outputFile.println("                         CHANGE    $ " + formatter.format(change));
          
//           JOptionPane.showMessageDialog(null,"Change: " + "$"+formatter.format(change));
          
          refreshTotal(table,tendered,change);
          
          int item_count = 0;
          
          for( int i = 0; i < table.getRowCount();i++) {
              
        	  if(table.getValueAt(i,0) == null || table.getValueAt(i,0).toString().equalsIgnoreCase("")  ) { } 
        	  else{ item_count++; }
          }
          outputFile.println("Item Count: " + item_count);
          outputFile.println("----------------------------------------");
          outputFile.println("Thank you for shopping with " + invoice.getStoreName() + "");
          outputFile.println("STORE HOURS: ");
          outputFile.println("MONDAY - SATURDAY: 8:00 AM - 8:00 PM");
          outputFile.println("SUNDAY: 9:00 AM - 4:00 PM ");
          outputFile.println("NO CASH REFUNDS ");
          outputFile.println("STORE CREDIT / EXCHANGE ONLY WITH ORIGINAL RECEIPT");
          
          outputFile.println("For the best Point of Sale System call Lockwind at +1 347 808 5425");
          outputFile.println("----------------------------------------");
          
          outputFile.close();
          
          
          invoice.buildInvoice();
          

          
      }catch(IOException e){}

      
      try{Process p =  Runtime.getRuntime().exec("cmd /c printReceipt.bat");}
      catch(IOException eex){}
  
  
      table.requestFocus();
      table.changeSelection(0,0, false,false);      
  
  } // End Function/Method here.

  
  
  
  
  
  
  
  
  public void updateQTY(){

	  System.out.println("Register->UpdateQTY");
	  int i,j = 0;
  
	  if(table.isEditing()){ table.getCellEditor().stopCellEditing(); }
  
	  i = table.getSelectedRow();
	  j = table.getSelectedColumn();
	  
	  String inputQty = "";
	  inputQty = JOptionPane.showInputDialog(null,"Enter the Quantity: ",table_manager.getData(table,i,j).toString());

	  error_message = validator.validateQuantity(inputQty);
  
	  if(error_message.equalsIgnoreCase("") == true) {  table_manager.setData(table,i,1,inputQty);  }
	  else { JOptionPane.showMessageDialog(null, error_message); }
	  table_manager.setData(table,i,5,table_manager.getSubTotal(table,i));
	  table_manager.setData(table,i,6,table_manager.getTax(table,i));
	  
	  updateTax();
 
	  // table_manager.setData(table,i,1,"1");
	  // refreshTotal(table,0.00,0.00);
	  // table.changeSelection(++i,0,false,false);
  }
  
  /*
  public void updateDescription(){
  int i = 0; int j = 0;
  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
  
  i = table.getSelectedRow();
  j = table.getSelectedColumn();
  String inputQty = "";
  inputQty = JOptionPane.showInputDialog(null,"Enter the Product Name:",table_manager.getData(table,i,j).toString());
  
if(inputQty == null || (inputQty != null && ("".equals(inputQty))))          {
      
  }
  else{
      table_manager.setData(table,i,3,inputQty);
      //table_manager.setData(table,i,4,table_manager.getSubTotal(table,i));
	  //table_manager.setData(table,i,5,table_manager.getTax(table,i));
      
      // refreshTotal(table,0.00,0.00);
  }
  table.changeSelection(++i,0,false,false);

}
  
  */
  
  public void updateDescription() {
      if (table.isEditing()) {
          table.getCellEditor().stopCellEditing();
      }

      int[] activecell = new int[2];
      
      
      int selectedRow = table.getSelectedRow();
      int selectedColumn = table.getSelectedColumn();

      if (isValidRowAndColumn(selectedRow, selectedColumn)) {
          String inputQty = JOptionPane.showInputDialog(null, "Enter the Product Name:", table_manager.getData(table, selectedRow, selectedColumn).toString());

          model.getColumnCount();
          model.getRowCount();
          
          if (isValidInput(inputQty)) {
              // Validate and sanitize the input if needed
              table_manager.setData(table, selectedRow, PRODUCT_NAME_COLUMN, inputQty);
              // Uncomment the lines below if needed
              // table_manager.setData(table, selectedRow, 4, table_manager.getSubTotal(table, selectedRow));
              // table_manager.setData(table, selectedRow, 5, table_manager.getTax(table, selectedRow));

              // refreshTotal(table, 0.00, 0.00);
              table.changeSelection(++selectedRow, 0, false, false);
          } else {
              // Handle invalid or empty input
              JOptionPane.showMessageDialog(table, "Invalid input. Please enter a valid product name.", "Error", JOptionPane.ERROR_MESSAGE);
          }
      }
  }
  
  private boolean isValidRowAndColumn(int row, int column) {
      return row >= 0 && column >= 0;
  }

  private boolean isValidInput(String input) {
	  if (input == null) {
	        return false;
	    }

	    // Allow only alphanumeric characters and space
	    String sanitizedInput = input.replaceAll("[^a-zA-Z0-9\\s]", "");

	    // Check if the sanitized input is not empty
	    return !sanitizedInput.trim().isEmpty();
	    }
  
  public void updatePrice(){

  int i = 0; int j = 4;
  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
  
  i = table.getSelectedRow();
//  j = table.getSelectedColumn();

  String inputQty = "";
  
  inputQty = JOptionPane.showInputDialog(null,"Enter New Price -> $ xx.xx ",table_manager.getData(table,i,j).toString());
  error_message = validator.validatePrice(inputQty);
  System.out.println("Register.updatePrice() -> New price entered: " + inputQty);

  
  if(error_message.equalsIgnoreCase("0") ) {
	
	table_manager.setData(table,i,4,inputQty);
  } else {
//	  JOptionPane.showMessageDialog(null, error_message);
	  inputQty = JOptionPane.showInputDialog(null,"Please enter new price: $ xx.xx ",table_manager.getData(table,i,j).toString());
	  validator.validatePrice(inputQty);
		  
	// table_manager.setData(table,i,4,".99");
  }
 
   table_manager.setData(table,i,5,table_manager.getSubTotal(table,i));
   table_manager.setData(table,i,6,table_manager.getTax(table,i));
  // refreshTotal(table,0.00,0.00);
  
   table.requestFocus();
  table.changeSelection(++i,0,false,false);
  

}
  public void updatePricePermanent(String gtin){
  
  String a = "";
  String b = "";
  String c = "";
  String d = "";
  
  
  String x = product_management_system.getProductInfo(gtin);
  
  System.out.println(x);

  StringTokenizer str = null;
  
  str = new StringTokenizer(x,",");

  while(str.hasMoreTokens()){
      a = str.nextToken();
      b = str.nextToken();
      c = str.nextToken();
      d = str.nextToken();
  }

}
  public void updateTax(){
	  
  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
  
  int i = 0;
  int j = 0;

  i = table.getSelectedRow();
  j = 6; // 6 is currently the tax column in the JTable, hardcoded
  
  if(table_manager.getData(table,i,j).toString().equalsIgnoreCase("") || table_manager.getData(table,i,j) == null)
  {
      table_manager.setData(table,i,j,table_manager.getTax(table,i));
  }
  else
  {
      table_manager.setData(table,i,j,"0");
      line_item.setTaxRate(TAX_RATE);
      line_item.setTaxes();
  }
  
  refreshTotal(table,0.00,0.00);

}



  
  

  public void updateDiscount(){
  
  int i = 0; int j = 0;
  
  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
  
  	formatter = new DecimalFormat("#0.00");
  
  	i = table.getSelectedRow();
  	j = table.getSelectedColumn();

  
  	String inputQty = "";
  	inputQty = ali.requestDiscount();
  
  System.out.println("The customer has requested a discount of " + inputQty + " %");

  table_manager.setData(table,i,6,inputQty);

  table_manager.applyDiscount(table,i,6,inputQty);
 
  // refreshTotal(table,0.00,0.00);
  
  table.changeSelection(++i,0,false,false);
}

/*
  public void TenderAPI() { // This is the new tender action that will be activated, by button, m
	
	  this.createElectronicDocument();
      this.sendElectronicDocument();
      this.saveElectronicDocument();  
      
  }
*/
  
  
  public void downloadAPILineItem() 
  { 
	  
	  System.out.println("Function Action: downloadLineItem -> row: " + i);
	  
	  
	  /*
	  String[] temp = new String[table.getColumnCount()*2];
      j = 0;
      for(int k = 0; k < table.getColumnCount(); k++) { // Per column  
    	  if(table_manager.getData(table,i,j) == null) { // Column value is null 
    		  temp[k] = "";
//    		  temp[i] = String.valueOf("");
  	//System.out.println("NULL VALUE FOUND AT TABLE: " + i + "," + j);
  	} else { 
  		
  		temp[k] = table_manager.getData(table,i,j).toString();
  	
  	}
  	j++;
  	System.out.println("LINE ITEM COLUMN VALUE: " + j + " : " + temp[k]);
  }

  temp[10] = String.valueOf( invoice.getInvoiceNumber() );
  temp[11] = "download";
  //  temp[12] = "";
  temp[13] = invoice.getIssuerUUID();
  temp[14] = invoice.getLocationUUID();
  temp[15] = invoice.getTransactionUUID();


      */

  
  
  
  System.out.println("Register::Enter Key Action proc ->Action: DownloadLineItem -> Data: ElectronicDocumentLineItem() -> Destination: https://www.Lockwind.com ");
  	  
	  try { 
			 API test = new API();
			 invoice_number = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter invoice number") );
			 
			 String issuer_uuid = "5d4de950d4f69";
			 
			 temp =  test.downloadAPILineItem(issuer_uuid,String.valueOf(invoice_number),"scanned","","","") ;
			 Thread.sleep(2000);
			 
			 JOptionPane.showMessageDialog(null,temp);
			 System.out.println(temp);
			 
			 StringTokenizer str = new StringTokenizer(temp,",");
			 
			 i = 0;
			 j = 0;

			 String token = "";
			 int token_count = 0;
			 
			 int x = table_manager.getColumnCount();
			 

			 String csvString = "Name, Age, City\nJohn Doe, 30, New York\nJane Smith, 25, Los Angeles\nAlice Brown, 28, Chicago";

	            // Tokenize the CSV String
	            StringTokenizer tokenizer = new StringTokenizer(temp, ",");
	            String[] columnNames = tokenizer.nextToken().split(", ");
	            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

	            while (tokenizer.hasMoreTokens()) {
	                String[] rowData = tokenizer.nextToken().split(", ");
	                
	            }

					// System.out.println( table_manager.setData(table,i,j) );
			 
			 
			 }
			 catch(Exception e) {}
			 
	  	System.out.println("LINE ITEM DOWNLOAD COMPLETE");
			 
			 
		 
	  
      
//  table_manager.setData(table, i, 9, temp[12]); // set 

	  
	  
  }
  
  public void updateLineItemUUID(int i) { 
	  
		try {
 			if(table_manager.getData(table, i,9) == null || table_manager.getData(table, i,9).toString().equalsIgnoreCase("") ) {
 				String line_item_uuid = "";
 			    System.out.println( " line item uuid new one is: " + line_item_uuid);
			  	
 			    line_item_uuid = http.getUUID();
 				
		            System.out.println( " line item uuid new one is: " + line_item_uuid);
			  		line_item.setUUID(line_item_uuid);
			  		table_manager.setData( table,i,9,line_item.getUUID());	            	
	            }else { System.out.println("Enter key action proc: line item already assigned" ); }
		}catch( Exception e) {
			System.out.println("System error at updateLineItemUUID");
			
		}
	
 			
  }
  
  public void uploadLineItem( int i ) { 
	  System.out.println("Function Action: uploadLineItem -> row: " + i);
	  
	  String[] temp = new String[table.getColumnCount()*2];
      j = 0;
      for(int k = 0; k < table.getColumnCount(); k++) { // Per column  
    	  if(table_manager.getData(table,i,j) == null) { // Column value is null 
    		  temp[k] = "";
//    		  temp[i] = String.valueOf("");
  	//System.out.println("NULL VALUE FOUND AT TABLE: " + i + "," + j);
  	} else { 
  		
  		temp[k] = table_manager.getData(table,i,j).toString();
  	
  	}
  	j++;
  	System.out.println("LINE ITEM COLUMN VALUE: " + j + " : " + temp[k]);
  }

  
  
  temp[10] = String.valueOf( invoice.getInvoiceNumber() );
  temp[11] = "scanned";
  //  temp[12] = "";
  temp[13] = invoice.getIssuerUUID();
  temp[14] = invoice.getLocationUUID();
  temp[15] = invoice.getTransactionUUID();
  
  System.out.println("Register::Enter Key Action proc ->Action:Publish -> Data: ElectronicDocumentLineItem() -> Destination: https://www.Lockwind.com ");

//  table_manager.setData(table, i, 9, temp[12]); // set 

  try {
	  http.sendProductPostAPILineItem( temp[0],temp[1],temp[2],temp[3],temp[4],temp[5],temp[6],temp[7],temp[8],temp[9], temp[10],temp[11],temp[12],temp[13],temp[14],temp[15] );;
	  
      }catch(Exception e) {};

  
  temp[10] = String.valueOf( invoice.getInvoiceNumber() );
  temp[11] = "scanned";
  //  temp[12] = "";
  temp[13] = invoice.getIssuerUUID();
  temp[14] = invoice.getLocationUUID();
  temp[15] = invoice.getTransactionUUID();
  
  System.out.println("Register::Enter Key Action proc ->Action:Publish -> Data: ElectronicDocumentLineItem() -> Destination: https://www.Lockwind.com ");

//  table_manager.setData(table, i, 9, temp[12]); // set 

  try {
	  
	  FileWriter file = new FileWriter("shopping_cart.txt");
	  PrintWriter outputFile = new PrintWriter(file);
	  for(int ix = 0; ix < 16; ix++)   { outputFile.print(temp[ix] + ","); }
	  // temp[0],temp[1],temp[2],temp[3],temp[4],temp[5],temp[6],temp[7],temp[8],temp[9], temp[10],temp[11],temp[12],temp[13],temp[14],temp[15] ) ;
	  outputFile.close();
      }catch(Exception e) {};

  }
  
  public void tenderLineItem( int i ) { 

	  
	  System.out.println("Register->tenderLineItem(int i) :: + "  + table_manager.getData(table, i, 0) );
	  String[] temp = new String[table.getColumnCount()*2];
      j = 0;
for(int k = 0; k < table.getColumnCount(); k++) // Per column 
  {
  	if(table_manager.getData(table,i,j) == null) { // Column value is null 
  	//System.out.println("NULL VALUE FOUND AT TABLE: " + i + "," + j);
  		temp[k] = "";
  	}
  	else {
  	temp[k] = table_manager.getData(table,i,j).toString();
  	}
  	j++;
//   	System.out.println("LINE ITEM COLUMN VALUE: " + j + " : " + temp[k]);
  	System.out.print ( temp[k]  + " |  " );
  }

  
  
  temp[10] = String.valueOf( invoice.getInvoiceNumber() );
  temp[11] = "paid";
//  temp[12] = "";
  temp[13] = invoice.getIssuerUUID();
  temp[14] = invoice.getLocationUUID();
  temp[15] = invoice.getTransactionUUID();
  
  System.out.println("Register::Enter Key Action proc ->Action:Publish -> Data: ElectronicDocumentLineItem() -> Destination: https://www.Lockwind.com ");

//  table_manager.setData(table, i, 9, temp[12]); // set 

  try {
	  http.sendProductPostAPILineItem( temp[0],temp[1],temp[2],temp[3],temp[4],temp[5],temp[6],temp[7],temp[8],temp[9], temp[10],temp[11],temp[12],temp[13],temp[14],temp[15] );;
	  
      }catch(Exception e) {};

  }
  
  
  public void tenderAction(double transaction_tender_value)
  {
	  // Currently working on this function to make sure it works correctly. 1/7/24 (BG)
      System.out.println("Register->@tenderAction()");
      System.out.println("Register -> Invoice Number: " + invoice.getInvoiceNumber() + " -> UUID: " + invoice.getTransactionUUID() );

      if(line_item.getDescription().equalsIgnoreCase("") ) {
    	  
    	  JOptionPane.showMessageDialog(null, "Error:  line Item is null.");

      }else {
      
      System.out.println( "LINE ITEM STATE: -- >"  + line_item.toString() );
      
      row 					= 0; 
      col 					= 0;
	  da          			= "";
      fmt					= null;
      file        			= null;
      outputFile  			= null;
      tendered             	= 0.00;
      subtotal             	= 0.00;
      totaltaxes           	= 0.00;
      total                	= 0.00;
      change               	= 0.00;
      discount             	= 0.00;
      double qty_count 			= 0;

	  formatter 			= new DecimalFormat("#0.00");
      today                 = new Date();
      fmt                   = DateFormat.getDateInstance      (styles[3], locale[0]);
      simpDate             	= new SimpleDateFormat("hh:mm:ss a");

      
      if(tender_amount.getText().equalsIgnoreCase("")) { tendered				= ali.chargeCustomer(); }
      else { tendered 				= Double.parseDouble(tender_amount.getText() ); }
      
      invoice				. setTransactionTenderValue(String.valueOf( tendered ));
      
      subtotal 				= table_manager.getColumnTotal(table,5);
      totaltaxes 			= table_manager.getColumnTotal(table,6);
      discount 				= table_manager.getColumnTotal(table,7);
      
      total 				= subtotal + totaltaxes;
      total 				= total - discount;
      change 				= tendered - total;
      
      invoice				. setTransactionCurrency("USD");
      invoice				. setTransactionSubTotal(String.valueOf(subtotal));
      invoice				. setTransactionTaxesTotal(String.valueOf(totaltaxes));
      invoice				. setTransactionTotal(String.valueOf(total));
      invoice				. setTransactionDiscountTotal(String.valueOf(discount));
      invoice				. setTransactionTenderValue(String.valueOf(tendered));
      invoice				. setTransactionChangeValue(String.valueOf(change));
      invoice				. setBalanceDue(String.valueOf(total-tendered));
      
      System.out.println( invoice.toString() );

      if(change > 0.01) { JOptionPane.showMessageDialog(null,"Change: " + "$ "+ format_manager.formatDoubleUS(change)); }
      else { JOptionPane.showMessageDialog(null,"Balance not paid in full: " + "$ "+ format_manager.formatDoubleUS(change)); }
      
      row = table.getSelectedRow();
      col = table.getSelectedColumn();
      
      
      try {
          account_selected = account_name_input.getSelectedItem().toString();
          invoice.setBillToCustomerCodeData(account_name_input.getSelectedItem().toString() );
      }catch(Exception ex){}
      
      
      try{
          
    	  // This process writes the invoice number to the file locally as a backup.
          file            = new FileWriter( invoice.getDirectory() + "invoice_number" + invoice.getFileExtension() ,true);
          outputFile      = new PrintWriter(file);
          outputFile	  . println( invoice.getInvoiceNumber() );
          outputFile	  . close();
          file			  . close();
                    
          file            = new FileWriter(  invoice.getDirectory() + "INV Register-tenderaction " + invoice.getInvoiceNumber() + invoice.getFileExtension() );
          outputFile      = new PrintWriter(file);
          
          outputFile.println("----------------------------------------");
          outputFile.println("-------------" + invoice.getStoreName() + "-------------");
          outputFile.println(" " +  invoice.getStoreAddress()+ " " + invoice.getStoreSecondAddress());
          outputFile.println(" " + invoice.getStorePhoneNumber()+ "      ");
          outputFile.println( invoice.getStoreFaxNumber()+"\n");
                            
          today                 =     new Date();
          fmt                   =     DateFormat.getDateInstance      (styles[3], locale[0]);
          simpDate             	=     new SimpleDateFormat("hh:mm:ss a");
          
          outputFile.println(" " + fmt.format(today) +  "    " + simpDate.format(today) + "   Invoice No: " + invoice.getInvoiceNumber() );
          outputFile.println(" CUSTOMER CODE: " + account_name_input.getSelectedItem() );
          outputFile.println(" CUSTOMER Name: " + account_name_input.getSelectedItem() );
          outputFile.println(" CUSTOMER Phone Number: " + account_name_input.getSelectedItem() );
          outputFile.println(" CUSTOMER Email: " + account_name_input.getSelectedItem() );
          outputFile.println(" NAME:  CASH ");
          //outputFile.println("CODE:  CASH");
          outputFile.println(" REG:   REGISTER 1");
          outputFile.println("----------------------------------------");
          
          outputFile.println(" QTY UPC                 PRICE  SUBTOTAL");
          outputFile.println(" DESCRIPTION                            ");
          
          while(table_manager.getData(table,in,0) != null && (!table_manager.getData(table,in,0).toString().equals("")) ){
              
              
              
              da = " ";
              if(table_manager.getData(table,in,1)!= null){da = da + format_manager.increaseLength(table_manager.getData(table,in,1).toString(),3);}
              if(table_manager.getData(table,in,0)!= null){da = da + format_manager.increaseLength(table_manager.getData(table,in,0).toString(),19);}
              
              if(table_manager.getData(table,in,2)!= null){
                  productName = table_manager.getData(table,in,2).toString();
                  productName = format_manager.increaseLength(productName,43);
                  productName = " "+ productName.substring(0,42);}
              
              if(table_manager.getData(table,in,4)!= null){
                  da = da + format_manager.increaseLength(table_manager.getData(table,in,3).toString(),10);
                  da = da + format_manager.increaseLength(table_manager.getData(table,in,4).toString(),4);}
              
              if(table_manager.getData(table,in,9) == null || table_manager.getData(table,in,9).toString().equalsIgnoreCase("")){ } 
              else {	  System.out.println("Register->tenderAction()\n\n");
            	  this.tenderLineItem(in);
              }
              outputFile.println(da);
              outputFile.println(productName+"\n");
              
              System.out.println("saving account " + in);
              da = "";in++;
              
          }
		  in = 0; 
	      // Must reset counter to zero to start loop again.
		  for( int i = 0; i < table.getRowCount();i++) { if(table.getValueAt(i,0) == null || table.getValueAt(i,0).toString().equalsIgnoreCase("")  ) {}
          else{ item_count++; qty_count += Integer.parseInt(table_manager.getData(table, i, 1).toString() ) ; } }

          
		  String receipt_spacing = "                         ";
          outputFile.println(receipt_spacing + "SUB TOTAL $"+ format_manager.formatDoubleUS(subtotal));
          outputFile.println(receipt_spacing + "SALES TAX $"+ format_manager.formatDoubleUS(totaltaxes));
          if(discount != 0.00) {
          outputFile.println(receipt_spacing + "DISCOUNT  $"+ format_manager.formatDoubleUS(discount));} else{}
          outputFile.println(receipt_spacing + "TOTAL     $"+ format_manager.formatDoubleUS(total));
          outputFile.println("");
          outputFile.println(receipt_spacing + "TENDERED  $"+ format_manager.formatDoubleUS(tendered));
          outputFile.println(receipt_spacing + "CHANGE    $"+ format_manager.formatDoubleUS(change));
          
          outputFile.println(receipt_spacing + "\nAddenda: "+ addenda.getText() );
          
          outputFile.println(receipt_spacing + "\nLine Item Count: "+ item_count );
          outputFile.println(receipt_spacing + "\nUnit Item Count: "+ qty_count );

          // refreshTotal(table,tendered,change);
          

          outputFile.println("----------------------------------------");
          outputFile.print("THANKS FOR SHOPPING AT");
          
          store_print_name = " " + invoice.getStoreName().trim() + " ";          
          outputFile.println(store_print_name);
          outputFile.println("");
          outputFile.println("For the best Point of Sale System call Lockwind at +1 347 808 5425");
          outputFile.println("----------------------------------------");
          outputFile.close();
          
          System.out.println("Saving invoice to Local File System for loading later");
          
          saveInvoice();
          
          try {
        	  
              // invoiceNumber = Integer.parseInt(http.getCurrentInvoiceNumber(retailerUUID));
              // ClientInvoiceReport http = new ClientInvoiceReport();
              //http.setInformation(account_name_input.getSelectedItem().toString(),String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD", format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change));
              
              //in = 0;
              //System.out.println("UPLOADING PRODUCT:");
              //http.sendProductPost(client_id,client_name,String.valueOf(invoiceNumber),table_manager.getData(table,in,0).toString(),table_manager.getData(table,in,1).toString(),table_manager.getData(table,in,2).toString(),table_manager.getData(table,in,3).toString(),table_manager.getData(table,in,4).toString(),table_manager.getData(table,in,5).toString(),table_manager.getData(table,in,6).toString(),table_manager.getData(table,in,7).toString() );
              
              //Thread.sleep(500);
          
          }catch(Exception xexe){}
          
      }catch(IOException ex){}

      
   
// PrintReceipt function

      
      
      
     try {
    	   PrintReceipt test = new PrintReceipt();
    	 System.out.println("Printing receipt to this printer: " + default_printer_receipt.getSelectedItem().toString());
  //       test.main(null);
          test.main(default_printer_receipt.getSelectedItem().toString() );
          test.printToDisplayPrinter(default_printer_display.getSelectedItem().toString(), "Total: " + format_manager.formatDoubleUS(total) );
     
     }
     catch(Exception PrintReceipte) {}
     
      try{
    	  
    	 Process p =  Runtime.getRuntime().exec("cmd /c printReceipt.bat");
       System.out.println("Initiating Printing Process: " + p.pid());
      }
      catch(IOException eex){}


      
//      saveTableToReceipt(table,client_id);
      inventory_manager.saveProductSold(table,client_id);

      try { // Create the original invoice transaction number and UUID.
          System.out.println("Process Activation: Uploading to Lockwind Cloud:");
			System.out.println("TenderAction()\t\t");
//          response = http.sendPost( invoice.getTransactionUUID(), invoice.getConsumerUUID(),invoice.getIssuerUUID(),client_id,invoice.getStoreName().trim(),account_selected,String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD",format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change));
          response = http.sendPost( invoice.getTransactionUUID(), invoice.getConsumerUUID(),invoice.getIssuerUUID(),client_id,invoice.getStoreName().trim(),account_selected,invoice.getInvoiceNumber(),fmt.format(today),simpDate.format(today),"USD",format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change));
          System.out.println("AddTransactionAPI -> WS Response:" + response);
//          invoice.setTransactionUUID(invoice.getIssuerUUID(),"");
	}catch(Exception exe) { 
		exe.printStackTrace();
	}
      
      try {
      System.out.println("Process Activation: Create new invoice after tendering previous invoice to Lockwind Cloud:");
      // http.IncrementInvoiceNumber(retailerUUID);
      System.out.println("Register: tenderAction -> Invoice Number: " + invoice.getInvoiceNumber() + " -> UUID: " + invoice.getTransactionUUID() );
      
      
      http.IncrementInvoiceNumber( invoice.getIssuerUUID() );
      
      invoice										= new Invoice( retailerUUID, posUUID,client_id );
      invoice.setInvoiceDefaultValues();
	  System.out.println("Register-> setInvoiceDefaultValues() -> ** retailer_uuid: " + retailerUUID);
	  System.out.println("Register-> setInvoiceDefaultValues() -> ** invoice_number: " + invoice.getInvoiceNumber() );
	  System.out.println("Register-> setInvoiceDefaultValues() -> ** transaction_uuid: " + invoice.getTransactionUUID() );

//      invoiceNumber = Integer.parseInt( http.getCurrentInvoiceNumber(retailerUUID) );
//      invoiceNumberLabelDescription.setText( String.valueOf(invoiceNumber));

//    invoiceNumber = Integer.parseInt( invoice.getInvoiceNumber() );
    invoiceNumberLabelDescription.setText( invoice.getInvoiceNumber() );

      
      System.out.println(" ** Register: tenderAction -> Invoice Number: " + invoice.getInvoiceNumber() + " -> UUID: " + invoice.getTransactionUUID() );
      
  	try { // Create the original invoice transaction number and UUID.
			System.out.println("TenderAction()\t\t");
//          response = http.sendPost( invoice.getTransactionUUID(), invoice.getConsumerUUID(),invoice.getIssuerUUID(),client_id,invoice.getStoreName().trim(),"",String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD","" ,"", "");
          response = http.sendPost( invoice.getTransactionUUID(), invoice.getConsumerUUID(),invoice.getIssuerUUID(),client_id,invoice.getStoreName().trim(),"",invoice.getInvoiceNumber(),fmt.format(today),simpDate.format(today),"USD","" ,"", "");
          System.out.println("WS Response:" + response);
          System.out.println("TenderAction -> WS Response after clearing invoice : " + response.toString() );
          System.out.println("Register->@tenderAction()");
          System.out.println("Register -> Invoice Number: " + invoice.getInvoiceNumber() + " -> UUID: " + invoice.getTransactionUUID() );

	}catch(Exception exe) { 
		exe.printStackTrace();
	}
    } catch(Exception ex) {
    	System.out.println("TenderAction() -> Error, cannot close current invoice and create a new one");
    	ex.printStackTrace();
    	
    }


      
      table_manager.setData(table,row,col,"");
      table_manager.clearTable(table);
	  clearRegister();
      
	  // Reset for user 
	  table.requestFocus();
	  table.changeSelection(0,0,false,false);
	  table.requestFocus();
	  
	  


            	System.out.println(payment_method.getSelectedItem() + " selected");

			if(payment_method.getSelectedItem().toString().equalsIgnoreCase("CASH") ) {
			//Display mesasge on verifone
			// try { register_verifone.displayMessage("Balance Due: $0.00",10); /* Thread.sleep(5000); */ register_verifone.endSession(); }
			// catch(Exception e){ System.out.println(e.toString() ); }
			
			}else{
			}
			
			
			
				/*
			if(registerStatus == true){
			
			  try { 

 				  register_verifone.captureCardEarlyReturn();

				  Thread.sleep(1000);
				  register_verifone.authorizeCard();
				  Thread.sleep(1000);
 					register_verifone.endSession();
				  Thread.sleep(2000);

				  }catch(Exception verifone_exception) {
					  System.out.println(verifone_exception.toString());
				  }
}else { 
System.out.println("Register tenderAction Error: Credit card payment terminal cannot be reached");
}
			}

*/

			
      } // close out check to see if line_item is null or not -> if not do not proess.
				
  } // close function tenderAction(double)
  

  
public static void main(String[] args){Register test = new Register();

System.out.println("This is Lockwind POS Version 4.15 with a modification date of 1/2/25");
System.out.println("\n\n********************************************************************************");
// test.printMethods(); -> this method can be used to view the stack of methods currently operating.
}

  
} // End class Register



