package pay.point.sample;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FormatManager {


	private NumberFormat formatter;
	
	public FormatManager() {

	}
	
	public String formatDoubleUS(double value) {
        
		String return_value = "";
		String pattern = "#0.00";
		
		formatter = new DecimalFormat(pattern);
		
		return_value = formatter.format(value);
		
		return return_value;
		
	}
	
	public String formatDouble(String value ) {
		String pattern = "###.##";
	    	DecimalFormat myFormatter = new DecimalFormat(pattern);
	    	String output = myFormatter.format(Double.parseDouble(value) );
	    	System.out.println(value + "  " + pattern + "  " + output);
		return output;
	 }
	
	public String increaseLength(String x,int length){
		String target = "";
		StringBuilder str = null;
		str = new StringBuilder(x);

		if(x.length() < length){
		for(int index = x.length(); index <= length; index++){
		str.insert(index,' ');}}

		target = str.toString();
		return target;
		}

}
