package pay.point.sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;


/*
 * 
As of 8/7/23 at 6:48 PM
registerPOS()

getDeviceName()
rebootDevice()
getCounter()
testMac()

checkForUpdates()
checkUpdateStatus()
applyUpdates()


unregisterPOS()
unregisterPOSAll()

startSession()
endSession()

addLineItem()
overrideLineItem()
removeLineItem()
removeLineItemAll()

authorizeCard()
captureCard()
captureCardEarlyReturn()
creditCard()

addTip()
removeTip()
cancelTransaction()
voidTransaction()
lastTransaction()

activateGiftCard()
reactivateGiftCard()
addValueGiftCard()
checkBalanceGiftCard()
cashOutGiftCard()
deactivateGiftCard()

checkBalanceEBT()
tokenQueryCard()

---------------------------------------------------------
getStatus()
getStatusSAF()

requestEmail()
requestDonation()

confirmationAPMKlarna()
setIdleScreen()
laneClosed()

testCases()

*/


public class SessionManager extends JFrame implements WindowListener {

	private SampleClientRegisterEncryption verifone;
	
	public SessionManager(String address, int port, int secondary_port) {
    
		verifone = new SampleClientRegisterEncryption(address,port,secondary_port);
		
		setTitle("Session Manager");
		//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
		addWindowListener(this);
		setSize(600, 600);
        setLocationRelativeTo(null);

        // Register 
        JButton registerPOSButton 		= new JButton("RegisterPOS");
        
        JButton getDeviceName 			= new JButton("Get Device Name");
        JButton rebootDevice 			= new JButton("Reboot Device");
        JButton getCounter 				= new JButton("Get Counter");
        JButton testMacButton 			= new JButton("Test MAC");
        
        JButton checkForUpdates 		= new JButton("Check for Updates");
        JButton checkUpdateStatus 		= new JButton("Check Updates Status");
        JButton applyUpdates 			= new JButton("Apply Updates");
        
        JButton unregisterPOSButton 	= new JButton("UnRegister POS");
        JButton unregisterPOSAll 		= new JButton("UnRegister All POS");
        
        // Transaction Functions
        JButton startSessionButton 		= new JButton("Start Session");
        JButton endSessionButton 		= new JButton("End Session");

        JButton addLineItem 			= new JButton ("Add Line Item");
        JButton overrideLineItem 		= new JButton ("Override Line Item");
        JButton removeLineItem 			= new JButton ("Remove Line Item");
        JButton removeLineItemAll 		= new JButton ("Remove All Line Items");
        
        // Essential Functions        
        JButton authorizeCard 			= new JButton ("Authorize Card ");
        JButton captureCard 			= new JButton ("Capture Card");
        JButton captureCardEarlyReturn 	= new JButton ("Capture Card Early Return");
        JButton creditCard 				= new JButton("Return Funds to Credit Card");
        
        JButton addTip 					= new JButton("Add Tip");
        JButton removeTip 				= new JButton("Remove Tip");
        JButton voidTransaction 		= new JButton("Void Transaction");
        JButton cancelTransactionButton = new JButton("Cancel Transaction");
        JButton lastTransaction 			= new JButton("Last Transaction");
        
        JButton activateGiftCard 		= new JButton("Activate Gift Card");
        JButton reactivateGiftCard 		= new JButton("Reactivate Gift Card");
        JButton addValueGiftCard 		= new JButton("Add Value Gift Card");
        JButton checkBalanceGiftCard 	= new JButton("Check Balance Gift Card");
        JButton cashOutGiftCard 		= new JButton("Cashout Gift Card");
        JButton deactivateGiftCard 		= new JButton("Deactivate Gift Card");
        
        JButton checkBalanceEBT 		= new JButton("Check Balance EBT");
        JButton tokenQueryCard	 		= new JButton("Token Query Card");
        
        // Non Essential Functions
        JButton getStatus 				= new JButton("Get Status");
        JButton getStatusSAF 			= new JButton("Get Status SAF");
        
        // Non Essential Functions
        JButton requestEmail 			= new JButton ("Request Email");
        JButton requestDonationButton 	= new JButton("Request Donation");
        
        JButton confirmationAPMKlarna	= new JButton("Confirmation APM Klarna");
        JButton setIdleScreenButton 	= new JButton("Set Message for Idle Screen");
        JButton laneClosedButton 		= new JButton("Lane Closed");
        JButton displayMessage			= new JButton("Display Message"); // Will show balance due

        // Add button listeners
        registerPOSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                verifone.registerPOS();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> RegisterPOS Button");
            	ee.printStackTrace();
            	
            	}
            	
            }
        });
        
        getDeviceName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.getDeviceName();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> getDeviceName Button");
                	ee.printStackTrace();
                	
                	}
            }
        });

        rebootDevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.rebootDevice();
                	}catch(Exception ee) { 
                	
                		
                	System.out.println("Exception e SessionManager -> Reboot Device");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        getCounter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.getCounter();
                	}catch(Exception ee) { 
                		
                	System.out.println("Exception e SessionManager -> Get Counter");
                	ee.printStackTrace();
                	
                	}
            }
        });
        testMacButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.testMac();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> testMac Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        checkForUpdates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.checkForUpdates();
                	}catch(Exception ee) { 
                	
                		
                	System.out.println("Exception e SessionManager -> Check for Updates");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        checkUpdateStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.checkUpdateStatus();
                	}catch(Exception ee) { 
                	
                		
                	System.out.println("Exception e SessionManager -> Check for Updates");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        applyUpdates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.applyUpdates();
                	}catch(Exception ee) { 
                	
                		
                	System.out.println("Exception e SessionManager -> Apply Updates");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        
        unregisterPOSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                verifone.unregisterPOS();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> RegisterPOS Button");
            	ee.printStackTrace();
            	
            	}
            	
            }
        });
        unregisterPOSAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                verifone.unregisterPOSAll();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> RegisterPOS Button");
            	ee.printStackTrace();
            	
            	}
            	
            }
        });
        
        startSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.startSession();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> startSession Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        endSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.endSession();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> endSession Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
    	addLineItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	try { 
            	verifone.addLineItem();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> addLineItem Button");
            	ee.printStackTrace();
            	
            	}
        	}
    	});
    	
    	overrideLineItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.overrideLineItem();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> overrideLineItem Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
    	
    	removeLineItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.removeLineItem();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> removeLineItem Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
    	
    	removeLineItemAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.removeLineItemAll();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> removeLineItemAll Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
    	authorizeCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.authorizeCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> authorizeCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        captureCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.captureCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> captureCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
       
        captureCardEarlyReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.captureCardEarlyReturn();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> captureCardEarlyReturn Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        creditCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.creditCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> creditCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        
        addTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.addTip();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> addTip Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        removeTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.removeTip();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> Remove Tip");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        voidTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.voidTransaction();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> creditCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        cancelTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.cancelTransaction();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> cancel Transaction Button");
                	ee.printStackTrace();
                	
                	}
            }
        });


        lastTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.lastTransaction();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> lastTransaction Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        
        activateGiftCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.activateGiftCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> activate Gift Card Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        reactivateGiftCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.reactivateGiftCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> reactivateGiftCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        addValueGiftCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.addValueGiftCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> addValueGiftCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        checkBalanceGiftCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.checkBalanceGiftCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> checkBalanceGiftCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        cashOutGiftCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.cashOutGiftCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> cashOutGiftCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        deactivateGiftCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.deactivateGiftCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> deactivateGiftCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        checkBalanceEBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.checkBalanceEBT();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> checkBalanceEBT Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        
        tokenQueryCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.tokenQueryCard();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> tokenQueryCard Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        getStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.getStatus();
                	}catch(Exception ee) { 
                	
                		
                	System.out.println("Exception e SessionManager -> Get Status");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
        
        getStatusSAF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.getStatusSAF();
                	}catch(Exception ee) { 
                	
                		
                	System.out.println("Exception e SessionManager -> Get Status");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
    	requestEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                	verifone.requestEmail();
                	}catch(Exception ee) { 
                	
                	System.out.println("Exception e SessionManager -> requestEmail Button");
                	ee.printStackTrace();
                	
                	}
            }
        });
        
    	   requestDonationButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
               	try { 
                   	verifone.requestDonation();
                   	}catch(Exception ee) { 
                   	
                   		
                   	System.out.println("Exception e SessionManager -> lastTransaction Button");
                   	ee.printStackTrace();
                   	
                   	}
               }
           });
        
        
    	   confirmationAPMKlarna.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
               	try { 
                   	verifone.confirmationAPMKlarna();
                   	}catch(Exception ee) { 
                   	
                   		
                   	System.out.println("Exception e SessionManager -> confirmationAPMKlarna Button");
                   	ee.printStackTrace();
                   	
                   	}
               }
           });        
        
    	   setIdleScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	try { 
                
            		verifone.setIdleScreen();
            	
            	} catch(Exception ee) { 
            	System.out.println("Exception e SessionManager -> setIdleScreen Button");
            	ee.printStackTrace();
            	}
            }
        });
        
        laneClosedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                verifone.laneClosed();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> Lane Closed Button");
            	ee.printStackTrace();
            	}
            }
        });
        
        
        
        displayMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try { 
                verifone.displayMessage();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> Display Message Button");
            	ee.printStackTrace();
            	}
            }
        });
        
        
        
        // Set layout
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(registerPOSButton);
        add(getDeviceName);
        add(rebootDevice);
        add(getCounter);
        add(testMacButton);
        
        add(checkForUpdates);
        add(checkUpdateStatus);
        add(applyUpdates);
        
        add(unregisterPOSButton);
        add(unregisterPOSAll);
        
        add(startSessionButton);
        add(endSessionButton);
        
        add(addLineItem);
        add(overrideLineItem);
        add(removeLineItem);
        add(removeLineItemAll);
        
        
        add(authorizeCard);
        add(captureCard);
        add(captureCardEarlyReturn);
        add(creditCard);

        add(addTip);
        add(removeTip);
        add(voidTransaction);
        add(cancelTransactionButton);
        add(lastTransaction);

        
        add(activateGiftCard);
        add(reactivateGiftCard);
        add(addValueGiftCard);
        add(checkBalanceGiftCard);
        add(cashOutGiftCard);
        add(deactivateGiftCard);
        add(checkBalanceEBT);
        add(tokenQueryCard);
                
        add(getStatus);
        add(getStatusSAF);
        
        
        add(requestEmail);
        add(requestDonationButton);

        add(confirmationAPMKlarna);
        add(setIdleScreenButton);
        add(laneClosedButton);
        add(displayMessage);
        
    }

	
	public void windowActivated (WindowEvent arg0) {    
	    System.out.println("JFrame Window activated");    
	}    
	  
	// overriding windowClosed() method of WindowListener interface which prints the given string when window is closed  
	public void windowClosed (WindowEvent arg0) {    
	    System.out.println("Frame Widnow closed");    
	}    
	  
	// overriding windowClosing() method of WindowListener interface which prints the given string when we attempt to close window from system menu  
	public void windowClosing (WindowEvent arg0) {    
	    System.out.println("JFrame Window closing");
	    dispose();    
	}    
	  
	// overriding windowDeactivated() method of WindowListener interface which prints the given string when window is not active  
	public void windowDeactivated (WindowEvent arg0) {    
	    System.out.println("JFrame Window deactivated");    
	}    
	  
	// overriding windowDeiconified() method of WindowListener interface which prints the given string when window is modified from minimized to normal state  
	public void windowDeiconified (WindowEvent arg0) {    
	    System.out.println("JFrame Window deiconified");    
	}    
	  
	// overriding windowIconified() method of WindowListener interface which prints the given string when window is modified from normal to minimized state  
	public void windowIconified(WindowEvent arg0) {    
	    System.out.println("JFrame Window iconified");    
	}    
	  
	// overriding windowOpened() method of WindowListener interface which prints the given string when window is first opened  
	public void windowOpened(WindowEvent arg0) {    
	    System.out.println("JFrame Window opened");    
	}    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	System.out.println("Starting Session Manager");
            }
        });
    }
}
