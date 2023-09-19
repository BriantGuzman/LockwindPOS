package pay.point.sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class APIVerifone {

	    
	private String verifone_device_address;
	private int verifone_main_port;
	private int verifone_secondary_port;
	
	private SampleClientRegisterEncryption verifone;

	public APIVerifone() { 
  		verifone_device_address = "192.168.50.197";
		verifone_main_port = 5015;
		verifone_secondary_port = 5016; 

		verifone = new SampleClientRegisterEncryption(verifone_device_address,verifone_main_port,verifone_secondary_port);
	}
	
    // Add button listeners
	
	public void registerPOS() {
        	try { 
            verifone.registerPOS();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> RegisterPOS Button");
        	ee.printStackTrace();
        	
        	}
	}
	
	public void getDeviceName() {
    	try { 
        	verifone.getDeviceName();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> getDeviceName Button");
        	ee.printStackTrace();
        	
        	}
	}
	
	public void rebootDevice() {
    	try { 
        	verifone.rebootDevice();
        	}catch(Exception ee) { 
        	System.out.println("Exception e SessionManager -> Reboot Device");
        	ee.printStackTrace();
		
	}
	}
            	

	public void getCounter() {
		
    	try { 
        	verifone.getCounter();
        	}catch(Exception ee) { 
        	System.out.println("Exception e SessionManager -> Get Counter");
        	ee.printStackTrace();
        	
        	}
    	}


	public void testMac() {
		
    	try { 
        	verifone.testMac();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> testMac Button");
        	ee.printStackTrace();            	
        	}
    	}
	

	public void checkForUpdates() {
		
        	try { 
            	verifone.checkForUpdates();
            	}catch(Exception ee) { 
            		
            	System.out.println("Exception e SessionManager -> Check for Updates");
            	ee.printStackTrace();
            	
            	}
        }
    

	public void checkUpdateStatus() {
		
    	try { 
        	verifone.checkUpdateStatus();
        	}catch(Exception ee) { 
        	
        		
        	System.out.println("Exception e SessionManager -> Check for Updates");
        	ee.printStackTrace();
        	
        	}

	}

	
	public void applyUpdates() {
		
		   	try { 
            	verifone.applyUpdates();
            	}catch(Exception ee) { 
            	System.out.println("Exception e SessionManager -> Apply Updates");
            	ee.printStackTrace();
            	
            	}
        }

	
    
	public void  unregisterPOSButton() { 
		
		
        	try { 
            verifone.unregisterPOS();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> RegisterPOS Button");
        	ee.printStackTrace();
        	
        	}
	}
	

	
	public void unregisterPOSAll() { 
    	try { 
            verifone.unregisterPOSAll();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> RegisterPOS Button");
        	ee.printStackTrace();
        	
        	}
	}
        	
	public void startSession() {
		
    	try { 
        	verifone.startSession();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> startSession Button");
        	ee.printStackTrace();
        	
        	}
    }


	public void endSession() { 
    	try { 
        	verifone.endSession();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> endSession Button");
        	ee.printStackTrace();
        	
        	}
	}
    
	public void addLineItem() { 
    	try { 
        	verifone.addLineItem();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> addLineItem Button");
        	ee.printStackTrace();
        	
        	}
		
	}
	
	public void overrideLineItem() { 
		
    	try { 
        	verifone.overrideLineItem();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> overrideLineItem Button");
        	ee.printStackTrace();
        	
        	}
		
	}
	
	public void removeLineItem() { 
		
    	try { 
        	verifone.removeLineItem();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> removeLineItem Button");
        	ee.printStackTrace();
        	
        	}
    }
	

	
	public void removeLineItemAll() { 
		
    	try { 
        	verifone.removeLineItemAll();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> removeLineItemAll Button");
        	ee.printStackTrace();
        	
        	}
    }
		

	public void authorizeCard() { 
		
    	try { 
        	verifone.authorizeCard();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> authorizeCard Button");
        	ee.printStackTrace();
        	
        	}
    }
	
    public void captureCard() {
    	
        	try { 
            	verifone.captureCard();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> captureCard Button");
            	ee.printStackTrace();
            	
            	}
        }

    public void captureCardEarlyReturn() { 
    	
    	try { 
        	verifone.captureCardEarlyReturn();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> captureCardEarlyReturn Button");
        	ee.printStackTrace();
        	
        	}
    }
    
    
    

    public void creditCard() { 
    	
    
        	try { 
            	verifone.creditCard();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> creditCard Button");
            	ee.printStackTrace();
            	
            	}
        }
    

    public void addTip() { 
    	
    	try { 
        	verifone.addTip();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> addTip Button");
        	ee.printStackTrace();
        	
        	}
    	
    	
    }

    public void removeTip() { 
    	
    	try { 
        	verifone.removeTip();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> Remove Tip");
        	ee.printStackTrace();
        	
        	}
    	
    }

    public void voidTransaction() { 

    	try { 
            	verifone.voidTransaction();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> creditCard Button");
            	ee.printStackTrace();
            	
            	}
        }
    
    public void cancelTransaction() { 
    	try { 
        	verifone.cancelTransaction();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> cancel Transaction Button");
        	ee.printStackTrace();
        	
        	}
    }
    	

    public void lastTransaciton() {
    	
    	try { 
        	verifone.lastTransaction();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> lastTransaction Button");
        	ee.printStackTrace();
        	
        	}
    }

    
    public void activateGiftCard() { 
    	   	try { 
            	verifone.activateGiftCard();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> activate Gift Card Button");
            	ee.printStackTrace();
            	
            	}
        }

    public void reactivateGiftCard() { 
    	
    	   	try { 
            	verifone.reactivateGiftCard();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> reactivateGiftCard Button");
            	ee.printStackTrace();
            	
            	}
        }

    
    public void addValueGiftCard() { 
    	   	try { 
            	verifone.addValueGiftCard();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> addValueGiftCard Button");
            	ee.printStackTrace();
            	
            	}
        }

    
    public void checkBalanceGiftCard() { 
    	
    	try { 
        	verifone.checkBalanceGiftCard();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> checkBalanceGiftCard Button");
        	ee.printStackTrace();
        	
        	}
    }
    

    public void cashOutGiftCard() { 
   	
    	try { 
        	verifone.cashOutGiftCard();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> cashOutGiftCard Button");
        	ee.printStackTrace();
        	
        	}
    }
    
    
    
    public void deactivateGiftCard() { 
        	try { 
            	verifone.deactivateGiftCard();
            	}catch(Exception ee) { 
            	System.out.println("Exception e SessionManager -> deactivateGiftCard Button");
            	ee.printStackTrace();
            	
            	}
    }

    public void checkBalanceEBT() { 
    
        	try { 
            	verifone.checkBalanceEBT();
            	}catch(Exception ee) { 
            	
            	System.out.println("Exception e SessionManager -> checkBalanceEBT Button");
            	ee.printStackTrace();
            	
            	}
        }
    
    
    public void tokenQueryCard() {
    	
    	try { 
        	verifone.tokenQueryCard();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> tokenQueryCard Button");
        	ee.printStackTrace();
        	
        	}
    }
    
    public void getStatus() {
    	try { 
        	verifone.getStatus();
        	}catch(Exception ee) { 
        	
        		
        	System.out.println("Exception e SessionManager -> Get Status");
        	ee.printStackTrace();
        	
        	}
    	
    }
	
    public void getStatusSAF() { 
    	try { 
        	verifone.getStatusSAF();
        	}catch(Exception ee) { 
        		
        	System.out.println("Exception e SessionManager -> Get Status");
        	ee.printStackTrace();
        	
     }
    }
    	
    
	public void requestEmail() { 
    	try { 
        	verifone.requestEmail();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> requestEmail Button");
        	ee.printStackTrace();
        	
        	}
		
	}
	
	public void requestDonation() {
           	try { 
               	verifone.requestDonation();
               	}catch(Exception ee) { 
               		
               	System.out.println("Exception e SessionManager -> lastTransaction Button");
               	ee.printStackTrace();
               	
               	}
           }

    
	public void confirmationAPMKlarna() {
           	try { 
               	verifone.confirmationAPMKlarna();
               	}catch(Exception ee) { 
               	
               		
               	System.out.println("Exception e SessionManager -> confirmationAPMKlarna Button");
               	ee.printStackTrace();
               	
               	}
           }
	
	public void setIdleScreenButton() {
        	try { 
            verifone.setIdleScreen();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> setIdleScreen Button");
        	ee.printStackTrace();
        	}
        	
        }
    
    public void laneClosedButton() {
        	try { 
            verifone.laneClosed();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> Lane Closed Button");
        	ee.printStackTrace();
        	
        	}
        	
        }
    
    public void displayMessage() {
        	try { 
            verifone.displayMessage();
        	}catch(Exception ee) { 
        	
        	System.out.println("Exception e SessionManager -> Display Message Button");
        	ee.printStackTrace();
        	
        	}
        	
        }
	
	
	
} // end class
