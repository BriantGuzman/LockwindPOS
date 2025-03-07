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
public class NewCustomer {
    
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    
    private JFrame frame;
    private SpringLayout springLayout;
    
    private JPanel panel;
    
    private JTextField customerCodeTextField;
    private JTextField nameText;
    private JTextField phoneNumberTextField;
    private JTextField emailTextField;
    private JTextField address;
    private JTextField city;
    private JTextField state;
    private JTextField zipcode;
    private JTextField country;

    private JLabel customerCodeLabel;
    private JLabel nameLabel;
    private JLabel phoneNumberLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JLabel cityLabel;
    private JLabel stateLabel;
    private JLabel zipcodeLabel;
    private JLabel countryLabel;
    
    private JButton update;
    private JButton clear;
    private JButton exit;
    
    public NewCustomer()
    {
        screenSize = null;
        screenWidth = 0;
        screenHeight = 0;
        frame = null;
        springLayout = null;
        panel = null;
        
        customerCodeTextField = null;
        nameText = null;
        emailTextField = null;
        phoneNumberTextField = null;
        address = null;
        city = null;
        state = null;
        zipcode = null;
        country = null;
        
        customerCodeLabel = null;
        nameLabel = null;
        emailLabel = null;
        phoneNumberLabel = null;
        
        addressLabel = null;
        cityLabel = null;
        stateLabel = null;
        zipcodeLabel = null;
        countryLabel = null;
        
        
        
        clear = null;
        update = null;
        exit = null;
        
    }
    
    public NewCustomer(String GTIN){
        this();
        NewCustomer test = new NewCustomer();
        test.setScreenSize();
        test.setFrame();
        test.setComponentDefaultValues();
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
        frame.setPreferredSize(new Dimension(getScreenWidth()/2,getScreenHeight()/2));
        frame.setTitle("CUSTOMER INFORMATION");
    }

    
    public void setComponentDefaultValues()
    {
        setLayout();
        
        panel = new JPanel();
        panel.setLayout(getLayout());
        frame.add(panel);
        
        customerCodeTextField = new JTextField(20);
        nameText = new JTextField(20);
        phoneNumberTextField = new JTextField(20);
        emailTextField = new JTextField(20);
        
        address = new JTextField(20);
        city = new JTextField(20);
        state = new JTextField(20);
        zipcode = new JTextField(20);
        country = new JTextField(20);
        
        
        customerCodeLabel = new JLabel("CODE");
        nameLabel = new JLabel("Name");
        phoneNumberLabel = new JLabel("Phone Number");
        emailLabel = new JLabel("Email");

        
        addressLabel = new JLabel("Address");
        cityLabel = new JLabel("City");
        stateLabel = new JLabel("State");
        zipcodeLabel = new JLabel("Zipcode");
        countryLabel = new JLabel("Country");

        
        update = new JButton("UPDATE");
        clear = new JButton("CLEAR");
        exit = new JButton("EXIT");
        
        //NORTHERN COORDINATES
        springLayout.putConstraint(SpringLayout.NORTH, customerCodeTextField,30,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, nameText,60,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, phoneNumberTextField,90,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, emailTextField,120,springLayout.NORTH, panel);
        
// textfields north position here
        springLayout.putConstraint(springLayout.NORTH, address,150,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, city,180,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, state,210,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, zipcode,240,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, country,270,springLayout.NORTH, panel);

        // label north position here     
        springLayout.putConstraint(springLayout.NORTH, customerCodeLabel,30,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, nameLabel,60,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, phoneNumberLabel,90,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, emailLabel,120,springLayout.NORTH, panel);

        springLayout.putConstraint(springLayout.NORTH, addressLabel,150,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, cityLabel,180,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, stateLabel,210,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, zipcodeLabel,240,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, countryLabel,270,springLayout.NORTH, panel);

        springLayout.putConstraint(springLayout.NORTH, update,300,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, clear,300,springLayout.NORTH, panel);
        springLayout.putConstraint(springLayout.NORTH, exit,300,springLayout.NORTH, panel);
        
        // Text fields west position here
        
        springLayout.putConstraint(springLayout.WEST, customerCodeTextField,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, nameText,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, phoneNumberTextField,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, emailTextField,120,springLayout.WEST, panel);
        
        springLayout.putConstraint(springLayout.WEST, address,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, city,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, state,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, zipcode,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, country,120,springLayout.WEST, panel);

        
        
        // Labels west position here
        springLayout.putConstraint(springLayout.WEST, customerCodeLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, nameLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, phoneNumberLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, emailLabel,20,springLayout.WEST, panel);
        
        springLayout.putConstraint(springLayout.WEST, addressLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, cityLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, stateLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, zipcodeLabel,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, countryLabel,20,springLayout.WEST, panel);

        
        
        springLayout.putConstraint(springLayout.WEST, update,20,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, clear,120,springLayout.WEST, panel);
        springLayout.putConstraint(springLayout.WEST, exit,200,springLayout.WEST, panel);
        
        panel.add(customerCodeTextField);
        panel.add(nameText);
        panel.add(phoneNumberTextField);
        panel.add(emailTextField);
        
        panel.add(address);
        panel.add(city);
        panel.add(state);
        panel.add(zipcode);
        panel.add(country);
        
        panel.add(customerCodeLabel);
        panel.add(nameLabel);
        panel.add(phoneNumberLabel);
        panel.add(emailLabel);

        panel.add(addressLabel);
        panel.add(cityLabel);
        panel.add(stateLabel);
        panel.add(zipcodeLabel);
        panel.add(countryLabel);

        
        panel.add(clear);
        panel.add(update);
        panel.add(exit);
    
        
        
        //ACTION LISTENERS ------------------------------------------------------------
        
        customerCodeTextField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                try{
                    String u = "";
                    u = customerCodeTextField.getText();
                    String line = "";
                    String target = "";
                    String first = "";
                    StringTokenizer str = null;
                    
                    File file = new File("./NewCustomer.csv");
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
                                
                                address.setText(str.nextToken());
                                city.setText(str.nextToken());
                                state.setText(str.nextToken());
                                zipcode.setText(str.nextToken());
                                country.setText(str.nextToken());
                                
                            }
                            else{
                                nameText.setText("");
                                phoneNumberTextField.setText("");
                                emailTextField.setText("");
                                
                                address.setText("");
                                city.setText("");
                                state.setText("");
                                zipcode.setText("");
                                country.setText("");
                                
                            }}}
                    
                    if(target == null || target.equalsIgnoreCase("")){
                        nameText.setText("");
                        phoneNumberTextField.setText("");
                        emailTextField.setText("");

                        address.setText("");
                        city.setText("");
                        state.setText("");
                        zipcode.setText("");
                        country.setText("");

                    }
                inputFile.close();    
                }
                catch(IOException exxx){}
                
            }});
        
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                customerCodeTextField.setText("");
                nameText.setText("");
                phoneNumberTextField.setText("");
                emailTextField.setText("");

                address.setText("");
                city.setText("");
                state.setText("");
                zipcode.setText("");
                state.setText("");
                country.setText("");

                customerCodeTextField.requestFocus();

            
            }});
        
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent exxe){
                
                
                //BEGIN UPDATE OF NEW STOCK
                try{
                    
                	String a,b,c,d,e,f,g,h,i;
                    
                    
                    a = customerCodeTextField.getText();
                    b = nameText.getText();
                    c = phoneNumberTextField.getText();
                    d = emailTextField.getText();
                    
                    e = address.getText();
                    f = city.getText();
                    g = state.getText();
                    h = zipcode.getText();
                    i = country.getText();
                    
                    if(a.equalsIgnoreCase("")  || a == null || b.equalsIgnoreCase("")  || b == null || c.equalsIgnoreCase("")  || c == null || d.equalsIgnoreCase("")  || d == null)
                    {
                        JOptionPane.showMessageDialog(null,"Please fill all required fields");
                    }
                    else
                    {
                        
                        
                        FileWriter file = new FileWriter("./NewCustomer.csv",true);
                        PrintWriter outputFile = new PrintWriter(file);
                        
                        
                        //    outputFile.println(a+","+b+","+c+","+d);
                        
                        Locale[]     locale         =     {US,UK,GERMANY,FRANCE}        ;
                        int[]         styles         =     {FULL,LONG,MEDIUM,SHORT}    ;
                        
                        DateFormat fmt                 =     null                ;
                        fmt =         DateFormat.getDateInstance      (styles[3], locale[0]);
                        
                        
                        
                        SimpleDateFormat simpDate                        ;
                        simpDate             =     new SimpleDateFormat("hh:mm:ss a");
                        Date today = new Date();
                        
                        outputFile.println(a+","+b+","+c+","+d+","+e+","+f+","+g+","+h+","+i+",NewCustomer,"+fmt.format(today)+","+simpDate.format(today)+",");
                        outputFile.close();
                        
                        JOptionPane.showMessageDialog(null,("Successfully added:\n"+a+"\n"+b+"\n"+c+"\n"+d));
                        
                        customerCodeTextField.setText("");
                        nameText.setText("");
                        phoneNumberTextField.setText("");
                        emailTextField.setText("");
                        address.setText("");
                        city.setText("");
                        state.setText("");
                        zipcode.setText("");
                        country.setText("");
                        
                        customerCodeTextField.requestFocus();
                        
                    }}
                
                catch(IOException exex){}
                
            }});
        
        
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));}});}
    
    public void finishFrame(){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args){
        
            NewCustomer test = new NewCustomer();
            test.setScreenSize();
            test.setFrame();
            test.setComponentDefaultValues();
            test.finishFrame();
        
    }
}


