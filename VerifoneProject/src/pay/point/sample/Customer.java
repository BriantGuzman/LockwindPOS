package pay.point.sample;

public class Customer extends ServiceData {
	  
	
	  private String bill_to_customer_code_data;
	  private String bill_to_customer_name_data;
	  private String bill_to_customer_address_data;
	  private String bill_to_customer_city_data;
	  private String bill_to_customer_state_data;
	  private String bill_to_customer_zipcode_data;
	  private String bill_to_customer_country_data;
	  private String bill_to_customer_phone_number_data;
	  private String bill_to_customer_email_address_data;

	  private String ship_to_customer_code_data;
	  private String ship_to_customer_name_data;
	  private String ship_to_customer_address_data;
	  private String ship_to_customer_city_data;
	  private String ship_to_customer_state_data;
	  private String ship_to_customer_zipcode_data;
	  private String ship_to_customer_country_data;
	  private String ship_to_customer_phone_number_data;
	  private String ship_to_customer_email_address_data;

	  
	  private Customer next;

	  
	  public Customer() {
		  
		  
		  bill_to_customer_name_data 			= null;
		  bill_to_customer_address_data 		= null;
		  bill_to_customer_city_data 			= null;
		  bill_to_customer_state_data 			= null;
		  bill_to_customer_zipcode_data 		= null;
		  bill_to_customer_country_data 		= null;
		  bill_to_customer_phone_number_data 	= null;
		  bill_to_customer_email_address_data 	= null;

		  ship_to_customer_name_data 			= null;
		  ship_to_customer_address_data 		= null;
		  ship_to_customer_city_data 			= null;
		  ship_to_customer_state_data 			= null;
		  ship_to_customer_zipcode_data 		= null;
		  ship_to_customer_country_data 		= null;
		  ship_to_customer_phone_number_data 	= null;
		  ship_to_customer_email_address_data 	= null;
	  }

	  public Customer(String customer_code)
	  {
		this();
		bill_to_customer_name_data = "Generic Customer";
		ship_to_customer_name_data = "Generic Customer";
		bill_to_customer_code_data = customer_code; 
	  }

	  public void setNext(Customer c) { next = c; }
	  public Customer getNext() { return next; }

	  
		// BILL TO CUSTOMER INFORMATION = MUTATORS
	  public void setBillToCode(String x) 			{ bill_to_customer_code_data			= 	x; }
	  public void setBillToName(String x) 			{ bill_to_customer_name_data			= 	x; }
	  public void setBillToAddress(String x) 		{ bill_to_customer_address_data			= 	x; }
	  public void setBillToCity(String x) 			{ bill_to_customer_city_data			= 	x; }
	  public void setBillToState(String x) 			{ bill_to_customer_state_data			= 	x; }
	  public void setBillToZipcode(String x) 		{ bill_to_customer_zipcode_data			= 	x; }
	  public void setBillToCountry(String x) 		{ bill_to_customer_country_data			= 	x; }
	  public void setBillToPhoneNumber(String x) 	{ bill_to_customer_phone_number_data	= 	x; }
	  public void setBillToEmailAddress(String x) 	{ bill_to_customer_email_address_data	= 	x; }

	// BILL TO CUSTOMER INFORMATION = ACCESSORS
	  public String getBillToCode() 				{ return bill_to_customer_code_data; }
	  public String getBillToName() 				{ return bill_to_customer_name_data; }
	  public String getBillToAddress() 				{ return bill_to_customer_address_data; }
	  public String getBillToCity() 				{ return bill_to_customer_city_data; }
	  public String getBillToState() 				{ return bill_to_customer_state_data; }
	  public String getBillToZipcode() 				{ return bill_to_customer_zipcode_data; }
	  public String getBillToCountry() 				{ return bill_to_customer_country_data; }
	  public String getBillToPhoneNumber() 			{ return bill_to_customer_phone_number_data; }
	  public String getBillToEmailAddress() 		{ return bill_to_customer_email_address_data; }

	  
	  
		// SHIP TO CUSTOMER INFORMATION = MUTATORS
  	  public void setShipToCode(String x) 			{ ship_to_customer_code_data			= 	x; }
  	  public void setShipToCodeData(String x) 		{ ship_to_customer_code_data			= 	x; }
  	  public void setShipToName(String x) 			{ ship_to_customer_name_data			= 	x; }
  	  public void setShipToAddress(String x)		{  ship_to_customer_address_data		= 	x; }
  	  public void setShipToCity(String x) 			{ ship_to_customer_city_data			= 	x; }
  	  public void setShipToState(String x) 			{ ship_to_customer_state_data			= 	x; }
  	  public void setShipToZipcode(String x)		{ ship_to_customer_zipcode_data			= 	x; }
  	  public void setShipToCountry(String x)		{  ship_to_customer_country_data		= 	x; }
  	  public void setShipToPhoneNumber(String x) 	{ ship_to_customer_phone_number_data	= 	x; }
  	  public void setShipToEmailAddress(String x) 	{ ship_to_customer_email_address_data	= 	x; }

		// SHIP TO CUSTOMER INFORMATION = ACCESSORS

  	  public String getShipToCode() 		{ return ship_to_customer_code_data; }
  	  public String getShipToName() 		{ return ship_to_customer_name_data; }
  	  public String getShipToAddress() 		{  return ship_to_customer_address_data; }
  	  public String getShipToCity() 		{ return ship_to_customer_city_data; }
  	  public String getShipToState() 		{ return ship_to_customer_state_data; }
  	  public String getShipToZipcode() 		{ return ship_to_customer_zipcode_data; }
  	  public String getShipToCountry() 		{ return ship_to_customer_country_data; }
  	  public String getShipToPhoneNumber() 	{ return ship_to_customer_phone_number_data; }
  	  public String getShipToEmailAddress() {  return ship_to_customer_email_address_data; }
	  
	  

	} // close class:: Customer
	  
	  /*
	  
  	  private String customerCode;
	  private String customerName;
	  private String customerPhoneNumber;
	  private String customerEmail;

	  
	  
	  public Customer(){
	      
	      customerCode = "";
	      customerName = "";
	      customerPhoneNumber = "";
	      customerEmail = "";
	      next = null;
	      
	  }
	  public Customer(String customerCode,String customerName, String customerPhoneNumber,String customerEmail){
	      
	      this.customerCode = customerCode;
	      this.customerName = customerName;
	      this.customerPhoneNumber = customerPhoneNumber;
	      this.customerEmail = customerEmail;
	      
	  }
	  
	  
	  public String toString() {
		  
		  String temp = "";
		  
		  temp = customerCode + " " + customerName + " " + customerPhoneNumber + " " + customerEmail;
		  
		  return temp;
	  }
	  public void setCustomerCode(String code){
	      customerCode = code;
	  }
	  public String getCustomerCode(){
	      return customerCode;
	  }
	  public void setCustomerName(String name){
	      customerName = name;
	  }
	  public String getCustomerName(){
	      return customerName;
	  }
	  public void setCustomerPhoneNumber(String phoneNumber){
	      customerPhoneNumber = phoneNumber;
	  }
	  public String getCustomerPhoneNumber(){
	      return customerPhoneNumber;
	  }
	  public void setCustomerEmail(String email){
	      customerEmail = email;
	  }
	  public String getCustomerEmail(){
	      return customerEmail;
	  }
	  
	  */
	  
	  
	  