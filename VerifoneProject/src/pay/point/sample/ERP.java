package pay.point.sample;

public class ERP {

		 private String storeName;
		 private String storeAddress;
		 private String storeSecondAddress;
		 private String storePhoneNumber;
		 private String storeFaxNumber;
		 
		 public ERP(){
		     storeName 				= null;
		     storeAddress 			= null;
		     storeSecondAddress 	= null;
		     storePhoneNumber 		= null;
		     storeFaxNumber			= null;
		 }
		 
		 public void 		setStoreName(String store_name) { this.storeName = store_name; }
		 public String 		getStoreName() { return this.storeName; }
		 
		 public void 		setAddress(String address) { this.storeAddress = address; }
		 public String		getAddress() { return this.storeAddress; }
		 
		 public void 		setSecondAddress(String sa){ this.storeSecondAddress = sa; }
		 public String		getSecondAddress() { return this.storeSecondAddress; }
		 
		 public void 		setPhoneNumber(String pn) { this.storePhoneNumber = pn; }
		 public String		getPhoneNumber() { return this.storePhoneNumber; }
		 
		 public void 		setFaxNumber(String fn) { this.storeFaxNumber = fn; }
		 public String		getFaxNumber() { return this.storeFaxNumber; }
		 
		 public void 		setStoreAddress(String address){ this.storeAddress = address; }
		 public String 		getStoreAddress(){ return this.storeAddress; }

		 public void  		setStoreSecondAddress(String secondAddress){ this.storeSecondAddress = secondAddress; }
		 public String 		getStoreSecondAddress(){ return this.storeSecondAddress; }
		 
		 public void 		setStorePhoneNumber(String phoneNumber){ this.storePhoneNumber = phoneNumber; }
		 public String 		getStorePhoneNumber(){ return this.storePhoneNumber; }
		 
		 public void 		setStoreFaxNumber(String faxNumber){ this.storeFaxNumber = faxNumber; }
		 public String 		getStoreFaxNumber(){ return this.storeFaxNumber; }
		 
}
