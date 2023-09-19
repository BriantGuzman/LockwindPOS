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
 * @author Chris_w9
 *
 */
public class SampleClient {
	
	private static final String PUBLIC_KEY = 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAugflCYHdNLX1PK+2JpedLoL4JdwpapkpwoSIYOhBF9FFri+roRTqPyyosLFGMMnO5l65z9YY1cQYSENWfhLvPROD2Oruyl1k2wSYWT+23wTB0jJjA4ktk7Q2cErNzMNiLLP0tB3rOYJHxC1HjskKBmkblF5ZDeCNzVyeEdF37zfCDD5bBIjPSpmLgH1swDQIvpULhwhmyf1AaJX+oaaCQgu6wxrbP17auMJzAjhddwUgIbkCiAEcYu8fwyTXQWFcQtfA3nufCITAcI7jmtxrXKqKWgZ23oIgvmIM1y9l6Bp9QT8MvDn63wfj54fyOW5Jb66G19x/xVGF5lH68qPErwIDAQAB";
	private static final String PRIVATE_KEY = 
			"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6B+UJgd00tfU8r7Yml50ugvgl3ClqmSnChIhg6EEX0UWuL6uhFOo/LKiwsUYwyc7mXrnP1hjVxBhIQ1Z+Eu89E4PY6u7KXWTbBJhZP7bfBMHSMmMDiS2TtDZwSs3Mw2Iss/S0Hes5gkfELUeOyQoGaRuUXlkN4I3NXJ4R0XfvN8IMPlsEiM9KmYuAfWzANAi+lQuHCGbJ/UBolf6hpoJCC7rDGts/Xtq4wnMCOF13BSAhuQKIARxi7x/DJNdBYVxC18Dee58IhMBwjuOa3GtcqopaBnbegiC+YgzXL2XoGn1BPwy8OfrfB+Pnh/I5bklvrobX3H/FUYXmUfryo8SvAgMBAAECggEAMmmxR8JJj98/dhKn6g1sKw6S8K+ZCao4Bt6jlp9aBHpRx8JjYGOqlzQjAr8HpnEKAKPq9seuMz/Q1MRqy/+VlZeUQ1RnIa/thOzZ3FXH2OgRHkVJT8v87eoIVqXu326TTEn4Jld1R0Bm8mLS4X7ZmKMjNjHbMEeKJfzTWUDKn6imPU5/mkJIoCVNi1CM+A8QTxoCFhWzdxHj5GCExAzQJdTFDHLEDygZOjX5iGSRenYYc5dxLutroWc8a9XPuftPIBooPCYAsRbUYUE16bXosQ38aO8lU3E1tyCD0nj0sq1Oiiuw/UJmFizj9pJUzCJpWDgk4wqzOffOf2gI+5rhAQKBgQDfkOUUp0Qzo1h/MJ49BGqSLZZ1pl/UEsGhVeV2RKx7TukOq0Lj9tChAnhouA6GsjR9AG8AWiZCN4ZkMlRVHAAppkk/6WFE4FHkmvWlUmNsNdwY5z4Ww5tggywg4j9WPif5FjWfvq7V/UYvl24mJ1XChAh3HGOX674DPfyLBgvfWwKBgQDVBPeIMs4FtVo4WoIfdx3A2xvbwd9TVsCJiLV5iXcy9ksbPVLif7tO5ZR63GkevVNc4PHWupaqA3f8VCBe3M12e6xdlH59zXPRVubczK8pBqcKt84qj7yk5Y6OgiFQEp/u16G2M7FhagQ14J2N0pGHdYtZmn+A694c2HD98LBkPQKBgQC7Wgiv0zCzeXrrM8oX5kCM+ckyFNgPuBwuYPZns0s8Frf2RA1NTwQtOg2/7Ca4OFUGQDvFdsbDDRcBlq/XlxyHysNt3N1XxAi85CNhhPaus0AcWoVMvGXUbninohJj6rjC5BrSIRERYSvVLDjxnlsfJFiXwOGxaayVuPePZeTDKwKBgQCtX+mHtLHx+3R+wUt/CJfyy2KVLenyDn2OcvIhBT07ATKH7RV0u7lbsYdzp8j29+jNg1fSCPNvVHtnp6DhFJ01fdsAH0gEZB+Llks4Em/N2FhEZO0rvuku3Jd2bXtnjIEXB/HaNaB9RKhAoZwaPfOsaIMOXqy/5TlWCOOOC0PFkQKBgGB7PkgYnGg0R610g0JaXJJ3kaeGnZYJapXgUF6ZDKz6IQI1ufhyQ29tqSxSW2GydLQRFFr4pXEEoYCGGO5KVkP5bGe/3Hyj4O82JiOkwFyVtNp3yjhPFbwlOzgQnAHEbmpidLTBP3THDtqNWGb1U586pNuxDpIMQrGR9Gva0OzK";
	
	private String macLabel = "";
	private byte[] macKey = new byte[0];
	private int counter = 0;
	
	private final String address;
	private final int port;
	
	/**
	 * Initializes a new Client object
	 * 
	 * @param address remote IPv4 address of the Device
	 * @param port remote TCP Port of the Device
	 */
	public SampleClient(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	/**
	 * Registers the POS with the device
	 * 
	 * @throws Exception
	 */
	public void register() throws Exception {
		// generate a random ENTRY_CODE
		Random generator = new Random();
		String entryCode = String.valueOf(generator.nextInt(9999));

		// print out entry code to the user through the UI
		System.out.printf("ENTRY_CODE is %s%n", entryCode);
		
		JOptionPane.showMessageDialog(null, entryCode);

		// generate an RSA 2048 Public/Private keypair
		//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		//keyGen.initialize(2048);
		//KeyPair keypair = keyGen.genKeyPair();
		
		// load RSA 2048 Keys from strings
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        final PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(PRIVATE_KEY)));
        final PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(PUBLIC_KEY)));
		final KeyPair keypair = new KeyPair(publicKey, privateKey);

		System.out.format("Public key is (as base64) %s%n", PUBLIC_KEY);
		System.out.format("Private key is (as base64) %s%n", PRIVATE_KEY);

		// build request
		Document request = Documents.create("TRANSACTION");
		Element docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "REGISTER");
		Documents.addElement(docElement, "ENTRY_CODE", entryCode);
		Documents.addElement(docElement, "KEY", PUBLIC_KEY);

		// transmit to Point Solution and interrogate the response
		Document responseXml = send(address, port, request);
		Element responseDocElement = responseXml.getDocumentElement();

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
		String encryptedMACKey = Documents.selectFirst(responseDocElement, "MAC_KEY", null);
		if (null == encryptedMACKey || encryptedMACKey.isEmpty()) {
			throw new Exception("MAC_KEY was not returned");
		}
		
		// base 64 decode MAC_KEY
		byte[] macKeyBase64Decoded = DatatypeConverter.parseBase64Binary(encryptedMACKey);

		// initialize cipher
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

		// Decrypt MAC_KEY with Private RSA Key
		macKey = cipher.doFinal(macKeyBase64Decoded);
		System.out.format("Decrypted MAC_KEY is (as base64) %s%n", DatatypeConverter.printBase64Binary(macKey));
	}

	/**
	 * Tests that the MAC_LABEL is successfully registered
	 * 
	 * @throws Exception
	 */
	public void testMac() throws Exception {
		
		// get counter and calculate mac
		String nextCounter = String.valueOf(++counter);
		String mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		Document request = Documents.create("TRANSACTION");
		Element docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "TEST_MAC");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);

		// transmit to Point Solution and interrogate the response
		Document responseXml = send(address, port, request);
		Element responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}
	}
	

	
	
	
	
	
	public void getVersion() throws Exception {
		
		System.out.println("@SampleClient.getVersion() -> Starting function");


		String nextCounter = String.valueOf(++counter);
		String mac = printMacAsBase64(macKey, nextCounter);
		System.out.format("COUNTER is 1. MAC is %s%n", mac);
		
		// build request
		Document request = Documents.create("TRANSACTION");
		Element docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "TEST_MAC");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "COUNTER", nextCounter);
		Documents.addElement(docElement, "MAC", mac);

		// transmit to Point Solution and interrogate the response
		Document responseXml = send(address, port, request);
		Element responseDocElement = responseXml.getDocumentElement();

		// validate that the RESULT_CODE came back a SUCCESS
		if (!"-1".equals(Documents.selectFirst(responseDocElement, "RESULT_CODE", "990099"))) { 
			throw new Exception(Documents.selectFirst(responseDocElement, "RESPONSE_TEXT", "unknown error"));
		}

	
	
	}
	
	
	
	
	
	
	/**
	 * Unregisters the POS with the Device
	 * 
	 * @throws Exception
	 */
	public void unregister() throws Exception {
		// generate an ENTRY_CODE
		Random generator = new Random();
		String entryCode = String.valueOf(generator.nextInt(9999));
		
		// print out entry code to the user through the UI
		System.out.format("ENTRY_CODE is %s%n", entryCode);
		
		// build request
		Document request = Documents.create("TRANSACTION");
		Element docElement = request.getDocumentElement();
		Documents.addElement(docElement, "FUNCTION_TYPE", "SECURITY");
		Documents.addElement(docElement, "COMMAND", "UNREGISTER");
		Documents.addElement(docElement, "MAC_LABEL", macLabel);
		Documents.addElement(docElement, "ENTRY_CODE", entryCode);

		// transmit to Point Solution and interrogate the response
		Document responseXml = send(address, port, request);
		Element responseDocElement = responseXml.getDocumentElement();

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
