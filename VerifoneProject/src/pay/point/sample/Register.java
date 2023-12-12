//Last Update: 11/5/2023

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

import jakarta.xml.bind.DatatypeConverter;	
// import javax.xml.bind.DatatypeConverter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



/* Code removed from Register class
	  
	  private String[] column_header 			= 		{"UPC","QTY","DESCRIPTION","PRICE","SUBTOTAL","TAX","DISCOUNT","ONHAND"};
      private String[] column_header 			= 		{"UPC","QTY","CATEGORY","DESCRIPTION","PRICE","SUBTOTAL","TAX","DISCOUNT","ONHAND"};
	  private int[]    column_width  			= 		{135,55,100,100,55,85,55,75,75};
*/


//---------------------------------------------------- CLASS REGISTER : THIS IS THE MAIN CLASS
public class Register  implements ActionListener,FocusListener {

	  private APIVerifone verifone;
	  
	  private Document POSStatus;
	  private Element statusElement;
	
	  private String mac_label					= "Register1"; // This is the name of the point of sale that is identifiable by Verifone.
	  private Invoice invoice;

	  String invoice_directory 					= 		"./target/classes/lockwind/com/outbound_invoice/";
      String invoice_file_extension_txt 		= 		".txt";

	  private Locale[] locale        			=       {US,UK,GERMANY,FRANCE}  ;
	  private int[]    styles        			=       {FULL,LONG,MEDIUM,SHORT};
	  
	  
      private String[] paymentMethods 			= 		new String[] {"CASH","CREDIT/DEBIT CARD / APPLE PAY","ON ACCOUNT","CHECK"};
      private String transaction_payment_method = 		"";
	  
	  private String[] column_header 			= 		{"UPC","QTY","CATEGORY","DESCRIPTION","PRICE","SUBTOTAL","TAX","DISCOUNT","ONHAND","LINE ITEM"};
	  private int[]    column_width  			= 		{135,55,200,100,55,85,55,75,75,75};

      private String[] electronic_document_transaction_type = 		new String[] { "RECEIPT", "INVOICE","CREDIT NOTE","DEBIT NOTE","PAYMENT RECEIPT","ASN","CONFIRM RECEIPT","PURCHASE ORDER", "PURCHASE ORDER ACK","TEXT MESSAGE"}; // Add on 9/6/23 to allow the system to store the transaction type of a given electronic document and transfer it out once tender is completed.
	  private JComboBox<String> electronic_document_transaction_type_selected; // Added on 9/6/23 to allow the customer to select a transaction type


	  private int i, j, row, col, invoiceNumber;
	  private double tendered, subtotal, totaltaxes, change, discount, transaction_tender_amount;

	  private double TAX_RATE 					= 		0.08975; // This is only temporary, it must be fixed.
	  private int table_row_count				= 		40;
	  private int table_col_count				= 		10;



	  
	  private JComboBox<String> payment_method;


	  private JComboBox<String> default_printer_receipt; // Added 4/23/2022 for printing simplicity
	  private JComboBox<String> default_printer_invoice; // Added 4/23/2022 for printing simplicity
	  private JComboBox<String> default_printer_display; // Added 4/23/2022 for printing simplicity
	  
	  private JLabel default_printer_receipt_label;		 // Added 4/23/2022 for printing simplicity
	  private JLabel default_printer_invoice_label;		 // Added 4/23/2022 for printing simplicity
	  private JLabel default_printer_display_label;		 // Added 4/23/2022 for printing simplicity

	  private JTextArea addenda;
	  private JMenuBar menuBar;
	  
	// Program Specific Components
	  private Internet internet;
	  private ERP erp;
	  private ERP program;
	  private Date today;

	  
	  private String client_id, client_name;
	  private API http;
	  
	  // Variables for transaction calculations
	  
	  private String url_response, error_message;

	  // ENGINES
	  private ProductManagementSystem product_management_system;
	  private InvoiceManagementSystem invoice_management_system;
	  private CustomerManagementSystem customer_management_system;
	  private PrinterManagementSystem printer_management_system;

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
	  private JButton b,c,d,e,f,g,z,zz;
	  private JButton bx001; // customers
	  private JButton bx002; // suppliers
	  private JButton bx003; // update
	  private JButton bx004; // price_change
	  private JButton bx005; // new_stock
	  private JButton bx006; // inventory
	  private JButton bx007; // Verifone Manager
	  private JButton bx008; // Verifone Manager
	  private JButton pim_button;
	  private JButton salesReport;
	  
	  
	  private JComboBox<String> transaction_type;
	  private JComboBox<String> transaction_type_value;
	  private JComboBox<String> account_name_input;
	  
	  private JTextField tender_amount;

	  // User Specific Components
	  private String customer_selected; // This is used as a variable that is used to select the customer from the account name combobox for fulfillment. 
	  private String retailerUUID;
	  private String da;
	  private int index;
	  private Ali ali;	  

	  private ElectronicDocument record = null;
	  private ElectronicDocumentLineItem invoice_line_item;  
	  private double total;
	  
	  // ADVANCED COMMUNICATIONS & SIGNALS
	  private boolean registerStatus;
	  private boolean sessionInProgress;
	  
		
	  private String macLabel = "";
	  private byte[] macKey = new byte[0];
	  private int counter = 0;
		
	  private String address;
	  private int port;
	  private int secondary_port;
	  private int invoice_number;
	  Random generator = null;
	  String entryCode = "";
		
	  KeyFactory kf;
	  PrivateKey privateKey;
	  PublicKey publicKey;
	  KeyPair keypair; 

	  private static final String PUBLIC_KEY = 
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAugflCYHdNLX1PK+2JpedLoL4JdwpapkpwoSIYOhBF9FFri+roRTqPyyosLFGMMnO5l65z9YY1cQYSENWfhLvPROD2Oruyl1k2wSYWT+23wTB0jJjA4ktk7Q2cErNzMNiLLP0tB3rOYJHxC1HjskKBmkblF5ZDeCNzVyeEdF37zfCDD5bBIjPSpmLgH1swDQIvpULhwhmyf1AaJX+oaaCQgu6wxrbP17auMJzAjhddwUgIbkCiAEcYu8fwyTXQWFcQtfA3nufCITAcI7jmtxrXKqKWgZ23oIgvmIM1y9l6Bp9QT8MvDn63wfj54fyOW5Jb66G19x/xVGF5lH68qPErwIDAQAB";
	  private static final String PRIVATE_KEY = 
				"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6B+UJgd00tfU8r7Yml50ugvgl3ClqmSnChIhg6EEX0UWuL6uhFOo/LKiwsUYwyc7mXrnP1hjVxBhIQ1Z+Eu89E4PY6u7KXWTbBJhZP7bfBMHSMmMDiS2TtDZwSs3Mw2Iss/S0Hes5gkfELUeOyQoGaRuUXlkN4I3NXJ4R0XfvN8IMPlsEiM9KmYuAfWzANAi+lQuHCGbJ/UBolf6hpoJCC7rDGts/Xtq4wnMCOF13BSAhuQKIARxi7x/DJNdBYVxC18Dee58IhMBwjuOa3GtcqopaBnbegiC+YgzXL2XoGn1BPwy8OfrfB+Pnh/I5bklvrobX3H/FUYXmUfryo8SvAgMBAAECggEAMmmxR8JJj98/dhKn6g1sKw6S8K+ZCao4Bt6jlp9aBHpRx8JjYGOqlzQjAr8HpnEKAKPq9seuMz/Q1MRqy/+VlZeUQ1RnIa/thOzZ3FXH2OgRHkVJT8v87eoIVqXu326TTEn4Jld1R0Bm8mLS4X7ZmKMjNjHbMEeKJfzTWUDKn6imPU5/mkJIoCVNi1CM+A8QTxoCFhWzdxHj5GCExAzQJdTFDHLEDygZOjX5iGSRenYYc5dxLutroWc8a9XPuftPIBooPCYAsRbUYUE16bXosQ38aO8lU3E1tyCD0nj0sq1Oiiuw/UJmFizj9pJUzCJpWDgk4wqzOffOf2gI+5rhAQKBgQDfkOUUp0Qzo1h/MJ49BGqSLZZ1pl/UEsGhVeV2RKx7TukOq0Lj9tChAnhouA6GsjR9AG8AWiZCN4ZkMlRVHAAppkk/6WFE4FHkmvWlUmNsNdwY5z4Ww5tggywg4j9WPif5FjWfvq7V/UYvl24mJ1XChAh3HGOX674DPfyLBgvfWwKBgQDVBPeIMs4FtVo4WoIfdx3A2xvbwd9TVsCJiLV5iXcy9ksbPVLif7tO5ZR63GkevVNc4PHWupaqA3f8VCBe3M12e6xdlH59zXPRVubczK8pBqcKt84qj7yk5Y6OgiFQEp/u16G2M7FhagQ14J2N0pGHdYtZmn+A694c2HD98LBkPQKBgQC7Wgiv0zCzeXrrM8oX5kCM+ckyFNgPuBwuYPZns0s8Frf2RA1NTwQtOg2/7Ca4OFUGQDvFdsbDDRcBlq/XlxyHysNt3N1XxAi85CNhhPaus0AcWoVMvGXUbninohJj6rjC5BrSIRERYSvVLDjxnlsfJFiXwOGxaayVuPePZeTDKwKBgQCtX+mHtLHx+3R+wUt/CJfyy2KVLenyDn2OcvIhBT07ATKH7RV0u7lbsYdzp8j29+jNg1fSCPNvVHtnp6DhFJ01fdsAH0gEZB+Llks4Em/N2FhEZO0rvuku3Jd2bXtnjIEXB/HaNaB9RKhAoZwaPfOsaIMOXqy/5TlWCOOOC0PFkQKBgGB7PkgYnGg0R610g0JaXJJ3kaeGnZYJapXgUF6ZDKz6IQI1ufhyQ29tqSxSW2GydLQRFFr4pXEEoYCGGO5KVkP5bGe/3Hyj4O82JiOkwFyVtNp3yjhPFbwlOzgQnAHEbmpidLTBP3THDtqNWGb1U586pNuxDpIMQrGR9Gva0OzK";

	  private ElectronicDocument electronic_document;
	  
	  private Document request;
	  private Element docElement;
		
	  private Document responseXml;
	  private Element responseDocElement;
		
	  private String encryptedMACKey;
	  private byte[] macKeyBase64Decoded;
	  private Cipher cipher;
		
	  private String nextCounter;
	  private String mac;
		
	  private String temp;

	  private Font font1;
	  private Font font2;
      private Timer timer;

      
	  public void setConstructorValues() {
			// action: organize elements, Initialize components,

		  	invoice                						= null;
		  	font1                						= null;
		  	font2                						= null;
		  	
		  	width                						= 0.00;
		    height               						= 0.00;
		    w                       					= 0;
		    h                       					= 0;
		    transaction_tender_amount 					= 0.00;
		    invoiceNumber           					= 0;

		    screenSize        							= null;
		    internet                    				= null;
		    internetLabel               				= null;	
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
		    bx008                   					= null;
		    
		    button_tender               			    = null;
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

			invoice_line_item							= null;

			error_message 								= "";			

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
			cipher = null;

			nextCounter 								= "";
			mac 										= "";
			
			temp 										= "";
			formatter 									= null;
			
			electronic_document							= null;
			
	  }
	  
	  public void setComponentDefaultValues() {
		  
		  	// REGISTER SET COMPONENT DEFAULT VALUES

		    retailerUUID                   				= "5d4de950d4f69";
			client_id                   				= "5d4de950d4f69";

		    http                        				= new API();
//		    verifone									= new APIVerifone();
			validator 									= new ValidationPlatform();
		    product_management_system   				= new ProductManagementSystem();
		    invoice_management_system   				= new InvoiceManagementSystem();
		    customer_management_system 					= new CustomerManagementSystem();
		    printer_management_system 					= new PrinterManagementSystem();
		    inventory_manager 		  					= new QTY();
		    ali											= new Ali();
		    internet                    				= new Internet();
			erp                       					= new ERP();
			record 										= new ElectronicDocument();
		  	invoice										= new Invoice();
		    model                       				= new DefaultTableModel(table_row_count,table_col_count);
		    table_manager				    			= new TableManager(column_header,column_width);
		    format_manager								= new FormatManager();
		    table                       				= new JTable(model);
		    formatter 									= new DecimalFormat("#0.00");
		    today                       				= new Date();
		    simpDate                    				= new SimpleDateFormat("hh:mm:ss a");
		    formatter 									= new DecimalFormat("#0.00");

		  	frame                       				= new JFrame();
		  	panel                       				= new JPanel();
		  	tablePanel                 		 			= new JPanel();			
		  	bottomPanel									= new JPanel();
		  	
		    timeLabel                   				= new JLabel("");
		  	internetLabel              					= new JLabel("");
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
		    bx008                          				= new JButton("");

		    button_tender              				 	= new JButton("");
  		    pim_button				  				 	= new JButton("");
		    
		    springLayout                				= new SpringLayout();
		    panelLayout    								= new SpringLayout();
		    layout                      				= new SpringLayout();
		    
		    tender_amount								= new JTextField(10);
		    addenda										= new JTextArea(4,3);
		    
		  	
		    font1										= new Font("Times New Roman", Font.BOLD, 15);
			font2										= new Font("Tahoma",Font.BOLD,12);
		    
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

			invoice										. setIssuerUUID(retailerUUID);
			invoice										. setConsumerUUID(client_id);

			product_management_system	  				. setRetailerUUID( invoice.getIssuerUUID() );
		    invoice_management_system	  				. setRetailerUUID( invoice.getIssuerUUID() );
	        invoice_management_system					. setConsumerUUID( invoice.getConsumerUUID() );


	        this.setInvoiceDefaultValues();
	        this.setInitialInvoiceNumber();
  		    this.setUIDefaultValues();
			//this.setInvoiceDefaultValues();
		    this.setComponentTextValues();
			this.setComponentFontValues();
			this.setColorScheme();
			this.setComponentDimensionValues();
		    this.setActionListener();
		    this.setVerifoneDefaultValues();
  		 
  		
	  }
	  public void setInitialInvoiceNumber() {
		  try { 


				String temp = http.getCurrentInvoiceNumber(retailerUUID);
				

				if(retailerUUID == null ) { invoice.setInvoiceNumber(String.valueOf(-1)) ; }
				else{
					System.out.println("Result Code: ->>>" + temp);

					if(temp.equalsIgnoreCase("java.net.UnknownHostException: lockwind.com")) { invoice.setInvoiceNumber("-401"); }
					else { invoice.setInvoiceNumber(temp); } }

			} catch(Exception e){ }

			System.out.println("Register.setComponenentDefaultValues() " +  "Invoice Number from Cloud: " +  temp );
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
		    fmt                         				= DateFormat.getDateInstance(styles[3], locale[0]);
			
		  	frame										. setLayout(springLayout);
		  	panel										. setLayout(panelLayout);
		  	tablePanel									. setLayout(new BorderLayout());
		    bottomPanel									. setLayout(panelLayout);

	  }
	  public void setVerifoneDefaultValues() {
		   	address 						= "192.168.50.197"; // Update this to be set depending on the POS network and verifone IP address per client.
 		    port 							= 5015;
 		    secondary_port 					= 5016;
 		
 		    generator 						= new Random();
 			entryCode 						= String.valueOf(generator.nextInt(9999));
 		
 		
 		try { 
 			
 			kf 								= KeyFactory.getInstance("RSA");
 			privateKey						= kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(PRIVATE_KEY)));
 			publicKey 						= kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(PUBLIC_KEY)));
 			keypair 						= new KeyPair(publicKey, privateKey);

 		}catch(Exception e) {
 			
 		}
 
	  }
	  
	  public void setInvoiceDefaultValues()
	  {
	        invoice										. setStoreName("165 St. Hardware Inc.");
	        invoice										. setStoreAddress("1099 St. Nicholas Avenue, ");
			invoice										. setStoreSecondAddress("NY, NY, 10032");
			invoice										. setStoreFaxNumber("FAX:             ");
			invoice										. setDirectory("./");
			invoice										. setFileExtension(".txt");
			invoice										. setOriginSystemID("POS0001");
			invoice										. setDestinationSystemID("ERP0001");
			invoice										. setOriginSystem("Lockwind POS");
			invoice										. setDestinationSystem("Lockwind ERP");
		  	invoice										. setBillToCustomerNameLabel("Bill to Customer: ");
			invoice										. setBillToCustomerNameData("Customer Name");
			invoice										. setBillToCustomerAddressLabel("Bill To Address: ");
			invoice										. setBillToCustomerAddressData("Address");
			invoice										. setBillToCustomerCityLabel("City: ");
			invoice										. setBillToCustomerCityData("city");
			invoice										. setBillToCustomerStateLabel("State: ");
			invoice										. setBillToCustomerStateData("state");
			invoice										. setBillToCustomerZipcodeLabel("Zipcode: ");
			invoice										. setBillToCustomerZipcodeData("zipcode");
			invoice										. setBillToCustomerCountryLabel("Country: ");
			invoice										. setBillToCustomerCountryData("Country");
			invoice										. setBillToCustomerPhoneNumberLabel("Phone Number:");
			invoice										. setBillToCustomerPhoneNumberData("+xx xxx xxx xxxx");
			invoice										. setBillToCustomerEmailAddressLabel("Email: ");
			invoice										. setBillToCustomerEmailAddressData("xx@xxxx.com");
			invoice										. setShipToCustomerNameLabel("Ship To Customer: ");
			invoice										. setShipToCustomerNameData("customer name");
			invoice										. setShipToCustomerAddressLabel("Ship To Address: ");
			invoice										. setShipToCustomerAddressData("address");			
			invoice										. setShipToCustomerCityLabel("City: ");
			invoice										. setShipToCustomerCityData("city");
			invoice										. setShipToCustomerStateLabel("State: ");
			invoice										. setShipToCustomerStateData("state");
			invoice										. setShipToCustomerZipcodeLabel("Zipcode: ");
			invoice										. setShipToCustomerZipcodeData("zipcode");
			invoice										. setShipToCustomerCountryLabel("Country: ");
			invoice										. setShipToCustomerCountryData("country");
			invoice										. setShipToCustomerPhoneNumberLabel("Phone Number:");
			invoice										. setShipToCustomerPhoneNumberData("+xx xxx xxx xxxx");
			invoice										. setShipToCustomerEmailAddressLabel("Email: ");
			invoice										. setShipToCustomerEmailAddressData("xx@xxxx.com");  
	  }
	  
	  public void setComponentTextValues(){
		    bx001										. setName("customers");
		    bx002										. setName("suppliers");
		    bx003										. setName("update");
		    bx004										. setName("price_change");
		    bx005										. setName("new_stock");
		    bx006										. setName("inventory");
		    bx007										. setName("verifone_manager");
		    bx008										. setName("extra_button");
		    button_tender								. setName("tender");
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
			bx008										. setText("Extra button"); // 2nd Column of buttons, 3rd from the top
			salesReport									. setText("Sales Report"); // 3rd Column of buttons, 2nd from the top 
			button_tender								. setText("Tender"); // 3rd Column of buttons, 1st from the top
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
			default_printer_receipt_label				. setText("Receipt Printer: ");
			default_printer_invoice_label				. setText("Invoice Printer: ");
			default_printer_display_label				. setText("Display Printer: ");
			
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
	  
	  public void setComponentFontValues()
	  {
		  storeName							.setFont(font1);
			storePhoneNumber				.setFont(font1);
			storeFaxNumber					.setFont(font1);
			subtotalLabel					.setFont(font1);
			taxesLabel						.setFont(font1);
			totalLabel						.setFont(font1);
			tenderLabel						.setFont(font1);
			changeLabel						.setFont(font1);
			discountLabel					.setFont(font1);
			salesReport						.setFont(font1);

			bx001							.setFont(font2);
			bx002							.setFont(font2);
			bx003							.setFont(font2);
			bx004							.setFont(font2);
			bx005							.setFont(font2);
			bx006							.setFont(font2);
			bx007							.setFont(font2);
			bx008							.setFont(font2);
			button_tender					.setFont(font2);
			pim_button						.setFont(font2);
	  }
	  
	  public void setColorScheme()
	  {
		  panel								.setBackground(Color.decode("#F0F0F0"));
		    bottomPanel						.setBackground(Color.decode("#F0F0F0"));
		    tablePanel						.setBackground(Color.decode("#000000"));
			bx001							.setBackground(Color.WHITE);
		    bx002							.setBackground(Color.WHITE);
			bx003							.setBackground(Color.WHITE);
			bx003							.setBackground(new Color(59,89,112));
			  
			panel							.setForeground(Color.decode("#FFFFFF"));
		    storeName						.setForeground(Color.decode("#000000"));
		    storePhoneNumber				.setForeground(Color.decode("#000000"));
		    storeFaxNumber					.setForeground(Color.decode("#000000"));
		    bx001							.setForeground(Color.BLACK);
		    bx002							.setForeground(Color.BLACK);
		    bx003							.setForeground(new Color(59,89,12));
		    bx003							.setForeground(Color.BLACK);
		    bx004							.setForeground(Color.decode("#FF0000"));
		    bx005							.setForeground(Color.BLUE);
		    bx006							.setForeground(Color.BLUE);
		    bx007							.setForeground(Color.BLUE);
		    bx008							.setForeground(Color.BLUE);
			button_tender					.setForeground(Color.BLACK);
			pim_button						.setForeground(Color.BLACK);
			
	  }
	  
	  public void setComponentDimensionValues()
	  {
		    frame							.setPreferredSize(new Dimension(w,h-30));
		    panel							.setPreferredSize(new Dimension(w,250));
			bottomPanel						.setPreferredSize(new Dimension(w,100));

			tablePanel						.setPreferredSize(new Dimension(w,700));
			bx001							.setPreferredSize(new Dimension(130,30));
			bx002							.setPreferredSize(new Dimension(130,30));
			bx003							.setPreferredSize(new Dimension(130,30));
			bx004							.setPreferredSize(new Dimension(130,30));
			bx005							.setPreferredSize(new Dimension(130,30));
			bx006							.setPreferredSize(new Dimension(130,30));
			bx006							.setPreferredSize(new Dimension(130,30));

			button_tender					.setPreferredSize(new Dimension(130,30)); 
			pim_button						.setPreferredSize(new Dimension(130,30));
		    payment_method					.setPreferredSize(new Dimension(150,30));
		    tender_amount					.setPreferredSize(new Dimension(150,30));
		    salesReport						.setPreferredSize(new Dimension(130,30));
		    addenda							.setPreferredSize(new Dimension(150,30));
		    label_addenda					.setPreferredSize(new Dimension(150,30));
	  }
	  public void setActionListener()
	  {
		    bx001							.addActionListener(this);
		    bx002							.addActionListener(this);
		    bx003							.addActionListener(this);
		    bx004							.addActionListener(this);
		    bx005							.addActionListener(this);
		    bx006							.addActionListener(this);
		    bx007							.addActionListener(this);
		    bx008							.addActionListener(this);
		    button_tender					.addActionListener(this);
		    salesReport						.addActionListener(this);
		    pim_button						.addActionListener(this);
		    payment_method					.addActionListener(this);
		    tender_amount					.addActionListener(this);
		    addenda							.addFocusListener(this);

	  }
	  
	  public void setUILayout() {
		  
		  springLayout.putConstraint(SpringLayout.NORTH, panel,0,SpringLayout.NORTH, frame);
		  springLayout.putConstraint(SpringLayout.NORTH, bottomPanel,250,SpringLayout.NORTH, frame);
		  springLayout.putConstraint(SpringLayout.NORTH, tablePanel,350,SpringLayout.NORTH, frame);


		  panelLayout.putConstraint(panelLayout.NORTH, timeLabel,00,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, account_name_input,90,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, account_name_label,95,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, invoiceNumberLabel,70,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, invoiceNumberLabelDescription,70,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, internetLabel,120,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, storeName,140,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, storePhoneNumber,160,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, storeFaxNumber,180,layout.NORTH, panel);

		  
		  panelLayout.putConstraint(panelLayout.NORTH, subtotalLabel,10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, taxesLabel,30,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, totalLabel,50,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, tenderLabel,70,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, changeLabel,90,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, discountLabel,110,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, subtotalLabelDescription,10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, taxesLabelDescription,30,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, totalLabelDescription,50,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, tenderLabelDescription,70,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, changeLabelDescription,90,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, discountLabelDescription,110,layout.NORTH, panel);
		  
		  panelLayout.putConstraint(panelLayout.NORTH, bx001,5,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx002,50,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx003,100,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx004,5,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx005,50,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx006,100,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx007,150,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, bx008,200,layout.NORTH, panel);
		  
		  panelLayout.putConstraint(panelLayout.NORTH, button_tender,5,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, salesReport,50,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, pim_button,100,layout.NORTH, panel);
		  
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_name,10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_name_data,30,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_address,35,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_address_data,55,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_city,60,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_city_data,80,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_state,85,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_state_data,105,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_zipcode,110,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_zipcode_data,130,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_country,135,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_country_data,155,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_email_address,160,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_email_address_data,180,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_phone_number,185,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_bill_to_customer_phone_number_data,205,layout.NORTH, panel);
		  
		  
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_name,10,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_name_data,30,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_address,35,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_address_data,55,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_city,60,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_city_data,80,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_state,85,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_state_data,105,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_zipcode,110,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_zipcode_data,130,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_country,135,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_country_data,155,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_email_address,160,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_email_address_data,180,layout.NORTH, panel);

		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_phone_number,185,layout.NORTH, panel);
		  panelLayout.putConstraint(panelLayout.NORTH, label_ship_to_customer_phone_number_data,205,layout.NORTH, panel);


		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_receipt,00,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_invoice,30,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_display,60,layout.NORTH, bottomPanel);

		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_receipt_label,0,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_invoice_label,30,layout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, default_printer_display_label,60,layout.NORTH, bottomPanel);

		  panelLayout.putConstraint(panelLayout.NORTH, payment_method,10,panelLayout.NORTH, bottomPanel);
  	      panelLayout.putConstraint(panelLayout.NORTH, tender_amount,60,panelLayout.NORTH, bottomPanel);
  	      panelLayout.putConstraint(panelLayout.NORTH, addenda, 30, panelLayout.NORTH, bottomPanel);
          panelLayout.putConstraint(panelLayout.NORTH, label_addenda, 0, panelLayout.NORTH, bottomPanel);
		  panelLayout.putConstraint(panelLayout.NORTH, retailerUUIDDescription,5,layout.NORTH, bottomPanel);
  	      
		  springLayout.putConstraint(SpringLayout.WEST, panel,0,SpringLayout.WEST, frame);
		  springLayout.putConstraint(SpringLayout.WEST, tablePanel,000,SpringLayout.WEST, frame);
		  springLayout.putConstraint(SpringLayout.WEST, bottomPanel,000,SpringLayout.WEST, frame);

		  
		  panelLayout.putConstraint(panelLayout.WEST, storeName,30,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, storePhoneNumber,30,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, storeFaxNumber,30,layout.WEST, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, timeLabel,30,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, account_name_input,105,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, account_name_label,35,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, invoiceNumberLabel,35,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, invoiceNumberLabelDescription,110,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, internetLabel,35,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, subtotalLabel,300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, taxesLabel,300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, totalLabel,300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, tenderLabel,300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, changeLabel,300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, discountLabel,300,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, subtotalLabelDescription,220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, taxesLabelDescription,220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, totalLabelDescription,220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, tenderLabelDescription,220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, changeLabelDescription,220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, discountLabelDescription,220,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx001,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx002,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx003,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx004,550,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx005,550,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx006,550,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx007,550,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, bx008,550,layout.WEST, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, button_tender,700,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, salesReport,700,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, pim_button,700,layout.WEST, panel);
		  
		  // SHIP TO COMPONENTS
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_name,850,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_name_data,850,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_address,850,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_address_data,850,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_city,850,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_city_data,850,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_state,850,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_state_data,850,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_zipcode,850,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_zipcode_data,850,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_country,850,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_country_data,850,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_email_address,850,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_email_address_data,850,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_phone_number,850,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_bill_to_customer_phone_number_data,850,layout.WEST, panel);

		  // BILL TO COMPONENTS
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_name,1000,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_name_data,1300,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_address,1000,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_address_data,1300,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_city,1000,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_city_data,1300,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_state,1000,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_state_data,1300,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_zipcode,1000,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_zipcode_data,1300,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_country,1000,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_country_data,1300,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_email_address,1000,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_email_address_data,1300,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_phone_number,1000,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_phone_number_data,1300,layout.WEST, panel);
		  
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_receipt,550,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_invoice,550,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_display,550,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, default_printer_receipt_label,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_invoice_label,400,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, default_printer_display_label,400,layout.WEST, panel);
		  
		  
		  panelLayout.putConstraint(panelLayout.WEST, payment_method,30,panelLayout.WEST, bottomPanel);
		  panelLayout.putConstraint(panelLayout.WEST, tender_amount,30,panelLayout.WEST, bottomPanel);
		  panelLayout.putConstraint(panelLayout.WEST, addenda, 220, panelLayout.WEST, bottomPanel);
		  panelLayout.putConstraint(panelLayout.WEST, label_addenda, 220, panelLayout.WEST, bottomPanel);
		  panelLayout.putConstraint(panelLayout.WEST, retailerUUIDDescription,850,panelLayout.WEST, bottomPanel);

	  	}
	  
	  
	  public void setUIComponent() {
		  
		  // Add UI Elements
		  panel.add(storeName);
		  panel.add(storePhoneNumber);
		  panel.add(storeFaxNumber);
		
		  panel.add(bx001);
		  panel.add(bx002);
		  panel.add(bx003);
		  panel.add(bx004);
		  panel.add(bx005);
		  panel.add(bx006);
		  panel.add(bx007);
		  panel.add(bx008);
		  
		  panel.add(button_tender);
		  
		  panel.add(account_name_input);
		  panel.add(account_name_label);

		// Made adjustment 
		  panel.add(invoiceNumberLabel);
		  panel.add(internetLabel);
		  panel.add(totalLabel);
		  panel.add(subtotalLabel);
		  panel.add(taxesLabel);
		  panel.add(tenderLabel);
		  panel.add(changeLabel);
		  panel.add(discountLabel);

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
		  panel.add(label_bill_to_customer_address_data);
		  panel.add(label_bill_to_customer_city_data);
		  panel.add(label_bill_to_customer_state_data);
		  panel.add(label_bill_to_customer_zipcode_data);
		  panel.add(label_bill_to_customer_country_data);
		  panel.add(label_bill_to_customer_email_address_data);
		  panel.add(label_bill_to_customer_phone_number_data);

		  
/*		  
 * 		  panel.add(label_bill_to_customer_address);
		  panel.add(label_bill_to_customer_city);
		  panel.add(label_bill_to_customer_state);
		  panel.add(label_bill_to_customer_zipcode);
		  panel.add(label_bill_to_customer_country);
		  panel.add(label_bill_to_customer_email_address);
		  panel.add(label_bill_to_customer_phone_number);
*/
		  
		  
		  panel.add(label_ship_to_customer_name);
		  panel.add(label_ship_to_customer_address);
		  panel.add(label_ship_to_customer_city);
		  panel.add(label_ship_to_customer_state);
		  panel.add(label_ship_to_customer_zipcode);
		  panel.add(label_ship_to_customer_country);
		  panel.add(label_ship_to_customer_email_address);
		  panel.add(label_ship_to_customer_phone_number);
		  
		  panel.add(label_ship_to_customer_name_data);
		  panel.add(label_ship_to_customer_address_data);
		  panel.add(label_ship_to_customer_city_data);
		  panel.add(label_ship_to_customer_state_data);
		  panel.add(label_ship_to_customer_zipcode_data);
		  panel.add(label_ship_to_customer_country_data);
		  panel.add(label_ship_to_customer_email_address_data);
		  panel.add(label_ship_to_customer_phone_number_data);
		  
		  bottomPanel.add(default_printer_receipt);
		  bottomPanel.add(default_printer_invoice);
		  bottomPanel.add(default_printer_display);

		  bottomPanel.add(default_printer_receipt_label);
		  bottomPanel.add(default_printer_invoice_label);
		  bottomPanel.add(default_printer_display_label);

		  
	      bottomPanel.add(payment_method);
	      bottomPanel.add(tender_amount);
	      bottomPanel.add(addenda);
	      bottomPanel.add(label_addenda);
		  bottomPanel.add(retailerUUIDDescription);

	  }
	  

	  
	  public void buildTopPanel()
	  {
		    try {
		        
		    	
		    	client_name						= invoice.getStoreName();
		    	invoiceNumber					= Integer.parseInt( invoice.getInvoiceNumber() );
		        retailerUUIDDescription			. setText( invoice.getIssuerUUID() );

		        }
		        catch(Exception e) {			System.out.println(  e.toString( )); }  
		        

		        timeLabel						.setFont(new Font("Times New Roman", Font.BOLD, 30));
		        timeLabel						.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(null,fmt.format(today),0,0,new Font("Times New Roman", Font.BOLD, 20), Color.BLUE), timeLabel.getBorder()));
		      

		          
		        ActionListener taskPerformer = new ActionListener() {

		        	public void actionPerformed(ActionEvent evt) {
		        		
		              simpDate          = new SimpleDateFormat("hh:mm:ss a");
		              today 			= new Date();
		              timeLabel.setText(simpDate.format(today) );
		              internetLabel.setText( internet.checkConnection() );
		          
		        }};
		        
		        timer = new Timer(1000, taskPerformer);
		        timer .start();


		          

		          try {
		              invoiceNumberLabel.setText(  "Invoice #:" );
		              
		              if (http.getCurrentInvoiceNumber(retailerUUID).toString().equalsIgnoreCase(""))
		              {
		            	  try { 
		            		  
		            		  error_message = "Invoice Number Error: Check Internet Connection";
		            		  System.out.println("Invoiec Number Format Error:" + error_message);
		            		  
		            	  }catch(NumberFormatException e)
		            	  {
		            		  System.out.println("Invoice Number Format Exception: " + e.toString() );
		            	  }
		              }else { 
			              invoiceNumberLabelDescription.setText( String.valueOf(http.getCurrentInvoiceNumber(retailerUUID)) );		            	  
				          invoiceNumberLabelDescription.setFont(new Font("Times New Roman", Font.BOLD, 15));
				          invoiceNumberLabelDescription.setForeground(Color.decode("#0000FF"));
		              }
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
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Lockwind POS");

	        menuItem = new JMenuItem("Tender Transaction",KeyEvent.VK_T);
			menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));
			
	        menuItem.setName("TenderTransaction");
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_7, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription(" Tender Transaction ");
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
				  tenderAction(0);
				}
			  });
		  
			menu.add(menuItem);


	        
	        // Menu Item located on dropdown list
	        menuItem = new JMenuItem("Park Transaction",KeyEvent.VK_C);
	        menuItem.setName("ParkTransaction");
	        menuItem.addActionListener(this);
			menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

			JOptionPane.showMessageDialog(null,"Parking Transaction");
			System.out.println("User Action: Parking Transaction");
			
			// this.parkTransaction();
			
				  // Add a call to the function to save the transaction details into a structured file.
				  // This action should create a parked transaction in an XML file that can then be recovered later.
				  // Each parked transaction should have an document number and machine ID in order to be processed.
				}	
			  });
   		   menuItem.setBackground(Color.decode("#FFFF00"));
		   menuItem.setForeground(Color.decode("#000000"));  
	       menu.add(menuItem);
			
	       // No ParkTransaction Module built yet

	        
	        menuItem 			= new JMenuItem("Copy Last Transaction",KeyEvent.VK_C);
	        menuItem			. setName("CopyLastTransaction");
			System.out.println	 ("User Action: Copy Last Transaction");
	        menuItem			. addActionListener(this);
	        menu				. add(menuItem);
	        
			// No Copy Last Transaction Module built yet

	        
	        menuItem = new JMenuItem("Add Invoice Comment",KeyEvent.VK_D);
	        menuItem.setName("AddInvoiceComment");
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_8, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Restart Program",KeyEvent.VK_C);
	        menuItem.setName("RestartProgram");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Refresh Program",KeyEvent.VK_C);
	        menuItem.setName("RefreshProgram");
	        menuItem.addActionListener(this);
			menuItem.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent ev) {
				
					System.out.println("User Action: Clear Register");

				  	clearRegister();
				}
				
			  });
			
   		   menuItem.setBackground(Color.decode("#00FF0F"));
		   menuItem.setForeground(Color.decode("#000000"));  
		   menu.add(menuItem);

	        menuItem = new JMenuItem("Exit Program",KeyEvent.VK_C);
	        menuItem.setName("ExitProgram");
	        menuItem.addActionListener(this);
	        
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					System.out.println("User Action: Shut down Pont Of Sale System");
					System.exit(0);
				}
			  });
			menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));  
			menu.add(menuItem);
	        menuBar.add(menu);

	        
	        menu = new JMenu("Receipts Management");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "View the Receipts Management Menu");
	        
	        menuItem = new JMenuItem("Sales Report",KeyEvent.VK_C);
	        menuItem.setName("SalesReport");
	        menuItem.addActionListener(this);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "View the Sales Report");
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Find Receipt",KeyEvent.VK_C);
	        menuItem.setName("FindReceipt");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Find a receipt");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuItem = new JMenuItem("Reprint Receipt",KeyEvent.VK_C);
	        menuItem.setName("ReprintReceipt");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Reprint a receipt");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuBar.add(menu);

	        
	        menu = new JMenu("Inventory Management");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Inventory Management");

	        menuItem = new JMenuItem("Products",KeyEvent.VK_C);
	        menuItem.setName("Products");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "View Products");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Services",KeyEvent.VK_C);
	        menuItem.setName("Services");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "View Services");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Rentals",KeyEvent.VK_C);
	        menuItem.setName("Rentals");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "View Rentals ");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        
	        menuItem = new JMenuItem("New Stock",KeyEvent.VK_C);
	        menuItem.setName("NewStock");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Add new Stock");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Price Change",KeyEvent.VK_C);
	        menuItem.setName("PriceChange");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Price Change");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);


	        menuItem = new JMenuItem("Add Inventory",KeyEvent.VK_C);
	        menuItem.setName("AddInventory");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Addn Inventory");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Product Catalogue",KeyEvent.VK_C);
	        menuItem.setName("ProductCatalogue");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "View the Product Catalogue");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);


			menuItem = new JMenuItem("PIM System",KeyEvent.VK_C);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "View the PIM System");
	        menuItem.setName("PIMSystem");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuBar.add(menu);

	        // ---->Continue adding the accessible descriptions below per menu item @9/15/23 7:04 AM - Briant Guzman  also continue optimizing program below this point.
	        
	        menu = new JMenu("Accounts Receivable");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Accounts Receivable");
	        menuItem.addActionListener(this);
	    	        
	        menuItem = new JMenuItem("Customer Management System",KeyEvent.VK_C);
	        menuItem.setName("CustomerManagementSystem");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Customer Management System");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Review Customer Pricing",KeyEvent.VK_C);
	        menuItem.setName("ReviewCustomerPricing");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Review Customer Pricing");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Customer Invoices",KeyEvent.VK_C);
	        menuItem.setName("InvoicesAR");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Customer Invoices");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Record Payment",KeyEvent.VK_C);
	        menuItem.setName("PaymentRemittance");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Payment Remittance");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	       	        
	        menuBar.add(menu);

	        
	        menu = new JMenu("Accounts Payable");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Accounts Payable");
	        
	        menuItem = new JMenuItem("Vendor Management System",KeyEvent.VK_C);
	        menuItem.setName("VendorManagementSystem");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Vendor Management System");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Vendor Catalogs",KeyEvent.VK_C);
	        menuItem.setName("Vendor Catalogs");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Vendor Catalogues");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuItem = new JMenuItem("Purchase Orders",KeyEvent.VK_C);
	        menuItem.setName("PurchaseOrders");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Purchase Orders");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Invoices to Pay",KeyEvent.VK_C);
	        menuItem.setName("InvoicesAP");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Vendor Invoices");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);


	        menuItem = new JMenuItem("Advance Shipping Notices",KeyEvent.VK_C);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Advance Shipping Notices");
	        	        menuItem.setName("ASNs");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuBar.add(menu);

	        



	        menu = new JMenu("User Management");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");
	        
	        // This menu item will open the user management system.
	        menuItem = new JMenuItem("Users & Permissions",KeyEvent.VK_C);
	        menuItem.setName("UserPermissions");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "User Permissiosn");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        // This menu item will open the group management system.
	        menuItem = new JMenuItem("Groups & Permissions",KeyEvent.VK_C);
	        menuItem.setName("GroupPermissions");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Group Permissions");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuBar.add(menu);

	        
	        menu = new JMenu("Help");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menuItem = new JMenuItem("Contact Customer Support",KeyEvent.VK_C);
	        menuItem.setName("ContactCustomerSupport");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Contact Customer Support");
	        			menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));  
		  menuItem.addActionListener(this);
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					try { Desktop.getDesktop().browse(new URI("https://lockwind.com/customer_support.php" )); } 
					catch (Exception e1) { e1.printStackTrace(); }
			  }
			  });
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Platform Training",KeyEvent.VK_C);
	        menuItem.setName("PlatformTraining");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Platform Training");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuItem = new JMenuItem("About Lockwind POS",KeyEvent.VK_C);
	        menuItem.setName("About");
	        menu.getAccessibleContext().setAccessibleDescription(
	                "About Lockwind POS");
	        	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuBar.add(menu);

	        


	        menu = new JMenu("Alerts");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "Alerts");
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
	    // account_name_input.setEditable(true);

//		CONSTRUCTOR
public Register() {
	
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
	frame.add(bottomPanel);
	frame.add(tablePanel);
	
	frame.pack();
	frame.setVisible(true);

	table.requestFocus();
	table.changeSelection(0,0, false,false);

	System.out.print("Column:count - > "  + table.getModel().getColumnCount());

	// Print names of columns assigned to table model
	for(int i = 0; i < table.getModel().getColumnCount(); i++){
		System.out.println(table.getColumnName(i));
	}
	
	
	
/*// Temporarily disabled in order to complete it correctly. on 9/18/2023. */

	/*
			System.out.println("Register-> Session with Verifone Device");
			try { 
			  registerPOS();
			  registerStatus = true;
			System.out.println( Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ); // returns mac_label
}catch(Exception e){
System.out.println( "Failed to register POS, please register manually: " + e.toString() );
}
*/

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


public void RegisterVerifone() { 

	
	System.out.println("Register-> Session with Verifone Device");
    
	if(registerStatus == false)
try {

	
	//	  registerPOS();
	//	  Thread.sleep(1000);
	//	  registerStatus = true;
	//		System.out.println( Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ); // returns mac_label

}catch(Exception e) {
		System.out.println("Error at Register set default values " + e.toString());
	}
	else {
		System.out.println( "Failed to register POS, please register manually: " + e.toString() );
	} 
	
}


public void updateRow(JTable table, int i){
	table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); // Update Subtotal for this row
	table_manager.setData( table,i,6,table_manager.getTax(table,i)); // Update taxes for this row
}

public void clearRegister() {
	
	subtotalLabel	.setText("$ 0.00");
	taxesLabel		.setText("$ 0.00");
	totalLabel		.setText("$ 0.00");
	tenderLabel		.setText("$ 0.00");
	changeLabel		.setText("$ 0.00");
	discountLabel	.setText("$ 0.00");
	tender_amount	.setText("");
	addenda			.setText("");

    account_name_input.setSelectedIndex(0);

	table_manager	.clearTable(table);
	table			.changeSelection(0,0,false,false);
	table			.requestFocus();
  
  }
  


public void buildActionListener() {
	
	//----------------------------------------------------------F1 GTIN UPDATE ACTION PROC
		String GTINAction = "GTINAction";

		InputMap inputMap = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap actionMap = table.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), GTINAction);
		actionMap.put(GTINAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {
	   		
			// Create a new method to process GTIN data here. actionGTIN() as was the previous method.
			  
		}});

	//---------------------------------------------------------F1 GTIN UPDATE ACTION END 



	//----------------------------------------------------------F2 QTY UPDATE ACTION PROC
		String qtyAction = "qtyAction";

//		InputMap inputMap = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//		ActionMap actionMap = table.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), qtyAction);
		actionMap.put(qtyAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {
	          updateQTY();
		}
	      
	  });

	//---------------------------------------------------------F2 QTY UPDATE ACTION END 


	//----------------------------------------------------------F3 DESCRIPTION UPDATE ACTION PROC
		String descriptionAction = "descriptionAction";

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), descriptionAction);
		actionMap.put(descriptionAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {
	          
	          updateDescription();

		}});

	//---------------------------------------------------------F3 PRICE UPDATE ACTION END 



	//----------------------------------------------------------F4 PRICE UPDATE ACTION PROC
		String priceAction = "priceAction";

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), priceAction);
		actionMap.put(priceAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {

	          updatePrice();
	          
		}});

	//---------------------------------------------------------F4 PRICE UPDATE ACTION END 



	//----------------------------------------------------------F5 TAX ACTION PROC
		String taxAction = "taxAction";

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), taxAction);
		actionMap.put(taxAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {
	          updateTax();
		}});
	//---------------------------------------------------------F5 TAX ACTION END 

	//----------------------------------------------------------F6 DISCOUNT UPDATE ACTION PROC
		String discountAction = "discountAction";

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), discountAction);
		actionMap.put(discountAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {

	          updateDiscount();

		}});

	//---------------------------------------------------------F6 DISCOUNT UPDATE ACTION END 

		//----------------------------------------------------------F12 DISCOUNT UPDATE ACTION PROC
		String tenderAction = "tenderAction";

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), tenderAction);
		actionMap.put(tenderAction, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {

	          tenderAction(0.00);
	          

		}});

	//---------------------------------------------------------F12 DISCOUNT UPDATE ACTION END 	
		
		
	//----------------------------------------------------------DELETE KEY ACTION PROC
		String DELETE = "Delete";
		inputMap = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		actionMap = table.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), DELETE);
		actionMap.put(DELETE, new AbstractAction() {
	   	public void actionPerformed(ActionEvent e) {
		int i = 0; int j = 0;
		if(table.isEditing()){table.getCellEditor().stopCellEditing();}

		i = table.getSelectedRow();
		j = table.getSelectedColumn();
		table_manager.clearRow(table,i);

		JOptionPane.showMessageDialog(null,"Refreshing Total Now");
		refreshTotal(table,0.00,0.00);


		if(registerStatus == true){
			
			  try { 
	  			  removeLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;

			  }catch (Exception e1) {
				  	System.out.println("Register - delete key action error: " + "cannot connect to register" + e1.toString() );
			  }
		}else {}


		table.changeSelection(i,0, false,false);
		}});
	//---------------------------------------------------------DELETE KEY ACTION END 

	//---------------------------------------------------------ENTER KEY ACTION PROC
		
		InputMap im 			= table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap am 			= table.getActionMap();
		KeyStroke enterKey 		= KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		im						. put(enterKey, "Action.enter");
		am						. put("Action.enter", new AbstractAction() {

		public void actionPerformed(ActionEvent evt) {
		
			table				. putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		if (table.isEditing()){table.getCellEditor().stopCellEditing();}
		

		int i 					= 0; 
		int j 					= 0;

		i 						= table.getSelectedRow();
		j 						= table.getSelectedColumn();
		

		double discount 		= 0.00;
		double discountPrice	= 0.00;
		double st 				= 0.00;
		String productInfo 		= "";
		String inputGTIN 		= "";
		invoice_line_item 		= new ElectronicDocumentLineItem();
		
	    
		if(j== 0) // Column: UPC
		{
		
//		JOptionPane.showMessageDialog(null,"Register.Enter Key Action Proc - Updating GTIN");
//		JOptionPane.showMessageDialog(null,table_manager.getData(table,i,j).toString());
		
		 inputGTIN 				= table_manager.getData(table,i,0).toString(); // COL 0: GTIN
			
		 table_manager			. setData(table,i,1,"1"); // COL 1: QTTY
 
		 try { productInfo = product_management_system.getProductInfoAPICategory(inputGTIN); } // COL 2: CATEGORY
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN"); }
		 table_manager.setData(table,i,2,productInfo);

		 try { productInfo = product_management_system.getProductInfoAPIBrandDescription(inputGTIN); } // COL 3: DESCRIPTION
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN"); }
		 table_manager.setData(table,i,3,productInfo);

		 try { productInfo = product_management_system.getProductInfoAPIPriceRetail(inputGTIN); } // COL 4: PRICE
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN Retail Price"); }
		  table_manager.setData(table,i,4,productInfo); // PRICE RETAIL

		  invoice_line_item.setUPC			(table_manager.getData(table,i,0).toString() );
		  invoice_line_item.setQTY			(Double.parseDouble( table_manager.getData(table,i,1).toString() ) );
		  invoice_line_item.setCategory		(table_manager.getData(table,i,2).toString() );
		  invoice_line_item.setDescription	(table_manager.getData(table,i,3).toString() );
		  invoice_line_item.setRetailPrice	(Double.parseDouble( table_manager.getData(table,i,4).toString() ) );
		  invoice_line_item.getSubtotal		();
		  invoice_line_item.getTaxes		();
		  
		  invoice.addInvoiceLineItem(invoice_line_item);

		  
		  
//		 try { productInfo = product_management_system.getProductInfoAPIPriceRetail(inputGTIN); } // COL 5: SUBTOTAL
//		 catch(Exception e) { System.out.println("Exception thrown on askGTIN Retail Price"); }
//		 table_manager.setData(table,i,5,table_manager.getSubTotal(table,i)); 

		 
		 /*
		 try { productInfo = product_management_system.getProductInfoAPIPriceRetail(inputGTIN); } // COL 6: TAX
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN Retail Price"); }
		 table_manager.setData(table,i,5,(table,i)); 
 		 */
		if(j == 1) // Column: QTY
		{
			JOptionPane.showMessageDialog(null,"Register.Enter Key Action Proc - Updating QTY");
			updateQTY();
            updateSubTotal();
		}
		
		if(j == 2) // Column: Category 
		{
			JOptionPane.showMessageDialog(null, "Category cannot be edited");
			
		}
		
	    
		if( j == 3){ // Column: Description
	          updateDescription();
	      }
	      if ( j == 4){ // Column: Price
	          updatePrice();
			  updateSubTotal();
	      }
	      if( j == 5){ // Column: Subtotal
			  updateSubTotal();
	      }
		  if( j == 6) // Column: Tax
		  {
				updateTax();
		  }
	      
	      if ( j == 7 ) // Column: Discount
	      {
	          	updateDiscount();
	      }

	      if ( j == 8 ) // Column: OnHand
	      {
	          updateDiscount();
	      }

 			updateRow(table,i);
	 		refreshTotal(table,0.00,0.00);

			System.out.println( "Electronic Document: " + invoice.toString() + "");
			subtotalLabel.setText( 	"$ " + invoice.getTransactionSubTotal() ); // Set value to UI Label
 			taxesLabel.setText( 	"$ " + invoice.getTransactionTaxesTotal() ); // Set value to UI Label
 			totalLabel.setText(		"$ " + formatter.format(Double.parseDouble(invoice.getTransactionTotal() ) ) ); // Set value to UI Label
 			discountLabel.setText(	"$ " + invoice.getTransactionDiscountTotal() ); // Set value to UI Label

 			table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); // Update Subtotal for this row
 			table_manager.setData( table,i,6,table_manager.getTax(table,i)); // Update taxes for this row


		  // refreshTotal(table,0.00,0.00);


			System.out.println("**********------------->>>>>> line item row: " + i + ";");
			
			System.out.println(formatter.format(Double.parseDouble(invoice.getTransactionSubTotal() ) ) );
			System.out.println(formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal() ) ) );
			System.out.println(formatter.format(Double.parseDouble(invoice.getTransactionTotal() ) ) );			

			System.out.println(invoice_line_item.toString() );

// Register Enter Key action Proc 

	if(registerStatus == false){
			
	try { 
			//registerPOS();
			//registerStatus = true;
		
			
		
	} catch(Exception a1) {  }
	}

	if(registerStatus == true){
			
			  try { 

			String temp = "";
			  		System.out.println("New Message from Verifone: ");
				  temp = Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ;

//			JOptionPane.showMessageDialog( null, Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ); // returns mac_label
			mac_label =  temp; // returns mac_label

			startSession();
        
	  		System.out.println("New Message from Verifone: ");
			  temp = Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ;
            System.out.println(temp);
            sessionInProgress = true;

//			JOptionPane.showMessageDialog( null, Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) );  // return Session Started

	  			  addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;

			  		System.out.println("New Message from Verifone: ");
				  temp = Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) ;
	            System.out.println(temp);

} catch(Exception e){
		System.out.println("Error starting session: " + e.toString() );
//				  startSession();

				try { 
//	  			  addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
	//				String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				
				if( table_manager.getData(table, i, 0).toString().equalsIgnoreCase("") ) 
				{
				  addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				}
				else { 
				 overrideLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				}

				} catch(Exception exx){
			JOptionPane.showMessageDialog( null, exx.toString() +  " : " + Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) );
				try { 
				  addLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				 overrideLineItem( formatter.format(Double.parseDouble(invoice.getTransactionSubTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())) ,formatter.format(Double.parseDouble(invoice.getTransactionTotal())),
					String.valueOf(i), "SKU",table_manager.getData(table, i, 0).toString(), table_manager.getData(table, i, 3).toString(),table_manager.getData(table, i, 1).toString(), table_manager.getData(table, i, 4).toString(),table_manager.getData(table, i, 5).toString() ) ;
				} catch(Exception exe){
			JOptionPane.showMessageDialog( null, exe.toString() +  " : " + Documents.selectFirst( responseDocElement, "RESPONSE_TEXT", "" ) );
}

}
}
}else { 
System.out.println("Register Enter Key Action Error: Credit card payment terminal cannot be reached");

}}

}});





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
	      int row = table.rowAtPoint(evt.getPoint());
	      int col = table.columnAtPoint(evt.getPoint());
	      if (row >= 0 && col >= 0) {
// 		NumberFormat formatter = new DecimalFormat("#0.00");


	if(table_manager.getData(table,row,col) == null || table_manager.getData(table,row,col).toString().equalsIgnoreCase("") )
	{

//	  refreshTotal(table,0.00,0.00);

	  if(col == 0)
	  {
		  // actionGTIN();
	      // refreshTotal(table,0.00,0.00);
	  }
	  if(col == 1)
	  {
	      JOptionPane.showMessageDialog(null,"Action at Quantity Column = product is null");
//	      table.changeSelection(++row,0,false,false);
	  }
//	if(col == 6) { /*updateDiscount(); */}
	}
	else
	{
	  if(col == 0) { /*  actionGTIN();  */
	  }
	  if( col == 1) { updateQTY(); }
	  if( col == 2) {} // updateCategory(); -- implement this function to retrieve the category according to the UPC/GTIN provided.
	  if( col == 3) { updateDescription(); }
	  if( col == 4) { updatePrice(); }
	  if( col == 5) { table.changeSelection(row,0,false,false); } // updateSubtotal();
	  
	  if( col == 6) { updateTax(); }
	  if( col == 7) { updateDiscount(); }

	  
//		table_manager.setData(table,row,col,"");
	}
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
				  unregisterPOSAll();
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


public void focusGained(FocusEvent e)
{
	if( e.getSource() instanceof JTextArea)
	{
		JTextArea temp = null;
		temp = (JTextArea) e.getSource();
		
		if( temp.getName().equalsIgnoreCase( "addenda") )
		{
			System.out.println("Reading Addenda Information: ");
		}
	}
}
public void focusLost(FocusEvent e)
{
	if( e.getSource() instanceof JTextArea)
	{
		JTextArea temp = null;
		temp = (JTextArea) e.getSource();
		
		if( temp.getName().equalsIgnoreCase( "addenda") )
		{
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
    	  
    	  if(menu_item.getName().equalsIgnoreCase("Customers")) {
    		  JOptionPane.showMessageDialog(null,"Opening CRM");
    	  }
    	  

		  if(menu_item.getName().equalsIgnoreCase("ProductCatalogue")) {
			JOptionPane.showMessageDialog(null,"Opening Product Menu");
			RestaurantMenuGUI test = new RestaurantMenuGUI();

		}


		}
	  
	  
	  
      
      if( e.getSource() instanceof JComboBox){
          
          JComboBox temp = (JComboBox) e.getSource();
          
          if( temp.getName().equalsIgnoreCase("account_name_input"))
          {
              customer_selected = account_name_input.getSelectedItem().toString();
			  
			  Customer customer = null;
			  customer = customer_management_system.getCustomerTarget(customer_selected);

			  // Fix the lines below and make sure that the invoices data are being set by the transaction.

//             invoice.setBillToCustomerNameData( customer.getCustomerBillToName() );
//			 invoice.setBillToCustomerCodeData( customer.getCustomerBillToCodeData() );
  //           invoice.setBillToCustomerAddressData(customer_management_system.getCustomerBillToAddress(customer_selected));
    //         invoice.setBillToCustomerCityData(customer_management_system.getCustomerBillToCity(customer_selected));
      //     invoice.setBillToCustomerStateData(customer_management_system.getCustomerBillToState(customer_selected));
       //      invoice.setBillToCustomerZipcodeData(customer_management_system.getCustomerBillToZipcode(customer_selected));
          //   invoice.setBillToCustomerCountryData(customer_management_system.getCustomerBillToCountry(customer_selected));
              
		//	 label_bill_to_customer_name_data.setText( customer.getCustomerBillToName() );

			  label_bill_to_customer_address_data.setText( invoice.getBillToCustomerAddressData() );
              label_bill_to_customer_city_data.setText( invoice.getBillToCustomerCityData() );
              label_bill_to_customer_state_data.setText( invoice.getBillToCustomerStateData() );
              label_bill_to_customer_zipcode_data.setText( invoice.getBillToCustomerZipcodeData() );
              label_bill_to_customer_country_data.setText( invoice.getBillToCustomerCountryData() );
              label_bill_to_customer_phone_number_data.setText( invoice.getBillToCustomerPhoneNumberData() );
              label_bill_to_customer_email_address_data.setText( invoice.getBillToCustomerEmailAddressData());


//			  invoice.setShipToCustomerCodeData(customer_management_system.getCustomerShipToName(customer_selected));
//			  invoice.setShipToCustomerAddressData(customer_management_system.getCustomerShipToAddress(customer_selected));
//			  invoice.setShipToCustomerCityData(customer_management_system.getCustomerShipToName(customer_selected));
//			  invoice.setShipToCustomerStateData(customer_management_system.getCustomerShipToName(customer_selected));
//			  invoice.setShipToCustomerZipcodeData(customer_management_system.getCustomerShipToName(customer_selected));
//			  invoice.setShipToCustomerCountryData(customer_management_system.getCustomerShipToName(customer_selected));
 



			  //			  JOptionPane.showMessageDialog(null,"Selected Account: " + account_name_input.getSelectedItem() );
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
          
          if( temp.getName().equalsIgnoreCase("payment_method"))
          {
        	  
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
          
          
          if( temp.getName().equalsIgnoreCase("account_name_input")){
              JOptionPane.showMessageDialog(null,"Action Source: " + temp.getName() ) ;

          }
          else{
              
          }
      }
      
      if(e.getSource() instanceof JButton )
      {
          JButton temp = (JButton) e.getSource();
          
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

      		String verifone_device_address = "192.168.50.197";
    		int verifone_main_port = 5015;
    		int verifone_secondary_port = 5016; 

      		SessionManager client = new SessionManager(verifone_device_address, verifone_main_port,verifone_secondary_port);
    		client.setVisible(true);
          }
          
          
          if( temp.getName().equalsIgnoreCase("new_stock"))
          {
        	  
//     	  try { Desktop.getDesktop().browse(new URI("http://lockwind.com/test/PIM/price_change.php?retailer_uuid=" + retailerUUID )); } 
          int row = 0; int col = 0;
          row = table.getSelectedRow();
          col = 0;
//          col = table.getSelectedColumn();


          if(table_manager.getData(table, row, col) != null) 
          {

    	  try { 
        	  String x = table_manager.getData(table,row,col).toString();

    		  Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php?retailer_uuid=" + retailerUUID + "&reference_code=" + x ));
          }
          
          catch(Exception e2)
          {

        	  try { Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php" ) );  
        	  } catch (Exception e1) { e1.printStackTrace(); }

        	  e2.printStackTrace();
          }
          }
          else { 
        	  System.out.println("Opening new stock window because table manager is null;");
        	  try { Desktop.getDesktop().browse(new URI("https://lockwind.com/test/pim_index.php" ) );  
        	  } catch (Exception e1) { e1.printStackTrace(); }

          }
        	  
          }
          if( temp.getName().equalsIgnoreCase("inventory"))
          {
              QTY test = new QTY();
              test.setScreenSize();
              test.setFrame();
              test.setComponentDefaultValues();
              test.finishFrame();            }
          
          if( temp.getName().equalsIgnoreCase("sales_report_button"))
          {
        	  try { 
        	        Desktop.getDesktop().browse(new URI("https://lockwind.com/test/SalesReport.php"));  
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
      }
          if( temp.getName().equalsIgnoreCase("tender_cash")){
              if(table.isEditing()){table.getCellEditor().stopCellEditing();}
              tenderAction(0.00);
      }

      }   
      }
  
  
  
  
  
	  public void askGTIN(JTable table,int i, int j, String temp) {

   
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String currentTimeString = sdf.format(currentTime);
		System.out.println("@askGTIN ->  STARTING FUNCTION CALL: " + currentTimeString) ;

		StringBuilder tempData = new StringBuilder();
		String inputGTIN = "";
		String productInfo = "";
   
		System.out.println("@askGTIN ->  PROCESSING: " + currentTimeString) ;
	 
		// Process A: GTIN is not provided by user using barcode scanner.
		currentTimeString = sdf.format(currentTime);
		System.out.println("@askGTIN ->  Checking if GTIN is provided or not: " + currentTimeString) ;		 
		
		if (temp.equalsIgnoreCase("") ) {  
		
		currentTimeString = sdf.format(currentTime);
		System.out.println("@askGTIN ->  GTIN is not provided: " + currentTimeString) ;		 

		currentTimeString = sdf.format(currentTime);		
		System.out.println("@askGTIN ->  Requesting GTIN Information to user: " + currentTimeString) ;
		inputGTIN = JOptionPane.showInputDialog(null,"SCAN/ENTER BARCODE:");
		table_manager.setData( table,i,0, inputGTIN );
		 
		currentTimeString = sdf.format(currentTime);		
		System.out.println("@askGTIN -> Validating the GTIN reference code using the class ValidationPlatform: " + currentTimeString) ;
		 error_message = validator.validateReferenceCode(inputGTIN);
   
		 if(error_message.equalsIgnoreCase("") )
		 {

			table_manager.setData( table,i,0,table_manager.getData(table,i,0).toString() );
			inputGTIN = table_manager.getData(table,i,0).toString();
			table_manager.setData( table,i,1,"1" );
		   
			try { productInfo = product_management_system.getProductInfoAPICategory(inputGTIN); }
			catch(Exception e) { System.out.println("Exception thrown on askGTIN"); }
			table_manager.setData(table,i,2,productInfo);
   
			try { productInfo = product_management_system.getProductInfoAPIBrandDescription(inputGTIN); }
			catch(Exception e) { System.out.println("Exception thrown on askGTIN"); }
			table_manager.setData(table,i,3,productInfo);
   
			try { productInfo = product_management_system.getProductInfoAPIPriceRetail(inputGTIN); }
			catch(Exception e) { System.out.println("Exception thrown on askGTIN Retail Price"); }
			// table_manager.setData(table,i,4,productInfo); // PRICE RETAIL
			
			

//			table_manager.setData(table,i,5,"9.99"); // SUBTOTAL
//		   table_manager.setData(table,i,5,"0.00"); // SUBTOTAL
//		   table_manager.setData(table,i,5,"0.00"); // TAX
//		   table_manager.setData(table,i,5,"0.00"); // DISCOUNT
		   // table_manager.setData(table,i,5,""); // ONHAND	

		 // Process A.1 - Validation of GTIN is successful now proceed to input data into JTable.
//			 table_manager.setData(table,i,0,inputGTIN);
//			 product_management_system.setRetailerUUID(table_manager.getRetailerUUID());			  
//			 productInfo = product_management_system.getProductInfoAPI(u);
//			 productInfo = product_management_system.getProductInfoAPI();

			 //			 table_manager.fillRow(table,i,table_manager.getData(table,i,0).toString(),product_management_system.getRetailerUUID());
//			 table_manager.setData(table,i,1,"1");
//			 table_manager.setData(table,i,4,table_manager.getSubTotal(table,i));table_manager.setData(table,i,5,table_manager.getTax(table,i));
	 
			 // Change this to get quantity on hand and apply to both this method and the calling method , actionGTIN so that both may be seen regardless of action taken by user.
   
//			 updateOnHand(table);
//			 refreshTotal(table,0.00,0.00);
		 }else {
			 JOptionPane.showMessageDialog(null, error_message);
			 table_manager.setData(table,i,0,"");
			 table.changeSelection(i,0,false,false);
		 }
	 }


	 // Process B: GTIN is provided by user using barcode scanner.	
	 else { 
		 
		 error_message = validator.validateReferenceCode(temp);
   
		 if(error_message.equalsIgnoreCase("") == true)
		 {
		 
//		 inputGTIN = table_manager.getData(table,i,0).toString();

		table_manager.setData( table,i,0,table_manager.getData(table,i,0).toString() );
		 table_manager.setData( table,i,1,"1" );
		
		 try { productInfo = product_management_system.getProductInfoAPICategory(inputGTIN); }
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN"); }
		 table_manager.setData(table,i,2,productInfo);

		 try { productInfo = product_management_system.getProductInfoAPIBrandDescription(inputGTIN); }
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN"); }
		 table_manager.setData(table,i,3,productInfo);


		 try { productInfo = product_management_system.getProductInfoAPIPriceRetail(inputGTIN); }
		 catch(Exception e) { System.out.println("Exception thrown on askGTIN Retail Price"); }
		 
		 // table_manager.setData(table,i,4,productInfo); // PRICE RETAIL

//		 table_manager.setData(table,i,5,"9.99"); // SUBTOTAL
//		table_manager.setData(table,i,5,"0.00"); // SUBTOTAL
//		table_manager.setData(table,i,5,"0.00"); // TAX
//		table_manager.setData(table,i,5,"0.00"); // DISCOUNT
		// table_manager.setData(table,i,5,""); // ONHAND

		 

		 table.changeSelection(i,0,false,false);
		}

		
		 //table_manager.fillRow( table, i,table_manager.getData(table,i,0).toString(), product_management_system.getRetailerUUID()  );
		else {
			JOptionPane.showMessageDialog(null, error_message);
			table_manager.setData(table,i,0,"");
			table.changeSelection(i,0,false,false);
		}
	   }

	   System.out.println("Current Selection Cell: " + i + " " + j);
	   updateOnHand(table);
	   
	   table_manager.setData( table,i,5,table_manager.getSubTotal(table,i));
	   // table_manager.setData(table,i,5,table_manager.getTax(table,i) );
	   table.changeSelection((i+1),0,false,false);
	}
  


public void actionGTIN() {
  
	  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
	  
	  i = 0;
	  j = 0;
	  
	  i = table.getSelectedRow();
	  j = table.getSelectedColumn();

  try { 
        String temp = "";
        temp = table_manager.getData(table,i,j).toString();        
        System.out.println("Method started: actionGTIN: " + temp);
        
	  }
	  catch(NullPointerException null_pointer) {
		  
	  }
	  
}

  public void updateOnHand(JTable table){
  
  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
  
  int i = 0;
  i = table.getSelectedRow();
//  j = table.getSelectedColumn();

  int qty_sold = 0;
  int qty_current = 0;
  int qty_on_hand = 0;
  
  inventory_manager = new QTY(false);
  
  qty_sold = inventory_manager.getQtySold( table_manager.getData(table,i,0).toString() );
  qty_current =  inventory_manager.getCurrentQty( table_manager.getData(table,i,0).toString() );
  qty_on_hand = qty_current - qty_sold;
  
  table_manager.setData(table,i,7,String.valueOf( qty_on_hand ) );

}



 public double getTableSubTotalValue() 
 {
	 return table_manager.getColumnTotal(table,5) ; // 6/26/23 Updated value to column 5 per new jTable design including category	 
 }
 public double getTableTaxValue()
 {
	 return table_manager.getColumnTotal(table,6);  // 6/26/23 Updated value to column 6 per new jTable design including category
 }
 public double getTableDiscountValue()
 {
	 return table_manager.getColumnTotal(table,7);  // 6/26/23 Updated value to column 6 per new jTable design including category
 }
  
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



  String sub_total_value_string = formatter.format( Double.parseDouble(invoice.getTransactionSubTotal() ) );
  String tax_value_string = formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal() ) );
  String total_value_String = formatter.format( Double.parseDouble(invoice.getTransactionTotal()) );
  String discount_value_string = format_manager.formatDoubleUS( Double.parseDouble(invoice.getTransactionDiscountTotal() ) );
  
  subtotalLabel.setText( "$ "+ formatter.format( Double.parseDouble(invoice.getTransactionSubTotal() ))); // Set value to UI Label
  taxesLabel.setText( "$ "+ formatter.format( Double.parseDouble(invoice.getTransactionTaxesTotal() ))); // Set value to UI Label
  totalLabel.setText( "$ "+ formatter.format( Double.parseDouble(invoice.getTransactionTotal() ))); // Set value to UI Label
  discountLabel.setText( "$ "+ formatter.format( Double.parseDouble(invoice.getTransactionDiscountTotal() ))); // Set value to UI Label
  
  // taxesLabel.setText( "$ "+ invoice.getTransactionTaxesTotal() ); // Set value to UI Label
 // totalLabel.setText("$ "+ invoice.getTransactionTotal() ); // Set value to UI Label
//  discountLabel.setText("$ " + invoice.getTransactionDiscountTotal() ); // Set value to UI Label
  
// verifone.endSession();
}




 

  public void refreshTotal(JTable table, double tender_value, double change_value)
{
  
  System.out.println("TableManager->@refreshTotal(table,double,double)");

  // NumberFormat formatter = new DecimalFormat("#0.00");
  
  double sub_total_value  = 0.00;
  double tax_value        = 0.00;
  double total_value      = 0.00;
  double discount_value   = 0.00;
  
  sub_total_value = table_manager.getColumnTotal(table,5) ; // 6/26/23 Updated value to column 5 per new jTable design including category
  
  tax_value       = table_manager.getColumnTotal(table,6);  // 6/26/23 Updated value to column 6 per new jTable design including category
  discount_value  = table_manager.getColumnTotal(table, 7);  // 6/26/23 Updated value to column 7 per new jTable design including category
  
  total_value     = (sub_total_value + tax_value) - discount_value;

  invoice.setTransactionSubTotal(String.valueOf( sub_total_value ));
  invoice.setTransactionTaxesTotal( String.valueOf( tax_value ));
  invoice.setTransactionDiscountTotal( String.valueOf(discount_value ));
  invoice.setTransactionTotal( String.valueOf(total_value) );
  

  System.out.println(" Register.refreshTotal() ");
  System.out.println( "-> sub_total_value = " + invoice.getTransactionSubTotal() );
  System.out.println(" -> tax_value = " + invoice.getTransactionTaxesTotal() );
  System.out.println(" -> discount_value = " + invoice.getTransactionDiscountTotal() );
  System.out.println(" -> total_value = " + invoice.getTransactionTotal() );
  System.out.println(" -> tender_value = " + invoice.getTransactionTenderValue() );

  
  



  String sub_total_value_string = formatter.format( Double.parseDouble(invoice.getTransactionSubTotal() ) );
  String tax_value_string = formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal() ) );
  String total_value_String = formatter.format( Double.parseDouble(invoice.getTransactionTotal()) );
  String discount_value_string = format_manager.formatDoubleUS( Double.parseDouble(invoice.getTransactionDiscountTotal() ) );
  
  subtotalLabel.setText( "$ "+ formatter.format(Double.parseDouble(  invoice.getTransactionSubTotal() ))); // Set value to UI Label
  taxesLabel.setText(    "$ "+ formatter.format(Double.parseDouble(  invoice.getTransactionTaxesTotal() ))); // Set value to UI Label
  totalLabel.setText(    "$ "+ formatter.format(Double.parseDouble(  invoice.getTransactionTotal() ))); // Set value to UI Label
  discountLabel.setText( "$ "+ formatter.format(Double.parseDouble(  invoice.getTransactionDiscountTotal() ))); // Set value to UI Label
  
  if( tender_value != 0.00 ) {
      System.out.println(tender_value);
      System.out.println(change_value);

      String tender_value_string = formatter.format(tender_value);
      String change_value_string = formatter.format(change_value);

      tenderLabel.setText("$ " + invoice.getTransactionTenderValue() );
      changeLabel.setText("$ " + invoice.getTransactionChangeValue() );

  }
  else{
      
  }

}
  public void saveTableToReceipt(JTable table,String client_id){   
	  
  
	  System.out.println("Register->@saveTableToReceipt()");
      
      String da               = "";
      FileWriter file         = null;
      PrintWriter outputFile  = null;

      int invoiceNumber       = 0;

//      CreateReceipt test      = new CreateReceipt();

      Date today;
      SimpleDateFormat simpDate;

      DateFormat fmt              = null;
      Locale[]     locale         =     {US,UK,GERMANY,FRANCE}        ;
      int[]         styles        =     {FULL,LONG,MEDIUM,SHORT}    ;
      String[]     styleNames     =     {"FULL","LONG","MEDIUM","SHORT"};
      fmt                         =     null;

      double tendered             = 0.00;
      double subtotal             = 0.00;
      double totaltaxes           = 0.00;
      double total                = 0.00;
      double change               = 0.00;
      double discount             = 0.00;
      double TAX_RATE             = 0.08975;

      
      try{
          try { invoiceNumber = Integer.parseInt(invoice.getInvoiceNumber() ); }catch(Exception e){}
          
          file            = new FileWriter("INV"+ String.valueOf(invoiceNumber) +".txt");
          outputFile      = new PrintWriter(file);

          
          outputFile.println    ("----------------------------------------");
          /*
          outputFile.println        (erp.getStoreName())        ;
          outputFile.println(erp.getStoreAddress() + " " + erp.getStoreSecondAddress());
          outputFile.println(""+erp.getStorePhoneNumber()+ "      " +erp.getStoreFaxNumber());
          outputFile.println();

          */
          outputFile.println("Hello World from Briant Guzman\n");
          outputFile.println(invoice.getStoreName() );
          outputFile.println(invoice.getBillToCustomerAddressData() + " " + invoice.getBillToCustomerAddressData());
          outputFile.println(""+ invoice.getBillToCustomerPhoneNumberData() + "      " + invoice.getBillToCustomerFaxNumberData());
          outputFile.println();

          
          today                 =     new Date();
          fmt                   =     DateFormat.getDateInstance      (styles[3], locale[0]);
          simpDate             	=     new SimpleDateFormat("hh:mm:ss a");
          
          
          outputFile.println(fmt.format(today) +  "    " + simpDate.format(today) + "   Invoice No: " + invoiceNumber);
          outputFile.println("CUSTOMER CODE: " + account_name_input.getSelectedItem() );
          outputFile.println("CUSTOMER Name: " + account_name_input.getSelectedItem() );
          outputFile.println("CUSTOMER Phone Number: " + account_name_input.getSelectedItem() );
          outputFile.println("CUSTOMER Email: " + account_name_input.getSelectedItem() );
          outputFile.println("NAME: ");
          //outputFile.println("CODE:  CASH");
          outputFile.println("REG:   REGISTER 1");
          outputFile.println("----------------------------------------");
          
          outputFile.println(" QTY UPC                 PRICE  SUBTOTAL");
          outputFile.println(" DESCRIPTION                            ");
          
          int in = 0;
          String productName = "";
          
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
              
              System.out.println("saving account " + in);
              da = "";in++;
              
          }
          
          // String amount = JOptionPane.showInputDialog("Please Enter Amount Given");
          
          tendered = Double.parseDouble(invoice.getTransactionTenderValue() );
          subtotal = Double.parseDouble( invoice.getTransactionTotal() );
          totaltaxes = Double.parseDouble( invoice.getTransactionTaxesTotal() );
          discount = Double.parseDouble( invoice.getTransactionDiscountTotal() );

          
          // discount = table_manager.getColumnTotal(table,6);
          // totaltaxes = table_manager.getColumnTotal(table,5);
          // discount = table_manager.getColumnTotal(table,6);
          
          total = subtotal + totaltaxes;
          total = total - discount;
          
          change = tendered - total;
          
          // NumberFormat formatter = new DecimalFormat("#0.00");
          outputFile.println("                         SUB TOTAL $"+formatter.format(subtotal));
          outputFile.println("                         SALES TAX $"+formatter.format(totaltaxes));
          
          if(discount != 0.00)
          {outputFile.println("                        DISCOUNT  $"+formatter.format(discount));}
          else{}
          
          
          outputFile.println("                         TOTAL     $"+formatter.format(total));
          outputFile.println("");
          outputFile.println("                         TENDERED  $"+formatter.format(tendered));
          outputFile.println("                         CHANGE    $"+formatter.format(change));
          
          refreshTotal(table,tendered,change);
          
          
          JOptionPane.showMessageDialog(null,"Change: " + "$"+formatter.format(change));

          int item_count = 0;
          
          for( int i = 0; i < table.getRowCount();i++)
          {
              if(table.getValueAt(i,0) == null || table.getValueAt(i,0).toString().equalsIgnoreCase("")  )
              {
                  
              }else{
                  item_count++;
              }
          }
          outputFile.println("Item Count: " + item_count);
          
          
          outputFile.println("----------------------------------------");
          outputFile.println("Thank you for shopping with " + invoice.getStoreName() + "");
          outputFile.println("For the best Point of Sale System call Lockwind at +1 347 808 5425");
          outputFile.println("");
          outputFile.println("----------------------------------------");
          
          outputFile.close();
          
          
          
          
          
  		try {
		    
			// invoice.setDirectory("./target/classes/lockwind/com/outbound_invoice/");
  		System.out.println("************************************************");
  		System.out.println(invoice.toString());
  		System.out.println("************************************************");
  	
		
		invoice										.setTransactionType("Invoice");
		invoice										.setTransactionUUID(invoice.getInvoiceNumber());
		invoice										.setIssuerUUID( invoice.getIssuerUUID() );
		invoice										.setConsumerUUID(invoice.getConsumerUUID() );		
		invoice										.setLabelIssuerAddressData( invoice.getStoreAddress()  +  " " + invoice.getStoreSecondAddress() );
		invoice										.setLabelIssuerPhoneNumberData( invoice.getStorePhoneNumber() );
		invoice										.setLabelIssuerFaxNumberData( invoice.getStoreFaxNumber() );
		
		invoice										.setLabelIssuerAddress("Address: ");
		invoice										.setLabelIssuerPhoneNumber("TEL: ");
		invoice										.setLabelIssuerFaxNumberData("Fax: ");
		invoice										.setLabelIssuerCity("City: ");
		invoice										.setLabelIssuerCountry("Country: ");
		
		invoice										.setTransactionDate(fmt.format(today));
		invoice										.setTransactionTime(simpDate.format(today));
		invoice										.setBillToCustomerCodeData( account_name_input.getSelectedItem().toString() );
		invoice										.setStoreName( invoice_management_system.getEntityName(invoice.getIssuerUUID()) );
		invoice										.setLabelIssuerPhoneNumberData("+1 212 740 4652");
		invoice										.setBillToCustomerCodeData( account_name_input.getSelectedItem().toString() );
		invoice										.setStorePhoneNumber( invoice.getLabelIssuerPhoneNumberData() );
		invoice										.setLabelIssuerFaxNumberData("+1 347 808 5425");
		
		invoice										.setBillToCustomerNameData( account_name_input.getSelectedItem().toString() );
		invoice										.setBillToCustomerEmailAddressData( account_name_input.getSelectedItem().toString() );
		invoice										.setBillToCustomerPhoneNumberData( account_name_input.getSelectedItem().toString() );
		
        outputFile.println("REG:   REGISTER 1");
        outputFile.println("----------------------------------------");
        
        outputFile.println(" QTY UPC                 PRICE  SUBTOTAL");
        outputFile.println(" DESCRIPTION                            ");

		
		
		}catch(Exception e) {
			
			System.out.println("Error: " + e.toString() );
			
		}

          
          
          
          invoice.buildInvoice();

          
//          JOptionPane.showMessageDialog(null, "ITEM COUNT: " + item_count);
          /*
          try {
              ClientInvoiceReport http = new ClientInvoiceReport();
              http.setInformation(account_name_input.getSelectedItem().toString(),String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD",formatter.format(total),formatter.format(tendered),formatter.format(change));
              http.sendPost();
              in = 0;
              System.out.println("UPLOADING PRODUCT:");
              http.sendProductPost(client_id,client_name,String.valueOf(invoiceNumber),table_manager.getData(table,in,0).toString(),table.getData(table,in,1).toString(),getData(table,in,2).toString(),getData(table,in,3).toString(),getData(table,in,4).toString(),getData(table,in,5).toString(),getData(table,in,6).toString(),getData(table,in,7).toString() );
              

              // Thread.sleep(1000);

          
          
          }catch(Exception e){}
          */
          
      }catch(IOException e){}

      
      try{Process p =  Runtime.getRuntime().exec("cmd /c printReceipt.bat");}
      catch(IOException eex){}
  
  
      table.requestFocus();
      table.changeSelection(0,0, false,false);
      
  
  /* build out this method. */ }

  public void updateQTY(){

	  System.out.println("Register->UpdateQTY");
  int i = 0; int j = 0;
  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
  
  i = table.getSelectedRow();
  j = table.getSelectedColumn();
  String inputQty = "";
  inputQty = JOptionPane.showInputDialog(null,"Enter the Quantity:",table_manager.getData(table,i,j).toString());

  error_message = validator.validateQuantity(inputQty);
  
  if(error_message.equalsIgnoreCase("") == true)
  {
      table_manager.setData(table,i,1,inputQty);
  }
  else {
	  JOptionPane.showMessageDialog(null, error_message);
	  table_manager.setData(table,i,1,"1");
  }
  
 // table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); //-> this is updating the retail price column with the value of the subtotal for this row (error).

  	updateTax();
 
 	refreshTotal(table,0.00,0.00);
 	table.changeSelection(++i,0,false,false);
  
  
}
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
  j = table.getSelectedColumn();
  
  if(table_manager.getData(table,i,5).toString().equalsIgnoreCase("0") || table_manager.getData(table,i,5) == null)
  {
      table_manager.setData(table,i,5,table_manager.getTax(table,i));
      table.changeSelection(++i,0,false,false);
  }
  else
  {
      table_manager.setData(table,i,5,"0");
      table.changeSelection(++i,0,false,false);
  }
  // refreshTotal(table,0.00,0.00);
  
  table.changeSelection(++i,0, false,false);

}


  public void updateSubTotal(){

	  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
  
	  int i = 0;
	  int j = 0;

	  i = table.getSelectedRow();
	  j = table.getSelectedColumn();
  
	  if(table_manager.getData(table,i,1).toString().equalsIgnoreCase("0") == false || table_manager.getData(table,i,1) == null) {
	  
	  double qty = 0;
  	  double price = 0;
  
     qty =  Double.parseDouble( table_manager.getData(table, i, 1).toString() ); 
     price =  Double.parseDouble( table_manager.getData(table, i, 4).toString() ); 
      
      table_manager.setData(table,i,5, String.valueOf( qty*price)   );

  }
  else
  {
//      table_manager.setData(table,i,5,"0");
  }

  table.changeSelection(++i,0,false,false);

  // refreshTotal(table,0.00,0.00);
  
//  table.changeSelection(i,0, false,false);

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


  
  
  
  
  public void tenderAction(double transaction_tender_value)
  {
      System.out.println("Register->@tenderAction()");
      
      row = 0; 
      col = 0;

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
      
      String account_selected 	= null;
      JOptionPane t 			= null;
      String response 			= "";
      int in 					= 0;
      String productName 		= "";
      String store_print_name 	= "";
      int item_count 			= 0;
     
      if(tender_amount.getText().equalsIgnoreCase(""))
      {
    	  tendered				= ali.chargeCustomer();
      }
      else {
          tendered 				= Double.parseDouble(tender_amount.getText() );
      }
      
      subtotal 				= table_manager.getColumnTotal(table,5);
      totaltaxes 			= table_manager.getColumnTotal(table,6);
      discount 				= table_manager.getColumnTotal(table,7);

      
      
      total 				= subtotal + totaltaxes;
      total 				= total - discount;
      change 				= tendered - total;
      
      invoice.setTransactionCurrency("USD");
      invoice.setTransactionSubTotal(String.valueOf(subtotal));
      invoice.setTransactionTaxesTotal(String.valueOf(totaltaxes));
      invoice.setTransactionTotal(String.valueOf(total));
      invoice.setTransactionDiscountTotal(String.valueOf(discount));
      invoice.setTransactionTenderValue(String.valueOf(tendered));
      invoice.setTransactionChangeValue(String.valueOf(change));
      invoice.setBalanceDue(String.valueOf(total-tendered));
      
      System.out.println( invoice.toString() );
      
      if(change > 0.01)
      {
      JOptionPane.showMessageDialog(null,"Change: " + "$ "+ format_manager.formatDoubleUS(change));
      }
      else {
      JOptionPane.showMessageDialog(null,"Balance not paid in full: " + "$ "+ format_manager.formatDoubleUS(change));
    	  
      }
      
      
      row = table.getSelectedRow();
      col = table.getSelectedColumn();
      
      
      
      try {
    	  formatter = new DecimalFormat("#0.00");
          
    	  
//          invoiceNumber = Integer.parseInt(http.getCurrentInvoiceNumber(retailerUUID));
          
    	  
          
          today                 =     new Date();
          fmt                   =     DateFormat.getDateInstance      (styles[3], locale[0]);
          
          simpDate             =     new SimpleDateFormat("hh:mm:ss a");
          
          account_selected = account_name_input.getSelectedItem().toString();
          
          invoice.setBillToCustomerCodeData(account_name_input.getSelectedItem().toString() );
          
          //http.setInformation(account_name_input.getSelectedItem().toString(),String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD", format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change));
          
          System.out.println("Process Activation: Uploading to Lockwind Cloud:");
          
          t = new JOptionPane();
          //response = http.sendPost( invoice.getConsumerUUID(),invoice.getIssuerUUID(),client_id,invoice.getStoreName().trim(),account_selected,String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD",format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change));
          // System.out.println("WS Response:" + response);
          
//          Thread.sleep(1000);

          response = http.sendProductPost( invoice.getConsumerUUID(),invoice.getIssuerUUID(),client_id,invoice.getStoreName().trim(),account_selected,String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD",format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change),
        		  table_manager.getData(table,in,0).toString(),
        		  table_manager.getData(table,in,1).toString(),
        		  table_manager.getData(table,in,2).toString(),
        		  table_manager.getData(table,in,3).toString(),
        		  table_manager.getData(table,in,4).toString(),
        		  table_manager.getData(table,in,5).toString(),
        		  table_manager.getData(table,in,6).toString(),
        		  table_manager.getData(table,in,7).toString(),
        		  table_manager.getData(table,in,8).toString(),
        		  table_manager.getData(table,in,9).toString()
        		  
        		  );

          
          //http.sendProductPost(client_id,client_name,String.valueOf(invoiceNumber),table_manager.getData(table,in,0).toString(),table_manager.getData(table,in,1).toString(),table_manager.getData(table,in,2).toString(),table_manager.getData(table,in,3).toString(),table_manager.getData(table,in,4).toString(),table_manager.getData(table,in,5).toString(),table_manager.getData(table,in,6).toString(),table_manager.getData(table,in,7).toString() );

      }catch(Exception ex){}
      
      
      try{
          
    	  try { 
    		  invoiceNumber = Integer.parseInt(http.getCurrentInvoiceNumber(retailerUUID)); 
              invoice.setInvoiceNumber( String.valueOf(invoiceNumber));
    		  
    	  }catch(Exception ex){}
          
//          file            = new FileWriter("./target/classes/lockwind/com/outbound_invoice/INV/invoice_number.txt");
    	  
//          file            = new FileWriter( "./target/classes/lockwind/com/outbound_invoice/invoice_number" + invoice.getFileExtension() );
//        file            = new FileWriter( "./target/classes/lockwind/com/outbound_invoice/INV"+ String.valueOf(invoiceNumber) +".txt");
          //outputFile.println( String.valueOf(invoiceNumber));


    	  // This process writes the invoice number to the file locally as a backup.
          file            = new FileWriter( invoice.getDirectory() + "invoice_number" + invoice.getFileExtension() ,true);
          outputFile      = new PrintWriter(file);
          outputFile	  . println( invoice.getInvoiceNumber() );
          outputFile	  . close();
          file			  . close();
                    
          file            = new FileWriter(  invoice.getDirectory() + "INV" + String.valueOf(invoiceNumber) + invoice.getFileExtension() );
          outputFile      = new PrintWriter(file);
          
          
     	 /*
  	    invoice.setIssuerName();
  	    invoice.setLocation();
  	    invoice.setSecondLocation();
  	    invoice.setIssuerPhoneNumber();
  	    invoice.setIssuerFaxNumber();
  */
          
          outputFile.println("----------------------------------------");
          outputFile.println( invoice.getStoreName())        ;
          outputFile.println(" " +  invoice.getStoreAddress()+ " " + invoice.getStoreSecondAddress());
          outputFile.print(" " + invoice.getStorePhoneNumber()+ "      ");
          outputFile.println( invoice.getStoreFaxNumber()+"\n");
                            
          today                 =     new Date();
          fmt                   =     DateFormat.getDateInstance      (styles[3], locale[0]);
          
          simpDate             =     new SimpleDateFormat("hh:mm:ss a");
          
          
          outputFile.println(" " + fmt.format(today) +  "    " + simpDate.format(today) + "   Invoice No: " + invoiceNumber);
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
              
              outputFile.println(da);
              outputFile.println(productName+"\n");
              
              System.out.println("saving account " + in);
              da = "";in++;
              
          }
          
          outputFile.println("                         SUB TOTAL $"+ format_manager.formatDoubleUS(subtotal));
          outputFile.println("                         SALES TAX $"+ format_manager.formatDoubleUS(totaltaxes));
          if(discount != 0.00) {
          outputFile.println("                         DISCOUNT  $"+ format_manager.formatDoubleUS(discount));} else{}
          
          
          outputFile.println("                         TOTAL     $"+ format_manager.formatDoubleUS(total));
          outputFile.println("");
          outputFile.println("                         TENDERED  $"+ format_manager.formatDoubleUS(tendered));
          outputFile.println("                         CHANGE    $"+ format_manager.formatDoubleUS(change));
          
          outputFile.println("                         \nAddenda: "+ addenda.getText() );
          
          // refreshTotal(table,tendered,change);
          

          outputFile.println("----------------------------------------");
          outputFile.print("THANKS FOR SHOPPING AT");
          
          store_print_name = " " + invoice.getStoreName().trim() + " ";
          
          outputFile.println(store_print_name);
          outputFile.println("");
          outputFile.println("----------------------------------------");
          outputFile.close();
          
          
          for( int i = 0; i < table.getRowCount();i++)
          {
              if(table.getValueAt(i,0) == null || table.getValueAt(i,0).toString().equalsIgnoreCase("")  )
              {
                  
              }else{
                  item_count++;
              }
          }
          
          
          try {
        	  
              // invoiceNumber = Integer.parseInt(http.getCurrentInvoiceNumber(retailerUUID));
              // ClientInvoiceReport http = new ClientInvoiceReport();
              //http.setInformation(account_name_input.getSelectedItem().toString(),String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD", format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change));
              //http.sendPost(client_id);
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


      
      saveTableToReceipt(table,client_id);
      inventory_manager.saveProductSold(table,client_id);

      try {
      http.IncrementInvoiceNumber(retailerUUID);
      invoiceNumber = Integer.parseInt( http.getCurrentInvoiceNumber(retailerUUID) );
      invoiceNumberLabelDescription.setText( String.valueOf(invoiceNumber));
      }
      catch(Exception ex) {}
      
      table_manager.setData(table,row,col,"");
      table_manager.clearTable(table);
	  clearRegister();
      
	  table.requestFocus();
	  table.changeSelection(0,0,false,false);
	  table.requestFocus();


            	System.out.println(payment_method.getSelectedItem() + " selected");

			if(payment_method.getSelectedItem().toString().equalsIgnoreCase("CASH") )
			{
			
			try {
				displayMessage("Balance Due: $0.00",10);
//				  Thread.sleep(5000);
				  endSession();
				  }
				  catch(Exception e){
				  System.out.println(e.toString() );
				  
			}
			
			}else{
			
			
			
			if(registerStatus == true){
			
			
			  try { 

				  captureCardEarlyReturn();
				  Thread.sleep(1000);
				  authorizeCard();
				  Thread.sleep(1000);
				  endSession();
//				  Thread.sleep(2000);

				  }catch(Exception verifone_exception) {
					  System.out.println(verifone_exception.toString());
				  }
}else { 
System.out.println("Register tenderAction Error: Credit card payment terminal cannot be reached");
}
				
			}




  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
	public boolean registerPOS() throws Exception {

		// Point SCA 4.0 Engage Integration Guide 3.00 Page 44

		
		// generate a random ENTRY_CODE
		//Random generator = new Random();
		//String entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		// System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		
		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        // final KeyFactory kf = KeyFactory.getInstance("RSA");
        // final PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(PRIVATE_KEY)));
        // final PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(PUBLIC_KEY)));
		// final KeyPair keypair = new KeyPair(publicKey, privateKey);

		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);


		// build request
		//Document request = Documents.create("TRANSACTION");
		//Element docElement = request.getDocumentElement();
		
		
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "REGISTER");
		Documents.addElement(docElement, "ENTRY_CODE", entryCode);
		Documents.addElement(docElement, "KEY", PUBLIC_KEY);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		
		
		if( Documents.selectFirst(responseDocElement, "RESULT", "OK").toString().equalsIgnoreCase("OK") ) {
	
//		registerStatus = true;
//		JOptionPane.showMessageDialog(null, "Registered with CC Terminal" + registerStatus);
		System.out.println( "Registered with CC Terminal: " + registerStatus);

		//  		System.out.println("New Message from Verifone: ");

		}else{
					registerStatus = false;
					throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error unable to Register POS"));
		}
		
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}

		
		// validate that the MAC_KEY was returned
		//String encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
	
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
		
		return registerStatus;
	}
	
	public void startSession() throws Exception {

		// Point SCA 4.0 Engage Integration Guide 3.00 Page 70

		
		// generate a random ENTRY_CODE
//		Random generator = new Random();
	//	String entryCode = String.valueOf(generator.nextInt(9999));

		
		
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
//		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        // KeyFactory kf = KeyFactory.getInstance("RSA");
        // PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(PRIVATE_KEY)));
        // PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(PUBLIC_KEY)));
		// KeyPair keypair = new KeyPair(publicKey, privateKey);

//		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
//		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);

		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SESSION");
		Documents.addElement(docElement, "COMMAND", "START");
		Documents.addElement(docElement, "INVOICE", String.valueOf(invoice_number++) );
		Documents.addElement(docElement, "SWIPE_AHEAD", String.valueOf(1));
		Documents.addElement(docElement, "STORE_NUM", String.valueOf(27));
		Documents.addElement(docElement, "LANE", String.valueOf(1));
		Documents.addElement(docElement, "CASHIER_ID", String.valueOf(1));
		Documents.addElement(docElement, "SERVER_ID", String.valueOf(1));
		Documents.addElement(docElement, "SHIFT_ID", String.valueOf(1));
		Documents.addElement(docElement, "TABLE_NUM", String.valueOf(0));
		Documents.addElement(docElement, "BUSINESSDATE", "20230624");
		Documents.addElement(docElement, "PURCHASE_ID", "STELLAR MANAGEMENT");
		Documents.addElement(docElement, "TRAINING_MODE", "ON");
		Documents.addElement(docElement, "TRAINING_MODE", "TRUE");
		
//		Documents.addElement(docElement, "ENTRY_CODE", entryCode);
//		Documents.addElement(docElement, "KEY", PUBLIC_KEY);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
		/*
		 * COMMAND RESPONSE
		 * <RESPONSE>
<RESPONSE_TEXT>SESSION started</RESPONSE_TEXT>
<RESULT>OK</RESULT>
<RESULT_CODE>-1</RESULT_CODE>
<TERMINATION_STATUS>SUCCESS</TERMINATION_STATUS>
<COUNTER>2</COUNTER>
</RESPONSE>
		 * 
		 * This start session command does not return the MAC_LABEL XML element, therefore I remove line.
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
*/
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
	}
	
	
	
	public void endSession() throws Exception {

		// Point SCA 4.0 Engage Integration Guide 3.00 Page 76

		
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
//		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        
//		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
//		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);

		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "FUNCTION_TYPE", "SESSION");
		Documents.addElement(docElement, "COMMAND", "FINISH");

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
	}
	
	
public void addLineItem() throws Exception {
		
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 158

	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "ADD");
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", "04.98");
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", "00.43");
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", "05.42");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		Element docElement2 = request.createElement("LINE_ITEMS");
		Element docElement3 = request.createElement("MERCHANDISE");

		docElement.appendChild(docElement2);
		docElement2.appendChild(docElement3);
		
		Documents.addElement(docElement3, "LINE_ITEM_ID", "1");
		Documents.addElement(docElement3, "SKU", "1");
		Documents.addElement(docElement3, "UPC", "1");
		
		Documents.addElement(docElement3, "DESCRIPTION", "TEFLON TAPE");
		Documents.addElement(docElement3, "QUANTITY", "1");
		Documents.addElement(docElement3, "UNIT_PRICE", "1.99");
		Documents.addElement(docElement3, "EXTENDED_PRICE", "1.99");
		Documents.addElement(docElement3, "FONT_COL_VALUE", "000000");
//		Documents.addElement(docElement3, "LINE_ITEM_ID", "1");

		
		boolean offer = false;
		
		if(offer == true)
		{
			Element docElement4 = request.createElement("OFFER");
			Documents.addElement(docElement4, "TYPE", "1");
			Documents.addElement(docElement4, "LINE_ITEM_ID", "1");
			Documents.addElement(docElement4, "DESCRIPTION", "EMPLOYEE DISCOUNT");
			Documents.addElement(docElement4, "OFFER_AMOUNT", "1.00");
			Documents.addElement(docElement4, "OFFER_LINE_ITEM", "3"); // Create a table for managing the discounts applicable to the system.			
		}
		
		docElement3 = request.createElement("MERCHANDISE");
		docElement2.appendChild(docElement3);
		Documents.addElement(docElement3, "UNIT_PRICE", "2.99");
		Documents.addElement(docElement3, "DESCRIPTION", "TAPE");
		Documents.addElement(docElement3, "LINE_ITEM_ID", "2");
		Documents.addElement(docElement3, "EXTENDED_PRICE", "2.99");
		Documents.addElement(docElement3, "QUANTITY", "1");
		
		
		//Documents.addElement(docElement, "MERCHANDISE", ""); // NO VALUE
		//Documents.addElement(docElement, "LINE_ITEMS", ""); // NO VALUE
		
		//Documents.addElement(docElement, "TRANSACTION", ""); // NO VALUE
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
		/*
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
*/
	
}
	
public void addLineItem(String subtotal,String tax_amount,String trans_amount, String line_item_id, String SKU,String UPC, String line_item_description,String quantity, String unit_price,String extended_price) throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 158

	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "ADD");
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", subtotal);
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", tax_amount);
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", trans_amount);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		Element docElement2 = request.createElement("LINE_ITEMS");
		Element docElement3 = request.createElement("MERCHANDISE");

		docElement.appendChild(docElement2);
		docElement2.appendChild(docElement3);
		
		Documents.addElement(docElement3, "LINE_ITEM_ID", line_item_id);
		Documents.addElement(docElement3, "SKU", SKU);
		Documents.addElement(docElement3, "UPC", UPC);
		
		Documents.addElement(docElement3, "DESCRIPTION", line_item_description);
		Documents.addElement(docElement3, "QUANTITY", quantity);
		Documents.addElement(docElement3, "UNIT_PRICE", unit_price);
		Documents.addElement(docElement3, "EXTENDED_PRICE", extended_price);
		Documents.addElement(docElement3, "FONT_COL_VALUE", "000000");
//		Documents.addElement(docElement3, "LINE_ITEM_ID", "1");

		
		boolean offer = false;
		
		if(offer == true)
		{
			Element docElement4 = request.createElement("OFFER");
			Documents.addElement(docElement4, "TYPE", "1");
			Documents.addElement(docElement4, "LINE_ITEM_ID", "1");
			Documents.addElement(docElement4, "DESCRIPTION", "EMPLOYEE DISCOUNT");
			Documents.addElement(docElement4, "OFFER_AMOUNT", "1.00");
			Documents.addElement(docElement4, "OFFER_LINE_ITEM", "3"); // Create a table for managing the discounts applicable to the system.			
		}
		
/*
  		docElement3 = request.createElement("MERCHANDISE");
 		docElement2.appendChild(docElement3);
		Documents.addElement(docElement3, "UNIT_PRICE", "2.99");
		Documents.addElement(docElement3, "DESCRIPTION", "TAPE");
		Documents.addElement(docElement3, "LINE_ITEM_ID", "2");
		Documents.addElement(docElement3, "EXTENDED_PRICE", "2.99");
		Documents.addElement(docElement3, "QUANTITY", "1");
*/
		
		
		//Documents.addElement(docElement, "MERCHANDISE", ""); // NO VALUE
		//Documents.addElement(docElement, "LINE_ITEMS", ""); // NO VALUE
		
		//Documents.addElement(docElement, "TRANSACTION", ""); // NO VALUE
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
		/*
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
*/
	
}


public void overrideLineItem(String subtotal,String tax_amount,String trans_amount, String line_item_id, String SKU,String UPC, String line_item_description,String quantity, String unit_price,String extended_price) throws Exception {
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 161
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "OVERRIDE");
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", subtotal);
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", tax_amount);
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", trans_amount);

		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		Documents.addElement(docElement, "LINE_ITEM_ID", line_item_id);
		Documents.addElement(docElement, "DESCRIPTION", line_item_description);
		Documents.addElement(docElement, "QUANTITY", quantity);
		Documents.addElement(docElement, "UNIT_PRICE", unit_price);
		Documents.addElement(docElement, "EXTENDED_PRICE", extended_price);
		Documents.addElement(docElement, "FONT_COL_VALUE", "000000");
//		Documents.addElement(docElement3, "LINE_ITEM_ID", "1");

		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
			
}


public void overrideLineItem() throws Exception {
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 161
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "OVERRIDE");
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", "04.98");
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", "00.43");
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", "05.42");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		Documents.addElement(docElement, "LINE_ITEM_ID", "1"); // You must change the line item to reference the one that must be changed.
		Documents.addElement(docElement, "DESCRIPTION", "TEFLON TAPE"); // description can be changed with override command.
		Documents.addElement(docElement, "QUANTITY", "1");
		Documents.addElement(docElement, "UNIT_PRICE", "2.99");// price can be changed with override command.
		Documents.addElement(docElement, "EXTENDED_PRICE", "2.99");
		Documents.addElement(docElement, "FONT_COL_VALUE", "000000");
//		Documents.addElement(docElement3, "LINE_ITEM_ID", "1");

		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
			
}
	


public void getCounter() throws Exception {

	// Point SCA 4.0 Engage Integration Guide 3.00 Page 79
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
		Documents.addElement(docElement, "COMMAND", "GET_COUNTER");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
			
}




public void removeLineItem(String subtotal,String tax_amount,String trans_amount, String line_item_id, String SKU,String UPC, String line_item_description,String quantity, String unit_price,String extended_price) throws Exception {

	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137

	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "REMOVE");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", subtotal);
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", tax_amount);
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", trans_amount);
		
		Documents.addElement(docElement, "LINE_ITEM_ID", line_item_id); // This only removes the second line item if it exists.
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}


public void removeLineItem() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137

	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "REMOVE");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", "00.00");
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", "00.00");
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", "00.00");
		
		Documents.addElement(docElement, "LINE_ITEM_ID", "2"); // This only removes the second line item if it exists.
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}

public void removeLineItemAll() throws Exception {
	
	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "REMOVEALL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", "00.00");
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", "00.00");
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", "00.00");
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}
	

public void requestEmail() throws Exception {
	
	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "EMAIL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}








public void activateGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "ACTIVATE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT/ MERCH_CREDIT");
		Documents.addElement(docElement, "PAYMENT_SUBTYPE", ""); // OPTIONAL
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00"); // MUST BE A NON-ZERO AMOUNT
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		Documents.addElement(docElement, "SAF_FLAG", "");
		
		
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}



public void reactivateGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "REACTIVATE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT/ MERCH_CREDIT");
		Documents.addElement(docElement, "PAYMENT_SUBTYPE", ""); // OPTIONAL
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00"); // MUST BE A NON-ZERO AMOUNT
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "TOT_NUM_CARDS", "");
		
		
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		
		
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}


public void addValueGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "ADD_VALUE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00"); // MUST BE A NON-ZERO AMOUNT
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}








public void checkBalanceGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "BALANCE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT");
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}





public void cashOutGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "GIFT_CLOSE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT");
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}




public void deactivateGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "DEACTIVATE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT");
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}





public void checkBalanceEBT() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 134
	// This command checks the balance on an EBT card.
	
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "BALANCE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "EBT");
		Documents.addElement(docElement, "EBT_TYPE", "EBT"); // FOOD_STAMP or CASH_BENEFITS -> required for EBT Transactions
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
	
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}










public void tokenQueryCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 154
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "TOKEN_QUERY");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT");
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "ACCT_NUM", ""); // OPTIONAL
		Documents.addElement(docElement, "CARD_EXP_MONTH", ""); // OPTIONAL
		Documents.addElement(docElement, "CARD_EXP_YEAR", ""); // OPTIONAL
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", "ZIP"); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}





public void getDeviceName() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 196

	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "GET_DEVICENAME");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}
	
	/**
	 * Tests that the MAC_LABEL is successfully registered
	 * 
	 * @throws Exception
	 */
	public void requestDonation() throws Exception {
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "CHARITY");
		Documents.addElement(docElement, "DISPLAY_TEXT1", "How much do you love me?");
		Documents.addElement(docElement, "AMOUNT1", "Not at all");
		Documents.addElement(docElement, "AMOUNT2", "A little");
		Documents.addElement(docElement, "AMOUNT3", "A lot");
		Documents.addElement(docElement, "POS_RECON", "GET ECHO FROM TERMINAL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	

	
	
	public void authorizeCard() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 100

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "AUTH");
//		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "TRANS_AMOUNT", formatter.format(Double.parseDouble(invoice.getTransactionTotal())));


		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT");
		Documents.addElement(docElement, "AUTH_CODE", "SAMPLE_CODE"); // NOT A REALISTIC VALUE
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "CUSTOMER_STREET", "1099 ST NICHOLAS");
		Documents.addElement(docElement, "CUSTOMER_ZIP", "10032");
		Documents.addElement(docElement, "MANUAL_PROMPT", "");
		Documents.addElement(docElement, "REF_TROUTD", "5.00");
		Documents.addElement(docElement, "REF_CTROUTD", "5.00");
		Documents.addElement(docElement, "RECURRING", "5.00");
		Documents.addElement(docElement, "BILLPAY", "5.00");
		Documents.addElement(docElement, "FORCE_FLAG", "5.00");
		Documents.addElement(docElement, "CAPTURECARD_EARLYRETURN", "5.00");

		// TAX AMOUNTS
		Documents.addElement(docElement, "TAX_AMOUNT", formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal())));
		Documents.addElement(docElement, "TAX_IND", "2"); // 0 = TAX NOT PROVIDED, 2 - TAX = 0.0, 1 - TAX != 0.00
		Documents.addElement(docElement, "CMRCL_FLAG", ""); // B = BUSINESS, C = CORPORATE, P = PURCHASING
		
		
		// KEYED ACCOUTN  INFORMATION FOR GIFT OR CREDIT CARD PAYMENT TYPES ONLY
/*
		Documents.addElement(docElement, "ACCT_NUM", "373953192351004");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "12");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "25");
		Documents.addElement(docElement, "BARCODE","");
		Documents.addElement(docElement, "PIN_CODE","");
		Documents.addElement(docElement, "CVV2", "");
	*/	
		// PROCESSOR BASED TOKEN PROCESSING (CONDITIONAL)
//		Documents.addElement(docElement, "CARD_TOKEN", "");
//		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
//		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
//		Documents.addElement(docElement, "BANK_USERDATA","");
//		Documents.addElement(docElement, "OC_INDUSTRY_CODE","");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
  
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "APPROVED"))) { 
//			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
			JOptionPane.showMessageDialog(null,"APPROVED");
		}
		else {
		
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		}
	}
	
	
	
	
	public void captureCard() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 107
		
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "CAPTURE");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "TIP_AMOUNT", "5.00");
		Documents.addElement(docElement, "POS_RECON", "1234");
		Documents.addElement(docElement, "EBTCASH_ELEGIBLE", "1234");
		Documents.addElement(docElement, "EBTSNAP_ELEGIBLE", "1234");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT");
		Documents.addElement(docElement, "PAYMENT_SUBTYPE", "CREDIT");
		Documents.addElement(docElement, "EBT_TYPE", "TRUE");
		Documents.addElement(docElement, "AUTH_CODE", "TRUE");
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "CUSTOMER_STREET", "1099 ST NICHOLAS");
		Documents.addElement(docElement, "CUSTOMER_ZIP", "10032");
		Documents.addElement(docElement, "MANUAL_PROMPT", "");
		Documents.addElement(docElement, "PAYMENT_TYPES", "CREDIT|DEBIT|GIFT|FSA");
		
		Documents.addElement(docElement, "REF_TROUTD", "5.00");
		Documents.addElement(docElement, "REF_CTROUTD", "5.00");
		Documents.addElement(docElement, "RECURRING", "5.00");
		Documents.addElement(docElement, "BILLPAY", "5.00");
		Documents.addElement(docElement, "FORCE_FLAG", "5.00");
		Documents.addElement(docElement, "CAPTURECARD_EARLYRETURN", "5.00");

		// TAX AMOUNTS
		Documents.addElement(docElement, "TAX_AMOUNT", "5.00");
		Documents.addElement(docElement, "TAX_IND", "2"); // 0 = TAX NOT PROVIDED, 2 - TAX = 0.0, 1 - TAX != 0.00
		Documents.addElement(docElement, "CMRCL_FLAG", ""); // B = BUSINESS, C = CORPORATE, P = PURCHASING
		
		
		// KEYED ACCOUTN  INFORMATION FOR GIFT OR CREDIT CARD PAYMENT TYPES ONLY
/*
		Documents.addElement(docElement, "ACCT_NUM", "A lot");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "12");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "49");
		Documents.addElement(docElement, "BARCODE","");
		Documents.addElement(docElement, "PIN_CODE","");
		Documents.addElement(docElement, "CVV2", "");

		// PROCESSOR BASED TOKEN PROCESSING (CONDITIONAL)
		Documents.addElement(docElement, "CARD_TOKEN", "A lot");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "12");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "49");
		Documents.addElement(docElement, "BANK_USERDATA","");
		Documents.addElement(docElement, "OC_INDUSTRY_CODE","");
			*/	

		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
  
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	
	
	

	public void creditCard() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 118

		// This transactions returns funds to a customers account.
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "CREDIT");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT"); // CREDIT|DEBIT|GIFT|EBT\CHECK\ALIPAY/KLARNA/WECHAT/PAYPAL/VENMO
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "CTROUTD", "TRUE"); // DO NOT USE IF PAYMENT_TYPE=DEBIT
		Documents.addElement(docElement, "FORCE_FLAG", "TRUE");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// KEYED ACCOUNT  INFORMATION FOR GIFT OR CREDIT CARD PAYMENT TYPES ONLY
		Documents.addElement(docElement, "ACCT_NUM", "A lot");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "12");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "49");
		Documents.addElement(docElement, "BARCODE","");
		Documents.addElement(docElement, "PIN_CODE","");
		Documents.addElement(docElement, "CVV2", "");
	
		Documents.addElement(docElement, "AMOUNT_HEALTHCARE", "");
		Documents.addElement(docElement, "AMOUNT_PRESCRIPTION", "");
		Documents.addElement(docElement, "AMOUNT_VISION", "");
		Documents.addElement(docElement, "AMOUNT_CLINIC", "");
		Documents.addElement(docElement, "AMOUNT_DENTAL", "");

		Documents.addElement(docElement, "CARD_TOKEN", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BANK_USERDATA", "");
		Documents.addElement(docElement, "OC_INDUSTRY_CODE", "");
  
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	
public void voidTransaction() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 125

		// This transactions returns funds to a customers account.
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "void");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "CTROUTD", "TRUE"); // DO NOT USE IF PAYMENT_TYPE=DEBIT
		Documents.addElement(docElement, "EMV_REVERSAL_TYPE", "401"); // CREDIT|DEBIT|GIFT|EBT\CHECK\ALIPAY/KLARNA/WECHAT/PAYPAL/VENMO
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	

public void addTip() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 128

		// This transactions returns funds to a customers account.
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "ADD_TIP");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT"); // CREDIT|DEBIT|GIFT
		Documents.addElement(docElement, "CTROUTD", " "); // Transaction ID to which tip will be attached. NOTE: Not used for SVS. SVS requires swiped track data or keyed account number
		Documents.addElement(docElement, "TIP_AMOUNT", "5.00");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}


public void removeTip() throws Exception {
	
	// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 130

	// This transactions removes the tip from a transaction.
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
	// build request
	request = Documents.create("TRANSACTION");
	docElement = request.getDocumentElement();
	Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
	Documents.addElement(docElement, "COMMAND", "RESET_TIP");
	Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT"); // CREDIT|DEBIT|GIFT
	Documents.addElement(docElement, "CTROUTD", " "); // Transaction ID to which tip will be attached. NOTE: Not used for SVS. SVS requires swiped track data or keyed account number
	Documents.addElement(docElement, "COUNTER", nextCounter);
	Documents.addElement(docElement, "MAC", mac);
	Documents.addElement(docElement, "MAC_LABEL", macLabel);
	
	// transmit to Point Solution and interrogate the response
	responseXml = send(address, port, request);
	responseDocElement = responseXml.getDocumentElement();

	// validate that the RESULT_CODE came back a SUCCESS
	if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
		throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}
}






	//
	public void cancelTransaction() throws Exception {

		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 83

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "CANCEL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	public void rebootDevice() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 85

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "REBOOT");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	public void getStatus() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 87 -> Get device Status

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "STATUS");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	public void getStatusSAF() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 93

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "STATUS");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}	
	
	
	public void checkForUpdates() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 95

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "ANY_UPDATES");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}	
	
	
public void checkUpdateStatus() throws Exception {
		
		// Point SCA 4.0 Engage Integration Guide 3.00 Page 96
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "UPDATE_STATUS");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}	
	



public void applyUpdates() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 82
	
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
	// build request
	request = Documents.create("TRANSACTION");
	docElement = request.getDocumentElement();
	
	Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
	Documents.addElement(docElement, "COMMAND", "APPLYUPDATES");
	Documents.addElement(docElement, "FLAG", "TRUE");
	Documents.addElement(docElement, "COUNTER", nextCounter);
	Documents.addElement(docElement, "MAC", mac);
	Documents.addElement(docElement, "MAC_LABEL", macLabel);
	
	// transmit to Point Solution and interrogate the response
//	Document responseXml = send(address, port, request); 
	responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
	responseDocElement = responseXml.getDocumentElement();

	// validate that the RESULT_CODE came back a SUCCESS
	if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
		throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}
}	

	
public void confirmationAPMKlarna() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 87
	
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
	// build request
	request = Documents.create("TRANSACTION");
	docElement = request.getDocumentElement();
	Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
	Documents.addElement(docElement, "COMMAND", "CONFIRMATION");
	Documents.addElement(docElement, "VALUE", "CONFIRMED"); // Possible Values: CONFIRMED/DENIED
	Documents.addElement(docElement, "COUNTER", nextCounter);
	Documents.addElement(docElement, "MAC", mac);
	Documents.addElement(docElement, "MAC_LABEL", macLabel);
	
	// transmit to Point Solution and interrogate the response
//	Document responseXml = send(address, port, request); 
	responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
	responseDocElement = responseXml.getDocumentElement();

	// validate that the RESULT_CODE came back a SUCCESS
	if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
		throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}
}	



	public void setIdleScreen() throws Exception {
		
		// Point SCA 4.0 Engage Integration Guide 3.00 Page 80

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		temp = "";
		temp = JOptionPane.showInputDialog(null,"Provide message for Idle Screen");

		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
		Documents.addElement(docElement, "COMMAND", "LANE_CLOSED");
		Documents.addElement(docElement, "DISPLAY_TEXT", temp);
		Documents.addElement(docElement, "FONT_COL_VALUE", "000000");
		Documents.addElement(docElement, "FONT_SIZE", "50");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
		
	
	
	
	
	
	/**
	 * Tests that the MAC_LABEL is successfully registered
	 * 
	 * @throws Exception
	 */
	public void testMac() throws Exception {
		
		// Point SCA 4.0 Engage Integration Guide 3.00 Page 

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "TEST_MAC");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	public void laneClosed() throws Exception {
		
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
		Documents.addElement(docElement, "COMMAND", "LANE_CLOSED");
		Documents.addElement(docElement, "DISPLAY_TEXT", "Lane is Closed.");
		Documents.addElement(docElement, "FONT_COL_VALUE", "FF0000");
		Documents.addElement(docElement, "FONT_SIZE", "50");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
/*
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
*/
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
	}


	
	public void lastTransaction() throws Exception {
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        
		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);

		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "REPORT");
		Documents.addElement(docElement, "COMMAND", "LAST_TRAN");
//		Documents.addElement(docElement, "ENTRY_CODE", entryCode);
//		Documents.addElement(docElement, "KEY", PUBLIC_KEY);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
/*
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
*/
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
	}
	
	
	
	
	public void captureCardEarlyReturn() throws Exception {
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
//		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "CAPTURE");
		
		
				
		Documents.addElement(docElement, "TRANS_AMOUNT", formatter.format(Double.parseDouble(invoice.getTransactionTotal())) );
		Documents.addElement(docElement, "CAPTURECARD_EARLYRETURN", "TRUE");
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "FORCE_FLAG", "FALSE");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	
	
	public void displayMessage() throws Exception {
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "DISPLAY_MESSAGE");
		Documents.addElement(docElement, "DISPLAY_TEXT", "BALANCE DUE: $ 0.00");
		Documents.addElement(docElement, "TIMEOUT_DATA", "10");
		Documents.addElement(docElement, "BUTTON_DISPLAY", "0");
		Documents.addElement(docElement, "BUTTON_LABEL", "OKAY");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}

	}
	
	
		public void displayMessage(String message, int timeout_duration) throws Exception {
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "DISPLAY_MESSAGE");
		Documents.addElement(docElement, "DISPLAY_TEXT", message);
		Documents.addElement(docElement, "TIMEOUT_DATA", String.valueOf(timeout_duration));
		Documents.addElement(docElement, "BUTTON_DISPLAY", "0");
		Documents.addElement(docElement, "BUTTON_LABEL", "OKAY");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}

	}
	
	
		
	/**
	 * Unregisters the POS with the Device
	 * 
	 * @throws Exception
	 */
	public void unregisterPOS() throws Exception {
		// generate an ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));
		
		// print out entry code to the user through the UI
		System.out.format("ENTRY_CODE is %s%n", entryCode);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "UNREGISTER");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "ENTRY_CODE", entryCode);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099")))
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}

	
	
	public void unregisterPOSAll() throws Exception {
		// generate an ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));
		
		// print out entry code to the user through the UI
		System.out.format("ENTRY_CODE is %s%n", entryCode);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "UNREGISTERALL");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099")))
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Calculates the MAC of the COUNTER and base 64 encodes it as a String
	 */
	private static String printMacAsBase64(byte[] macKey, String counter) throws Exception {
		// import AES 128 MAC_KEY
		SecretKeySpec signingKey = new SecretKeySpec(macKey, "AES");
		
		// create new HMAC object with SHA-256 as the hashing algorithm
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		// integer -> string -> bytes -> encrypted bytes
		byte[] counterMac = mac.doFinal(counter.getBytes("UTF-8"));

		// base 64 encoded string
		return DatatypeConverter.printBase64Binary(counterMac);
	}
	
	/**
	 * Sends an Document over socket to the address and port and returns
     *  the response as a Document
	 */
	private static Document send(String address, int port, Document request) throws Exception {
		Socket socket = null;
		try {
			// create new socket
			System.out.format("request: %n%s%n", Documents.print(request, true));
			socket = new Socket(address, port);
			Documents.write(request, socket.getOutputStream());
			
			final Document response = Documents.parse(new BufferedInputStream(socket.getInputStream()));
			System.out.format("response: %n%s%n", Documents.print(response, true));
			return response;
		} finally {
			// close socket if not closed
			if (!(socket == null || socket.isClosed())) {
				try {
					socket.close();
				} catch (IOException e) {
					// log error
					e.printStackTrace();
				}
			}
		}
	}

  

  
public static void main(String[] args){Register test = new Register();

System.out.println("This is Lockwind POS Version 3.6 with a modification date of 8/1/23");
}

  
} // End class Register

