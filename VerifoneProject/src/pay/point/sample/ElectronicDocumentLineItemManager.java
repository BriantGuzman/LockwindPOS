//Last Update: 12/13/2023
package pay.point.sample;

public class ElectronicDocumentLineItemManager { 
	
	private ElectronicDocumentLineItem head;
	private int line_item_count; // 
	private int index; // Index used for looping in class functions.
	
	public ElectronicDocumentLineItemManager() {
		head  = null;
		line_item_count = 0;
	}
	
	
	public void addLineItem(ElectronicDocumentLineItem item) {
        index = 0;
		
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
            line_item_count++;
            index++;
        }
    }
	public ElectronicDocumentLineItem getLineItem(int index) {
		 
		ElectronicDocumentLineItem temp = head;
		
		index = index - 1;
		for( int i = 0; i <= index; i++) {
			temp = temp.getNext();
		}
		return temp;
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
