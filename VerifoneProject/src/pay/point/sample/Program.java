
package pay.point.sample;
import javax.swing.JOptionPane;

public class Program {
	public static void main(String[] args) {
		System.out.println("Program enter");
		
		// parse command line arguments
//		String address = "127.0.0.1";
		String address = "192.168.50.197";
		int port = 5015;
		int secondary_port = 5016; 
		for (String arg : args) {
			if (arg.startsWith("address=")) {
				address = arg.split("=", 2)[1];
			} else if (arg.startsWith("port=")) {
				try {
					port = Integer.parseInt(arg.split("=", 2)[1]);
				} catch (NumberFormatException e) {
					System.err.println("port must be numeric, using default of 5015");
				}
			} 
		}
		
		// execute sample client
		try
		{
			executeSample(address, port,secondary_port);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			System.out.println("Program exit");
		}
	}
	
	private static void executeSample(String address, int port, int secondary_port) throws Exception
	{
		System.out.printf("Creating sample client at %s:%d%n", address, port,secondary_port);
	//	SampleClientRegisterEncryption client = new SampleClientRegisterEncryption(address, port);
		SessionManager client = new SessionManager(address, port,secondary_port);
		client.setVisible(true);

//		client.registerPOS();
//		System.out.println("Successfully registered");
		

		
//		client.testMac();
//		System.out.println("Successfully returned version");
		// client.testMac();
		// System.out.println("Successfully tested MAC");
		
//		client.unregisterPOS();
//		System.out.println("Successfully unregistered");
	}
}
