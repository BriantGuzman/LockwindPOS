package pay.point.sample;

public class TaxEngine { 
	
	// This class is going to return the appropriate taxes per line item.
	// The database of taxes is going to be cloud based and this class will send a request for taxes and the system will return a response from the cloud.
	// The return is then passed into the JTable for document generation.
	
	
	private double NYCSalesTaxRate = .08875;
	private double FortLeeSalesTaxRate = .0663;
	
	
	public TaxEngine() {
		
	}
	public double getNYCSalesTax() {
		
		return NYCSalesTaxRate;
	}
	
}