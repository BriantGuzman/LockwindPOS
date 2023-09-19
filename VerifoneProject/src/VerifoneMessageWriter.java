import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class VerifoneMessageWriter {
    public static void main(String[] args) {
        String ipAddress = "192.168.50.197";
        int port = 2010; // Default port for Verifone M400

        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            Socket socket = new Socket(address, port);

            // Create an output stream to send the message to the Verifone M400 device
            OutputStream outputStream = socket.getOutputStream();

            // Write the message to the output stream
            String message = "Hello, World!";
            outputStream.write(message.getBytes());
            outputStream.flush();

            // Close the socket connection
            socket.close();

            System.out.println("Message sent to Verifone M400 device at " + ipAddress);
        } catch (Exception e) {
            System.out.println("Error sending message to Verifone M400 device at " + ipAddress);
            e.printStackTrace();
        }
    }
}