package pay.point.sample;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import jakarta.xml.bind.DatatypeConverter;	
// import javax.xml.bind.DatatypeConverter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * Exhibits how to perform basic tasks with Point Solution
 * 
 * 
 * @author Chris_w9
 *
 */


/*
 * 
 * 
 */

public class SampleClientRegisterEncryption {
	
	private static final String PUBLIC_KEY = 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAugflCYHdNLX1PK+2JpedLoL4JdwpapkpwoSIYOhBF9FFri+roRTqPyyosLFGMMnO5l65z9YY1cQYSENWfhLvPROD2Oruyl1k2wSYWT+23wTB0jJjA4ktk7Q2cErNzMNiLLP0tB3rOYJHxC1HjskKBmkblF5ZDeCNzVyeEdF37zfCDD5bBIjPSpmLgH1swDQIvpULhwhmyf1AaJX+oaaCQgu6wxrbP17auMJzAjhddwUgIbkCiAEcYu8fwyTXQWFcQtfA3nufCITAcI7jmtxrXKqKWgZ23oIgvmIM1y9l6Bp9QT8MvDn63wfj54fyOW5Jb66G19x/xVGF5lH68qPErwIDAQAB";
	private static final String PRIVATE_KEY = 
			"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6B+UJgd00tfU8r7Yml50ugvgl3ClqmSnChIhg6EEX0UWuL6uhFOo/LKiwsUYwyc7mXrnP1hjVxBhIQ1Z+Eu89E4PY6u7KXWTbBJhZP7bfBMHSMmMDiS2TtDZwSs3Mw2Iss/S0Hes5gkfELUeOyQoGaRuUXlkN4I3NXJ4R0XfvN8IMPlsEiM9KmYuAfWzANAi+lQuHCGbJ/UBolf6hpoJCC7rDGts/Xtq4wnMCOF13BSAhuQKIARxi7x/DJNdBYVxC18Dee58IhMBwjuOa3GtcqopaBnbegiC+YgzXL2XoGn1BPwy8OfrfB+Pnh/I5bklvrobX3H/FUYXmUfryo8SvAgMBAAECggEAMmmxR8JJj98/dhKn6g1sKw6S8K+ZCao4Bt6jlp9aBHpRx8JjYGOqlzQjAr8HpnEKAKPq9seuMz/Q1MRqy/+VlZeUQ1RnIa/thOzZ3FXH2OgRHkVJT8v87eoIVqXu326TTEn4Jld1R0Bm8mLS4X7ZmKMjNjHbMEeKJfzTWUDKn6imPU5/mkJIoCVNi1CM+A8QTxoCFhWzdxHj5GCExAzQJdTFDHLEDygZOjX5iGSRenYYc5dxLutroWc8a9XPuftPIBooPCYAsRbUYUE16bXosQ38aO8lU3E1tyCD0nj0sq1Oiiuw/UJmFizj9pJUzCJpWDgk4wqzOffOf2gI+5rhAQKBgQDfkOUUp0Qzo1h/MJ49BGqSLZZ1pl/UEsGhVeV2RKx7TukOq0Lj9tChAnhouA6GsjR9AG8AWiZCN4ZkMlRVHAAppkk/6WFE4FHkmvWlUmNsNdwY5z4Ww5tggywg4j9WPif5FjWfvq7V/UYvl24mJ1XChAh3HGOX674DPfyLBgvfWwKBgQDVBPeIMs4FtVo4WoIfdx3A2xvbwd9TVsCJiLV5iXcy9ksbPVLif7tO5ZR63GkevVNc4PHWupaqA3f8VCBe3M12e6xdlH59zXPRVubczK8pBqcKt84qj7yk5Y6OgiFQEp/u16G2M7FhagQ14J2N0pGHdYtZmn+A694c2HD98LBkPQKBgQC7Wgiv0zCzeXrrM8oX5kCM+ckyFNgPuBwuYPZns0s8Frf2RA1NTwQtOg2/7Ca4OFUGQDvFdsbDDRcBlq/XlxyHysNt3N1XxAi85CNhhPaus0AcWoVMvGXUbninohJj6rjC5BrSIRERYSvVLDjxnlsfJFiXwOGxaayVuPePZeTDKwKBgQCtX+mHtLHx+3R+wUt/CJfyy2KVLenyDn2OcvIhBT07ATKH7RV0u7lbsYdzp8j29+jNg1fSCPNvVHtnp6DhFJ01fdsAH0gEZB+Llks4Em/N2FhEZO0rvuku3Jd2bXtnjIEXB/HaNaB9RKhAoZwaPfOsaIMOXqy/5TlWCOOOC0PFkQKBgGB7PkgYnGg0R610g0JaXJJ3kaeGnZYJapXgUF6ZDKz6IQI1ufhyQ29tqSxSW2GydLQRFFr4pXEEoYCGGO5KVkP5bGe/3Hyj4O82JiOkwFyVtNp3yjhPFbwlOzgQnAHEbmpidLTBP3THDtqNWGb1U586pNuxDpIMQrGR9Gva0OzK";
	
	private String macLabel = "";
	private byte[] macKey = new byte[0];
	private int counter = 0;
	
	private String address;
	private int port;
	private int secondary_port;
	private int invoice_number;
	Random generator = null;
	String entryCode = "";
	
	KeyFactory kf;
	PrivateKey privateKey;
	PublicKey publicKey;
	KeyPair keypair; 
	
	private Document request;
	private Element docElement;
	
	private Document responseXml;
	private Element responseDocElement;
	
	private String encryptedMACKey;
	private byte[] macKeyBase64Decoded;
	private Cipher cipher;
	
	private String nextCounter;
	private String mac;
	
	private String temp;

	
	/**
	 * Initializes a new Client object
	 * 
	 * @param address remote IPv4 address of the Device
	 * @param port remote TCP Port of the Device
	 */
	
	public SampleClientRegisterEncryption() {
		kf = null;
		privateKey = null;
		publicKey = null;
		keypair = null;
		
		request = null;
		docElement = null;
		
		encryptedMACKey = "";
		macKeyBase64Decoded = null;
		cipher = null;

		nextCounter = "";
		mac = "";
		
		temp = "";
	}
	public SampleClientRegisterEncryption(String address, int port,int secondary_port) {
		this();
		
		this.address = address;
		this.port = port;
		this.secondary_port = secondary_port;
		
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));
		
		
		try { 
		kf = KeyFactory.getInstance("RSA");
        privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(PRIVATE_KEY)));
        publicKey = kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(PUBLIC_KEY)));
		keypair = new KeyPair(publicKey, privateKey);
		}catch(Exception e) {
			
		}
		
		
		
	}
	
	/**
	 * Registers the POS with the device
	 * 
	 * @throws Exception
	 */
	public void registerPOS() throws Exception {

		// Point SCA 4.0 Engage Integration Guide 3.00 Page 44

		
		// generate a random ENTRY_CODE
		//Random generator = new Random();
		//String entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		// System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        // final KeyFactory kf = KeyFactory.getInstance("RSA");
        // final PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(PRIVATE_KEY)));
        // final PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(PUBLIC_KEY)));
		// final KeyPair keypair = new KeyPair(publicKey, privateKey);

		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);


		// build request
		//Document request = Documents.create("TRANSACTION");
		//Element docElement = request.getDocumentElement();
		
		
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "REGISTER");
		Documents.addElement(docElement, "ENTRY_CODE", entryCode);
		Documents.addElement(docElement, "KEY", PUBLIC_KEY);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}

		
		// validate that the MAC_KEY was returned
		//String encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
	
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
	}
	
	public void startSession() throws Exception {

		// Point SCA 4.0 Engage Integration Guide 3.00 Page 70

		
		// generate a random ENTRY_CODE
//		Random generator = new Random();
	//	String entryCode = String.valueOf(generator.nextInt(9999));

		
		
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
//		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        // KeyFactory kf = KeyFactory.getInstance("RSA");
        // PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(PRIVATE_KEY)));
        // PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(PUBLIC_KEY)));
		// KeyPair keypair = new KeyPair(publicKey, privateKey);

//		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
//		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);

		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SESSION");
		Documents.addElement(docElement, "COMMAND", "START");
		Documents.addElement(docElement, "INVOICE", String.valueOf(invoice_number++) );
		Documents.addElement(docElement, "SWIPE_AHEAD", String.valueOf(1));
		Documents.addElement(docElement, "STORE_NUM", String.valueOf(27));
		Documents.addElement(docElement, "LANE", String.valueOf(1));
		Documents.addElement(docElement, "CASHIER_ID", String.valueOf(1));
		Documents.addElement(docElement, "SERVER_ID", String.valueOf(1));
		Documents.addElement(docElement, "SHIFT_ID", String.valueOf(1));
		Documents.addElement(docElement, "TABLE_NUM", String.valueOf(0));
		Documents.addElement(docElement, "BUSINESSDATE", "20230624");
		Documents.addElement(docElement, "PURCHASE_ID", "STELLAR MANAGEMENT");
		Documents.addElement(docElement, "TRAINING_MODE", "ON");
		Documents.addElement(docElement, "TRAINING_MODE", "TRUE");
		
//		Documents.addElement(docElement, "ENTRY_CODE", entryCode);
//		Documents.addElement(docElement, "KEY", PUBLIC_KEY);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
		/*
		 * COMMAND RESPONSE
		 * <RESPONSE>
<RESPONSE_TEXT>SESSION started</RESPONSE_TEXT>
<RESULT>OK</RESULT>
<RESULT_CODE>-1</RESULT_CODE>
<TERMINATION_STATUS>SUCCESS</TERMINATION_STATUS>
<COUNTER>2</COUNTER>
</RESPONSE>
		 * 
		 * This start session command does not return the MAC_LABEL XML element, therefore I remove line.
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
*/
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
	}
	
	
	
	public void endSession() throws Exception {

		// Point SCA 4.0 Engage Integration Guide 3.00 Page 76

		
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
//		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        
//		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
//		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);

		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "FUNCTION_TYPE", "SESSION");
		Documents.addElement(docElement, "COMMAND", "FINISH");

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
	}
	
	
public void addLineItem() throws Exception {
		
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 158

	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "ADD");
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", "04.98");
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", "00.43");
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", "05.42");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		Element docElement2 = request.createElement("LINE_ITEMS");
		Element docElement3 = request.createElement("MERCHANDISE");

		docElement.appendChild(docElement2);
		docElement2.appendChild(docElement3);
		
		Documents.addElement(docElement3, "LINE_ITEM_ID", "1");
		Documents.addElement(docElement3, "SKU", "1");
		Documents.addElement(docElement3, "UPC", "1");
		
		Documents.addElement(docElement3, "DESCRIPTION", "TEFLON TAPE");
		Documents.addElement(docElement3, "QUANTITY", "1");
		Documents.addElement(docElement3, "UNIT_PRICE", "1.99");
		Documents.addElement(docElement3, "EXTENDED_PRICE", "1.99");
		Documents.addElement(docElement3, "FONT_COL_VALUE", "000000");
//		Documents.addElement(docElement3, "LINE_ITEM_ID", "1");

		
		boolean offer = false;
		
		if(offer == true)
		{
			Element docElement4 = request.createElement("OFFER");
			Documents.addElement(docElement4, "TYPE", "1");
			Documents.addElement(docElement4, "LINE_ITEM_ID", "1");
			Documents.addElement(docElement4, "DESCRIPTION", "EMPLOYEE DISCOUNT");
			Documents.addElement(docElement4, "OFFER_AMOUNT", "1.00");
			Documents.addElement(docElement4, "OFFER_LINE_ITEM", "3"); // Create a table for managing the discounts applicable to the system.			
		}
		
		docElement3 = request.createElement("MERCHANDISE");
		docElement2.appendChild(docElement3);
		Documents.addElement(docElement3, "UNIT_PRICE", "2.99");
		Documents.addElement(docElement3, "DESCRIPTION", "TAPE");
		Documents.addElement(docElement3, "LINE_ITEM_ID", "2");
		Documents.addElement(docElement3, "EXTENDED_PRICE", "2.99");
		Documents.addElement(docElement3, "QUANTITY", "1");
		
		
		//Documents.addElement(docElement, "MERCHANDISE", ""); // NO VALUE
		//Documents.addElement(docElement, "LINE_ITEMS", ""); // NO VALUE
		
		//Documents.addElement(docElement, "TRANSACTION", ""); // NO VALUE
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
		/*
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
*/
	
}
	


public void overrideLineItem() throws Exception {
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 161
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "OVERRIDE");
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", "04.98");
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", "00.43");
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", "05.42");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		Documents.addElement(docElement, "LINE_ITEM_ID", "1"); // You must change the line item to reference the one that must be changed.
		Documents.addElement(docElement, "DESCRIPTION", "TEFLON TAPE"); // description can be changed with override command.
		Documents.addElement(docElement, "QUANTITY", "1");
		Documents.addElement(docElement, "UNIT_PRICE", "2.99");// price can be changed with override command.
		Documents.addElement(docElement, "EXTENDED_PRICE", "2.99");
		Documents.addElement(docElement, "FONT_COL_VALUE", "000000");
//		Documents.addElement(docElement3, "LINE_ITEM_ID", "1");

		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
			
}
	


public void getCounter() throws Exception {

	// Point SCA 4.0 Engage Integration Guide 3.00 Page 79
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
		Documents.addElement(docElement, "COMMAND", "GET_COUNTER");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
			
}

public void removeLineItem() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137

	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "REMOVE");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", "00.00");
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", "00.00");
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", "00.00");
		
		Documents.addElement(docElement, "LINE_ITEM_ID", "2"); // This only removes the second line item if it exists.
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}

public void removeLineItemAll() throws Exception {
	
	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "LINE_ITEM");
		Documents.addElement(docElement, "COMMAND", "REMOVEALL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		Documents.addElement(docElement, "RUNNING_SUB_TOTAL", "00.00");
		Documents.addElement(docElement, "RUNNING_TAX_AMOUNT", "00.00");
		Documents.addElement(docElement, "RUNNING_TRANS_AMOUNT", "00.00");
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}
	

public void requestEmail() throws Exception {
	
	// generate a random ENTRY_CODE
	generator = new Random();
	entryCode = String.valueOf(generator.nextInt(9999));

	// print out entry code to the user through the UI
	System.out.printf("ENTRY_CODE is %s%n", entryCode);
	
//	JOptionPane.showMessageDialog(null, entryCode);
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "EMAIL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}








public void activateGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "ACTIVATE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT/ MERCH_CREDIT");
		Documents.addElement(docElement, "PAYMENT_SUBTYPE", ""); // OPTIONAL
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00"); // MUST BE A NON-ZERO AMOUNT
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		Documents.addElement(docElement, "SAF_FLAG", "");
		
		
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}



public void reactivateGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "REACTIVATE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT/ MERCH_CREDIT");
		Documents.addElement(docElement, "PAYMENT_SUBTYPE", ""); // OPTIONAL
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00"); // MUST BE A NON-ZERO AMOUNT
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "TOT_NUM_CARDS", "");
		
		
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		
		
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}


public void addValueGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "ADD_VALUE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00"); // MUST BE A NON-ZERO AMOUNT
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}








public void checkBalanceGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "BALANCE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT");
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}





public void cashOutGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "GIFT_CLOSE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT");
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}




public void deactivateGiftCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 137
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "DEACTIVATE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "GIFT");
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", ""); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}





public void checkBalanceEBT() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 134
	// This command checks the balance on an EBT card.
	
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "BALANCE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "EBT");
		Documents.addElement(docElement, "EBT_TYPE", "EBT"); // FOOD_STAMP or CASH_BENEFITS -> required for EBT Transactions
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
	
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}










public void tokenQueryCard() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 154
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "TOKEN_QUERY");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT");
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "ACCT_NUM", ""); // OPTIONAL
		Documents.addElement(docElement, "CARD_EXP_MONTH", ""); // OPTIONAL
		Documents.addElement(docElement, "CARD_EXP_YEAR", ""); // OPTIONAL
		Documents.addElement(docElement, "MANUAL_PROMPT_OPTIONS", "ZIP"); // OPTIONAL
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		/*
		Documents.addElement(docElement, "ACCT_NUM", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BARCODE", "");
		Documents.addElement(docElement, "PIN_CODE", "");
		Documents.addElement(docElement, "CVV2", "");
		*/
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}





public void getDeviceName() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 196

	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "GET_DEVICENAME");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
	
}
	
	/**
	 * Tests that the MAC_LABEL is successfully registered
	 * 
	 * @throws Exception
	 */
	public void requestDonation() throws Exception {
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "CHARITY");
		Documents.addElement(docElement, "DISPLAY_TEXT1", "How much do you love me?");
		Documents.addElement(docElement, "AMOUNT1", "Not at all");
		Documents.addElement(docElement, "AMOUNT2", "A little");
		Documents.addElement(docElement, "AMOUNT3", "A lot");
		Documents.addElement(docElement, "POS_RECON", "GET ECHO FROM TERMINAL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	

	
	
	public void authorizeCard() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 100

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "AUTH");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT");
		Documents.addElement(docElement, "AUTH_CODE", "SAMPLE_CODE"); // NOT A REALISTIC VALUE
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "CUSTOMER_STREET", "1099 ST NICHOLAS");
		Documents.addElement(docElement, "CUSTOMER_ZIP", "10032");
		Documents.addElement(docElement, "MANUAL_PROMPT", "");
		Documents.addElement(docElement, "REF_TROUTD", "5.00");
		Documents.addElement(docElement, "REF_CTROUTD", "5.00");
		Documents.addElement(docElement, "RECURRING", "5.00");
		Documents.addElement(docElement, "BILLPAY", "5.00");
		Documents.addElement(docElement, "FORCE_FLAG", "5.00");
		Documents.addElement(docElement, "CAPTURECARD_EARLYRETURN", "5.00");

		// TAX AMOUNTS
		Documents.addElement(docElement, "TAX_AMOUNT", "5.00");
		Documents.addElement(docElement, "TAX_IND", "2"); // 0 = TAX NOT PROVIDED, 2 - TAX = 0.0, 1 - TAX != 0.00
		Documents.addElement(docElement, "CMRCL_FLAG", ""); // B = BUSINESS, C = CORPORATE, P = PURCHASING
		
		
		// KEYED ACCOUTN  INFORMATION FOR GIFT OR CREDIT CARD PAYMENT TYPES ONLY
/*
		Documents.addElement(docElement, "ACCT_NUM", "373953192351004");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "12");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "25");
		Documents.addElement(docElement, "BARCODE","");
		Documents.addElement(docElement, "PIN_CODE","");
		Documents.addElement(docElement, "CVV2", "");
	*/	
		// PROCESSOR BASED TOKEN PROCESSING (CONDITIONAL)
//		Documents.addElement(docElement, "CARD_TOKEN", "");
//		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
//		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
//		Documents.addElement(docElement, "BANK_USERDATA","");
//		Documents.addElement(docElement, "OC_INDUSTRY_CODE","");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
  
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "APPROVED"))) { 
//			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
			JOptionPane.showMessageDialog(null,"APPROVED");
		}
		else {
		
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		}
	}
	
	
	
	
	public void captureCard() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 107
		
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "CAPTURE");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "TIP_AMOUNT", "5.00");
		Documents.addElement(docElement, "POS_RECON", "1234");
		Documents.addElement(docElement, "EBTCASH_ELEGIBLE", "1234");
		Documents.addElement(docElement, "EBTSNAP_ELEGIBLE", "1234");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT");
		Documents.addElement(docElement, "PAYMENT_SUBTYPE", "CREDIT");
		Documents.addElement(docElement, "EBT_TYPE", "TRUE");
		Documents.addElement(docElement, "AUTH_CODE", "TRUE");
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "CUSTOMER_STREET", "1099 ST NICHOLAS");
		Documents.addElement(docElement, "CUSTOMER_ZIP", "10032");
		Documents.addElement(docElement, "MANUAL_PROMPT", "");
		Documents.addElement(docElement, "PAYMENT_TYPES", "CREDIT|DEBIT|GIFT|FSA");
		
		Documents.addElement(docElement, "REF_TROUTD", "5.00");
		Documents.addElement(docElement, "REF_CTROUTD", "5.00");
		Documents.addElement(docElement, "RECURRING", "5.00");
		Documents.addElement(docElement, "BILLPAY", "5.00");
		Documents.addElement(docElement, "FORCE_FLAG", "5.00");
		Documents.addElement(docElement, "CAPTURECARD_EARLYRETURN", "5.00");

		// TAX AMOUNTS
		Documents.addElement(docElement, "TAX_AMOUNT", "5.00");
		Documents.addElement(docElement, "TAX_IND", "2"); // 0 = TAX NOT PROVIDED, 2 - TAX = 0.0, 1 - TAX != 0.00
		Documents.addElement(docElement, "CMRCL_FLAG", ""); // B = BUSINESS, C = CORPORATE, P = PURCHASING
		
		
		// KEYED ACCOUTN  INFORMATION FOR GIFT OR CREDIT CARD PAYMENT TYPES ONLY
/*
		Documents.addElement(docElement, "ACCT_NUM", "A lot");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "12");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "49");
		Documents.addElement(docElement, "BARCODE","");
		Documents.addElement(docElement, "PIN_CODE","");
		Documents.addElement(docElement, "CVV2", "");

		// PROCESSOR BASED TOKEN PROCESSING (CONDITIONAL)
		Documents.addElement(docElement, "CARD_TOKEN", "A lot");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "12");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "49");
		Documents.addElement(docElement, "BANK_USERDATA","");
		Documents.addElement(docElement, "OC_INDUSTRY_CODE","");
			*/	

		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
  
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	
	
	

	public void creditCard() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 118

		// This transactions returns funds to a customers account.
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "CREDIT");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT"); // CREDIT|DEBIT|GIFT|EBT\CHECK\ALIPAY/KLARNA/WECHAT/PAYPAL/VENMO
		Documents.addElement(docElement, "MANUAL_ENTRY", "TRUE");
		Documents.addElement(docElement, "CTROUTD", "TRUE"); // DO NOT USE IF PAYMENT_TYPE=DEBIT
		Documents.addElement(docElement, "FORCE_FLAG", "TRUE");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// KEYED ACCOUTN  INFORMATION FOR GIFT OR CREDIT CARD PAYMENT TYPES ONLY
		Documents.addElement(docElement, "ACCT_NUM", "A lot");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "12");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "49");
		Documents.addElement(docElement, "BARCODE","");
		Documents.addElement(docElement, "PIN_CODE","");
		Documents.addElement(docElement, "CVV2", "");
	
		Documents.addElement(docElement, "AMOUNT_HEALTHCARE", "");
		Documents.addElement(docElement, "AMOUNT_PRESCRIPTION", "");
		Documents.addElement(docElement, "AMOUNT_VISION", "");
		Documents.addElement(docElement, "AMOUNT_CLINIC", "");
		Documents.addElement(docElement, "AMOUNT_DENTAL", "");

		Documents.addElement(docElement, "CARD_TOKEN", "");
		Documents.addElement(docElement, "CARD_EXP_MONTH", "");
		Documents.addElement(docElement, "CARD_EXP_YEAR", "");
		Documents.addElement(docElement, "BANK_USERDATA", "");
		Documents.addElement(docElement, "OC_INDUSTRY_CODE", "");
  
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	
public void voidTransaction() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 125

		// This transactions returns funds to a customers account.
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "void");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "CTROUTD", "TRUE"); // DO NOT USE IF PAYMENT_TYPE=DEBIT
		Documents.addElement(docElement, "EMV_REVERSAL_TYPE", "401"); // CREDIT|DEBIT|GIFT|EBT\CHECK\ALIPAY/KLARNA/WECHAT/PAYPAL/VENMO
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	

public void addTip() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 128

		// This transactions returns funds to a customers account.
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "ADD_TIP");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT"); // CREDIT|DEBIT|GIFT
		Documents.addElement(docElement, "CTROUTD", " "); // Transaction ID to which tip will be attached. NOTE: Not used for SVS. SVS requires swiped track data or keyed account number
		Documents.addElement(docElement, "TIP_AMOUNT", "5.00");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}


public void removeTip() throws Exception {
	
	// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 130

	// This transactions removes the tip from a transaction.
	
	// get counter and calculate mac
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
	// build request
	request = Documents.create("TRANSACTION");
	docElement = request.getDocumentElement();
	Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
	Documents.addElement(docElement, "COMMAND", "RESET_TIP");
	Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT"); // CREDIT|DEBIT|GIFT
	Documents.addElement(docElement, "CTROUTD", " "); // Transaction ID to which tip will be attached. NOTE: Not used for SVS. SVS requires swiped track data or keyed account number
	Documents.addElement(docElement, "COUNTER", nextCounter);
	Documents.addElement(docElement, "MAC", mac);
	Documents.addElement(docElement, "MAC_LABEL", macLabel);
	
	// transmit to Point Solution and interrogate the response
	responseXml = send(address, port, request);
	responseDocElement = responseXml.getDocumentElement();

	// validate that the RESULT_CODE came back a SUCCESS
	if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
		throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}
}






	//
	public void cancelTransaction() throws Exception {

		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 83

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "CANCEL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	public void rebootDevice() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 85

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "REBOOT");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	public void getStatus() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 87 -> Get device Status

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "STATUS");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	public void getStatusSAF() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 93

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "STATUS");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}	
	
	
	public void checkForUpdates() throws Exception {
		
		// SCA 4.0 ENGAGE INTEGRATION GUIDE 3.00 PAGE 95

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "ANY_UPDATES");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}	
	
	
public void checkUpdateStatus() throws Exception {
		
		// Point SCA 4.0 Engage Integration Guide 3.00 Page 96
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
		Documents.addElement(docElement, "COMMAND", "UPDATE_STATUS");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
//		Document responseXml = send(address, port, request); 
		responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}	
	



public void applyUpdates() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 82
	
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
	// build request
	request = Documents.create("TRANSACTION");
	docElement = request.getDocumentElement();
	
	Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
	Documents.addElement(docElement, "COMMAND", "APPLYUPDATES");
	Documents.addElement(docElement, "FLAG", "TRUE");
	Documents.addElement(docElement, "COUNTER", nextCounter);
	Documents.addElement(docElement, "MAC", mac);
	Documents.addElement(docElement, "MAC_LABEL", macLabel);
	
	// transmit to Point Solution and interrogate the response
//	Document responseXml = send(address, port, request); 
	responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
	responseDocElement = responseXml.getDocumentElement();

	// validate that the RESULT_CODE came back a SUCCESS
	if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
		throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}
}	

	
public void confirmationAPMKlarna() throws Exception {
	
	// Point SCA 4.0 Engage Integration Guide 3.00 Page 87
	
	nextCounter = String.valueOf(++counter);
	mac = printMacAsBase64(macKey, nextCounter);
	System.out.format("COUNTER is 1. MAC is %s%n", mac);
	
	// build request
	request = Documents.create("TRANSACTION");
	docElement = request.getDocumentElement();
	Documents.addElement(docElement, "FUNCTION_TYPE", "SECONDARYPORT");
	Documents.addElement(docElement, "COMMAND", "CONFIRMATION");
	Documents.addElement(docElement, "VALUE", "CONFIRMED"); // Possible Values: CONFIRMED/DENIED
	Documents.addElement(docElement, "COUNTER", nextCounter);
	Documents.addElement(docElement, "MAC", mac);
	Documents.addElement(docElement, "MAC_LABEL", macLabel);
	
	// transmit to Point Solution and interrogate the response
//	Document responseXml = send(address, port, request); 
	responseXml = send(address, secondary_port, request); // Added this line using the secondary port on 7/26/23
	responseDocElement = responseXml.getDocumentElement();

	// validate that the RESULT_CODE came back a SUCCESS
	if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
		throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}
}	



	public void setIdleScreen() throws Exception {
		
		// Point SCA 4.0 Engage Integration Guide 3.00 Page 80

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		temp = "";
		temp = JOptionPane.showInputDialog(null,"Provide message for Idle Screen");

		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
		Documents.addElement(docElement, "COMMAND", "LANE_CLOSED");
		Documents.addElement(docElement, "DISPLAY_TEXT", temp);
		Documents.addElement(docElement, "FONT_COL_VALUE", "000000");
		Documents.addElement(docElement, "FONT_SIZE", "50");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		
		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
		
	
	
	
	
	
	/**
	 * Tests that the MAC_LABEL is successfully registered
	 * 
	 * @throws Exception
	 */
	public void testMac() throws Exception {
		
		// Point SCA 4.0 Engage Integration Guide 3.00 Page 

		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "TEST_MAC");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	
	
	public void laneClosed() throws Exception {
		
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
		Documents.addElement(docElement, "COMMAND", "LANE_CLOSED");
		Documents.addElement(docElement, "DISPLAY_TEXT", "Lane is Closed.");
		Documents.addElement(docElement, "FONT_COL_VALUE", "FF0000");
		Documents.addElement(docElement, "FONT_SIZE", "50");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
/*
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
*/
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
	}


	
	public void lastTransaction() throws Exception {
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        
		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);

		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "REPORT");
		Documents.addElement(docElement, "COMMAND", "LAST_TRAN");
//		Documents.addElement(docElement, "ENTRY_CODE", entryCode);
//		Documents.addElement(docElement, "KEY", PUBLIC_KEY);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
/*
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
*/
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
	}
	
	
	
	
	public void captureCardEarlyReturn() throws Exception {
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
//		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "CAPTURE");
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.42");
		Documents.addElement(docElement, "CAPTURECARD_EARLYRETURN", "TRUE");
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "FORCE_FLAG", "FALSE");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
/*
		// validate that the MAC_LABEL was returned
		macLabel = Documents.selectFirst(responseDocElement, "MAC_LABEL", null);
		if (null == macLabel || macLabel.isEmpty())  {
			throw new Exception("MAC_LABEL was not returned");
		}
		// validate that the MAC_KEY was returned
		encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
*/
	}
	
	
	
	
	public void displayMessage() throws Exception {
		// generate a random ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		// get counter and calculate mac
		nextCounter = String.valueOf(++counter);
		mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "DISPLAY_MESSAGE");
		Documents.addElement(docElement, "DISPLAY_TEXT", "BALANCE DUE: $ 0.00");
		Documents.addElement(docElement, "TIMEOUT_DATA", "10");
		Documents.addElement(docElement, "BUTTON_DISPLAY", "0");
		Documents.addElement(docElement, "BUTTON_LABEL", "OKAY");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) {
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}

	}
	
	
	
	
	
	
	
	
	

	/**
	 * Tests that the MAC_LABEL is successfully registered
	 * 
	 * @throws Exception
	 */
	public void testCases() throws Exception {
		
		// get counter and calculate mac
		String nextCounter = String.valueOf(++counter);
		String mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		Document request = Documents.create("TRANSACTION");
		Element docElement = request.getDocumentElement();

		
		
		// Start Session
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();

		
		Documents.addElement(docElement, "FUNCTION_TYPE", "SESSION");
		Documents.addElement(docElement, "COMMAND", "START");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "LANE", "1");
		Documents.addElement(docElement, "STORE_NUM", "27");
		Documents.addElement(docElement, "INVOICE", "1");
		Documents.addElement(docElement, "SWIPE_AHEAD", "0");
		

		Document responseXml = send(address, port, request);
		Element responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
		
		
		
		// Start Session
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SESSION");
		Documents.addElement(docElement, "COMMAND", "FINISH");
//		Documents.addElement(docElement, "ENTRY_CODE", entryCode);
//		Documents.addElement(docElement, "KEY", PUBLIC_KEY);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}

		
		/*
		// REQUEST THE INFORMATION FOR THE LAST TRANSACTION
		Documents.addElement(docElement, "FUNCTION_TYPE", "REPORT");
		Documents.addElement(docElement, "COMMAND", "LAST_TRAN");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
*/
		
		/*
		
		// This code starts a session with the verifone system.

		Documents.addElement(docElement, "FUNCTION_TYPE", "SESSION");
		Documents.addElement(docElement, "COMMAND", "START");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "LANE", "1");
		Documents.addElement(docElement, "STORE_NUM", "27");
		Documents.addElement(docElement, "INVOICE", "TA1234");
		Documents.addElement(docElement, "SWIPE_AHEAD", "1");
		
		
		*/

		
		/*

		// This code finishes a session with the verifone system.
		Documents.addElement(docElement, "FUNCTION_TYPE", "SESSION");
		Documents.addElement(docElement, "COMMAND", "FINISH");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		*/
		
		
		
		
		/*
		 * 
		 * 
		 *
		
				// This code is to return funds to a customers account.
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "CAPTURE");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT"); 
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "TRANS_AMOUNT", "5.00");
		Documents.addElement(docElement, "MANUAL_ENTRY", "FALSE");
		Documents.addElement(docElement, "TAX_IND", "1");
		Documents.addElement(docElement, "RETAIL_ITEM_DESC_1", "FOOD 1");
		Documents.addElement(docElement, "PAYMENT_TYPES", "CREDIT");
		Documents.addElement(docElement, "ENCRYPT", "TRUE");
		Documents.addElement(docElement, "FORCE_FLAG", "FALSE");
		Documents.addElement(docElement, "RECURRING", "N");
		Documents.addElement(docElement, "BILLPAY", "N");
		Documents.addElement(docElement, "OC_INDUSTRY_CODE", "M");
		Documents.addElement(docElement, "SCMCI_INDICATOR", "2");
		Documents.addElement(docElement, "POS_RECON", "GET ECHO FROM TERMINAL");

		
		
		 * 
		 * 
		 */
		
		
		
		
		
		
		/* Not working yet, Session is required, this code makes a Request the token for the credit card payment.
		Documents.addElement(docElement, "FUNCTION_TYPE", "PAYMENT");
		Documents.addElement(docElement, "COMMAND", "TOKEN_QUERY");
		Documents.addElement(docElement, "PAYMENT_TYPE", "CREDIT");
		Documents.addElement(docElement, "ENCRYPT", "TRUE");
		Documents.addElement(docElement, "POS_RECON", "GET ECHO FROM TERMINAL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		*/
		
		/* Emoji's not working, figure it out.
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "CUST_QUESTION");
		Documents.addElement(docElement, "DISPLAY_TEXT_TOP", "Please provide your feedback?");
		Documents.addElement(docElement, "EMOJI1", "1F600");
		Documents.addElement(docElement, "EMOJI2", "1F614");
		Documents.addElement(docElement, "EMOJI3", "1F621");
		Documents.addElement(docElement, "DISPLAY_TEXT1", "Want coupons?");
		Documents.addElement(docElement, "AMOUNT1", "Yes");
		Documents.addElement(docElement, "AMOUNT2", "No");
		Documents.addElement(docElement, "POS_RECON", "GET ECHO FROM TERMINAL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		*/
		
		/*
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "CHARITY");
		Documents.addElement(docElement, "DISPLAY_TEXT1", "How much do you love me?");
		Documents.addElement(docElement, "AMOUNT1", "Not at all");
		Documents.addElement(docElement, "AMOUNT2", "A little");
		Documents.addElement(docElement, "AMOUNT3", "A lot");
		Documents.addElement(docElement, "POS_RECON", "GET ECHO FROM TERMINAL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		*/
		
		/* 
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "CHARITY");
		Documents.addElement(docElement, "DISPLAY_TEXT1", "Donate?");
		Documents.addElement(docElement, "AMOUNT1", "1.00");
		Documents.addElement(docElement, "AMOUNT2", "5.00");
		Documents.addElement(docElement, "AMOUNT3", "10.00");
		Documents.addElement(docElement, "AMOUNT4", "100 .00");
		Documents.addElement(docElement, "POS_RECON", "GET ECHO FROM TERMINAL");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);


		*/
		/* This code works and returns the name of the device.
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "GET_DEVICENAME");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
*/		
		
		/* Can only be completed during an active session
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "DISPLAY_LEFTPANEL");
		Documents.addElement(docElement, "POS_RECON", "1234");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		*/
/*		
		Documents.addElement(docElement, "FUNCTION_TYPE", "DEVICE");
		Documents.addElement(docElement, "COMMAND", "DISPLAY_MESSAGE");
		Documents.addElement(docElement, "DISPLAY_TEXT", "DO YOU LOVE ME?");
		Documents.addElement(docElement, "TIMEOUT_DATA", "10");
		Documents.addElement(docElement, "BUTTON_LABEL", "YES");
		Documents.addElement(docElement, "BUTTON_DISPLAY", "1");
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		*/
		
		
		
		/* 
		 * 


		// This code works 06182023 - It shows the message by sliding it from right to left on the screen.
		Documents.addElement(docElement, "FUNCTION_TYPE", "ADMIN");
		Documents.addElement(docElement, "COMMAND", "LANE_CLOSED");
		Documents.addElement(docElement, "DISPLAY_TEXT", "Welcome to 165 Hardware");
		Documents.addElement(docElement, "FONT_COL_VALUE", "000000");
		Documents.addElement(docElement, "FONT_SIZE", "50");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		


		 * 
		 * 
		 * Makes the screen white but does not show the text.
		 * 	
		Documents.addElement(docElement, "FUNCTION_TYPE", "admin");
		Documents.addElement(docElement, "COMMAND", "LANE_CLOSED");
		Documents.addElement(docElement, "DISPLAY_TEXT", "Welcome to 165 Hardware");
		Documents.addElement(docElement, "FONT_COL_VALUE", "000000");
		Documents.addElement(docElement, "FONT_SIZE", "50");
		
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		 */
		
		/*
		
*/
		
		// transmit to Point Solution and interrogate the response
		
		/*
		Document responseXml = send(address, port, request);
		Element responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
		
		*/
	}
	

	
	
	
	
	
	
	
	/**
	 * Unregisters the POS with the Device
	 * 
	 * @throws Exception
	 */
	public void unregisterPOS() throws Exception {
		// generate an ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));
		
		// print out entry code to the user through the UI
		System.out.format("ENTRY_CODE is %s%n", entryCode);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "UNREGISTER");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "ENTRY_CODE", entryCode);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099")))
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}

	
	
	public void unregisterPOSAll() throws Exception {
		// generate an ENTRY_CODE
		generator = new Random();
		entryCode = String.valueOf(generator.nextInt(9999));
		
		// print out entry code to the user through the UI
		System.out.format("ENTRY_CODE is %s%n", entryCode);
		
		// build request
		request = Documents.create("TRANSACTION");
		docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "UNREGISTERALL");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);

		// transmit to Point Solution and interrogate the response
		responseXml = send(address, port, request);
		responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099")))
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Calculates the MAC of the COUNTER and base 64 encodes it as a String
	 */
	private static String printMacAsBase64(byte[] macKey, String counter) throws Exception {
		// import AES 128 MAC_KEY
		SecretKeySpec signingKey = new SecretKeySpec(macKey, "AES");
		
		// create new HMAC object with SHA-256 as the hashing algorithm
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		// integer -> string -> bytes -> encrypted bytes
		byte[] counterMac = mac.doFinal(counter.getBytes("UTF-8"));

		// base 64 encoded string
		return DatatypeConverter.printBase64Binary(counterMac);
	}
	
	/**
	 * Sends an Document over socket to the address and port and returns
     *  the response as a Document
	 */
	private static Document send(String address, int port, Document request) throws Exception {
		Socket socket = null;
		try {
			// create new socket
			System.out.format("request: %n%s%n", Documents.print(request, true));
			socket = new Socket(address, port);
			Documents.write(request, socket.getOutputStream());
			
			final Document response = Documents.parse(new BufferedInputStream(socket.getInputStream()));
			System.out.format("response: %n%s%n", Documents.print(response, true));
			return response;
		} finally {
			// close socket if not closed
			if (!(socket == null || socket.isClosed())) {
				try {
					socket.close();
				} catch (IOException e) {
					// log error
					e.printStackTrace();
				}
			}
		}
	}

}
