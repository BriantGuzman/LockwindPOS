//Last Update: 5/29/2023

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

/* Code removed from Register class
	  
	  private String[] column_header 			= 		{"UPC","QTY","DESCRIPTION","PRICE","SUBTOTAL","TAX","DISCOUNT","ONHAND"};
      private String[] column_header 			= 		{"UPC","QTY","CATEGORY","DESCRIPTION","PRICE","SUBTOTAL","TAX","DISCOUNT","ONHAND"};
	  private int[]    column_width  			= 		{135,55,100,100,55,85,55,75,75};
*/


//---------------------------------------------------- CLASS REGISTER : THIS IS THE MAIN CLASS
public class Register  implements ActionListener,FocusListener {

	  private Invoice invoice;

//	  String invoice_directory 					= 		"./target/classes/lockwind/com/outbound_invoice/";
  //    String invoice_file_extension_txt 		= 		".txt";

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
	  
	  private JLabel label_bill_to_customer_name;
	  private JLabel label_bill_to_customer_name_data;

	  private JLabel label_ship_to_customer_name;
	  private JLabel label_ship_to_customer_name_data;

	  private JLabel label_bill_to_customer_address;
	  private JLabel label_bill_to_customer_address_data;
	  
	  private JLabel label_ship_to_customer_address;
	  private JLabel label_ship_to_customer_address_data;

	  private JLabel label_bill_to_customer_city;
	  private JLabel label_bill_to_customer_city_data;
	  
	  private JLabel label_ship_to_customer_city;
	  private JLabel label_ship_to_customer_city_data;

	  
	  private JLabel label_bill_to_customer_state;
	  private JLabel label_bill_to_customer_state_data;
	  
	  private JLabel label_ship_to_customer_state;
	  private JLabel label_ship_to_customer_state_data;

	  
	  private JLabel label_bill_to_customer_zipcode;
	  private JLabel label_bill_to_customer_zipcode_data;

	  private JLabel label_ship_to_customer_zipcode;
	  private JLabel label_ship_to_customer_zipcode_data;

	  
	  private JLabel label_bill_to_customer_country;
	  private JLabel label_bill_to_customer_country_data;

	  private JLabel label_ship_to_customer_country;
	  private JLabel label_ship_to_customer_country_data;
	  
	  private JLabel label_bill_to_customer_phone_number;
	  private JLabel label_bill_to_customer_phone_number_data;

	  private JLabel label_ship_to_customer_phone_number;
	  private JLabel label_ship_to_customer_phone_number_data;
	  
	  private JLabel label_bill_to_customer_email_address;
	  private JLabel label_bill_to_customer_email_address_data;
	  
	  private JLabel label_ship_to_customer_email_address;
	  private JLabel label_ship_to_customer_email_address_data;
	  
	  
	  private JButton button_tender;
	  
	  private JButton b,c,d,e,f,g,z,zz;
	  
	  
	  
	  private JButton bx001; // customers
	  private JButton bx002; // suppliers
	  private JButton bx003; // update
	  private JButton bx004; // price_change
	  private JButton bx005; // new_stock
	  private JButton bx006; // inventory

	  
	  private JButton pim_button;
	  private JButton salesReport;
	  private JComboBox<String> account_name_input;
	  private String customer_selected; // This is used as a variable that is used to select the customer from the account name combobox for fulfillment. 
	  
	  
	  private JComboBox<String> transaction_type;
	  private JComboBox<String> transaction_type_value;

	  private JTextField tender_amount;

	  
	  // User Specific Components

	  private String retailerUUID;
	  
	  private String da;
	  private int index;
	  private Ali ali;	  

	  private InvoiceLineItem invoice_line_item;
  
	  private ElectronicDocument record = null;


	  private double total;

	  public void setConstructorValues() {
			// action: organize elements, Initialize components,

		  	invoice = null;
		  	
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

			electronic_document_transaction_type = null;
			electronic_document_transaction_type_selected = null;
	  
	  }
	  
	  public void setComponentDefaultValues() {
		    http                        	= new API();
		    validator 						= new ValidationPlatform();
		    product_management_system   	= new ProductManagementSystem();
		    invoice_management_system   	= new InvoiceManagementSystem();
		    customer_management_system 		= new CustomerManagementSystem();
		    printer_management_system 		= new PrinterManagementSystem();
		    
		    inventory_manager 		  		= new QTY();
	//	    table_manager				    = new TableManager();
		    table_manager				    = new TableManager(column_header,column_width);
		    format_manager					= new FormatManager();
		    ali								= new Ali();
		    internet                    	= new Internet();
			erp                       		= new ERP();
			
		    model                       	= new DefaultTableModel(table_row_count,table_col_count);
		    table                       	= new JTable(model);
		    table							.setRowHeight(25);
		    table							.setCellSelectionEnabled(true);
		    table							.setColumnSelectionInterval(0,0);
		    table							.setRowSelectionInterval(0,0);

		    customer_management_system		.loadCustomerDatabase();
		    printer_management_system		.loadPrinterDatabase();

		    // Separator

		    retailerUUID					= "5d4de950d4f69";
			client_id                   	= "5d4de950d4f69";

			record = new ElectronicDocument();

		  	invoice = new Invoice();
			
			try { 
				System.out.println("Register.setComponenentDefaultValues() ");

				String temp = http.getCurrentInvoiceNumber(retailerUUID);
				System.out.println( "Invoice Number from Cloud: " +  temp );
				

				if(retailerUUID == null )
				{
					invoice.setInvoiceNumber(String.valueOf(-1)) ;
				}
				else{
					System.out.println("Result Code: ->>>" + temp);

					if(temp.equalsIgnoreCase("java.net.UnknownHostException: lockwind.com"))
					{
						invoice.setInvoiceNumber("-401");
					}
					else {
						invoice.setInvoiceNumber(temp);
					}
				}



					

				

			} catch(Exception e){

			//			invoice.setDirectory("./target/classes/lockwind/com/outbound_invoice/");
			invoice.setDirectory("./outbound_invoice/");
			invoice.setFileExtension(".txt");
		 	invoice.setIssuerUUID(retailerUUID);
		  	invoice.setConsumerUUID(client_id);

			}

		  	/**
		  	try {

		  		try { 
			  		if( == "" || http.getCurrentInvoiceNumber(retailerUUID) == null)
					{
				  		invoice.setInvoiceNumber(String.valueOf(-1));
					}
				  	else { 
					  	invoice.setInvoiceNumber(String.valueOf( Integer.parseInt(http.getCurrentInvoiceNumber(retailerUUID)) )); 		  		
				  	}
		  			
		  		}catch (java.net.UnknownHostException ef) {
		  			System.out.println("Error: UnkownHostException cannot get Invoice Number.");
		  			JOptionPane.showMessageDialog(null,"Internet not connected");
		  		}	


		  	}catch(Exception e) { 
		  		System.out.println( "Exception at: SetComponentDefaultValues Method: **" );
		  	e.printStackTrace();
		  	}
		  	
		  	*/

			
			
			
			try {
		    product_management_system	  	. setRetailerUUID( invoice.getIssuerUUID() );
		    invoice_management_system	  	. setRetailerUUID( invoice.getIssuerUUID() );
	          invoice_management_system		.setConsumerUUID( invoice.getConsumerUUID() );

	        invoice							.setStoreName( invoice_management_system.getEntityName(invoice.getIssuerUUID()) );
	        
//	    	erp								.setStoreName("         "+ invoice_management_system.getEntityName(invoice_management_system.getRetailerUUID()) +"    ");
	        }catch(Exception e) {}

			invoice							.setStoreAddress("1099 St. Nicholas Avenue,");
			invoice							.setStoreSecondAddress("NY, NY, 10032");
			invoice							.setStorePhoneNumber("TEL: +1 212 740 4652");
			invoice							.setStoreFaxNumber("FAX:             ");
		  	
			screenSize                  	= Toolkit.getDefaultToolkit().getScreenSize();
		    width                       	= screenSize.getWidth();
		    height                      	= screenSize.getHeight();
		    w                           	= (int)(width);
		    h                           	= (int)(height);

		    today                       	= new Date();
		    fmt                         	= DateFormat.getDateInstance(styles[3], locale[0]);
		    simpDate                    	= new SimpleDateFormat("hh:mm:ss a");


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
		    //e                         				= new JButton("");
			//f                        				   	= new JButton("");
			//g                          				= new JButton("");
			button_tender              				 	= new JButton("");
  		    pim_button				  				 	= new JButton("");
		    
		    springLayout                				= new SpringLayout();
		    panelLayout    								= new SpringLayout();
		    layout                      				= new SpringLayout();
		    
			payment_method 								= new JComboBox(paymentMethods);
		    tender_amount								= new JTextField(10);
		    addenda										= new JTextArea(4,3);

			
		    account_name_input  						= new JComboBox<String>( customer_management_system.printComboBoxList() );
		    default_printer_receipt						= new JComboBox<String>( printer_management_system.printComboBoxList() );
		    default_printer_invoice						= new JComboBox<String>( printer_management_system.printComboBoxList() );
		    default_printer_display						= new JComboBox<String>( printer_management_system.printComboBoxList() );
		    
		    account_name_input				.addActionListener(this);
		    account_name_input				.setName("account_name_input");
		    
		    
		    frame							.setLayout(springLayout);
		    panel							.setLayout(panelLayout);
			tablePanel						.setLayout(new BorderLayout());
			bottomPanel						.setLayout(panelLayout);

  		    frame							.setTitle("TRANSACTIONS PANEL");
		    bx001							.setName("customers");
		    bx002							.setName("suppliers");
		    bx003							.setName("update");
		    bx004							.setName("price_change");
		    bx005							.setName("new_stock");
		    bx006							.setName("inventory");
		    
		    /*
  		    b								.setName("customers");
			c								.setName("suppliers");
			d								.setName("update");
			e								.setName("price_change");
			f								.setName("new_stock");
			g								.setName("inventory");
			*/
		    button_tender					.setName("tender");
			pim_button						.setName("pim_button");
			salesReport						.setName("sales_report_button");
			payment_method					.setName("payment_method");
			tender_amount					.setName("tender_amount");
			addenda							.setName("addenda ");
			label_addenda					.setName("Label Addenda ");

			
			
			
			bx001							.setText("Customers"); // 1st Column of buttons, 1st from the top
			bx002							.setText("Suppliers"); // 1st Column of buttons, 2nd from the top
			bx003							.setText("Update"); // 1st Column of buttons, 3rd from the top
			bx004							.setText("Price Change"); // 2nd Column of buttons, 1nd from the top
			bx005							.setText("New Stock"); //  2nd Column of buttons, 2nd from the top
			bx006							.setText("Add Inventory"); // 2nd Column of buttons, 3rd from the top

			salesReport						.setText("Sales Report"); // 3rd Column of buttons, 2nd from the top 
			button_tender					.setText("Tender"); // 3rd Column of buttons, 1st from the top
			pim_button						.setText("PIM"); // 3rd Column of buttons, 3rd from the top
			
		    account_name_label				.setText("Customer");		    
  		    subtotalLabelDescription		.setText("Sub-Total:");
			taxesLabelDescription			.setText("Tax:");
			totalLabelDescription			.setText("Total:");
			tenderLabelDescription			.setText("Tender:");
			changeLabelDescription			.setText("Change:");
			discountLabelDescription		.setText("Discount:");

			subtotalLabel					.setText("$ 0.00");
			taxesLabel						.setText("$ 0.00");
			totalLabel						.setText("$ 0.00");
			tenderLabel						.setText("$ 0.00");
			changeLabel						.setText("$ 0.00");
			discountLabel					.setText("$ 0.00");
			label_addenda					.setText("Addenda");
			
			default_printer_receipt_label	.setText("Receipt Printer: ");
			default_printer_invoice_label	.setText("Invoice Printer: ");
			default_printer_display_label	.setText("Display Printer: ");
			
			invoice.setBillToCustomerNameLabel("Bill to Customer: ");
			invoice.setBillToCustomerNameData("customer name");
			invoice.setBillToCustomerAddressLabel("Bill To Address: ");
			invoice.setBillToCustomerAddressData("address");
			invoice.setBillToCustomerCityLabel("City: ");
			invoice.setBillToCustomerCityData("city");

			invoice.setBillToCustomerStateLabel("State: ");
			invoice.setBillToCustomerStateData("state");
			
			invoice.setBillToCustomerZipcodeLabel("Zipcode: ");
			invoice.setBillToCustomerZipcodeData("zipcode");

			invoice.setBillToCustomerCountryLabel("Country: ");
			invoice.setBillToCustomerCountryData("country");
			
			invoice.setBillToCustomerPhoneNumberLabel("Phone Number:");
			invoice.setBillToCustomerPhoneNumberData("+xx xxx xxx xxxx");

			invoice.setBillToCustomerEmailAddressLabel("Email: ");
			invoice.setBillToCustomerEmailAddressData("xx@xxxx.com");

			
			label_bill_to_customer_name.setText( invoice.getBillToCustomerNameLabel() );
			label_bill_to_customer_name_data.setText( invoice.getBillToCustomerNameData() );
			
			label_bill_to_customer_address.setText( invoice.getBillToCustomerAddressLabel() );
			label_bill_to_customer_address_data.setText( invoice.getBillToCustomerAddressData() );

			label_bill_to_customer_city.setText( invoice.getBillToCustomerCityLabel() );
			label_bill_to_customer_city_data.setText( invoice.getBillToCustomerCityData() );

			label_bill_to_customer_state.setText( invoice.getBillToCustomerStateLabel() );
			label_bill_to_customer_state_data.setText( invoice.getBillToCustomerStateData() );

			label_bill_to_customer_zipcode.setText( invoice.getBillToCustomerZipcodeLabel() );
			label_bill_to_customer_zipcode_data.setText( invoice.getBillToCustomerZipcodeData() );
			
			label_bill_to_customer_country.setText( invoice.getBillToCustomerCountryLabel() );
			label_bill_to_customer_country_data.setText( "country" );

			label_bill_to_customer_phone_number.setText( invoice.getBillToCustomerPhoneNumberLabel() );
			label_bill_to_customer_phone_number_data.setText( invoice.getBillToCustomerPhoneNumberData() );

			label_bill_to_customer_email_address.setText( invoice.getBillToCustomerEmailAddressLabel() );
			label_bill_to_customer_email_address_data.setText( invoice.getBillToCustomerEmailAddressData() );

			
			invoice.setShipToCustomerNameLabel("Ship To Customer: ");
			invoice.setShipToCustomerNameData("customer name");
			
			invoice.setShipToCustomerAddressLabel("Ship To Address: ");
			invoice.setShipToCustomerAddressData("address");
			
			invoice.setShipToCustomerCityLabel("City: ");
			invoice.setShipToCustomerCityData("city");

			invoice.setShipToCustomerStateLabel("State: ");
			invoice.setShipToCustomerStateData("state");
			
			invoice.setShipToCustomerZipcodeLabel("Zipcode: ");
			invoice.setShipToCustomerZipcodeData("zipcode");

			invoice.setShipToCustomerCountryLabel("Country: ");
			invoice.setShipToCustomerCountryData("country");
			
			invoice.setShipToCustomerPhoneNumberLabel("Phone Number:");
			invoice.setShipToCustomerPhoneNumberData("+xx xxx xxx xxxx");

			invoice.setShipToCustomerEmailAddressLabel("Email: ");
			invoice.setShipToCustomerEmailAddressData("xx@xxxx.com");

			
			

			label_ship_to_customer_name.setText( invoice.getShipToCustomerNameLabel() );
			label_ship_to_customer_name_data.setText( invoice.getShipToCustomerNameData() );

			label_ship_to_customer_address.setText( invoice.getShipToCustomerAddressLabel());
			label_ship_to_customer_address_data.setText( invoice.getShipToCustomerAddressData() );

			label_ship_to_customer_city.setText( invoice.getShipToCustomerCityLabel() );
			label_ship_to_customer_city_data.setText( invoice.getShipToCustomerCityData() );
				  
			label_ship_to_customer_state.setText( invoice.getShipToCustomerStateLabel() );
			label_ship_to_customer_state_data.setText( invoice.getShipToCustomerStateData()  );

			label_ship_to_customer_zipcode.setText( invoice.getShipToCustomerZipcodeLabel() );
			label_ship_to_customer_zipcode_data.setText( invoice.getShipToCustomerZipcodeData() );
				  
			label_ship_to_customer_country.setText( invoice.getShipToCustomerCountryLabel() );
			label_ship_to_customer_country_data.setText( invoice.getShipToCustomerCountryData() );
			
				  
			label_ship_to_customer_phone_number.setText( invoice.getShipToCustomerPhoneNumberLabel() );
			label_ship_to_customer_phone_number_data.setText( invoice.getShipToCustomerPhoneNumberData() );
				  
			label_ship_to_customer_email_address.setText( invoice.getShipToCustomerEmailAddressLabel()  );
			label_ship_to_customer_email_address_data.setText( invoice.getShipToCustomerEmailAddressData() );
			
			 
			storeName						.setFont(new Font("Times New Roman", Font.BOLD, 15));
			storePhoneNumber				.setFont(new Font("Times New Roman", Font.BOLD, 15));
			storeFaxNumber					.setFont(new Font("Times New Roman", Font.BOLD, 15));

			subtotalLabel					.setFont(new Font("Times New Roman", Font.BOLD, 15));
			taxesLabel						.setFont(new Font("Times New Roman", Font.BOLD, 15));
			totalLabel						.setFont(new Font("Times New Roman", Font.BOLD, 15));
			tenderLabel						.setFont(new Font("Times New Roman", Font.BOLD, 15));
			changeLabel						.setFont(new Font("Times New Roman", Font.BOLD, 15));
			discountLabel					.setFont(new Font("Times New Roman", Font.BOLD, 15));
			salesReport						.setFont(new Font("Times New Roman", Font.BOLD, 15));
			  
			bx001							.setFont(new Font("Tahoma",Font.BOLD,12));
			bx002							.setFont(new Font("Tahoma",Font.BOLD,12));
			bx003							.setFont(new Font("Tahoma",Font.BOLD,12));
			bx004							.setFont(new Font("Tahoma",Font.BOLD,12));
			bx005							.setFont(new Font("Tahoma",Font.BOLD,12));
			bx006							.setFont(new Font("Tahoma",Font.BOLD,12));
			
			button_tender					.setFont(new Font("Tahoma",Font.BOLD,12));
			pim_button						.setFont(new Font("Tahoma",Font.BOLD,12));
			
			panel							.setBackground(Color.decode("#F0F0F0"));
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
		    
			button_tender					.setForeground(Color.BLACK);
			pim_button						.setForeground(Color.BLACK);
			

			
			storeName                 		.setText(invoice.getStoreName());
			storePhoneNumber          		.setText(invoice.getStorePhoneNumber());
			storeFaxNumber            		.setText(invoice.getStoreFaxNumber());

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
			

			button_tender					.setPreferredSize(new Dimension(130,30)); 
			pim_button						.setPreferredSize(new Dimension(130,30));
		    payment_method					.setPreferredSize(new Dimension(150,30));
		    tender_amount					.setPreferredSize(new Dimension(150,30));
		    salesReport						.setPreferredSize(new Dimension(130,30));
		    addenda							.setPreferredSize(new Dimension(150,30));
		    label_addenda					.setPreferredSize(new Dimension(150,30));

  		    bx001							.addActionListener(this);
  		    bx002							.addActionListener(this);
  		    bx003							.addActionListener(this);
  		    bx004							.addActionListener(this);
  		    bx005							.addActionListener(this);
  		    bx006							.addActionListener(this);
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
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_name,1200,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_name_data,1200,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_address,1200,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_address_data,1200,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_city,1200,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_city_data,1200,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_state,1200,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_state_data,1200,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_zipcode,1200,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_zipcode_data,1200,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_country,1200,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_country_data,1200,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_email_address,1200,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_email_address_data,1200,layout.WEST, panel);

		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_phone_number,1200,layout.WEST, panel);
		  panelLayout.putConstraint(panelLayout.WEST, label_ship_to_customer_phone_number_data,1200,layout.WEST, panel);
		  
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

//		  panel.add(label_bill_to_customer_address);
//		  panel.add(label_bill_to_customer_city);
//		  panel.add(label_bill_to_customer_state);
//		  panel.add(label_bill_to_customer_zipcode);
//		  panel.add(label_bill_to_customer_country);
//		  panel.add(label_bill_to_customer_email_address);
//		  panel.add(label_bill_to_customer_phone_number);

		  
		  
		  panel.add(label_ship_to_customer_name);
		  panel.add(label_ship_to_customer_name_data);

//		  panel.add(label_ship_to_customer_address);
		  panel.add(label_ship_to_customer_address_data);

//		  panel.add(label_ship_to_customer_city);
		  panel.add(label_ship_to_customer_city_data);

//		  panel.add(label_ship_to_customer_state);
		  panel.add(label_ship_to_customer_state_data);

//		  panel.add(label_ship_to_customer_zipcode);
		  panel.add(label_ship_to_customer_zipcode_data);

//		  panel.add(label_ship_to_customer_country);
		  panel.add(label_ship_to_customer_country_data);

//		  panel.add(label_ship_to_customer_email_address);
		  panel.add(label_ship_to_customer_email_address_data);

//		  panel.add(label_ship_to_customer_phone_number);
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
	  
	  public void parkTransaction()
	  {


		
//		invoice_line_item = new InvoiceLineItem( String upc, String qty, String description, String price_retail, String subtotal, String tax, String discount_value, String onhand_value);
//		invoice.addInvoiceLineItem();


	  }
	  
	  public void buildTopPanel()
	  {
		    try {
		        
		    	
//		    	client_name                 	= invoice_management_system.getEntityName(retailerUUID);      
//		    	invoiceNumber 					= Integer.parseInt(http.getCurrentInvoiceNumber(retailerUUID)); 

		    	client_name						= invoice.getStoreName();
		    	invoiceNumber					= Integer.parseInt( invoice.getInvoiceNumber() );
		        retailerUUIDDescription			. setText( invoice.getIssuerUUID() );

		        }
		        catch(Exception e) {			System.out.println(e.toString( )); }  
		        

		        timeLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		        timeLabel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(null,fmt.format(today),0,0,new Font("Times New Roman", Font.BOLD, 20), Color.BLUE), timeLabel.getBorder()));
		      
		        
		        ActionListener taskPerformer = new ActionListener() {
		          public void actionPerformed(ActionEvent evt)
		          {
		              simpDate          = new SimpleDateFormat("hh:mm:ss a");
		              today 			= new Date();
		              timeLabel.setText(simpDate.format(today) );
		              internetLabel.setText( internet.checkConnection() );
		              
		          }};
		          Timer t = new Timer(1000, taskPerformer);
		          t.start();


		          try {
		              invoiceNumberLabel.setText(  "Invoice #:" );
		              invoiceNumberLabelDescription.setText( String.valueOf(http.getCurrentInvoiceNumber(retailerUUID)) );
		          }catch(Exception e) { System.out.println(e.toString());}
		      
		          invoiceNumberLabelDescription.setFont(new Font("Times New Roman", Font.BOLD, 15));
		          invoiceNumberLabelDescription.setForeground(Color.decode("#0000FF"));

		        
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
	 
	        /*
	        //Build the first menu.
	         * Eliminated as of 11/29/22
	         * This menu item does not specifically serve a purpose at the moment and will be replaced.
	         * 
	        menu = new JMenu("Menu");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
	        menuBar.add(menu);


			public void initializeElements(){

				menuBar = new JMenuBar();
				file = new JMenu("File");
				exitItem = new JMenuItem("Exit");
			
				exitItem.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent ev) {
					System.exit(0);
				  }
				});
			
				headMenu = new JMenu("Heads");
				bgMenu = new JMenu("Backgrounds");
			
			  }
			*/
	        
	        
			
	        
	        // Menu Name
	        menu = new JMenu("Lockwind POS");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");

	        menuItem = new JMenuItem("Tender Transaction",KeyEvent.VK_T);
			menuItem.setBackground(Color.decode("#00FF0F"));
			menuItem.setForeground(Color.decode("#000000"));
			
	        menuItem.setName("TenderTransaction");
	        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_7, ActionEvent.ALT_MASK));
	        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
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
				
				  
				  
				
				  // Add a call to the function to save the transaction details into a structured file.
				  // 
				}	
			  });
   		   menuItem.setBackground(Color.decode("#FFFF00"));
		   menuItem.setForeground(Color.decode("#000000"));  
	        menu.add(menuItem);
			// No ParkTransaction Module built yet

	        
	        menuItem = new JMenuItem("Copy Last Transaction",KeyEvent.VK_C);
	        menuItem.setName("CopyLastTransaction");
			System.out.println("User Action: Copy Last Transaction");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
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
	                "The only menu in this program that has menu items");
	        
	        menuItem = new JMenuItem("Sales Report",KeyEvent.VK_C);
	        menuItem.setName("SalesReport");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Find Receipt",KeyEvent.VK_C);
	        menuItem.setName("FindReceipt");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuItem = new JMenuItem("Reprint Receipt",KeyEvent.VK_C);
	        menuItem.setName("ReprintReceipt");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuBar.add(menu);

	        
	        menu = new JMenu("Inventory Management");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");

	        menuItem = new JMenuItem("Products",KeyEvent.VK_C);
	        menuItem.setName("Products");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Services",KeyEvent.VK_C);
	        menuItem.setName("Services");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Rentals",KeyEvent.VK_C);
	        menuItem.setName("Rentals");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        
	        menuItem = new JMenuItem("New Stock",KeyEvent.VK_C);
	        menuItem.setName("NewStock");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Price Change",KeyEvent.VK_C);
	        menuItem.setName("PriceChange");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);


	        menuItem = new JMenuItem("Add Inventory",KeyEvent.VK_C);
	        menuItem.setName("AddInventory");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Product Catalogue",KeyEvent.VK_C);
	        menuItem.setName("ProductCatalogue");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);


			menuItem = new JMenuItem("PIM System",KeyEvent.VK_C);
	        menuItem.setName("PIMSystem");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuBar.add(menu);

	        
	        menu = new JMenu("Accounts Receivable");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");
	        
	        menuItem = new JMenuItem("Customer Management System",KeyEvent.VK_C);
	        menuItem.setName("CustomerManagementSystem");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Review Customer Pricing",KeyEvent.VK_C);
	        menuItem.setName("ReviewCustomerPricing");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Customer Invoices",KeyEvent.VK_C);
	        menuItem.setName("InvoicesAR");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Record Payment",KeyEvent.VK_C);
	        menuItem.setName("PaymentRemittance");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	       	        
	        menuBar.add(menu);

	        
	        menu = new JMenu("Accounts Payable");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");
	        
	        menuItem = new JMenuItem("Vendor Management System",KeyEvent.VK_C);
	        menuItem.setName("VendorManagementSystem");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Vendor Catalogs",KeyEvent.VK_C);
	        menuItem.setName("Vendor Catalogs");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuItem = new JMenuItem("Purchase Orders",KeyEvent.VK_C);
	        menuItem.setName("PurchaseOrders");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Invoices to Pay",KeyEvent.VK_C);
	        menuItem.setName("InvoicesAP");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);


	        menuItem = new JMenuItem("Advance Shipping Notices",KeyEvent.VK_C);
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
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        // This menu item will open the group management system.
	        menuItem = new JMenuItem("Groups & Permissions",KeyEvent.VK_C);
	        menuItem.setName("GroupPermissions");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuBar.add(menu);

	        
	        menu = new JMenu("Help");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");
	        menuItem = new JMenuItem("Contact Customer Support",KeyEvent.VK_C);
	        menuItem.setName("ContactCustomerSupport");
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
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        
	        menuItem = new JMenuItem("About Lockwind POS",KeyEvent.VK_C);
	        menuItem.setName("About");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);

	        menuBar.add(menu);

	        


	        menu = new JMenu("Alerts");
	        menu.setMnemonic(KeyEvent.VK_A);
	        menu.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");
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



	System.out.print("Column:count");
	System.out.println(table.getModel().getColumnCount());

	// Print names of columns assigned to table model
	for(int i = 0; i < table.getModel().getColumnCount(); i++){
		System.out.println(table.getColumnName(i));
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
	
  	
    frame.setJMenuBar(createMenuBar());
  	frame.add(panel);
	frame.add(bottomPanel);
	frame.add(tablePanel);
	//frame.add(line_item_table);
	
  	frame.pack();
  	frame.setVisible(true);
  
  	table.requestFocus();
  	table.changeSelection(0,0, false,false);

 	*/

  
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
	table_manager.clearTable(table);

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
		// refreshTotal(table,0.00,0.00);

		table.changeSelection(i,0, false,false);
		}});
	//---------------------------------------------------------DELETE KEY ACTION END 

	//---------------------------------------------------------ENTER KEY ACTION PROC
		InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap am = table.getActionMap();
		KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		im.put(enterKey, "Action.enter");
		am.put("Action.enter", new AbstractAction() {

		public void actionPerformed(ActionEvent evt) {
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		if (table.isEditing()){table.getCellEditor().stopCellEditing();}
		
		NumberFormat formatter = new DecimalFormat("#0.00");

		int i = 0; 
		int j = 0;

		i = table.getSelectedRow();
		j = table.getSelectedColumn();

		double discount = 0.00;
		double discountPrice = 0.00;
		double st = 0.00;
		String productInfo = "";
		String inputGTIN = "";

	    
		if(j== 0) // Column: UPC
		{
		
//		JOptionPane.showMessageDialog(null,"Register.Enter Key Action Proc - Updating GTIN");
//		JOptionPane.showMessageDialog(null,table_manager.getData(table,i,j).toString());
		
		 inputGTIN = table_manager.getData(table,i,0).toString(); // COL 0: GTIN
			
		 table_manager.setData(table,i,1,"1"); // COL 1: QTTY
 
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
//			JOptionPane.showMessageDialog(null,"Register.Enter Key Action Proc - Updating QTY");
			updateQTY();
		}
		
		if(j == 2) // Column: Category 
		{}
	    
		if( j == 3){ // Column: Description
	          updateDescription();
	      }
	      if ( j == 4){ // Column: Price
	          updatePrice();
	      }
	      if( j == 5){ // Column: Subtotal
			  
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

 		//subtotalLabel.setText( "$ "+ invoice.getTransactionSubTotal() ); // Set value to UI Label
 		//taxesLabel.setText( "$ "+ invoice.getTransactionTaxesTotal() ); // Set value to UI Label
 		//totalLabel.setText("$ "+ invoice.getTransactionTotal() ); // Set value to UI Label
 		// discountLabel.setText("$ " + invoice.getTransactionDiscountTotal() ); // Set value to UI Label

// table_manager.setData( table,i,5,table_manager.getSubTotal(table,i)); // Update Subtotal for this row
 //table_manager.setData( table,i,6,table_manager.getTax(table,i)); // Update taxes for this row


		  // refreshTotal(table,0.00,0.00);

		}});


	//-----------------------------------------------------------------------------ENTER KEY END PROC

	  
	  //-----------------------------------------------------------------------------MOUSE LISTENER PROC

	  
	table.addMouseListener(new java.awt.event.MouseAdapter() {
	@Override
	public void mouseClicked(java.awt.event.MouseEvent evt) {
	      int row = table.rowAtPoint(evt.getPoint());
	      int col = table.columnAtPoint(evt.getPoint());
	      if (row >= 0 && col >= 0) {
		NumberFormat formatter = new DecimalFormat("#0.00");


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



public void refreshTotal()
{
  
  System.out.println("TableManager->@refreshTotal()");

  NumberFormat formatter = new DecimalFormat("#0.00");
  
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
  invoice.setTransactionTotal( formatter.format(String.valueOf(total_value) ) );

  String sub_total_value_string = formatter.format( Double.parseDouble(invoice.getTransactionSubTotal() ) );
  String tax_value_string = formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal() ) );
  String total_value_String = formatter.format( Double.parseDouble(invoice.getTransactionTotal()) );
  String discount_value_string = format_manager.formatDoubleUS( Double.parseDouble(invoice.getTransactionDiscountTotal() ) );
  
  subtotalLabel.setText( "$ "+ invoice.getTransactionSubTotal() ); // Set value to UI Label
  taxesLabel.setText( "$ "+ invoice.getTransactionTaxesTotal() ); // Set value to UI Label
  totalLabel.setText("$ "+ invoice.getTransactionTotal() ); // Set value to UI Label
  discountLabel.setText("$ " + invoice.getTransactionDiscountTotal() ); // Set value to UI Label
  

}




 

  public void refreshTotal(JTable table, double tender_value, double change_value)
{
  
  System.out.println("TableManager->@refreshTotal()");

  NumberFormat formatter = new DecimalFormat("#0.00");
  
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
  
  /*
  System.out.println( " Register.refreshTotal() -> sub_total_value = " + sub_total_value);
  System.out.println(" Register.refreshTotal() -> tax_value = " + tax_value);
  System.out.println(" Register.refreshTotal() -> discount_value = "+  discount_value);
  System.out.println(" Register.refreshTotal() -> total_value = " + total_value);
  System.out.println(" Register.refreshTotal() -> tender_value = " + tender_value);
*/
  
  
  System.out.println( " Register.refreshTotal() -> sub_total_value = " + invoice.getTransactionSubTotal() );
  System.out.println(" Register.refreshTotal() -> tax_value = " + invoice.getTransactionTaxesTotal() );
  System.out.println(" Register.refreshTotal() -> discount_value = " + invoice.getTransactionDiscountTotal() );
  System.out.println(" Register.refreshTotal() -> total_value = " + invoice.getTransactionTotal() );
  System.out.println(" Register.refreshTotal() -> tender_value = " + invoice.getTranasctionTenderValue() );



  String sub_total_value_string = formatter.format( Double.parseDouble(invoice.getTransactionSubTotal() ) );
  String tax_value_string = formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal() ) );
  String total_value_String = formatter.format( Double.parseDouble(invoice.getTransactionTotal()) );
  String discount_value_string = format_manager.formatDoubleUS( Double.parseDouble(invoice.getTransactionDiscountTotal() ) );
  
  subtotalLabel.setText( "$ "+ invoice.getTransactionSubTotal() ); // Set value to UI Label
  taxesLabel.setText( "$ "+ invoice.getTransactionTaxesTotal() ); // Set value to UI Label
  totalLabel.setText("$ "+ invoice.getTransactionTotal() ); // Set value to UI Label
  discountLabel.setText("$ " + invoice.getTransactionDiscountTotal() ); // Set value to UI Label
  
  if( tender_value != 0.00 ) {
      System.out.println(tender_value);
      System.out.println(change_value);

      String tender_value_string = formatter.format(tender_value);
      String change_value_string = formatter.format(change_value);

      tenderLabel.setText("$ " + tender_value_string );
      changeLabel.setText("$ " + change_value_string );

  }
  else{
      
  }

}
  public void saveTableToReceipt(JTable table,String client_id){   /* build out this method. */ }
  

  


  public void updateQTY(){

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
  
// table_manager.setData( table,i,4,table_manager.getSubTotal(table,i)); -> this is updating the retail price column with the value of the subtotal for this row (error).
//  refreshTotal(table,0.00,0.00);
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
 
  // table_manager.setData(table,i,5,table_manager.getSubTotal(table,i));
  // table_manager.setData(table,i,6,table_manager.getTax(table,i));
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
  
  table.changeSelection(i,0, false,false);

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
      System.out.println("Register->@saveTableToReceipt()");
      
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
      

      System.out.println( invoice.toString() );
      
      if(change > 0.00)
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
          
    	  
          System.out.println("UPLOADING PRODUCT:");
          
          today                 =     new Date();
          fmt                   =     DateFormat.getDateInstance      (styles[3], locale[0]);
          
          simpDate             =     new SimpleDateFormat("hh:mm:ss a");
          
          account_selected = account_name_input.getSelectedItem().toString();
          
          //http.setInformation(account_name_input.getSelectedItem().toString(),String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD", format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change));
          
          
          t = new JOptionPane();
          response = http.sendPost( invoice.getConsumerUUID(),invoice.getIssuerUUID(),client_id,invoice.getStoreName().trim(),account_selected,String.valueOf(invoiceNumber),fmt.format(today),simpDate.format(today),"USD",format_manager.formatDoubleUS(total) ,format_manager.formatDoubleUS(tendered), format_manager.formatDoubleUS(change));
          
          System.out.println("WS Response:" + response);
          
          // Thread.sleep(2000);
          
          
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
          outputFile.println(" " +  invoice.getStoreAddress() + " " + invoice.getStoreSecondAddress());
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
   PrintReceipt test = new PrintReceipt();
     
     try {
//    	 System.out.println("Printing receipt to this printer: " + default_printer_receipt.getSelectedItem().toString());
//         test.main(null);
  //   test.main(default_printer_receipt.getSelectedItem().toString() );
  //   test.printToDisplayPrinter(default_printer_display.getSelectedItem().toString(), "Total: " + format_manager.formatDoubleUS(total) );
     
     }
     catch(Exception PrintReceipte) {}
     
      /*
      try{
    	  
    	 // Process p =  Runtime.getRuntime().exec("cmd /c printReceipt.bat");
      // System.out.println("Initiating Printing Process: " + p.pid());
      }
      catch(IOException eex){}
  */


      
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
//      subtotalLabel	.setText("$0.00");
//	  taxesLabel	.setText("$0.00");
//	  totalLabel	.setText("$0.00");
//	  tenderLabel	.setText("$0.00");
//	  changeLabel	.setText("$0.00");
//	  discountLabel	.setText("$0.00");

 //     account_name_input.setSelectedIndex(0);
      
   //   tender_amount.setText("");
    //  table.requestFocus();
	//  table.changeSelection(0,0,false,false);
	//  addenda.setText("");

  }
  
  
public static void main(String[] args){Register test = new Register();

System.out.println("This is Lockwind POS Version 3.6 with a modification date of 8/1/23");
}

  
}

