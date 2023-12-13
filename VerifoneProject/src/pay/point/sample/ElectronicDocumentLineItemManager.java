package pay.point.sample;

public class ElectronicDocumentLineItemManager { 
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
