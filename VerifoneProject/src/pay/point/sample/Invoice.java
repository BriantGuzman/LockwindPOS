package pay.point.sample;

// Last Updated: 1/11/24
public class Invoice extends ElectronicDocument {

	// Inherited from parent class
	// private String invoice_number; -- for this reason I include it for context.
	// private InvoiceLineItem line_item;
		
	public Invoice() { }
	public Invoice(String issuer_uuid,String posUUID,String client_id)
	{
		super(issuer_uuid);
		this. setLocationUUID(posUUID);
		this. setConsumerUUID(client_id);
	}
		
	
	  public void setInvoiceDefaultValues() {
		  	// this. setIssuerUUID(getIssuerUUID);
		  	//			invoiceNumber								= Integer.parseInt(invoice.getInvoiceNumber() );
			this. setStoreName("165 St. Hardware Inc.");
	        this. setStoreAddress("1099 St. Nicholas Avenue, ");
			this. setStoreSecondAddress("NY, NY, 10032");
			this. setStorePhoneNumber("Phone #: +1 212 740 4652 ");
			this. setStoreFaxNumber("FAX:             ");
			this. setDirectory("./");
			this. setFileExtension(".txt");
			this. setOriginSystemID("POS0001");
			this. setDestinationSystemID("ERP0001");
			this. setOriginSystem("Lockwind POS");
			this. setDestinationSystem("Lockwind ERP");
			this. setBillToCustomerNameLabel("Bill to Customer: ");
			this. setBillToCustomerNameData("Customer Name");
			this. setBillToCustomerAddressLabel("Address: ");
			this. setBillToCustomerAddressData("Address");
			this. setBillToCustomerCityLabel("City: ");
			this. setBillToCustomerCityData("city");
			this. setBillToCustomerStateLabel("State: ");
			this. setBillToCustomerStateData("state");
			this. setBillToCustomerZipcodeLabel("Zipcode: ");
			this. setBillToCustomerZipcodeData("zipcode");
			this. setBillToCustomerCountryLabel("Country: ");
			this. setBillToCustomerCountryData("Country");
			this. setBillToCustomerPhoneNumberLabel("Phone Number:");
			this. setBillToCustomerPhoneNumberData("+xx xxx xxx xxxx");
			this. setBillToCustomerEmailAddressLabel("Email: ");
			this. setBillToCustomerEmailAddressData("xx@xxxx.com");
			this. setShipToCustomerNameLabel("Ship To Customer: ");
			this. setShipToCustomerNameData("customer name");
			this. setShipToCustomerAddressLabel("Ship To Address: ");
			this. setShipToCustomerAddressData("address");			
			this. setShipToCustomerCityLabel("City: ");
			this. setShipToCustomerCityData("city");
			this. setShipToCustomerStateLabel("State: ");
			this. setShipToCustomerStateData("state");
			this. setShipToCustomerZipcodeLabel("Zipcode: ");
			this. setShipToCustomerZipcodeData("zipcode");
			this. setShipToCustomerCountryLabel("Country: ");
			this. setShipToCustomerCountryData("country");
			this. setShipToCustomerPhoneNumberLabel("Phone Number:");
			this. setShipToCustomerPhoneNumberData("+xx xxx xxx xxxx");
			this. setShipToCustomerEmailAddressLabel("Email: ");
			this. setShipToCustomerEmailAddressData("xx@xxxx.com");
	  }
	
	
	}

	
	

	

