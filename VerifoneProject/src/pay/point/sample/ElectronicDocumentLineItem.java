package pay.point.sample;

class ElectronicDocumentLineItemManager { 
	private ElectronicDocumentLineItem head;
	private int index;
	
	public ElectronicDocumentLineItemManager() {
		head  = null;
		index = 0;
	}
	
	public void addLineItem(ElectronicDocumentLineItem item) {
        if (this.searchByID( (int)((item.getLineItemCount()) )) != null) {
            System.out.println("Item with ID " + item.getLineItemCount() + " already exists. Cannot add duplicate items.");
            return;
        }

        if (head == null) {
            head = item;
            index++;
        } else {
            ElectronicDocumentLineItem current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(item);
            index++;
        }
    }
	public void printLineItems() {
        ElectronicDocumentLineItem current = head;
        while (current != null) {
            System.out.println( current.toString() );
            current = current.getNext();
        }
    }
	public ElectronicDocumentLineItem searchByID(int searchID) {
        ElectronicDocumentLineItem current = head;
        while (current != null) {
            if (current.getLineItemCount() == searchID) {
                return current;
            }
            current = current.getNext();
        }
        return null; // Item not found
    }
	
}

public class ElectronicDocumentLineItem {

    //	private String GTIN;
        
        // Global Line Item Requirements
        private String upc; // column 0 - JTable at Register.java
        private double qty; // column 1 - JTable at Register.java
        private String category; // column 2 - JTable at Register.java
        private String description; // column 3 - JTable at Register.java
        private double price_retail; // column 4 - JTable at Register.java
        private double price_subtotal;  // column 5 - JTable at Register.java 
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
        private String tax_code;
        private double tax_rate;
        private String vat_code;
        private double vat_rate;
        
    
        // System calculated values, Cannot be set by user
        private double on_hand_inventory;
        
        // Must be included by the user
        private double discount;
        
        private ElectronicDocumentLineItem next;
        private ElectronicDocumentLineItem prev;
        
        public ElectronicDocumentLineItem() {
            
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
        public void setUPC(String upc) { this.upc = upc; }
        public String getUPC() { return this.upc; }
        
        public void setQTY(double qty) { this.qty = qty; }
        public double getQTY() { return this.qty; }
        
        public void setCategory(String category) { this.category = category; }
        public String getCategory() { return this.category; }

        public void setDescription(String description) { this.description = description; }
        public String getDescription() { return this.description; }
        
        public void setRetailPrice(double price_retail) { this.price_retail = price_retail; }
        public double getRetailPrice() { return this.price_retail; }
        
        public void setWholesalePrice(double price_wholesale) { this.price_wholesale = price_wholesale; }
        public double getWholesalePrice() { return this.price_wholesale; }
    
        public void setOnHand(double on_hand) { on_hand_value = on_hand; }
        public double getOnHand() { return on_hand_value; }
        
        public void setDiscount(double discount_value) { this.discount_value = discount_value; }
        public double getDiscount() { return this.discount_value; }
            
        public void setLineItemCount(int line_item_count) { this.line_item_count = line_item_count; }
        public double getLineItemCount() { return this.line_item_count; }
        
        
        // Tax Related Codes and Rates
        public void setTaxRate(double tax_rate) { this.tax_rate = tax_rate; }
        public double getTaxRate() { return this.tax_rate; }
        public void setTaxCode(String tax_code) { this.tax_code = tax_code; }
        public String getTaxCode() { return this.tax_code; }
        
        public void setSubtotal() { subtotal_value = qty * price_retail; }
        public double getSubtotal() { setSubtotal(); return subtotal_value; }
        
        public void setTaxes() { tax_value = this.getSubtotal() * this.getTaxRate(); }
        public double getTaxes() { setTaxes(); return tax_value; }
        
        public void setNext(ElectronicDocumentLineItem next) { this.next = next; }
        public ElectronicDocumentLineItem getNext() { return this.next; }
        
        public void setPrev(ElectronicDocumentLineItem prev) { this.prev = prev; }
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
        
    }
    