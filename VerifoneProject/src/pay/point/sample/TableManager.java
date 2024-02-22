package pay.point.sample;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.io.File;

import java.util.HashMap;
import java.util.Map;

class ColumnMapper {
    private Map<String, Integer> columnMap;
    private int columnCount;

    public ColumnMapper() {
        columnMap = new HashMap<>();
        columnCount = 0;
    }

    public void addColumn(String columnName) {
        columnMap.put(columnName, columnCount);
        columnCount++;
    }
    public int getColumnCount() {
    	int temp = 0;
    	temp = columnCount;
    	return temp;
    }

    public int getColumnNumber(String columnName) {
        if (columnMap.containsKey(columnName)) {
            return columnMap.get(columnName);
        }
        return -1; // Column not found
    }

    public static void main(String[] args) {
        
    }
}


public class TableManager {

	private ProductManagementSystem product_management_system;
	private FormatManager format_manager;
	private ColumnMapper columnMapper;
	
	private String columnName;
	private int columnNumber;


	public TableManager() {

		  product_management_system   	= new ProductManagementSystem();
		  format_manager				= new FormatManager();
	}

	public TableManager(String[] columnName,int[] column_width) {

		this();
		ColumnMapper columnMapper		= new ColumnMapper();
		// {"UPC","QTY","CATEGORY","DESCRIPTION","PRICE","SUBTOTAL","TAX","DISCOUNT","ONHAND"};
		
		for(int i = 0; i < columnName.length; i++)
		{
			columnMapper.addColumn(columnName[i]);
			// Removed statement below to make the output smaller - 2/15/24
//			System.out.println("TableManager -> Adding Column to ColumnMapper:" + columnName[i] + " at " + columnMapper.getColumnNumber(columnName[i] ) );
		}

  }


	public int getColumnCount()
	{
		return columnMapper.getColumnCount();
	}


	public void setRetailerUUID(String uuid) {
		  product_management_system.setRetailerUUID(uuid);
	}
	public String getRetailerUUID() { 
		String temp = "";
		temp = product_management_system.getRetailerUUID();
		return temp;
	}

	public void addColumnNames(){ }
	
	public Object getData(JTable table,int row_index,int col_index)
	{return table.getModel().getValueAt(row_index,col_index);}

	public void setData(JTable table,int row_index, int col_index,String value)
	{table.getModel().setValueAt(value,row_index,col_index);}

	public void clearTable(JTable table){
		
		int i = 0;
		
		int columnSelected = 0;
		int columnCount = table.getColumnCount();

		while( i < table.getModel().getRowCount()){
		
			while(  columnSelected <  columnCount)
			{
				setData(table,i,columnSelected++,"");	
			}
			columnSelected = 0;
			i++;
		}

	}

/*	    	  
	    	  Process p = null;
	    	  p =  Runtime.getRuntime().exec("cmd /c echo                                         > COM1" ); }
	      catch(IOException eex){}
	*/	  
	
	
	/* 
	  public void enterGTIN(JTable table,String retailer_uuid){
	      
		System.out.println("@enterGTIN -> GTIN ENTERED - PROCESSING NOW");
	      if(table.isEditing()){table.getCellEditor().stopCellEditing();}
	      int i = 0;
	      int j = 0;
	      
	      i = table.getSelectedRow();
	      j = table.getSelectedColumn();
	      
	      String temp = getData(table,i,0).toString();

//	      fillRow( table, i,getData(table,i,0).toString(), retailer_uuid ); //  Set barcode

//		  setData( table,i,0,getData(table,i,0).toString() ); // UPC/GTIN
//		  setData( table,i,1,"1" ); // Quantity Column
//	      setData( table,i,5,getSubTotal(table,i)); // Subtotal
//	      setData(table,i,6,getTax(table,i) ); // Taxes Column
	      
		  try{
	    	  
	    	  ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "senddat.bat");
	    	  File dir = new File(".");
	    	  pb.directory(dir);
	    	  Process p = pb.start();
	    	  
//	  	  Process p =  Runtime.getRuntime().exec("cmd /c senddat.bat");
	      }catch(IOException exxe) {}

	      //updateOnHand(table);
	      System.out.println("Current Row" + i);
	      ++i;
	      System.out.println("Next Row" + i);
	      
	      table.changeSelection(i,0,false,false);
    	  

	  }
	  

	  */
	  public int getLastEmptyRow(JTable table){
	      int i = 0;
	          Object[] columnData = new Object[table.getRowCount()];  // One entry for each row
	          Object[] rowData = new Object [table.getRowCount()];
	          while ( i < table.getRowCount() && table.getValueAt(i, 0) != null ){  // Loop through the rows
	              // Record the 5th column value (index 4)
	              System.out.println("Shopping Cart:row:" + i + ";GTIN:"  + table.getValueAt(i, 0)  + ";") ;
	              i++;
	          }
	      System.out.println("NextEmptyRow:"+ i +";");
//	          System.out.println(Arrays.toString(columnData));
	      
	      return i;
	  }
	
	public void fillColumn(JTable table,int col,String value){
		for(int index = 0; index < 14;index++){
		setData(table,index,col,value);
		}}
	
	public void clearRow(JTable table, int r)
	{

	System.out.println("TableManager->clearRow() : ColumnCount = " + table.getColumnCount() );
	for(int i = 0; i < table.getColumnCount(); i++)
	{
		setData(table,r,i,"");
	}

    try{
  	  
  	  ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "senddat.bat");
  	  File dir = new File(".");
  	  pb.directory(dir);
  	  Process p = pb.start();
  	  
//	  Process p =  Runtime.getRuntime().exec("cmd /c senddat.bat");
    }catch(IOException exxe) {}

	
/*	  BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(p.getInputStream()));
		String s = "";
		
		while ((s = inputStream.readLine()) != null) {
			System.out.println(s);
		}
  */  
    } // close clearROw()
    
	
	
	public double getColumnTotal(JTable table, int col){
		int i = 0;
		double sum = 0.00;
		double x = 0.00;

		while(getData(table,i,0) != null && (!getData(table,i,0).toString().equals("")) ) {
		if(getData(table,i,col) == null || getData(table,i,col).toString().equals("")){x = 0.00;sum += x;}
		else{x = Double.parseDouble(getData(table,i,col).toString());sum += x;}
		i++;}

		return sum;
	}
	
	public void updateTotal(JTable table, int r){
		
//		columnNumber = columnMapper.getColumnNumber("QTY");
//			columnNumber = columnMapper.getColumnNumber("SUBTOTAL");

		if( (getData(table,r,1) != null) && ! getData(table,r,4).toString().equals("") ){
			setData(table,r,5,format_manager.formatDouble( getSubTotal(table,r)));
//			columnNumber = columnMapper.getColumnNumber("TAX");
//			setData(table,r,columnNumber,format_manager.formatDouble( getTax(table,r)));
		}}
	
	
	public String getSubTotal(JTable table,int r)
	{
	String p = "";
	String q = "";
	double return_value = 0.00;

	double qty = 0.00;
	double price = 0.00;
	
	
	if(getData(table,r,1).toString().equalsIgnoreCase("") || getData(table,r,4).toString().equalsIgnoreCase(""))
	{return_value = 0.00;
	JOptionPane.showMessageDialog(null,"QTY or Price is not a valid ");
	}
	else{
		qty 	= Double.parseDouble( getData(table,r,1).toString() );
		price 	= Double.parseDouble( getData(table,r,4).toString() );
		q = getData(table,r,1).toString();
		p = getData(table,r,4).toString();
		
		return_value = (Double.parseDouble(p) * Double.parseDouble(q) );
	}

	return format_manager.formatDoubleUS(return_value);
	}

	public String getTax(JTable table,int r)
	{
	double tax_rate = 0.08875;
	double return_value = 0.00;
	String q = "";
	String p = "";


	if(getData(table,r,1).toString().equalsIgnoreCase("") || getData(table,r,4).toString().equalsIgnoreCase(""))
	{return_value = 0.00;}
	else{

	q = getData(table,r,1).toString();
	p = getData(table,r,4).toString();

	return_value = ((Double.parseDouble(p) * tax_rate ) )* Double.parseDouble(q) ;
	}
	return format_manager.formatDoubleUS(return_value);
	}
	
	
	
	public String setRowDiscount(JTable table, int r)
	{
	String d = "";
	double rd = 0.00;
	double dd = 0.00;

	if(getData(table,r,6) != null || ! getData(table,r,6).toString().equalsIgnoreCase("") )
	{
	dd = Double.parseDouble(getData(table,r,6).toString()) / 100;
	rd = ( Double.parseDouble(getData(table,r,4).toString()) + Double.parseDouble(getData(table,r,5).toString()) ) ;
	rd = rd * dd;
	d = format_manager.formatDoubleUS(rd);
	}
	else { d = "0.00"; }
	return d;
	}
	
	
	public void applyDiscount(JTable table,int i,int j,String input_value) {
		

		try { 
			
		     if (Double.parseDouble(input_value) > 0)
		     {
		    	 
			Double.parseDouble(input_value);

		      double discount = 0.00;
		      double discountPrice = 0.00;
		      double st = 0.00;

		      st = Double.parseDouble(getData(table,i,4).toString()) + Double.parseDouble(getData(table,i,5).toString());
		      
		      discount = (Double.parseDouble( input_value ) / 100)
		      *(Double.parseDouble(getData(table,i,4).toString())
		        +Double.parseDouble(getData(table,i,5).toString()));
		      
		      discountPrice = (st-discount);
		      
		      setData(table,i,j,format_manager.formatDoubleUS(discount));
		     }
		     else {
		    	 JOptionPane.showMessageDialog(null,"Discount cannot be negative");
			     setData(table,i,j,"");
		    	 
		     }



		}
		catch(NumberFormatException e) {
	    	 JOptionPane.showMessageDialog(null,"Discount is invalid");
		     setData(table,i,j,"");
			
		}

	}
	
	public void fillRow(JTable table,int r,String u,String retailer_uuid){

		  String productInfo = "";
		  StringTokenizer str = null;
		  String da = "";
		  int i = 0;
		  int j = 0;
		  try {
			  product_management_system.setRetailerUUID(retailer_uuid);			  
			  productInfo = product_management_system.getProductInfoAPI(u);

		  }catch(Exception eex){}
		  
		//  productInfo = product_management_system.getProductInfo(u);
		JOptionPane.showMessageDialog(null,"PRODUCT INFO:::: " + productInfo);
//		  System.out.println("PRODUCT INFO:::: " + productInfo + "\n\n");
		  
		  if(productInfo.equalsIgnoreCase("") == true) { }
		  else{
		  str = new StringTokenizer(productInfo,",");
		      if(str.hasMoreTokens()){da = str.nextToken();
				this.setData(table,r,j++,str.nextToken());
//		          if(u.equalsIgnoreCase("10")){} else{ this.setData(table,r,3,str.nextToken()); }
		      }
		  }
		}
} // close table manager
