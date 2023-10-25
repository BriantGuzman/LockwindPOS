package pay.point.sample;


public class ValidationPlatform {

	//private String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
	

	private String error_message;
	
	public ValidationPlatform()
	{
		error_message = "";
	}
	
	public String validateEmailAddress(String email_address)
	{
		if(email_address != null)
		{
			error_message = "Validation:Success";

		}
		else {
			error_message = "Validation:Failed";
			
		}
				
		return error_message;
	}

	
	public String validateReferenceCode(String gtin) {
		
	int invalid_reference_code = 0;
	String error_description = "";
	
	try
	{
	
	if(gtin.equalsIgnoreCase("") == true) { invalid_reference_code = 1; }
	if(gtin.equalsIgnoreCase(" ") == true) { invalid_reference_code = 2; }
	
	if(invalid_reference_code == 1) { error_description = "GTIN is empty"; }
	if(invalid_reference_code == 2) { error_description = "GTIN is invalid"; } 
	} catch(NullPointerException e){ error_description = "GTIN is Null"; } 
	return error_description;
	}
	
	public String validateQuantity(String x) {
		
		int invalid_reference_code = 0;
		String error_description = "";
		int x_value = 0;
		
		try { 
		x_value = Integer.parseInt(x);
		if(x_value < 0)
		{
			invalid_reference_code = 1;
		}
		if(x_value == 0) {
			invalid_reference_code = 2;
		}
		}
		catch(NumberFormatException exe) {
		invalid_reference_code = 3;
		}
			
		if(invalid_reference_code == 1)
		{
			error_description = "Quantity cannot be a negative number";
		}
		if(invalid_reference_code == 2) {
			error_description = "Quantity cannot equal zero.";
		}
		if(invalid_reference_code == 3) {
		    error_description = "Quantity is not a number";

		}
		
		return error_description;
		}
	
		public String validatePrice(String x)
		{
			int invalid_reference_code = 0;
			String error_description = "";
			double x_value = 0;
			
			try { 
			x_value = Double.parseDouble(x);

			if(x_value < 0.00 )
			{
				invalid_reference_code = 1;
				error_description = "Error->validatePrice(): Price cannot be less than zero.";
			}
			if(x_value == 0.00 || x_value == 0) {
				invalid_reference_code = 2;
				error_description = "Error->validatePrice(): Price cannot equal zero.";
			}
			}
			catch(NumberFormatException exe) {
			invalid_reference_code = 3;
			error_description = "Error->validatePrice(): This is not a valid price";
			}

			// return error_description;
			System.out.println(error_description);
			
			return String.valueOf(invalid_reference_code);
		}


		
		
}