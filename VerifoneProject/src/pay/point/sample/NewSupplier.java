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


class NewSupplier implements Runnable {
    
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    
    private JFrame frame;
    private SpringLayout springLayout;
    
    private JPanel panel;
    
    private JTextField supplierCodeTextField;
    private JTextField nameText;
    private JTextField phoneNumberTextField;
    private JTextField emailTextField;
    private JTextField websiteTextField;

    private JLabel supplierCodeLabel;
    private JLabel nameLabel;
    private JLabel phoneNumberLabel;
    private JLabel emailLabel;
    private JLabel websiteLabel;

    private JButton update;
    private JButton clear;
    private JButton exit;
    
    public NewSupplier()
    {
        screenSize = null;
        screenWidth = 0;
        screenHeight = 0;
        frame = null;
        springLayout = null;
        panel = null;
        
        supplierCodeTextField = null;
        nameText = null;
        emailTextField = null;
        phoneNumberTextField = null;
        websiteTextField = null;

        supplierCodeLabel = null;
        nameLabel = null;
        emailLabel = null;
        phoneNumberLabel = null;
        websiteLabel = null;
        
        clear = null;
        update = null;
        exit = null;
        
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
        frame.setPreferredSize(new Dimension(/*getScreenWidth(),getScreenHeight()*/400,300));
        frame.setTitle("SUPPLIER INFORMATION");
    }

    
    public void setComponentDefaultValues()
    {
        setLayout();
        
        panel = new JPanel();
        panel.setLayout(getLayout());
        frame.add(panel);
        
        supplierCodeTextField = new JTextField(20);
        nameText = new JTextField(20);
        phoneNumberTextField = new JTextField(20);
        emailTextField = new JTextField(20);
        websiteTextField = new JTextField(20);
        
        supplierCodeLabel = new JLabel("CODE");
        nameLabel = new JLabel("Name");
        phoneNumberLabel = new JLabel("Phone Number");
        emailLabel = new JLabel("Email");
        websiteLabel = new JLabel("Website");

        update = new JButton("UPDATE");
        clear = new JButton("CLEAR");
        exit = new JButton("EXIT");
        
        //NORTHERN COORDINATES
        springLayout.putConstraint(springLayout.NORTH, supplierCodeTextField,30,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, nameText,60,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, phoneNumberTextField,90,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, emailTextField,120,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, websiteTextField,150,springLayout.NORTH, panel);
        
        springLayout.putConstraint(springLayout.NORTH, supplierCodeLabel,30,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, nameLabel,60,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, phoneNumberLabel,90,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, emailLabel,120,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, websiteLabel,150,springLayout.NORTH, panel);

        springLayout.putConstraint(springLayout.SOUTH, update,0,springLayout.SOUTH, panel);
        springLayout.putConstraint(springLayout.SOUTH, clear,0,springLayout.SOUTH, panel);
        springLayout.putConstraint(springLayout.SOUTH, exit,0,springLayout.SOUTH, panel);
        
        springLayout.putConstraint(springLayout.WEST, supplierCodeTextField,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, nameText,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, phoneNumberTextField,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, emailTextField,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, websiteTextField,120,springLayout.WEST, panel);

        springLayout.putConstraint(springLayout.WEST, supplierCodeLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, nameLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, phoneNumberLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, emailLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, websiteLabel,20,springLayout.WEST, panel);

        springLayout.putConstraint(springLayout.WEST, update,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, clear,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, exit,200,springLayout.WEST, panel);
        
        panel.add(supplierCodeTextField);
        panel.add(nameText);
        panel.add(phoneNumberTextField);
        panel.add(emailTextField);
        panel.add(websiteTextField);

        panel.add(supplierCodeLabel);
        panel.add(nameLabel);
        panel.add(phoneNumberLabel);
        panel.add(emailLabel);
        panel.add(websiteLabel);

        panel.add(clear);
        panel.add(update);
        panel.add(exit);
    
        
        
        //ACTION LISTENERS ------------------------------------------------------------
        
        supplierCodeTextField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                try{
                    String u = "";
                    u = supplierCodeTextField.getText();
                    String line = "";
                    String target = "";
                    String first = "";
                    StringTokenizer str = null;
                    
                    File file = new File("NewSupplier.csv");
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
                                phoneNumberTextField.setText(str.nextToken() );
                                emailTextField.setText(str.nextToken());
                                websiteTextField.setText(str.nextToken());
                                
                            }
                            else{
                                nameText.setText("");
                                phoneNumberTextField.setText("");
                                emailTextField.setText("");
                                websiteTextField.setText("");
                            }}}
                    
                    if(target == null || target.equalsIgnoreCase("")){
                        nameText.setText("");
                        phoneNumberTextField.setText("");
                        emailTextField.setText("");
                        websiteTextField.setText("");
                    }
                inputFile.close();    
                }
                catch(IOException exxx){}
                
            }});
        
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                supplierCodeTextField.setText("");
                nameText.setText("");
                phoneNumberTextField.setText("");
                emailTextField.setText("");
                supplierCodeTextField.requestFocus();
            }});
        
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                
                //BEGIN UPDATE OF NEW SUPPLIER
                try{
                    String a = "";
                    String b = "";
                    String c = "";
                    String d = "";
                    String f = "";
                    
                    a = supplierCodeTextField.getText();
                    b = nameText.getText();
                    c = phoneNumberTextField.getText();
                    d = emailTextField.getText();
                    f = websiteTextField.getText();

                    if(a.equalsIgnoreCase("")  || a == null || b.equalsIgnoreCase("")  || b == null )
                    {
                        JOptionPane.showMessageDialog(null,"Please fill all required fields");
                    }
                    else
                    {
                        
                        
                        FileWriter file = new FileWriter("NewSupplier.csv",true);
                        PrintWriter outputFile = new PrintWriter(file);

                        Locale[]     locale         =     {US,UK,GERMANY,FRANCE}        ;
                        int[]         styles         =     {FULL,LONG,MEDIUM,SHORT}    ;
                        
                        DateFormat fmt                 =     null                ;
                        fmt =         DateFormat.getDateInstance      (styles[3], locale[0]);
                        
                        
                        
                        SimpleDateFormat simpDate                        ;
                        simpDate             =     new SimpleDateFormat("hh:mm:ss a");
                        Date today = new Date();

                        if(c.equalsIgnoreCase("")  || c == null) {
                            c = "NA";
                        }
                        if(d.equalsIgnoreCase("") || d == null) {
                            d = "NA";
                        }
                        if(f.equalsIgnoreCase("") || f == null) {
                            f = "NA";
                        }

                        
                        outputFile.println(a+","+b+","+c+","+d+","+f+",NewSupplier,"+fmt.format(today)+","+simpDate.format(today)+",");
                        outputFile.close();
                        
                        JOptionPane.showMessageDialog(null,("Successfully added:\n"+a+"\n"+b+"\n"+c+"\n"+d+"\n"+f));
                        
                        supplierCodeTextField.setText("");
                        nameText.setText("");
                        phoneNumberTextField.setText("");
                        emailTextField.setText("");
                        websiteTextField.setText("");
                        supplierCodeTextField.requestFocus();
                        
                    }}
                
                catch(IOException exx){}
                
            }});
        
        
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));}});}
    
    public void finishFrame(){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void run(String args[])
    {
    	if(args[0] != null)
        {
            NewSupplier test = new NewSupplier();
            test.setScreenSize();
            test.setFrame();
            test.setComponentDefaultValues();
            test.finishFrame();
        }
        else{
            NewSupplier test = new NewSupplier();
            test.setScreenSize();
            test.setFrame();
            test.setComponentDefaultValues();
            test.finishFrame();
            
        }
    }
    
    public void run() {
    	
    }
    public static void main(String[] args){
        
        
    }
}

