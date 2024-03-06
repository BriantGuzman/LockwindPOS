package pay.point.sample;

public class RegisterDeprecated {

}


/*
public void createElectronicDocument() {  
	


    
    try{

	System.out.println(" Register->@createElectronicDocument() "); 
	      
      row 					= 0; 
      col 					= 0;
	  da          			= "";
      fmt					= null;
      file        			= null;
      outputFile  			= null;
      tendered             	= 0.00;
      subtotal             	= 0.00;
      totaltaxes           	= 0.00;
      total                	= 0.00;
      discount             	= 0.00;
      change               	= 0.00;

      
	  formatter 			= new DecimalFormat("#0.00");
      today                 = new Date();
      fmt                   = DateFormat.getDateInstance(styles[3], locale[0]);
      simpDate             	= new SimpleDateFormat("hh:mm:ss a");
      row 					= table.getSelectedRow();
      col 					= table.getSelectedColumn();
      file            		= new FileWriter( invoice.getDirectory() + "invoice_number" + invoice.getFileExtension() ,true);
      outputFile      		= new PrintWriter(file);
      file            		= new FileWriter(  invoice.getDirectory() + "INV Create Electronic Document" + String.valueOf(invoiceNumber) + invoice.getFileExtension() );
      outputFile      		= new PrintWriter(file);
      PrintReceipt tp 		= new PrintReceipt();
 	  Process p 			=  Runtime.getRuntime().exec("cmd /c printReceipt.bat");

      if(					tender_amount.getText().equalsIgnoreCase("")) { tendered = ali.chargeCustomer(); }
      else { 				tendered = Double.parseDouble(tender_amount.getText() ); }
          
      

      subtotal 				= table_manager.getColumnTotal(table,5);
      totaltaxes 			= table_manager.getColumnTotal(table,6);
      discount 				= table_manager.getColumnTotal(table,7);
      account_selected 		= account_name_input.getSelectedItem().toString();

      total 				= subtotal + totaltaxes;
      total 				= total - discount;
      change 				= tendered - total;
      
      invoice				. setTransactionTenderValue(String.valueOf( tendered ));
      invoice				. setTransactionCurrency("USD");
      invoice				. setTransactionSubTotal(String.valueOf(subtotal));
      invoice				. setTransactionTaxesTotal(String.valueOf(totaltaxes));
      invoice				. setTransactionTotal(String.valueOf(total));
      invoice				. setTransactionDiscountTotal(String.valueOf(discount));
      invoice				. setTransactionTenderValue(String.valueOf(tendered));
      invoice				. setTransactionChangeValue(String.valueOf(change));
      invoice				. setBalanceDue(String.valueOf(total-tendered));
      invoice				. setBillToCustomerCodeData(account_name_input.getSelectedItem().toString() );

//      outputFile	  		. println( invoice.getInvoiceNumber() );
//      outputFile	  		. close();
//      file			  		. close();
          
      
      try { 
    		  
    	  
    	  invoiceNumber = Integer.parseInt(http.getCurrentInvoiceNumber(retailerUUID)); 
          invoice.setInvoiceNumber( String.valueOf(invoiceNumber));
          store_print_name = " " + invoice.getStoreName().trim() + " ";

    	  // This process writes the invoice number to the file locally as a backup.
                    
          outputFile.println("----------------------------------------");
          outputFile.println("           " + invoice.getStoreName() + "           ");
          outputFile.println(" " +  invoice.getStoreAddress()		+ " " + invoice.getStoreSecondAddress());
          outputFile.println(" " + 	invoice.getStorePhoneNumber()	+ "      ");
          outputFile.println( 		invoice.getStoreFaxNumber()		+"\n");
                            
          
          
          outputFile.println(" " + fmt.format(today) +  "    " + simpDate.format(today) + "   Invoice No: " + invoiceNumber);
          outputFile.println(" Customer: " + account_name_input.getSelectedItem() );
          outputFile.println(" Name: " + account_name_input.getSelectedItem() );
          outputFile.println(" Phone Number: " + account_name_input.getSelectedItem() );
          outputFile.println(" Email: " + account_name_input.getSelectedItem() );
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
          
          for( int i = 0; i < table.getRowCount();i++) // Modify this to include line item count on receipt.
          {
              if(table.getValueAt(i,0) == null || table.getValueAt(i,0).toString().equalsIgnoreCase("")  ) {} 
              else{ item_count++; }
          }

          
          outputFile.println("                         SUB TOTAL $" + format_manager.formatDoubleUS(subtotal));
          outputFile.println("                         SALES TAX $" + format_manager.formatDoubleUS(totaltaxes));
          if(discount != 0.00) {
          outputFile.println("                         DISCOUNT  $" + format_manager.formatDoubleUS(discount));} else{}
          outputFile.println("                         TOTAL     $" + format_manager.formatDoubleUS(total) + "\n\n");
          outputFile.println("                         TENDERED  $" + format_manager.formatDoubleUS(tendered));
          outputFile.println("                         CHANGE    $" + format_manager.formatDoubleUS(change) + "\n\n");
          outputFile.println("                         Addenda:  " + addenda.getText() );
          
          // refreshTotal(table,tendered,change);

          outputFile.println("----------------------------------------");
          outputFile.print("THANKS FOR SHOPPING AT");
          
          outputFile.println(store_print_name);
          outputFile.println("");
          outputFile.println("----------------------------------------");
          outputFile.close();
	  	
          
  //      if(change > 0.01) { JOptionPane.showMessageDialog(null,"Change: " + "$ "+ format_manager.formatDoubleUS(change)); }
  //      else { JOptionPane.showMessageDialog(null,"Balance not paid in full: " + "$ "+ format_manager.formatDoubleUS(change)); }

          
      }catch(Exception ex){
    	  
    	  System.out.println("System Exception Error: at CreateElectronicDocument ");
    	  ex.printStackTrace();
    	  
      }          
          
            // saveTableToReceipt(table,client_id);
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

          	
          	
            /*
            // Print Receipt Function should be in another function.
            		System.out.println("Printing receipt to this printer: " + default_printer_receipt.getSelectedItem().toString());
            		//       test.main(null);
                  tp.main(default_printer_receipt.getSelectedItem().toString() );
                  tp.printToDisplayPrinter(default_printer_display.getSelectedItem().toString(), "Total: " + format_manager.formatDoubleUS(total) );
                  System.out.println("Initiating Printing Process: " + p.pid());

                      */

          	  
/*          	  
          	System.out.println(payment_method.getSelectedItem() + " selected");
  
          			if(payment_method.getSelectedItem().toString().equalsIgnoreCase("CASH") ) {
          			
          			try {
          				displayMessage("Balance Due: $0.00",10);
          				// Thread.sleep(5000);
          				endSession(); 
          				} catch(Exception e){
          				  System.out.println(e.toString() );
          				  
          			} }else{
          			
          			if(registerStatus == true){
          			
          			  try { 

          				  captureCardEarlyReturn();
          				  Thread.sleep(1000);
          				  authorizeCard();
          				  Thread.sleep(1000);
          				  endSession();
//          				  Thread.sleep(2000);

          				  }catch(Exception verifone_exception) {
          					  System.out.println(verifone_exception.toString());
          				  }

          			}else { System.out.println("Register tenderAction Error: Credit card payment terminal cannot be reached"); }
          			}
                
                
          
      }
      catch(Exception ex){}
      
}

*/


/*
 * 

//try { productInfo = product_management_system.getProductInfoAPIPriceRetail(inputGTIN); } // COL 5: SUBTOTAL
//catch(Exception e) { System.out.println("Exception thrown on askGTIN Retail Price"); }
//table_manager.setData(table,i,5,table_manager.getSubTotal(table,i)); 


/*
try { productInfo = product_management_system.getProductInfoAPIPriceRetail(inputGTIN); } // COL 6: TAX
catch(Exception e) { System.out.println("Exception thrown on askGTIN Retail Price"); }
table_manager.setData(table,i,5,(table,i)); 

	
	
// System.out.println("Printing Line Items: "); 
	// invoice.getElectronicDocumentLineItemManager().printLineItems();



System.out.println(formatter.format(Double.parseDouble(invoice.getTransactionSubTotal() ) ) );
System.out.println(formatter.format(Double.parseDouble(invoice.getTransactionTaxesTotal() ) ) );
System.out.println(formatter.format(Double.parseDouble(invoice.getTransactionTotal() ) ) );			
System.out.println(line_item.toString() );



		http.sendProductPostAPILineItem( 
		
		table_manager.getData(table,i,0).toString(),table_manager.getData(table,i,1).toString(),table_manager.getData(table,i,2).toString(),
		table_manager.getData(table,i,3).toString(),table_manager.getData(table,i,4).toString(),table_manager.getData(table,i,5).toString(),
		table_manager.getData(table,i,6).toString(),table_manager.getData(table,i,7).toString(),table_manager.getData(table,i,8).toString(),
		table_manager.getData(table,i,9).toString()
		);


            http.sendProductPostAPILineItem( 
		
		table_manager.getData(table,i,0).toString(),table_manager.getData(table,i,1).toString(),table_manager.getData(table,i,2).toString(),
		table_manager.getData(table,i,3).toString(),table_manager.getData(table,i,4).toString(),table_manager.getData(table,i,5).toString(),
		table_manager.getData(table,i,6).toString(),table_manager.getData(table,i,7).toString(),table_manager.getData(table,i,8).toString(),
		table_manager.getData(table,i,9).toString()
		);


http.sendProductPostAPI(
		"consumer_uuid","issuer_uuid",client_id,client_name,
		"customer code",String.valueOf(invoiceNumber),"invoice_date","invoice_time",
		"USD",String.valueOf(invoice.getInvoiceTotal() ),"Tender Value",invoice.getTransactionChangeValue(),"ELECTRONIC DOCUMENT","INVOICE",
		table_manager.getData(table,i,0).toString(),table_manager.getData(table,i,1).toString(),table_manager.getData(table,i,2).toString(),
		table_manager.getData(table,i,3).toString(),table_manager.getData(table,i,4).toString(),table_manager.getData(table,i,5).toString(),
		table_manager.getData(table,i,6).toString(),table_manager.getData(table,i,7).toString(),table_manager.getData(table,i,8).toString(),
		table_manager.getData(table,i,9).toString()
		);
		*/

/*
 * The code below was removed on 2/2/24 in order to provide more clarity for the code.
 * Code is located in the enter key action proc.
 * 
	//			JOptionPane.showMessageDialog(null, "ITEM COUNT: " + item_count);
//          System.out.println("UPLOADING PRODUCT:"); 

*/            
/*		API -> SendProductPostAPI
 * 		 String consumer_uuid, String issuer_uuid,String client_id,String client_name,
 		 String customer_code,String invoice_number,String invoice_date,String invoice_time,
		 String invoice_currency,String total_value,String tender_value,String change_value,
		 String transaction_type,String transaction_type_value
		 String reference_code, String quantity, String category,
		 String description,String price, String subtotal, 
		 String tax, String discount, String onhand, String line_item_id
*/



/*

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

}
else
{
//table_manager.setData(table,i,5,"0");
}

}



//Deprecated as of 2/22/24
  public void updateSubTotal(){

	  int i,j = 0;

	  i = table.getSelectedRow();
	  j = table.getSelectedColumn();

	  if(table.isEditing()){table.getCellEditor().stopCellEditing();}
	  
      table_manager.setData(table,i,j,format_manager.formatDoubleUS(line_item.getSubtotal() ) );
      i++;
      table.changeSelection(i,0,false,false);

  // refreshTotal(table,0.00,0.00);
  
//  table.changeSelection(i,0, false,false);

}






*/


/*
// Deprecated as of 2/22/24 - No existing references in the Register.java source file.
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
		 //	table_manager.setData(table,i,5,"9.99"); // SUBTOTAL
		 //	table_manager.setData(table,i,5,"0.00"); // SUBTOTAL
		 //	table_manager.setData(table,i,5,"0.00"); // TAX
		 //	table_manager.setData(table,i,5,"0.00"); // DISCOUNT
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
	   

	   // table_manager.setData(table,i,5,table_manager.getTax(table,i) );
	   table.changeSelection((i+1),0,false,false);
	}

*/
