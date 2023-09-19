import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class VerifoneM400StatusChecker {
    public static void main(String[] args) {
        String ipAddress = "192.168.50.197";
        int port = 22; // You may need to change the port number to the appropriate one for the Verifone M400 device

        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            Socket socket = new Socket(address, port);
            socket.close();
            System.out.println("The Verifone M400 device at " + ipAddress + " is online and reachable.");
        } catch (IOException e) {
            System.out.println("The Verifone M400 device at " + ipAddress + " is offline or unreachable.");
        }
    }
}