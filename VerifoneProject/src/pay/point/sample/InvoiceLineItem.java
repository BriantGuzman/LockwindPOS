package pay.point.sample;

public class InvoiceLineItem {

    //	private String GTIN;
        
        // Global Line Item Requirements
        private String upc;
        private double qty;
        private String description;
        private double price_retail;
        private double price_wholesale;
        private double subtotal_value;
        private double tax_value;
        private double discount_value;
        private double on_hand_value;
        
        // Tax Related codes, a product can have multiple tax codes and rates as well as vat codes and rates.
        // Right now this is just for NY and it is a fixed value instead of a list which it will become.
        // V2 of this concept is the class InvoiceLineItemTax which will create a node for each type of tax associated with an object.
        private String tax_code;
        private double tax_rate;
        private String vat_code;
        private double vat_rate;
        
        // Global Calculated Values per line item instance, Cannot be set by user
        private double price_subtotal;
    
        // System calculated values, Cannot be set by user
        private double on_hand_inventory;
        
        // Must be included by the user
        private double discount;
        
        private InvoiceLineItem next;
        private InvoiceLineItem prev;
        
        public InvoiceLineItem() {
            
        }
        
        public InvoiceLineItem( String upc, String qty, String description, String price_retail, String subtotal, String tax, String discount_value, String onhand_value) {
            super();
            this.setUPC(upc);
            this.setQTY(Double.parseDouble(qty));
            this.setDescription(description);
            this.setRetailPrice( Double.parseDouble(price_retail) );
            this.setTaxCode("NYC Sales Tax");
            this.setTaxRate( Double.parseDouble(tax));
            this.setDiscount( Double.parseDouble(discount_value));
            this.setOnHand( Double.parseDouble( onhand_value ));
            
        }
        
        
        // Global Line Items Requirements
        public void setUPC(String upc) {
            this.upc = upc;
        }
        public String getUPC() {
            return this.upc;
        }
        public void setQTY(double qty) {
            this.qty = qty;
        }
        public double getQTY() {
            return this.qty;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getDescription() {
            return this.description;
        }
        public void setRetailPrice(double price_retail) {
            this.price_retail = price_retail;
        }
        public double getRetailPrice() {
            return this.price_retail;
        }
        
        public void setWholesalePrice(double price_wholesale) {
            this.price_wholesale = price_wholesale;
        }
        public double getWholesalePrice() {
            return this.price_wholesale;
        }
    
        public void setOnHand(double on_hand)
        {
            on_hand_value = on_hand;
        }
        public double getOnHand()
        {
            return on_hand_value;
        }
        
        public void setDiscount(double discount_value)
        {
            this.discount_value = discount_value;
        }
        public double getDiscount()
        {
            return this.discount_value;
        }
            
        
        // Tax Related Codes and Rates
        public void setTaxRate(double tax_rate) {
            this.tax_rate = tax_rate;
        }
        public double getTaxRate() {
            return this.tax_rate;
        }
        public void setTaxCode(String tax_code) {
            this.tax_code = tax_code;
        }
        public String getTaxCode() {
            return this.tax_code;
        }
        
        
        
        public double getSubtotal() {
            return this.price_subtotal;
        }
        public double getTotalTaxes() {
            return this.getSubtotal() * this.getTaxRate();
        }
    
        
        
        public void setNext(InvoiceLineItem next) {
            this.next = next;
        }
        public InvoiceLineItem getNext() {
            return this.next;
        }
        
        public void setPrev(InvoiceLineItem prev) {
            this.prev = prev;
        }
        public InvoiceLineItem getPrev() {
            return this.prev;
        }
        
        
        
    }
    