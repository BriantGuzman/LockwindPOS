

package pay.point.sample;

import static java.text.DateFormat.FULL;
import static java.text.DateFormat.LONG;
import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.SHORT;
import static java.util.Locale.FRANCE;
import static java.util.Locale.GERMANY;
import static java.util.Locale.UK;
import static java.util.Locale.US;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;

//------------------------------------------------------------------CLASS QTY
class QTY {
    
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    
    private JFrame frame;
    private SpringLayout springLayout;
    
    private JPanel panel;
    
    private JTextField UPCText;
    private JTextField nameText;
    private JTextField retailText;
    private JTextField costText;
    
    private JLabel UPCLabel;
    private JLabel nameLabel;
    private JLabel retailLabel;
    private JLabel costLabel;
    
    private JButton update;
    private JButton clear;
    private JButton exit;
    private JButton done;
    
    private TableManager table_manager;
    
    private ClientInvoiceReport http;
    
    
    public QTY(){
        
        screenSize          =   null;
        screenWidth         =   0;
        screenHeight        =   0;
        frame               =   null;
        springLayout        =   null;
        panel               =   null;
        
        UPCText             =   null;
        nameText            =   null;
        costText            =   null;
        retailText          =   null;
        
        UPCLabel            =   null;
        nameLabel           =   null;
        costLabel           =   null;
        retailLabel         =   null;
        
        clear               =   null;
        update              =   null;
        exit                =   null;
        
        table_manager		=	null;
        
        table_manager 		= 	new TableManager();
        http                = 	new ClientInvoiceReport();

        
    }
    
    public void setComponentDefaultValues()
    {
        setLayout();
        
        panel           =     new JPanel();
        UPCText         =     new JTextField(15);
        nameText        =     new JTextField(15);
        retailText      =     new JTextField(15);
        costText        =     new JTextField(15);
        
        panel.setLayout(getLayout());
        frame.add(panel);
        
        UPCLabel        =    new JLabel("Code");
        nameLabel       =    new JLabel("Description");
        retailLabel     =    new JLabel("Current Quantity");
        costLabel       =    new JLabel("Add Quantity");
        
        update          =    new JButton("UPDATE");
        clear           =    new JButton("CLEAR");
        exit            =     new JButton("EXIT");
        
        
        springLayout.putConstraint(springLayout.NORTH, UPCLabel,30,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, nameLabel,60,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, retailLabel,90,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, costLabel,120,springLayout.NORTH, panel);

        springLayout.putConstraint(springLayout.NORTH, UPCText,25,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, nameText,55,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, retailText,85,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, costText,115,springLayout.NORTH, panel);

        springLayout.putConstraint(springLayout.NORTH, update,150,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, clear,150,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, exit,150,springLayout.NORTH, panel);
        
        springLayout.putConstraint(springLayout.WEST, UPCText,140,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, nameText,140,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, retailText,140,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, costText,140,springLayout.WEST, panel);
        
        springLayout.putConstraint(springLayout.WEST, UPCLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, nameLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, retailLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, costLabel,20,springLayout.WEST, panel);
        
        springLayout.putConstraint(springLayout.WEST, update,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, clear,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, exit,200,springLayout.WEST, panel);
        
        panel.add(UPCText);
        panel.add(nameText);
        panel.add(retailText);
        panel.add(costText);
        
        panel.add(UPCLabel);
        panel.add(nameLabel);
        panel.add(retailLabel);
        panel.add(costLabel);
        
        retailText.setEditable(false);
        
        panel.add(clear);
        panel.add(update);
        panel.add(exit);
        
        UPCText.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if( ! UPCText.getText().equals("")  )
                    // nameText.setText(getProductInfo( UPCText.getText() ));
                retailText.setText(String.valueOf( getCurrentQty(UPCText.getText()))  );
                
            }});
        
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                UPCText.setText("");
                nameText.setText("");
                retailText.setText("");
                costText.setText("");
                UPCText.requestFocus();}});
        
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                try{
                    DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    
                    Calendar cal = Calendar.getInstance();
                    System.out.println(sdf.format(cal.getTime()));
                    
                    FileWriter file = new FileWriter("./target/classes/lockwind/com/inventory_management/qty.txt",true);
                    
                    PrintWriter outputFile = new PrintWriter(file);
                    
                    String a = "";
                    String b = "";
                    String c = "";
                    String d = "";
                    
                    a = UPCText.getText();
                    b = nameText.getText();
                    c = retailText.getText();
                    d = costText.getText();
                    
                    outputFile.println(a+","+d+","+c+","+b+","+sdf.format(cal.getTime())+",\n");
                    
                    outputFile.close();
                    JOptionPane.showMessageDialog(null,("Successfully added:\n"+a+"\n"+b+"\n"+c+"\n"+d));
                    
                    UPCText.setText("");
                    nameText.setText("");
                    retailText.setText("");
                    costText.setText("");
                    UPCText.requestFocus();}
                catch(IOException exx){}}});
        
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));}});}
    
    public void setScreenSize(){
        
        screenSize      =    Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth     =    (int)(screenSize.getWidth());
        screenHeight    =     (int)(screenSize.getHeight());}
    
    public int         getScreenWidth(){return screenWidth;}
    public int         getScreenHeight(){return screenHeight;}
    
    public void         setLayout(){springLayout = new SpringLayout();}
    public SpringLayout     getLayout(){return springLayout;}
    
    public void setFrame()
    {
        frame         =     new JFrame();
        frame.setPreferredSize(new Dimension(/*getScreenWidth(),getScreenHeight()*/400,230));
        frame.setTitle("NEW QUANTITY");
    }
    
    public int getCurrentQty(String GTIN){
        
        String target   = "";
        int qty_on_hand = 0;

        String line     = "";
        StringTokenizer str = null;
        File file       = null;
        Scanner inputFile = null;
        
        try{
            
            file        = new File("./target/classes/lockwind/com/inventory_management/qty.txt");
            inputFile   = new Scanner(file);
            
            while(inputFile.hasNextLine()){

                line    = inputFile.nextLine();

                str = new StringTokenizer(line,",");
                
                if(str.hasMoreTokens()){
                    
                    target = str.nextToken();
                    
                    if(target.equalsIgnoreCase(GTIN)) { qty_on_hand += Integer.parseInt(str.nextToken()); }
                    else{}
            }
        }
            inputFile.close();

        }catch(IOException e){}

        return qty_on_hand;
    }
    
    public int getQtySold( String GTIN){

        int qty_sold = 0;

        try {
        	System.out.println("QTY->getQtySold");
            File file = null;
            Scanner input = null;

            file        = new File("./target/classes/lockwind/com/inventory_management/qtySold.txt");

            input = new Scanner(file);
            
            String line = "";
            String line_gtin = "";
            String QTY = "";
            StringTokenizer str = null;

            while(input.hasNextLine()){
            
                line = input.nextLine();
                str = new StringTokenizer(line,",");
                line_gtin = str.nextToken();
                
                if(line_gtin.equalsIgnoreCase(GTIN))
                {
                    QTY = str.nextToken();
                    qty_sold += Integer.parseInt(QTY);
                }
            }
            input.close();
        }catch(IOException e) { }
        return qty_sold;
    }
    
    
    public void saveProductSold(JTable table,String client_id){

    	try{
    	FileWriter file = null;
    	PrintWriter outputFile = null;
    	String da = "";
    	int invoiceNumber = 0;

    	file = new FileWriter("./target/classes/lockwind/com/inventory_management/qtySold.txt",true);
    	outputFile = new PrintWriter(file);

    	//invoiceNumber = getIndex();

    	  try {
    	      invoiceNumber = Integer.parseInt(http.getCurrentInvoiceNumber(client_id));
    	  }catch(Exception e){}


    	Date today;					
    	DateFormat fmt = null;
    	today 				= 			      new Date();
    	Locale[] 	locale 		= 	{US,UK,GERMANY,FRANCE}		;
    	int[] 		styles 		= 	{FULL,LONG,MEDIUM,SHORT}	;
    	String[] 	styleNames 	= 	{"FULL","LONG","MEDIUM","SHORT"};
    	fmt 				= 	null				;
    	fmt = 		DateFormat.getDateInstance	  (styles[3], locale[0]);
    	System.out.println			("\t " + fmt.format(today))	;
    	SimpleDateFormat simpDate						;
    	simpDate 			=     new SimpleDateFormat("hh:mm:ss a");

    	int in = 0;

    	table_manager  = new TableManager();
    	
    	
    	while(table_manager.getData(table,in,0) != null && (!table_manager.getData(table,in,0).toString().equals("")) )
    	{
    	  
    	if(table_manager.getData(table,in,0)!= null){da = table_manager.getData(table,in,0).toString()+",";}
    	if(table_manager.getData(table,in,1)!= null){da = da + table_manager.getData(table,in,1).toString()+",";}
    	if(table_manager.getData(table,in,3)!= null){da = da + table_manager.getData(table,in,3).toString()+",";}
    	da = da + String.valueOf(invoiceNumber)+",";
    	da = da + fmt.format(today) + "," + simpDate.format(today)+",";
    	outputFile.println(da);
    	da = "";in++;}
    	
    	outputFile.close();
    	}
    	catch(IOException e){}
    	}
    
    
    
    
    
    
    
    
    public String getProductInfo(String upc){
        
        String da = "";
        String line = "";
        String productInfo = "";
        StringTokenizer str = null;
        
        File file = null;
        Scanner inputFile = null;
        
        try{
            
            file       = new File("datum.csv");
            inputFile = new Scanner(file);
            
            
            while(inputFile.hasNextLine()){
                
                line       = inputFile.nextLine();
                
                str       = new StringTokenizer(line,",");
                
                if(str.hasMoreTokens()){
                    da       = str.nextToken();
                    
                    if(da.equalsIgnoreCase(upc)){productInfo = line;}
                    else{
                        productInfo = "GTIN_NOT_FOUND";
                    }
                    str.nextToken();
                    str.nextToken();
                    str.nextToken();
                    str.nextToken();}}}
        
        catch(IOException e){}
        
        str       = new StringTokenizer(productInfo,",");
        
        if(str.hasMoreTokens()){
            str.nextToken();    productInfo = str.nextToken();}
        return productInfo;
    }
    
    public void finishFrame(){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);}
    
    public QTY(boolean gui) {
    	
    	if(gui == true)
    	{
            QTY test = new QTY();
            test.setScreenSize();
            test.setFrame();
            test.setComponentDefaultValues();
            test.finishFrame();
    		
    	}
    	else {
            QTY test = new QTY(); }
    	}

    
    public static void main(String[] args){
    	System.out.println("System Log: Started Class: QTY");
}//------------------------------------------------------------------CLASS QTY END
}