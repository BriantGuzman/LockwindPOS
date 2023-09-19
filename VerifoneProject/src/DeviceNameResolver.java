import java.net.InetAddress;
import java.net.UnknownHostException;

public class DeviceNameResolver {
    public static void main(String[] args) {
        String ipAddress = "192.168.50.180";

        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            String deviceName = address.getHostName();
            System.out.println("Device name for IP address " + ipAddress + " is: " + deviceName);
        } catch (UnknownHostException e) {
            System.out.println("Unable to resolve the device name for IP address " + ipAddress);
        }
    }
}
