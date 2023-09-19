package pay.point.sample;

public class Printer {
	 
	  private String printerCode;
	  private String printerMake;
	  private String printerModel;

	  private String printerName;
	  
	  private Printer next;
	  
	  public Printer(){
	      
	      printerCode = "";
	      printerMake = "";
	      printerModel = "";
	      printerName = "";

	      next = null;
	      
	  }
	  public Printer(String printerCode,String printerMake, String printerModel,String printerName){
	      
		  this.printerCode = printerCode;
		  this.printerMake = printerMake;
		  this.printerModel = printerModel;
		  this.printerName = printerName;
	      
	  }
	  
	  
	  public String toString() {
		  
		  String temp = "";
		  
		  temp = printerCode + " " + printerModel + " " + printerMake + " " + printerName;
	  
		  return temp;
	  }

	  public void setPrinterCode(String code) {
		  printerCode = code;
	  }
	  public String getPrinterCode()
	  {
		  return printerCode;
	  }
	  public void setPrinterMake(String make) {
		  
		  printerMake = make;
	  }
	  public String getPrinterMake() {
		  return printerMake;
	  }
	  public void setPrinterModel(String model) {
		  printerModel = model;
	  }
	  public String getPrinterModel() {
		  return printerModel;
	  }
	  public void setPrinterName(String printerName) {
		  this.printerName = printerName;
	  }
	  public String getPrinterName() {
		  return printerName;
	  }
	  
	  
	  
	  
	  public void setNext(Printer c)
	  {
	      next = c;
	  }
	  public Printer getNext()
	  {
	      return next;
	  }
	} // close class:: Customer