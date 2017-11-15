package gift.of.silence.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private volatile Thread thread;

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread = null;
    }

    @Override
    public void run() {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(43397);
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }

        while (thread == Thread.currentThread()) {
            DatagramPacket receivePacket = new DatagramPacket(new byte[508], 508);
            try {
                socket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                continue;
            }
            InetAddress ipAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            String request = new String(receivePacket.getData());
            request = request.trim();
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "received:: {0} lies mother fucker", request);

            if (request.equals("exit")) {
                this.stop();
                System.out.println("stopping");
            }

            byte[] sendData = request.toUpperCase().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            try {
                socket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
