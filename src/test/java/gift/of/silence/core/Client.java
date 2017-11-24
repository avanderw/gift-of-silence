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
        System.out.println("o-client: running");
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(256);
            InetAddress ipAddress = InetAddress.getByName("localhost");

            String request = null;
            while (request == null || !request.equals("disconnect")) {
                try {
                    request = console.readLine();
                    byte[] sendData = request.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 43397);
                    socket.send(sendPacket);
                    DatagramPacket receivePacket = new DatagramPacket(new byte[508], 508);
                    socket.receive(receivePacket);
                    String response = new String(receivePacket.getData());
                    System.out.println(String.format("%s", new Object[]{response}));
                } catch (IOException ex) {
                    System.out.println(String.format("?-%s", new Object[]{ex.getMessage()}));
                }
            }
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
