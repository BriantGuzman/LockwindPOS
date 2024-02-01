//Last Update: 12/13/2023

package pay.point.sample;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.IOException;

public class ElectronicDocumentLineItem {

    //	private String GTIN;
        
        // Global Line Item Requirements
        private String upc; // column 0 - JTable at Register.java
        private double qty; // column 1 - JTable at Register.java
        private String category; // column 2 - JTable at Register.java
        private String description; // column 3 - JTable at Register.java
        private double price_retail; // column 4 - JTable at Register.java
        // Global Calculated Values per line item instance, Cannot be set by user

        private double subtotal_value;   // column 6 - JTable at Register.java      
        private double tax_value; // column 7 - JTable at Register.java
        private double discount_value; // column 8 - JTable at Register.java
        
        private double on_hand_value; // column 9 - JTable at Register.java
        private int line_item_count; // column 10 - JTable at Register.java
        
        // Tax Related codes, a product can have multiple tax codes and rates as well as vat codes and rates.
        // Right now this is just for NY and it is a fixed value instead of a list which it will become.
        // V2 of this concept is the class InvoiceLineItemTax which will create a node for each type of tax associated with an object.

        private double price_wholesale; 
        private String tax_code; // can be used per line item. also the same as vat_code
        private double tax_rate; // can be used per line item. also the same as vat_rate

        private String vat_transferred_id;
        private String vat_transferred_type_code; // code that defines it.
        private String vat_transferred_code;
        private double vat_transferred_value;

        private String vat_retained_id;
        private String vat_retained_type_code;
        private String vat_retained_code;
        private double vat_retained_value;
        
        // System calculated values, Cannot be set by user
        private double on_hand_inventory;
        
        
        private ElectronicDocumentLineItem next;
        private ElectronicDocumentLineItem prev;
        
        
        // --
        private String date;
        private String store;
        private String store_sold_from;
        private String serial_number;
        private String customer_name;
        // private String description; --> located above
        private String receipt_number;
        private String type;
        // private String barcode; // Same as UPC located above
        private String style;
        private String alt_style;
        private String attribute1;
        private String attribute2;
        private String attribute_alternate_1;
        private String attribute_alternate_2;
        private String size;
        private String quantity_sold;
        private String total_price;
        private String reg_markdown;
        private String bo_markdown;
        private String clerk_name;
        private String salesperson_name;
        private String manager_name;
        private String quantity_on_hand;
        private String quantity_on_order;
        private String spoo;
        private String markdown_percent;
        private String bo_markdown_percent;
        private String comm_date;
        private String ext_rental_days;
        private String department;
        private String department_type;
        private String subtype1;
        private String subtype2;
        private String subtype3;

        /*
        public ElectronicDocumentLineItem(String date, String store, String store_sold_from, String serial_number, String customer_name, String description, String receipt_number,
        		String type, String style, String alt_style, String attribute1, String attribute2, String attribute_alternate_1, String attribute_alternate_2,
        		String size, String quantity_sold, String total_price, String quantity_sold, String total_price, String reg_markdown, String bo_markdown,
        		String clerk_name, String salesperson_name, String manager_name, String quantity_on_hand, String quantity_on_order, String spoo,
        		String markdown_percent, String bo_markdown_percent, String comm_date, String ext_rental_days, String department, String department_type, 
        		String subtype1, String subtype2, String subtype3
        		) {
            	
        }
        */
        
        public ElectronicDocumentLineItem(String line_item_file_name,int row_number) {
            // This method should load items sold from the CSV sales report
        	// We are using these files to analyze the data and train the AI to make reccomendations.
        	try { 
            	File file = new File("Items_Sold_01012023.csv");
            	Scanner input_file = new Scanner(file);
            	
            	StringTokenizer str = null;
            	
            	int k = 0;
            	
            	for(int i = 0; i < 30; i++) 
            	{
//            		System.out.println(input_file.nextLine() );
                	str = new StringTokenizer(input_file.nextLine(),",");
            		for(int j = 0; j <= str.countTokens(); j++)
            		{
                		System.out.println ( k++  + " " + str.nextToken()  + " ");            			
            		}
            		System.out.println();
            		k = 0;
            	}

            	input_file.close();

        		}catch(Exception e)
            	{
            		System.out.println(e.toString() );
            	}
        	
        }
        
        public ElectronicDocumentLineItem( String upc, String qty, String category, String description, String price_retail, String subtotal, String tax, String discount_value, String onhand_value, String line_item_count) {
            super();
            this.setUPC(upc);
            this.setQTY(Double.parseDouble(qty));
            this.setCategory(category);
            
            this.setDescription(description);
            this.setRetailPrice( Double.parseDouble(price_retail) );
            this.setTaxCode("NYC Sales Tax");
            this.setTaxRate( Double.parseDouble(tax));
            this.setDiscount( Double.parseDouble(discount_value));
            this.setOnHand( Double.parseDouble( onhand_value ));
            this.setLineItemCount(Integer.parseInt( line_item_count));
            
        }
        
        
        // Global Line Items Requirements
        public void 	setUPC(String upc) { this.upc = upc; }
        public String 	getUPC() { return this.upc; }
        
        public void 	setQTY(double qty) { this.qty = qty; }
        public double 	getQTY() { return this.qty; }
        
        public void 	setCategory(String category) { this.category = category; }
        public String 	getCategory() { return this.category; }

        public void 	setDescription(String description) { this.description = description; }
        public String 	getDescription() { return this.description; }
        
        public void 	setRetailPrice(double price_retail) { this.price_retail = price_retail; }
        public double 	getRetailPrice() { return this.price_retail; }

        public void 	setWholesalePrice(double price_wholesale) { this.price_wholesale = price_wholesale; }
        public double 	getWholesalePrice() { return this.price_wholesale; }
    
        public void 	setOnHand(double on_hand) { on_hand_value = on_hand; }
        public double 	getOnHand() { return on_hand_value; }
        
        public void 	setDiscount(double discount_value) { this.discount_value = discount_value; }
        public double 	getDiscount() { return this.discount_value; }
            
        public void 	setLineItemCount(int line_item_count) { this.line_item_count = line_item_count; }
        public double 	getLineItemCount() { return this.line_item_count; }
        
        
        // Tax Related Codes and Rates
        public void 	setTaxRate(double tax_rate) { this.tax_rate = tax_rate; }
        public double 	getTaxRate() { return this.tax_rate; }
        
        public void 	setTaxCode(String tax_code) { this.tax_code = tax_code; }
        public String 	getTaxCode() { return this.tax_code; }
        
        public void 	setTaxExempt() { tax_rate = 0.00; tax_value = 0.00;}
        public void 	removeTaxExempt() { }
        
        public void 	setTaxes() { tax_value = this.getSubtotal() * this.getTaxRate(); }
        public double 	getTaxes() { setTaxes(); return tax_value; }
        
        public void 	setSubtotal() { subtotal_value = qty * price_retail; }
        public double 	getSubtotal() { setSubtotal(); return subtotal_value; }
        
        
        public void 	setVATTransferredID(String vat_transferred_id) { this.vat_transferred_id = vat_transferred_id; }
        public String 	getVATTransferredID() { return this.vat_transferred_id; }

        public void 	setVATTransferredTypeCode(String vat_transferred_type_code) { this.vat_transferred_type_code = vat_transferred_type_code; }
        public String 	getVATTransferredTypeCode() { return this.vat_transferred_type_code; }

        public void 	setVATTransferredCode(String vat_transferred_code) { this.vat_transferred_code = vat_transferred_code; }
        public String 	getVATTransferredCode() { return this.vat_transferred_code; }

        public void 	setVATTransferredValue(double vat_transferred_value) { this.vat_transferred_value = vat_transferred_value; }
        public double 	getVATTransferredValue() { return this.vat_transferred_value; }


        public void 	setVATRetainedID(String vat_retained_id) { this.vat_retained_id = vat_retained_id; }
        public String 	getVATRetainedID() { return this.vat_retained_id; }

        public void 	setVATRetainedTypeCode(String vat_retained_type_code) { this.vat_retained_type_code = vat_retained_type_code; }
        public String 	getVATRetainedTypeCode() { return this.vat_retained_type_code; }

        public void 	setVATRetainedCode(String vat_retained_code) { this.vat_retained_code = vat_retained_code; }
        public String 	getVATRetainedCode() { return this.vat_retained_code; }

        public void 	setVATRetainedValue(double vat_retained_value) { this.vat_retained_value = vat_retained_value; }
        public double 	getVATRetainedValue() { return this.vat_retained_value; }

        
        
        public void 	setNext(ElectronicDocumentLineItem next) { this.next = next; }
        public ElectronicDocumentLineItem getNext() { return this.next; }
        
        public void 	setPrev(ElectronicDocumentLineItem prev) { this.prev = prev; }
        public ElectronicDocumentLineItem getPrev() { return this.prev; }
        
        
        
        public String toString() {
        	String temp = "";
        	
        	temp = "ElectronicDocument Line Item = " + 
        	
        	this.getUPC () 			+ " " + this.getQTY() 			+ " " + this.getCategory()	+ " " + 
        	this.getDescription() 	+ " " + this.getRetailPrice() 	+ " " + this.getSubtotal()  + " " + 
        	this.getTaxes() + " " 	+ " " + this.getDiscount() 		+ " " + this.getOnHand() 	+ " " + 
        	this.getLineItemCount();
        	
        	return temp;
        }
        
        
        public static void main(String[] args)
        {
        	
        	// Create a demo main method to start printing the line items in the sales report exported by Celerant System. -- 1/30/24
        	ElectronicDocumentLineItem demo = new ElectronicDocumentLineItem();
        }
        
    }
    