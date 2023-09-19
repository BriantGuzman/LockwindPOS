package pay.point.sample;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;


import static java.text.DateFormat.FULL;
import static java.text.DateFormat.LONG;
import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.SHORT;
import static java.util.Locale.FRANCE;
import static java.util.Locale.GERMANY;
import static java.util.Locale.UK;
import static java.util.Locale.US;

//------------------------------------------------------------------CLASS PRINT


//---------------------------------------------------------------CLASS NEW STOCK
class NewStock implements Runnable {
    
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
    
    private Thread go;
    
    public NewStock()
    {
        screenSize = null;
        screenWidth = 0;
        screenHeight = 0;
        frame = null;
        springLayout = null;
        panel = null;
        
        UPCText = null;
        nameText = null;
        costText = null;
        retailText = null;
        
        UPCLabel = null;
        nameLabel = null;
        costLabel = null;
        retailLabel = null;
        
        clear = null;
        update = null;
        exit = null;
        
        System.out.println("@NewStock()");
        
        go = null;
        
        if(go == null)
        {
        	go = new Thread(this);
        	go.start();
        }
    }
    
    public NewStock(String GTIN){
        this();
        NewStock test = new NewStock();
        test.setScreenSize();
        test.setFrame();
        test.setComponentDefaultValues(GTIN);
        test.finishFrame();
        
    }
    public void setScreenSize()
    {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth =  (int)(screenSize.getWidth());
        screenHeight = (int)(screenSize.getHeight());
    }
    public int getScreenWidth()
    {
        return screenWidth;
    }
    public int getScreenHeight()
    {
        return screenHeight;
    }
    
    public void setLayout()
    {
        springLayout = new SpringLayout();
    }
    public SpringLayout getLayout()
    {
        return springLayout;
    }
    
    public void setFrame()
    {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(/*getScreenWidth(),getScreenHeight()*/400,230));
        frame.setTitle("NEW STOCK");
    }
    
    public void setComponentDefaultValues(String GTIN)
    {
        setLayout();
        
        panel = new JPanel();
        panel.setLayout(getLayout());
        frame.add(panel);
        
        UPCText = new JTextField(20);
        nameText = new JTextField(20);
        retailText = new JTextField(20);
        costText = new JTextField(20);
        
        UPCLabel = new JLabel("Code");
        nameLabel = new JLabel("Description");
        retailLabel = new JLabel("Retail");
        costLabel = new JLabel("Cost");
        
        update = new JButton("UPDATE");
        clear = new JButton("CLEAR");
        exit = new JButton("EXIT");
        
        //NORTHERN COORDINATES
        springLayout.putConstraint(springLayout.NORTH, UPCText,30,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, nameText,60,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, retailText,90,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, costText,120,springLayout.NORTH, panel);
        
        springLayout.putConstraint(springLayout.NORTH, UPCLabel,30,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, nameLabel,60,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, retailLabel,90,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, costLabel,120,springLayout.NORTH, panel);
        
        springLayout.putConstraint(springLayout.NORTH, update,150,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, clear,150,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, exit,150,springLayout.NORTH, panel);
        
        springLayout.putConstraint(springLayout.WEST, UPCText,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, nameText,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, retailText,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, costText,120,springLayout.WEST, panel);
        
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
        
        panel.add(clear);
        panel.add(update);
        panel.add(exit);
        
        
        if(! GTIN.equalsIgnoreCase(""))
        {
            UPCText.setText(GTIN);
            nameText.requestFocus();
        }
        else{
        }
        
        
        
        //ACTION LISTENERS ------------------------------------------------------------
        
        UPCText.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                try{
                    String u = "";
                    u = UPCText.getText();
                    String line = "";
                    String target = "";
                    String first = "";
                    StringTokenizer str = null;
                    
                    File file = new File("target/classes/lockwind/com/datum.csv");
                    Scanner inputFile = new Scanner(file);
                    
                    while(inputFile.hasNextLine()){
                        line = inputFile.nextLine();
                        str =  new StringTokenizer(line,",");
                        
                        while(str.hasMoreTokens())
                        {first = str.nextToken();
                            
                            if(str.hasMoreTokens() && first.equalsIgnoreCase(u)){target = line;}}}
                    
                    if(target != null || ! target.equalsIgnoreCase("")){
                        System.out.println(target);
                        str = new StringTokenizer(target,",");
                        
                        if(str.hasMoreTokens()){first = str.nextToken();
                            if(first.equalsIgnoreCase(u)){
                                nameText.setText(str.nextToken());
                                retailText.setText(str.nextToken());
                                costText.setText(str.nextToken());}
                            else{
                                nameText.setText("");
                                retailText.setText("");
                                costText.setText("");
                            }}}
                    
                    if(target == null || target.equalsIgnoreCase("")){
                        nameText.setText("");
                        retailText.setText("");
                        costText.setText("");
                    }}
                catch(IOException exxx){}}});
        
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                UPCText.setText("");
                nameText.setText("");
                retailText.setText("");
                costText.setText("");
                UPCText.requestFocus();
            }});
        
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                
                //BEGIN UPDATE OF NEW STOCK
                try{
                    String a = "";
                    String b = "";
                    String c = "";
                    String d = "";
                    
                    a = UPCText.getText();
                    b = nameText.getText();
                    c = retailText.getText();
                    d = costText.getText();
                    
                    if(a.equalsIgnoreCase("")  || a == null || b.equalsIgnoreCase("")  || b == null || c.equalsIgnoreCase("")  || c == null || d.equalsIgnoreCase("")  || d == null)
                    {
                        JOptionPane.showMessageDialog(null,"Please fill all required fields");
                    }
                    else
                    {
                        
                        
                        FileWriter file = new FileWriter("target/classes/lockwind/com/datum.csv",true);
                        PrintWriter outputFile = new PrintWriter(file);
                        
                        
                        //    outputFile.println(a+","+b+","+c+","+d);
                        
                        Locale[]     locale         =     {US,UK,GERMANY,FRANCE}        ;
                        int[]         styles         =     {FULL,LONG,MEDIUM,SHORT}    ;
                        
                        DateFormat fmt                 =     null                ;
                        fmt =         DateFormat.getDateInstance      (styles[3], locale[0]);
                        
                        
                        
                        SimpleDateFormat simpDate                        ;
                        simpDate             =     new SimpleDateFormat("hh:mm:ss a");
                        Date today = new Date();
                        
                        outputFile.println(a+","+b+","+c+","+d+",NEWSTOCK,"+fmt.format(today)+","+simpDate.format(today)+",");
                        outputFile.close();
                        
                        JOptionPane.showMessageDialog(null,("Successfully added:\n"+a+"\n"+b+"\n"+c+"\n"+d));
                        
                        UPCText.setText("");
                        nameText.setText("");
                        retailText.setText("");
                        costText.setText("");
                        UPCText.requestFocus();
                        
                    }}
                
                catch(IOException exx){}
                
            }});
        
        
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            	stop();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));}});}
    
    
    public void stop() {
    	System.out.println("@NewStock.stop()");
    	if(go != null)
    	{
    		go = null;
    	}
    }
    public void finishFrame(){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void run() {
    	System.out.println("@NewStock.run()");
        setScreenSize();
        setFrame();
        setComponentDefaultValues("");
        finishFrame();
    	
    }
    public void run(String[] args){
        setScreenSize();
        setFrame();
        setComponentDefaultValues(args[0]);
        finishFrame();
    	
    }
    public static void main(String[] args){
        NewStock test = new NewStock();
        
        
        
    }
}

