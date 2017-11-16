package gift.of.silence.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private volatile Thread thread;

    private final Map<InetAddress, IConnectedSystem> connected = new HashMap();
    private final Game game = new Game();

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

        System.out.println("server started");
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
            String message = new String(receivePacket.getData());
            message = message.trim();

            byte[] sendData = null;
            DatagramPacket sendPacket = null;
            if (connected.containsKey(ipAddress)) {
                System.out.println(String.format("->%s:%d %s", new Object[]{ipAddress, port, message}));

                switch (message) {
                    case "play":
                        game.start();
                        sendData = "game playing".getBytes();
                        break;
                    case "pause":
                        game.stop();
                        sendData = "game pausing".getBytes();
                        break;
                    case "disconnect":
                        connected.remove(ipAddress);
                        game.remove(SubSystem.HELM);
                        sendData = "disconnecting".getBytes();
                        break;
                    default:
                        sendData = connected.get(ipAddress).onMessage(message).getBytes();
                }
            } else {
                System.out.println(String.format("o-%s:%d %s", new Object[]{ipAddress, port, message}));
                switch (message) {
                    case "helm":
                        connected.put(ipAddress, SubSystem.HELM);
                        game.simulate(SubSystem.HELM);
                        sendData = "helm registered".getBytes();
                        break;
                    default:
                        sendData = "role?".getBytes();
                }
            }

            System.out.println(String.format("<-%s:%d %s", new Object[]{ipAddress, port, new String(sendData)}));
            sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            try {
                socket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("server closed");
    }
}
