package gift.of.silence.network;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Network {

    public static DatagramSocket socket;
    private PortListener portListener;

    public Network() {
        Integer serverPort = 43397;
        try {
            socket = new DatagramSocket(serverPort);
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void startPortListener() {
        portListener = new PortListener();
        portListener.start();
    }

    public void stopPortListener() {
        portListener.stop();
    }
}
