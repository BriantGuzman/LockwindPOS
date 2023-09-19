//Last Update: 9/9/2022

import javax.swing.JOptionPane;

public class Ali {

	// Ali is an AI-based cashier 
	
	private double last_transaction;
	private double temp;
	private String return_value;
	private String amount;
	private double tendered;
	
	
	public Ali() {
		last_transaction = 0.00;
		temp = 0.00;
		return_value = "";
		amount = "";
		tendered = 0.00;
	}
	public void setLastTransaction(double last_transaction) { this.last_transaction = last_transaction; }
	
	public double getLastTransaction() {
		
		temp = last_transaction;
		
		return temp;
	}
	
	public String requestDiscount() { 

			return_value = "";
		
//	      inputQty = JOptionPane.showInputDialog(null,"Enter % to Discount:",table_manager.getData(table,i,6).toString());
	      return_value = JOptionPane.showInputDialog(null,"Enter % to Discount:");
		
		return return_value;

	}
	
	
	  public double chargeCustomer() {
		  amount = "";
	      tendered = 0.00;
	      
	      amount 	= JOptionPane.showInputDialog("Please Enter Amount Given");
	      tendered 			= Double.parseDouble(amount);
	      
	      return tendered;
	      
	  }
	  
	  public void messageUser(String message) {
	      
	      JOptionPane.showMessageDialog(null,message);
	      
	  }
	  
}
