package gift.of.silence.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress ipAddress = InetAddress.getByName("localhost");
            
            String request = null;
            while (request == null || !request.equals("exit")) {
                try {
                    request = console.readLine();
                    byte[] sendData = request.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 43397);
                    socket.send(sendPacket);
                    DatagramPacket receivePacket = new DatagramPacket(new byte[508], 508);
                    socket.receive(receivePacket);
                    String response = new String(receivePacket.getData());
                    Logger.getLogger(Client.class.getName()).log(Level.INFO, "response: {0}", response);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
