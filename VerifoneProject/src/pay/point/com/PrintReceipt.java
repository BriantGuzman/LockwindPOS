import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import java.io.FileInputStream;
import javax.print.*;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;


//-------------------------------------------------------------------------------
//PRINT RECEIPT FILE
class PrintReceipt implements Printable {
    
    public int getIndex()
    {
        x = 0;
        file_manager = new APIFileManager("invoice_number.txt");
        x = Integer.parseInt( file_manager.getFirstLine() );
        file_manager.closeRead();
        return x;

    }
        //try{
        	// ./target/classes/lockwind/com/outbound_invoice/INV/invoice_number.txt
            
            
//            inputFile = new Scanner(file);
            
//            x = Integer.parseInt(inputFile.nextLine());
            //inputFile.close();

//        }catch(IOException e){}
    
    
    public void setIndex()
    {
         x = 0;
        // try{
        	file_manager = new APIFileManager("invoice_number.txt");
        	
        	try {
        	x = Integer.parseInt( file_manager.getFirstLine() );
        	x++;
            }
        	catch(java.util.NoSuchElementException e) {
        		System.out.println("PrintReceipt: Error: setIndex: no line found");
        	}
        	file_manager.writeLine(String.valueOf(x));
        	file_manager.closeWrite();
        	
            // File file = new File("invoice_number.txt");
            // Scanner inputFile = new Scanner(file);
            //x = Integer.parseInt(inputFile.nextLine());
            // inputFile.close();
//            FileWriter output = new FileWriter("invoice_number.txt");
  //          PrintWriter outputFile = new PrintWriter(output);
    //        outputFile.println(String.valueOf(x));
      //      outputFile.close();
        // }catch(IOException e){}
    }
    
    public PrintReceipt() { // default no-arg constructor 
    	
    }

    
    public PrintReceipt(String filename) { // constructor with filename parameter
		file_manager = new APIFileManager(filename);

		x = 0;

		//Print values for rawprint()
		res = "";
    	printServiceAttributeSet = null;
    	printServices = null;
    	printServiceAttributeSet = null;
    	printdata = null;
    	pservice = null;
    	job = null;
    	flavor = null;
    	doc = null;
    	aset = null;
    
    	
    	
    }
    
    
    public void setPrintServiceAttributeSet(String printerName, String conte) {
    	printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(printerName, null));

    }
    
//    public static String rawprint(String printerName, String conte) {
        public String rawprint(String printerName, String conte) {
    	        
        printServices = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet);
        if (printServices.length != 1) {
            return "Can't  select printer :" + printerName;
        }
        
/*
        byte[] printdata = conte.getBytes();
        PrintService pservice = printServices[0];
        DocPrintJob job = pservice.createPrintJob();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(printdata, flavor, null);
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
 */
        printdata = conte.getBytes();
        pservice = printServices[0];
        job = pservice.createPrintJob();
        flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        doc = new SimpleDoc(printdata, flavor, null);
        aset = new HashPrintRequestAttributeSet();

        try {
            job.print(doc, aset);
        } catch(Exception e){
            res = e.getMessage();
            
        }
        return res;
    }
    
    public int print(Graphics g, PageFormat pf, int page) throws
    PrinterException {
        
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
        
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        
        /* Now we perform our rendering */
        g.drawString("H", 1, 1);
        
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
    
	private APIFileManager file_manager;
	private int x;
    
    private String res;
    private PrintServiceAttributeSet printServiceAttributeSet;
    private PrintService printServices[];
    private byte[] printdata;
    private PrintService pservice;
    private DocPrintJob job;
    private DocFlavor flavor;
    private Doc doc;
    private PrintRequestAttributeSet aset;
    private PrintJobWatcher pjw;

    private String defaultPrinter;
    private PrintService service;
    private String invoice_directory;
    private String invoice_number;
    private FileInputStream in;
    private PrintRequestAttributeSet pras;
    
    private InputStream ff;
    private Doc docff;
    private DocPrintJob jobff;
    
    
    public void startPrint() {
    	
//    	String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();
    	
    	
    	defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();

//       String defaultPrinter = args[0];
       service = PrintServiceLookup.lookupDefaultPrintService();       
 	  invoice_directory = "./target/classes/lockwind/com/outbound_invoice/";
 	  
// 	  APIFileManager file_manager = new APIFileManager(invoice_directory + "invoice_number.txt");

 	  APIFileManager file_manager = new APIFileManager("invoice_number.txt");

// 	  File file = new File(invoice_directory + "invoice_number.txt");
 	  //Scanner inputFile = new Scanner(file);
 	  
 	  invoice_number = "";
 	  invoice_number = file_manager.getFirstLine();
 	  file_manager.closeRead();
 	  
//      FileInputStream in = new FileInputStream(new File( invoice_directory + "INV" + invoice_number + ".txt" ));

 	  try {
       in = new FileInputStream(new File( invoice_directory + "INV" + invoice_number + ".txt" ));
 	  }catch(FileNotFoundException e) {
 		  System.out.println("PrintReceipt: error: file not found");
 		  System.out.println(e.toString());
 	  }
       
       
       
//       FileInputStream in = new FileInputStream(new File("INV"+String.valueOf(getIndex())+".txt"));
       
       this.setIndex();
       
       
       pras = new HashPrintRequestAttributeSet();
       pras.add(new Copies(1));
       
       flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
       doc = new SimpleDoc(in, flavor, null);
       
       job = service.createPrintJob();
       pjw = new PrintJobWatcher(job);
       
       try {
       try {
       job.print(doc, pras);
       pjw.waitForDone();
       in.close();
       }catch(PrintException e ) {
    	   
  		  System.out.println("PrintReceipt: error: printException");
  		  System.out.println(e.toString());
   
       }}
       catch(IOException e) {
   		  System.out.println("PrintReceipt: error: printException");
   		  System.out.println(e.toString());
    	   
       }
       
       
       ff = new ByteArrayInputStream("\f".getBytes());
       docff = new SimpleDoc(ff, flavor, null);
       jobff = service.createPrintJob();
    
       pjw = new PrintJobWatcher(jobff);
       try { 
           jobff.print(docff, null);
    	   
    }catch(PrintException e ) {
		  System.out.println("PrintReceipt: error: printException");
		  System.out.println(e.toString());
    }
    pjw.waitForDone();
       
       HelloWorldPrinter test = new HelloWorldPrinter();
       PrinterJob jobb = PrinterJob.getPrinterJob();
       jobb.setPrintable(test);
       try{
           jobb.print();
       }
       catch(PrinterException e){}
    }
    
    public static void main(String[] args) throws PrintException, IOException {
        
    	PrintReceipt pr = null;
    	pr = new PrintReceipt("invoice_number.txt");
    	
    	pr.startPrint();
       
        
    }
    

    public static void main(String args) throws PrintException, IOException {
        
//      String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();

    	PrintReceipt pr = null;
    	pr = new PrintReceipt("invoice_number.txt");
    	int target_printer = 0;
      
      String defaultPrinter = args;
//      PrintService service = PrintServiceLookup.lookupDefaultPrintService();
      PrintService[] service = PrinterJob.lookupPrintServices();
      
      for(int i = 0; i < service.length; i++)
      {
    	  System.out.println("Printing List of all available current print services");
    	  if( service[i].getName().equalsIgnoreCase(args)) {
    		  target_printer = i;
    	  }
      }
	  System.out.println( "Printing to target printer: " + service[target_printer].getName() );
      
      
      
	  String invoice_directory = "./target/classes/lockwind/com/outbound_invoice/";
	  	
	  
	  File file = new File(invoice_directory + "invoice_number.txt");
	  Scanner inputFile = new Scanner(file);
	  
	  String invoice_number = "";
	  invoice_number = inputFile.nextLine();
	  
	  inputFile.close();
	  

      FileInputStream in = new FileInputStream(new File( invoice_directory + "INV" + invoice_number + ".txt" ));

      
      
      
//      FileInputStream in = new FileInputStream(new File("INV"+String.valueOf(getIndex())+".txt"));
      
      pr.setIndex();
      PrintRequestAttributeSet  pras = new HashPrintRequestAttributeSet();
      pras.add(new Copies(1));
      
      DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
      Doc doc = new SimpleDoc(in, flavor, null);
      
      DocPrintJob job = service[target_printer].createPrintJob();
      PrintJobWatcher pjw = new PrintJobWatcher(job);
      job.print(doc, pras);
      pjw.waitForDone();
      in.close();
      
      InputStream ff = new ByteArrayInputStream("\f".getBytes());
      Doc docff = new SimpleDoc(ff, flavor, null);
      DocPrintJob jobff = service[target_printer].createPrintJob();
      pjw = new PrintJobWatcher(jobff);
      jobff.print(docff, null);
      pjw.waitForDone();
      
      HelloWorldPrinter test = new HelloWorldPrinter();
      PrinterJob jobb = PrinterJob.getPrinterJob();
      jobb.setPrintable(test);
      try{
          jobb.print();
      }
      catch(PrinterException e){}
      
  }






public static void printToDisplayPrinter(String printerName, String value) throws PrintException, IOException {
        
//      String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();

	PrintReceipt pr = null;
	pr = new PrintReceipt("invoice_number.txt");
	
    	int target_printer = 0;
      
      String defaultPrinter = printerName;
//      PrintService service = PrintServiceLookup.lookupDefaultPrintService();
      PrintService[] service = PrinterJob.lookupPrintServices();
      
      for(int i = 0; i < service.length; i++)
      {
    	  System.out.println("Printing List of all available current print services");
    	  if( service[i].getName().equalsIgnoreCase(printerName)) {
    		  target_printer = i;
    	  }
      }
	  System.out.println( "Printing to target printer: " + service[target_printer].getName() );
      
      
      
	  String invoice_directory = "./target/classes/lockwind/com/outbound_invoice/";
	  	
	  
	  File file = new File(invoice_directory + "printToDisplayPrinter.txt");
	  
	  PrintWriter output_file = new PrintWriter(file);
	  
	  output_file.print(value);
	  
	  output_file.close();
	  
	  
	  Scanner inputFile = new Scanner(file);
	  
	  
	  value = inputFile.nextLine();
	  
	  inputFile.close();
	  

	  
      FileInputStream in = new FileInputStream( 
    		  new File( invoice_directory + "printToDisplayPrinter.txt" ));

      
      
      
//      FileInputStream in = new FileInputStream(new File("INV"+String.valueOf(getIndex())+".txt"));
      
      pr.setIndex();
      PrintRequestAttributeSet  pras = new HashPrintRequestAttributeSet();
      pras.add(new Copies(1));
      
      DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
      Doc doc = new SimpleDoc(in, flavor, null);
      
      DocPrintJob job = service[target_printer].createPrintJob();
      PrintJobWatcher pjw = new PrintJobWatcher(job);
      job.print(doc, pras);
      pjw.waitForDone();
      in.close();
      
      InputStream ff = new ByteArrayInputStream("\f".getBytes());
      Doc docff = new SimpleDoc(ff, flavor, null);
      DocPrintJob jobff = service[target_printer].createPrintJob();
      pjw = new PrintJobWatcher(jobff);
      jobff.print(docff, null);
      pjw.waitForDone();
      
      HelloWorldPrinter test = new HelloWorldPrinter();
      PrinterJob jobb = PrinterJob.getPrinterJob();
      jobb.setPrintable(test);
      try{
          jobb.print();
      }
      catch(PrinterException e){}
      
  }



















































}


