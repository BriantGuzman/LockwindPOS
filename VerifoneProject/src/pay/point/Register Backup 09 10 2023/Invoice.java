// Last Updated: 5/29/23
public class Invoice extends ElectronicDocument {

	// Inherited from parent class
	// private String invoice_number; -- for this reason I include it for context.
	
	// Set by System
	private String invoice_directory;
	private String file_extension;


	private String ShipTo;
	private String ShipToDestinationFacility;

	// Set by user and can be changed
		private String payment_method;
	
	// Calculated by the system. (TableManager)
	private double total_sub;
	private double total_taxes;
	private double total_value;
	
	private double tender_value;
	
	private double change_value;
	
	// private InvoiceLineItem line_item;
	private InvoiceLineItem head;
		
	public Invoice() {
		payment_method 	= "";
		total_sub 		= 0.00;
		total_taxes 	= 0.00;
		total_value 	= 0.00;
		tender_value 	= 0.00;
		change_value 	= 0.00;
		head	 		= null;
	}
	
	public void setDirectory(String invoice_directory) {
		this.invoice_directory = invoice_directory;
	}
	public String getDirectory()
	{
		return this.invoice_directory;
	}
	public void setFileExtension(String ext) {
		this.file_extension = ext;
	}
	public String getFileExtension() {
		return this.file_extension;
	}
	
	public void setPaymentMethod(String payment_method){
	this.payment_method = payment_method;
	}
	public String getPaymentMethod(){
	return this.payment_method;
	}
	
	public double getInvoiceTotal() {
		return this.total_value;
	}
	public double getInvoiceSubTotal() {
		return this.total_sub;
	}
	public void setTenderValue(){
	this.tender_value = tender_value;
	}
	public double getTenderValue(double tender_value){
	return this.tender_value;
	}

	public void addInvoiceLineItem(InvoiceLineItem line_item) {
		
		InvoiceLineItem temp = null;
		
		boolean insert_completed = false;
		
		temp = head;
		
		if(temp == null)
		{
			temp = line_item;
		}
		else
		{
			
			while(temp.getNext() != null && insert_completed == false)
			{
				if(temp.getNext() == null)
				{
					temp.setNext(line_item);
					insert_completed = true;
				}
				temp = temp.getNext();
			}
		}
		
	}
	

	/**: This function/method is inherited from the ElectronicDocument class: copied here below for context.
	 public void setInvoiceNumber(String invoice_number) { this.invoice_number = invoice_number; }
	 public String getInvoiceNumber() { return this.invoice_number; }

	*/
	
	
	
	
}
