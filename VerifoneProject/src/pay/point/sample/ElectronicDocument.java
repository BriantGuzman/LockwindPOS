//Last Update: 12/13/2023

package pay.point.sample;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileWriter;
import java.io.BufferedWriter;


public class ElectronicDocument extends ServiceData {

	// Set by System
	private String document_directory; // This is the file path for the electronic document.
	private String file_extension; // This is the file extension for the electronic document.

	private ValidationPlatform validation_platform;
	private final String xmlFilePath = "./src/electronic_document/ElectronicDocument.xml";
	
	 
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder documentBuilder;

	private Document document;
	
	// Elements of the ElectronicDocument
	private Element root;
	private Element element;
    private ElectronicDocumentLineItemManager line_item_manager;


    private Document XMLFormat; // This is going to represent the keys and values of the document in XML format.
	private Element XMLFormatElement; // This is used to add document elements to the XML Format Instance.
    private Document PDFFormat; // This is going to represent the key values and locations of the document for the PDF generation of this electronic document instance.
	private Element PDFFormatElement; // This is used to add document elements to the PDF Format instance.

	
	private Attr attr;
	private TransformerFactory transformerFactory;
    private Transformer transformer;
    private DOMSource domSource;
    private StreamResult streamResult;
    private File file;
    
    private Scanner inputFile;
    private PrintWriter outputFile;
    
    private String line;
    
	
	 // What?
	 private String transaction_uuid;
	 private String transaction_type;
	 private String transaction_number;
	 
	 // When?
	 private String transaction_date;
	 private String transaction_time;

	 // Where?
	 private String origin_system;  // The URL or location of the system
	 private String destination_system;

	 private String origin_system_id;
	 private String destination_system_id;

	 private String origin_system_name;  // The name of the origin system . ie. REG1
	 private String destination_system_name;  // The name of the destination system

	 private String open_employee_id; // State the employee that opened the store that day
	 private String close_employee_id; // State the employee that closed the store that day 

	 private String header_comment; // Add a comment to the electronic document at the header level
	 
	 private String currency_type_key; // This is to denote an array of currency denominations.
	 private String currency_type_value; // This is to store the quantity of those currency denominations.
	 
	 // Why?
	 private String po_number;
	 private String invoice_number;

	 // How?
	 private String method_of_communication;
	 
	 private String transaction_currency;
	 private String total_value_sub;
	 private String total_value;
	 private String total_value_taxes;
	 private String total_value_discount;
	 private String tender_value;
	 private String change_value;
	 private String balance_due;

	 //
	 private String store_name;
	 private String store_address;
	 private String store_second_address;
	 private String store_phone_number;
	 private String store_fax_number;

	 private String label_issuer_uuid;
	 private String label_issuer_name;
	 private String label_issuer_address;
	 private String label_issuer_city;
	 private String label_issuer_state;
	 private String label_issuer_zipcode;
	 private String label_issuer_country;
	 private String label_issuer_phone_number;
	 private String label_issuer_fax_number;
	 private String label_issuer_email_address;

	 private String label_issuer_uuid_data;
	 private String label_issuer_uuid_location_data;
	 private String label_issuer_name_data;
	 private String label_issuer_address_data;
	 private String label_issuer_city_data;
	 private String label_issuer_state_data;
	 private String label_issuer_zipcode_data;
	 private String label_issuer_country_data;
	 private String label_issuer_phone_number_data;
	 private String label_issuer_fax_number_data;
	 private String label_issuer_email_address_data;
	 

	 //
		// Who?
	 private String consumer_uuid;

	 private String label_bill_to_customer_id;
	 private String label_bill_to_customer_id_data;
	 private String label_bill_to_customer_code;
	 private String label_bill_to_customer_code_data;

	 
	 private String label_bill_to_customer_name;
	 private String label_bill_to_customer_name_data;
	 private String label_bill_to_customer_address;
	 private String label_bill_to_customer_address_data;
	 private String label_bill_to_customer_city;
	 private String label_bill_to_customer_city_data;
	 private String label_bill_to_customer_state;
	 private String label_bill_to_customer_state_data;
	 private String label_bill_to_customer_zipcode;
	 private String label_bill_to_customer_zipcode_data;
	 private String label_bill_to_customer_country;
	 private String label_bill_to_customer_country_data;
	 
	 private String label_bill_to_customer_phone_number;
	 private String label_bill_to_customer_phone_number_data;
	 private String label_bill_to_customer_fax_number;
	 private String label_bill_to_customer_fax_number_data;
	 
	 private String label_bill_to_customer_email_address;
	 private String label_bill_to_customer_email_address_data;

	 private String label_ship_to_customer_id;
	 private String label_ship_to_customer_id_data;
	 private String label_ship_to_customer_code;
	 private String label_ship_to_customer_code_data;
	 private String label_ship_to_customer_name;
	 private String label_ship_to_customer_name_data;
	 private String label_ship_to_destination_facility;
	 private String label_ship_to_destination_facility_data;
	 private String label_ship_to_customer_address; 
	 private String label_ship_to_customer_address_data;
	 private String label_ship_to_customer_city;
	 private String label_ship_to_customer_city_data;
 	 private String label_ship_to_customer_state;
	 private String label_ship_to_customer_state_data;
	 private String label_ship_to_customer_zipcode;
	 private String label_ship_to_customer_zipcode_data;
	 private String label_ship_to_customer_country;
	 private String label_ship_to_customer_country_data;
	 private String label_ship_to_customer_phone_number;
	 private String label_ship_to_customer_phone_number_data;
	 private String label_ship_to_customer_email_address;
	 private String label_ship_to_customer_email_address_data;	 
	 private String label_ship_to_customer_date_expected_data;
	 private String label_ship_to_customer_date_arrived_data;
	 private String label_ship_to_customer_date_expected;
	 private String label_ship_to_customer_date_arrived;
	 
	 private String label_payment_due_date;	 

	 // Set by user and can be changed
	 private String payment_method;
	 
	 
	 private ElectronicDocumentLineItem head;
	 private ElectronicDocument next;
	 private ElectronicDocument prev;

	 private API http;
	 
	 // NO ARG CONSTRUCTOR
	 public ElectronicDocument() { 
		
		 document_directory		 = "";
		 file_extension			 = "";
		 
		 transaction_type    	 = "";
		 //issuer_uuid 			 = "";
		 consumer_uuid			 = "";
	     transaction_type		 = "";
	     transaction_number		 = "";
	     
		 invoice_number 		 = "-1";
		 JOptionPane.showMessageDialog(null,"Electronic Document Invoice Number =" + invoice_number);
		 
		 po_number			 	 = "";
	     
	     transaction_number      = "";
	     transaction_date        = "";
	     transaction_time        = "";
	     transaction_currency    = "";
	     total_value             = "";
	     tender_value        	 = "";
	     change_value        	 = "";
		 payment_method 		 = "";

	     
	     label_issuer_uuid		 = "";
		 label_issuer_uuid_data	 = "";
		 label_issuer_name		 = null;
		 label_issuer_name_data	 = null;
	     
	     store_name 			 = null;
	     store_address 			 = null;
	     store_second_address 	 = null;
	     store_phone_number 	 = null;
	     store_fax_number		 = null;

	     next					 = null;
	     prev				 	 = null;
	     
	     line_item_manager		= null;
	     
	     http					= null;
		 http = new API();
	     line_item_manager 		= new ElectronicDocumentLineItemManager();
	 }
	 
	 
	 
	 public ElectronicDocument(String issuer_uuid)
	 {
		 super();
	     line_item_manager 		= new ElectronicDocumentLineItemManager();
		 this.setIssuerUUID(issuer_uuid);
		 this.setInvoiceNumber();
		 
		 if(this.getInvoiceNumber().equalsIgnoreCase("") ) {
		 }else {
		 this.setTransactionUUID( this.getIssuerUUID() );
		 }
	 }

	 // ************************************************************************************************************************

	 public void setDirectory(String document_directory) { this.document_directory = document_directory; }
	 public String getDirectory() {  return this.document_directory; }
	 
	 public void setFileExtension(String ext) { this.file_extension = ext; }
	 public String getFileExtension() { return this.file_extension; }

	 // UUID
//	 public void 		setTransactionUUID(String uuid) { this.transaction_uuid = uuid; }
	 
	 public void 		setTransactionUUID(String retailer_uuid) { 
		 // The purpose of this function is to only return the UUID related to that specific invoice number being worked on right now.
		 try { 
		  API http = new API();
		 
		 this.setInvoiceNumber();
		 
		 // When the internet is not working the program should still load but not request a transaction UUID.
		  this.transaction_uuid = http.getTransactionUUID( this.getIssuerUUID(), this.getInvoiceNumber());	
			 System.out.println("Result: ElectronicDocument->setTransactionUUID: -> " + http.getTransactionUUID( this.getIssuerUUID(), this.getInvoiceNumber()));
			 System.out.println("Result: ElectronicDocument->setTransactionUUID: -> " + this.transaction_uuid);
			
		 }catch(Exception e) {
//				System.out.println("Result: ElectronicDocument->setTransactionUUID: -> " + this.transaction_uuid);
			 e.printStackTrace();
		 }
		 
	 }
	 public String		getTransactionUUID() { return this.transaction_uuid; }

	 // INVOICE NUMBER
	 private void setInvoiceNumber() {
		 
		 // If the internet is not working set the invoice number to -401.

		 try { 
//			  	http = new API();
			  	String temp = http.getCurrentInvoiceNumber(this.getIssuerUUID());
				

				if( this.getIssuerUUID() == null || this.getIssuerUUID().equalsIgnoreCase("") ) { 
					System.out.println("System Error:  ElectronicDocument-> setInvoiceNumber() retailer uuid is required");
					invoice_number = "-1";
				}
				else{
					

					if(temp.equalsIgnoreCase("java.net.UnknownHostException: lockwind.com")) { invoice_number = "-401"; }
					else { 
												
						invoice_number = temp; 
						System.out.println("Result: ElectronicDocument->setInvoiceNumber -> " + temp);
						System.out.println("Result: ElectronicDocument-> invoice_number -> " + invoice_number);
	
					} }

			} catch(Exception e){ }
		 
	 }
//	 public void 		setInvoiceNumber(String invoice_number) { this.invoice_number = invoice_number; }
	 public String 		getInvoiceNumber() {  
		 invoice_number = "1";
//		 if( invoice_number.equalsIgnoreCase("") || invoice_number == null)  { this.setInvoiceNumber(); }else { }
		 return this.invoice_number; 
	 }
//	 public String 		getInvoiceNumber() { setInvoiceNumber(); return this.invoice_number; }

	 // SYSTEM OF ORIGIN
	 public void 		setOriginSystem(String origin_system) { this.origin_system = origin_system; }
	 public String		getOriginSystem() { return this.origin_system; }

	 // SYSTEM OF ORIGIN ID
	 public void 		setOriginSystemID(String origin_system) { this.origin_system_id = origin_system; }
	 public String		getOriginSystemID() { return this.origin_system_id; }

	 // SYSTEM OF DESTINATION
	 public void 		setDestinationSystem(String destination_system) { this.destination_system = destination_system; }
	 public String		getDestinationSystem() { return this.destination_system; }

	 
	 public void 		setDestinationSystemID(String destination_system) { this.destination_system_id = destination_system; }
	 public String		getDestinationSystemID() { return this.destination_system_id; }

	 
	 // TRANSACTION TYPE
	 public void 		setTransactionType(String transaction_type) { this.transaction_type = transaction_type; }
	 public String		getTransactionType() { return this.transaction_type; }
	 
	 // TRANSACTION NUMBER
	 public void		setTransactionNumber(String transaction) { this.transaction_number = transaction; }
	 public String 		getTransactionNumber() { return this.transaction_number; }
	 
	 // TRANSACTION DATE // TIMESTAMP
	 public void		setTransactionDate(String transaction_date) { this.transaction_date = transaction_date; }
	 public String		getTransactionDate() { return this.transaction_date; }

	 // TRANSACTION TIME // TIMESTAMP
	 public void 		setTransactionTime(String transaction_time) { this.transaction_time = transaction_time; }
	 public String		getTransactionTime() { return this.transaction_time; }

	 // STORE NAME
	 public void 		setStoreName(String store_name) { this.store_name = store_name; }
	 public String 		getStoreName() { return this.store_name; }

	 // ADDRESS
	 public void 		setAddress(String address) { this.store_address = address; }
	 public String		getAddress() { return this.store_address; }
	 
	// SECOND ADDRESS
	 public void 		setSecondAddress(String sa){ this.store_second_address = sa; }
	 public String		getSecondAddress() { return this.store_second_address; }
	 // PHONE NUMBER
	 public void 		setPhoneNumber(String pn) { this.store_phone_number = pn; }
	 public String		getPhoneNumber() { return this.store_phone_number; }
	 
	 public void 		setFaxNumber(String fn) { this.store_fax_number = fn; }
	 public String		getFaxNumber() { return this.store_fax_number; }
	 
	 public void 		setStoreAddress(String address){ this.store_address = address; }
	 public String 		getStoreAddress(){ return this.store_address; }

	 public void  		setStoreSecondAddress(String secondAddress){ this.store_second_address = secondAddress; }
	 public String 		getStoreSecondAddress(){ return this.store_second_address; }
	 
	 public void 		setStorePhoneNumber(String phoneNumber){ this.store_phone_number = phoneNumber; }
	 public String 		getStorePhoneNumber(){ return this.store_phone_number; }
	 
	 public void 		setStoreFaxNumber(String faxNumber){ this.store_fax_number = faxNumber; }
	 public String 		getStoreFaxNumber(){ return this.store_fax_number; }

	 public void 		setLabelIssuerUuid(String label_issuer_uuid) { this.label_issuer_uuid = label_issuer_uuid; }
	 public String 		getLabelIssuerUuid() { return label_issuer_uuid; }

	 public void 		setLabelIssuerName(String label_issuer_name) { this.label_issuer_name = label_issuer_name; }
	 public String 		getLabelIssuerName() { return label_issuer_name; }

	 public void 		setLabelIssuerAddress(String label_issuer_address) { this.label_issuer_address = label_issuer_address; }
	 public String 		getLabelIssuerAddress() { return label_issuer_address; }

	 public void 		setLabelIssuerCity(String label_issuer_city) { this.label_issuer_city = label_issuer_city; }
	 public String 		getLabelIssuerCity() { return label_issuer_city; }
	 
	 public void 		setLabelIssuerState(String label_issuer_state) { this.label_issuer_state = label_issuer_state; }
	 public String 		getLabelIssuerState() { return label_issuer_state; }

	 public void 		setLabelIssuerZipcode(String label_issuer_zipcode) { this.label_issuer_zipcode = label_issuer_zipcode; }
	 public String 		getLabelIssuerZipcode() { return label_issuer_zipcode; }
	 
	 public void 		setLabelIssuerCountry(String label_issuer_country) { this.label_issuer_country = label_issuer_country; }
	 public String 		getLabelIssuerCountry() { return label_issuer_country; }

	 public void 		setLabelIssuerPhoneNumber(String label_issuer_phone_number) { this.label_issuer_phone_number = label_issuer_phone_number; }
	 public String 		getLabelIssuerPhoneNumber() { return label_issuer_phone_number; }

	 public void 		setLabelIssuerEmailAddress(String label_issuer_email_address) { this.label_issuer_email_address = label_issuer_email_address; }
	 public String 		getLabelIssuerEmailAddress() { return label_issuer_email_address; }

	 public void 		setLabelIssuerUuidData(String label_issuer_uuid_data) { this.label_issuer_uuid_data = label_issuer_uuid_data; }
	 public String 		getLabelIssuerUuidData() { return label_issuer_uuid_data; }

	 public void 		setLabelIssuerNameData(String label_issuer_name_data) { this.label_issuer_name_data = label_issuer_name_data; }
	 public String 		getLabelIssuerNameData() { return label_issuer_name_data; }

	 public void 		setLabelIssuerAddressData(String label_issuer_address_data) { this.label_issuer_address_data = label_issuer_address_data; }
	 public String 		getLabelIssuerAddressData() { return label_issuer_address_data; }


	 public void 		setLabelIssuerCityData(String label_issuer_city_data) {	this.label_issuer_city_data = label_issuer_city_data; }
	 public String 		getLabelIssuerCityData() { return label_issuer_city_data; }


	 public void 		setLabelIssuerStateData(String label_issuer_state_data) { this.label_issuer_state_data = label_issuer_state_data; }
	 public String 		getLabelIssuerStateData() { return label_issuer_state_data; }


	 public void 		setLabelIssuerZipcodeData(String label_issuer_zipcode_data) { this.label_issuer_zipcode_data = label_issuer_zipcode_data; }
	 public String		getLabelIssuerZipcodeData() { return label_issuer_zipcode_data; }


	 public void 		setLabelIssuerCountryData(String label_issuer_country_data) { this.label_issuer_country_data = label_issuer_country_data; }
	 public String 		getLabelIssuerCountryData() { return label_issuer_country_data; }

	 public void 		setLabelIssuerPhoneNumberData(String label_issuer_phone_number_data) { this.label_issuer_phone_number_data = label_issuer_phone_number_data; }
	 public String 		getLabelIssuerPhoneNumberData() { return label_issuer_phone_number_data; }

	 public void 		setLabelIssuerFaxNumberData(String label_issuer_fax_number_data) { this.label_issuer_fax_number_data = label_issuer_fax_number_data; }
	 public String 		getLabelIssuerFaxNumberData() { return label_issuer_fax_number_data; }

	 
	 public boolean 	setLabelIssuerNameEmailAddressData(String label_issuer_name_email_address) {
	 
		 String error_message = validation_platform.validateEmailAddress(label_issuer_name_email_address);
	    	
	    	if( error_message.equalsIgnoreCase("Validation:Success") )
	        	{
	        	this.label_issuer_email_address_data = label_issuer_name_email_address;
	        	System.out.println("setLabelIssuerNameEmailAddress: " + error_message );
	        	
	        	return true;
	        	}
	        else {
	        	System.out.println("setLabelIssuerNameEmailAddress: " + error_message );
	        	return false;
	        }
	    }		 
	 
	 public String 		getLabelIssuerEmailAddressData() { return label_issuer_email_address_data; }

	    
	 
	 // ************************************************************************************************************************

	 public void 		setLabelBillToCustomerID(String label_customer_id) { this.label_bill_to_customer_id = label_bill_to_customer_id; }
	 public String		getLabelBillToCustomerID() { return this.label_bill_to_customer_id ; }
		 
	 public void 		setLabelBillToCustomerIDData(String uuid) { this.transaction_uuid = uuid; }
	 public String		getLabelBillToCustomerIDData() { return this.transaction_uuid; }
	   
	 public void 		setBillToCustomerCodeLabel(String value) { label_bill_to_customer_code = value; }
	 public String 		getBillToCustomerCodeLabel(){ return label_bill_to_customer_code; }
	 
	 public void 		setBillToCustomerCodeData(String value){ label_bill_to_customer_code = value; }
	 public String 		getBillToCustomerCodeData(){ return label_bill_to_customer_code_data; }

	 public void 		setBillToCustomerNameLabel(String value) { label_bill_to_customer_name = value; }
	 public String 		getBillToCustomerNameLabel(){ return label_bill_to_customer_name; }

	 public void 		setBillToCustomerNameData(String value){ label_bill_to_customer_name_data = value; }
	 public String 		getBillToCustomerNameData(){ return label_bill_to_customer_name_data; }
		 
	 public void 		setBillToCustomerAddressLabel(String value) { label_bill_to_customer_address = value; }
	 public String 		getBillToCustomerAddressLabel(){ return label_bill_to_customer_address; }

	 public void 		setBillToCustomerAddressData(String value){ label_bill_to_customer_address_data = value; }
	 public String 		getBillToCustomerAddressData(){ return label_bill_to_customer_address_data; }
	 
	 public void 		setBillToCustomerCityLabel(String value) { label_bill_to_customer_city = value; }
	 public String 		getBillToCustomerCityLabel(){ return label_bill_to_customer_city; }
	 
	 public void 		setBillToCustomerCityData(String value){ label_bill_to_customer_city_data = value; }
	 public String  	getBillToCustomerCityData(){ return label_bill_to_customer_city_data; }
	 
	 public void  		setBillToCustomerStateLabel(String value) { label_bill_to_customer_state = value; }
	 public String   	getBillToCustomerStateLabel(){ return label_bill_to_customer_state; }
	 
	 public void   	  	setBillToCustomerStateData(String value){ label_bill_to_customer_state_data = value; }
	 public String   	getBillToCustomerStateData(){ return label_bill_to_customer_state_data; }
	 
	 public void   		setBillToCustomerZipcodeLabel(String value) { label_bill_to_customer_zipcode = value; }
	 public String 		getBillToCustomerZipcodeLabel(){ return label_bill_to_customer_zipcode; }
	 
	 public void 		setBillToCustomerZipcodeData(String value){ label_bill_to_customer_zipcode_data = value; }
	 public String 		getBillToCustomerZipcodeData(){ return label_bill_to_customer_zipcode_data; }

	 
	 public void 		setBillToCustomerCountryLabel(String value) { label_bill_to_customer_country = value; }
	 public String 		getBillToCustomerCountryLabel(){ return label_bill_to_customer_country; }
	 
	 public void 		setBillToCustomerCountryData(String value){ label_bill_to_customer_country_data = value; }
	 public String 		getBillToCustomerCountryData(){ return label_bill_to_customer_country_data; }

	 public void 		setBillToCustomerPhoneNumberLabel(String value) { label_bill_to_customer_phone_number = value; }
	 public String 		getBillToCustomerPhoneNumberLabel(){ return label_bill_to_customer_phone_number; }
	 
	 public void 		setBillToCustomerPhoneNumberData(String value){ label_bill_to_customer_phone_number_data = value; }
	 public String 		getBillToCustomerPhoneNumberData(){ return label_bill_to_customer_phone_number_data; }
	 
	 public void 		setBillToCustomerFaxNumberLabel(String value) { label_bill_to_customer_fax_number = value; }
	 public String 		getBillToCustomerFaxNumberLabel(){ return label_bill_to_customer_fax_number; }
	 
	 public void 		setBillToCustomerFaxNumberData(String value){ label_bill_to_customer_fax_number_data = value; }
	 public String 		getBillToCustomerFaxNumberData(){ return label_bill_to_customer_fax_number_data; }
	 
	 public void 		setBillToCustomerEmailAddressLabel(String value) { label_bill_to_customer_email_address = value; }
	 public String 		getBillToCustomerEmailAddressLabel(){ return label_bill_to_customer_email_address; }
	 
	 public void 		setBillToCustomerEmailAddressData(String value){ label_bill_to_customer_email_address_data = value; }
	 public String 		getBillToCustomerEmailAddressData(){ return label_bill_to_customer_email_address_data; }

	 public void 		setPaymentDueDateLabel(String value) { label_payment_due_date = "Due Date"; }
	 public String 		getPaymentDueDate(){ return label_payment_due_date; }

	 public void 		setPaymentMethod(String payment_method){ this.payment_method = payment_method; }
	 public String 		getPaymentMethod(){ return this.payment_method; }

	 
	 // ************************************************************************************************************************

	 // Expected Date
	 public void 		setShipToExpectedDateLabel(String value) { label_ship_to_customer_date_expected = value; }
	 public String 		getShipToExpectedDateLabel(){ return label_ship_to_customer_date_expected; }
	 
	 public void 		setShipToExpectedDateData(String value) { label_ship_to_customer_date_expected_data = value; }
	 public String 		getShipToExpectedDateData(){ return label_ship_to_customer_date_expected_data; }

	 // Actual Date
	 public void 		setShipToArrivalDateLabel(String value) { label_ship_to_customer_date_arrived = value; }
	 public String 		getShipToArrivalDateLabel(){ return label_ship_to_customer_date_arrived; }
	 
	 public void 		setShipToArrivalDateData(String value) { label_ship_to_customer_date_arrived_data = value; }
	 public String 		getShipToArrivalDateData(){ return label_ship_to_customer_date_arrived_data; }


	 // Ship to customer information
	 public void 		setShipToCustomerID(String value) { label_ship_to_customer_id = "Ship To Customer ID: "; }
	 public String 		getShipToCustomerID(){ return label_ship_to_customer_id; }

	 public void 		setShipToCustomerNameLabel(String value) { label_ship_to_customer_name = value; }
	 public String 		getShipToCustomerNameLabel(){ return label_ship_to_customer_name; }

	 public void 		setShipToCustomerNameData(String value){ label_ship_to_customer_name_data = value; }
	 public String 		getShipToCustomerNameData(){ return label_ship_to_customer_name_data; }

	 public void 		setShipToCustomerCodeData(String value){ label_ship_to_customer_code_data = value; }
	 public String 		getShipToCustomerCodeData(){ return label_ship_to_customer_code_data; }

	 public void 		setShipToCustomerAddressLabel(String value) { label_ship_to_customer_address = value; }
	 public String 		getShipToCustomerAddressLabel(){ return label_ship_to_customer_address; }
	 
	 public void 		setShipToCustomerAddressData(String value){ label_ship_to_customer_address_data = value; }
	 public String 		getShipToCustomerAddressData(){ return label_ship_to_customer_address_data; }

	 public void 		setShipToCustomerCityLabel(String value) { label_ship_to_customer_city = value; }
	 public String 		getShipToCustomerCityLabel(){ return label_ship_to_customer_city; }
	 
	 public void 		setShipToCustomerCityData(String value){ label_ship_to_customer_city_data = value; }
	 public String 		getShipToCustomerCityData(){ return label_ship_to_customer_city_data; }
	 
	 public void 		setShipToCustomerStateLabel(String value) { label_ship_to_customer_state = value; }
	 public String 		getShipToCustomerStateLabel(){ return label_ship_to_customer_state; }

	 public void 		setShipToCustomerStateData(String value){ label_ship_to_customer_state_data = value; }
	 public String 		getShipToCustomerStateData(){ return label_ship_to_customer_state_data; }
	 
	 public void 		setShipToCustomerZipcodeLabel(String value) { label_ship_to_customer_zipcode = value; }
	 public String 		getShipToCustomerZipcodeLabel(){ return label_ship_to_customer_zipcode; }
	 
	 public void 		setShipToCustomerZipcodeData(String value){ label_ship_to_customer_zipcode_data = value; }
	 public String 		getShipToCustomerZipcodeData(){ return label_ship_to_customer_zipcode_data; }

	 
	 public void 		setShipToCustomerCountryLabel(String value) { label_ship_to_customer_country = value; }
	 public String 		getShipToCustomerCountryLabel(){ return label_ship_to_customer_country; }
	 
	 public void	 	setShipToCustomerCountryData(String value){ label_ship_to_customer_country_data = value; }
	 public String 		getShipToCustomerCountryData(){ return label_ship_to_customer_country_data; }

	 public void 		setShipToCustomerPhoneNumberLabel(String value) { label_ship_to_customer_phone_number = value; }
	 public String 		getShipToCustomerPhoneNumberLabel(){ return label_ship_to_customer_phone_number; }
	 
	 public void 		setShipToCustomerPhoneNumberData(String value){ label_ship_to_customer_phone_number_data = value; }
	 public String 		getShipToCustomerPhoneNumberData(){ return label_ship_to_customer_phone_number_data; }
	 
	 public void 		setShipToCustomerEmailAddressLabel(String value) { label_ship_to_customer_email_address = value; }
	 public String 		getShipToCustomerEmailAddressLabel(){ return label_ship_to_customer_email_address; }
	 
	 public void 		setShipToCustomerEmailAddressData(String value){ label_ship_to_customer_email_address_data = value; }
	 public String 		getShipToCustomerEmailAddressData(){ return label_ship_to_customer_email_address_data; }
	 
	 // ************************************************************************************************************************
	 
	 
	 public void 		setIssuerUUID(String r_uuid) { label_issuer_uuid_data = r_uuid; }
	 public String 		getIssuerUUID() { return label_issuer_uuid_data; }

	 public void 		setLocationUUID(String r_uuid) { label_issuer_uuid_location_data = r_uuid; }
	 public String 		getLocationUUID() { return label_issuer_uuid_location_data; }

	 public void 		setConsumerUUID(String r_uuid) { consumer_uuid = r_uuid; }
	 public String 		getConsumerUUID() { return consumer_uuid; }
	 
	 public void		setPONumber(String po_number) { this.po_number = po_number; }
	 public String		getPONumber() { return this.po_number; }
	 
	 public void 		setTransactionCurrency(String transaction_currency) { this.transaction_currency = transaction_currency; } 
	 public String		getTransactionCurrency() {return this.transaction_currency; }
	 
	 public void		setTransactionTotal(String total_value) { this.total_value = total_value; } 
	 public String		getTransactionTotal() { return this.total_value; }

	 public void		setTransactionSubTotal(String total_value) { this.total_value_sub = total_value; } 
	 public String		getTransactionSubTotal() { return this.total_value_sub ; }

	 public void		setTransactionTaxesTotal(String total_value) { this.total_value_taxes = total_value; } 
	 public String		getTransactionTaxesTotal() { return this.total_value_taxes; }
	 
	 public void		setTransactionDiscountTotal(String total_value) { this.total_value_discount = total_value; } 
	 public String		getTransactionDiscountTotal() { return this.total_value_discount; }

	 
	 public void 		setTransactionTenderValue(String tender_value) { this.tender_value = tender_value; } 
	 public String		getTransactionTenderValue() { return this.tender_value; }
	 
	 public void 		setTransactionChangeValue(String change_value) {  this.change_value = change_value; } 
	 public String		getTransactionChangeValue() { return this.change_value; }

	 public void 		setBalanceDue(String balance_due) {  this.balance_due = balance_due; } 
	 public String		getBalanceDue() { return this.balance_due; }

	 public void 		setNext(ElectronicDocument next) { this.next = next; }
	 public 			ElectronicDocument 	getNext() { return this.next; }
	 
	 public void 		setPrev(ElectronicDocument prev) { this.prev = prev; }
	 public 			ElectronicDocument getPrev() { return this.prev; }

	 public void 		setCommunicationMethod(String method) { this.method_of_communication = method; }
	 public				String getCommunicationMethod() { return this.method_of_communication; }
	 

	 public void setElectronicDocumentLineItemManager() { 
		 line_item_manager = new ElectronicDocumentLineItemManager();
	 }
	 public ElectronicDocumentLineItemManager getElectronicDocumentLineItemManager() {
		 return line_item_manager;
	 }
	 public void addLineItem(ElectronicDocumentLineItem line_item) {  line_item_manager.addLineItem( line_item ); }
	 public ElectronicDocumentLineItem getLineItem(int index) { return line_item_manager.getLineItem(index); }
	 
	 public void parkTransaction() { 
		 // This code will help a customer park a transaction when necessary. 
	 }
	 public void copyLastTransaction() { 
		 // This function will create a copy of an electronic document.
	 }
	 
	 
	 
	 public String buildInvoice() {
		 StringBuilder temp = new StringBuilder();

		  String[] electronic_document_key = {
		  "origin_system_id",
		  "destination_system_id",
		  "origin_system",
		  "destination_system",
		  "document_type",
		  "transactionUUID",
		  "invoice_number",
		  "issuer_uuid",
		  "consumer_uuid",
		  "store_name",
		  "issuer_address_data",
		  "issuer_phone_number",
		  "issuer_fax_number",
		  "transaction_date",
		  "transaction_time",
		  "customer_code_data",
		  "store_phone_number",
		  "customer_email_address",
		  "customer_phone_number"		  
		  
		  };
	      String[] electronic_document_value = new String[electronic_document_key.length];


	      electronic_document_value[0] 	= this.getOriginSystemID();
	      electronic_document_value[1] 	= this.getDestinationSystemID();
	      electronic_document_value[2] 	= this.getOriginSystem();
	      electronic_document_value[3] 	= this.getDestinationSystem();
	      electronic_document_value[4] 	= this.getTransactionType();
	      electronic_document_value[5] 	= this.getTransactionUUID();
	      electronic_document_value[6] 	= this.getInvoiceNumber();
	      electronic_document_value[7] 	= this.getIssuerUUID();
	      electronic_document_value[8] 	= this.getConsumerUUID();
	      electronic_document_value[9] 	= this.getStoreName();
	      electronic_document_value[10] = this.getLabelIssuerAddressData();
	      electronic_document_value[11] = this.getLabelIssuerPhoneNumberData();
	      electronic_document_value[12] = this.getLabelIssuerFaxNumberData();
	      electronic_document_value[13] = this.getTransactionDate();
	      electronic_document_value[14] = this.getTransactionTime();
	      
	      electronic_document_value[15] = this.getBillToCustomerCodeData();
	      electronic_document_value[16] = this.getBillToCustomerPhoneNumberData();
	      electronic_document_value[17] = this.getBillToCustomerEmailAddressData();
	      electronic_document_value[18] = this.getBillToCustomerCodeData();
	      
//	      electronic_document_value[15] = this.getBillToCustomerCodeData();
//	      electronic_document_value[15] = this.getBillToCustomerCodeData();

	      
	      
	      this.toTXT(electronic_document_key, electronic_document_value);
	      
	      /*
	         
	         outputFile.println( electronic_document_key[0] + ":" + this.getOriginSystem() +';');
	         outputFile.println( electronic_document_key[1] + ":" + this.getDestinationSystem() +';');
	         outputFile.println( electronic_document_key[2] + ":" + this.getTransactionType() +';');
	         outputFile.println( electronic_document_key[3]  +":" + this.getTransactionUUID() +';');
	         outputFile.println( electronic_document_key[4]  + ":"  + this.getInvoiceNumber() + ";");
	         outputFile.println( electronic_document_key[5]  + ":"  + this.getIssuerUUID() + ";");
	         outputFile.println( electronic_document_key[6]  + ":"  + this.getConsumerUUID() + ";");
	         outputFile.println( electronic_document_key[7]  + ":"  + this.getInvoiceNumber() + ";");
	        		 
	        		 
	        outputFile.println("transactionUUID:" + this.getTransactionUUID() +';');
	         outputFile.println("invoice_number:" + this.getInvoiceNumber() +';');
	         outputFile.println("issuer_uuid:" + this.getIssuerUUID() +';');
	         outputFile.println("consumer_uuid:" + this.getConsumerUUID() +';');
	         
	         // template
	         // outputFile.println(electronic_document_key[]  + ":"  + this.getInvoiceNumber() + ";");
		     */   
	      
		 return temp.toString();
	 }
	 public String buildCreditNote() {
		 StringBuilder temp = new StringBuilder();
		 return temp.toString();
		 
	 }
	 public String buildDebitNote() {
		 StringBuilder temp = new StringBuilder();
		 return temp.toString();
		 
	 }
	 public String buildASN() {
		 StringBuilder temp = new StringBuilder();
		 return temp.toString();
		 
	 }
	 
	 public String toTXT(String[] key, String[] value) { 
		 
		 String temp = "";
		 
		 try { 
			 
         File tempFile            = new File(".//src//pay//point//sample//electronic_document//" + this.getInvoiceNumber()  + ".txt");
         outputFile      = new PrintWriter(tempFile);

         for(int i = 0; i < key.length; i++)
         {
        	 System.out.println(key[i] + ":" + value[i] + ";");
        	 outputFile.println(key[i] + ":" + value[i] + ";");
         }
         
                  
         
         outputFile.close();
         
		 }catch(IOException e)
		 {
			 System.out.println( "System error at: ElectronicDocument->toTXT" );
		 }
		 
		 return temp;
	 }
	 
	 public String toX12() {
		 
		 String x12 = "";
		 
		 return x12;
	 }
	 

     
	 public String toString() {
		StringBuilder temp = new StringBuilder();

		temp.append("\n\n");
		temp.append("IssuerUUID:" + getIssuerUUID() + ";");
		temp.append("TransactionUUID:" + getTransactionUUID() + ";");
		temp.append("Invoice Number:" + getInvoiceNumber() + ";");
		temp.append( "TotalSub:" + getTransactionSubTotal() +";");
		temp.append(" TotalTax:" + getTransactionTaxesTotal() + ";");
		temp.append(" TotalDiscount:" + getTransactionDiscountTotal() + ";");
		temp.append(" TransactionTotal:" + getTransactionTotal() + ";");
		temp.append(" TransactionTenderValue:" + getTransactionTenderValue() +";" );

		temp.append( "CustomerName:" + getBillToCustomerNameData() + ";");
		//temp.append( getBillToCustomerAddressLabel() + ":" + getBillToCustomerCityLabel() + ";" );
		//temp.append( getBillToCustomerStateLabel() + ", " + getBillToCustomerZipcodeLabel() );

		return temp.toString();
	 }
     
	 public String toXML() {
		 String XML = "";
		 
		 try {
			 
	            documentFactory = DocumentBuilderFactory.newInstance();
	 
	            documentBuilder = documentFactory.newDocumentBuilder();
	 
	            document = documentBuilder.newDocument();
	 
	            // root element
	            root = document.createElement("xml");
	            attr = document.createAttribute("version");
	            attr.setValue("1.0");
	            root.setAttributeNode(attr);
	            document.appendChild(root);
	            
	            attr = document.createAttribute("encoding");
	            attr.setValue("UTF-8");
	            root.setAttributeNode(attr);
//	            document.appendChild(root);
	       	 	
	            // employee element
	            element = document.createElement("issuer_uuid");
	            root.appendChild(element);
	 
	            
	            root = document.createElement("xml");
	            attr = document.createAttribute("version");
	            attr.setValue("1.0");
	            root.setAttributeNode(attr);
	            //document.appendChild(root);

	            
	            
	            
	            
	            
	            
	            /*
	            // set an attribute to staff element
	            Attr attr = document.createAttribute("id");
	            attr.setValue("10");
	            employee.setAttributeNode(attr);
	 
	            //you can also use staff.setAttribute("id", "1") for this
	 
	            // firstname element
	            Element firstName = document.createElement("FUNCTION_TYPE");
	            firstName.appendChild(document.createTextNode("SECURITY"));
	            employee.appendChild(firstName);
	 
	            // lastname element
	            Element lastname = document.createElement("COMMAND");
	            lastname.appendChild(document.createTextNode("REGISTER"));
	            employee.appendChild(lastname);
	 
	            // email element
	            Element email = document.createElement("ENTRY_CODE");
	            email.appendChild(document.createTextNode("entry code argument"));
	            employee.appendChild(email);
	 
	            // department elements
	            Element department = document.createElement("KEY");
	            department.appendChild(document.createTextNode("public encode key"));
	            employee.appendChild(department);
	             */
	            
	            // create the xml file
	            //transform the DOM Object to an XML File
	            
	            
	            
	            transformerFactory = TransformerFactory.newInstance();
	            transformer = transformerFactory.newTransformer();
	            domSource = new DOMSource(document);
	            streamResult = new StreamResult(new File(xmlFilePath));
	 
	            // If you use
	            // StreamResult result = new StreamResult(System.out);
	            // the output will be pushed to the standard output ...
	            // You can use that for debugging 
	 
	            transformer.transform(domSource, streamResult);
	 
	            System.out.println("Done creating XML File");
	        } catch (ParserConfigurationException pce) {
	            pce.printStackTrace();
	        } catch (TransformerException tfe) {
	            tfe.printStackTrace();
	        }

				/* 
	            try { 
	            
	            	
	            	String data = "hello world";
	            	
	                FileWriter filewriter = new FileWriter("Invoice.XML");

	                // Creates a BufferedWriter
	                BufferedWriter output = new BufferedWriter(filewriter);

	                // Writes the string to the file
	                output.write(data);

	                // Closes the writer
	                output.close();	
	            	
	            	
	            	

	            
	            file = new File("Invoice.XML");
	            inputFile = new Scanner(file);
	            line = "";
	            
	            while(inputFile.hasNextLine())
	            {
	            	line = inputFile.nextLine();
	            	System.out.println(line);
	            }
	            }catch(IOException e) {

					System.out.println("\n\n");
					e.printStackTrace(); 
					System.out.println("\n\n");
				}
	            
	            
	 
	        } catch (ParserConfigurationException pce) {
	            pce.printStackTrace();
	        } catch (TransformerException tfe) {
	            tfe.printStackTrace();
	        }
		 */
			System.out.println(XML);
		 return XML;
		 
	 }

	 
	 
     public String printDocument() { 
    	 
    	 String temp = "";
    	 
    	 System.out.println( this.getTransactionCurrency () );
    	 System.out.println( this.getTransactionSubTotal() );
    	 System.out.println( this.getTransactionTaxesTotal() );
    	 System.out.println( this.getTransactionTotal() );
    	 System.out.println( this.getTransactionDiscountTotal() );
    	 System.out.println( this.getTransactionChangeValue() );
    	 
    	 return temp;
     }
     
     
     public static void main(String[] args) {
    	 
    	 /*
    	 ElectronicDocument test = new ElectronicDocument();

    	 test.setOriginSystem("Lockwind POS");
    	 test.setDestinationSystem("Lockwind ERP");
    	 test.setTransactionType("Invoice");
    	 test.setStoreName("165 St. Hardware Inc.");
    	 
    	 test.setInvoiceNumber("20");
    	 test.toTXT();
    	 
    	 System.out.println("Electronic Document->main() " );
    */
    	 
     }

	 
	 
}
