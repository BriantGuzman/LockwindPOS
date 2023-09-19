package pay.point.sample;

// import javax.mail.Message;

public class Demo implements Runnable {

		
	public void run() {

		ElectronicDocument document = new ElectronicDocument();
		document.setTransactionType("invoice");
		document.setTransactionNumber("20");
		
		System.out.println(document);
		
		System.exit(0);
	}
	
	public static void main(String[] args) { 
		
		Demo test = new Demo();
		test.run();
		
		
	}
}
