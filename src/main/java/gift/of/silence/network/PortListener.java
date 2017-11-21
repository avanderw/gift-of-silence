package gift.of.silence.network;

import gift.of.silence.core.Server;
import gift.of.silence.core.SubSystem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortListener implements Runnable {

    private final Map<InetAddress, Map<Integer, PacketHandler>> connected = new HashMap();
    private volatile Thread thread;

    void start() {
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        thread = null;
    }

    @Override
    public void run() {
        Integer serverPort = 43397;
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(serverPort);
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }

        ExecutorService packetHandlers = Executors.newCachedThreadPool();
        System.out.println(String.format("%s:%s listening", socket.getInetAddress(), socket.getPort()));
        while (thread == Thread.currentThread()) {
            DatagramPacket packet = new DatagramPacket(new byte[508], 508);
            try {
                socket.receive(packet);
                packetHandlers.execute(new PacketHandler(packet));
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
                continue;
            }
            
            

            byte[] sendData = null;
            DatagramPacket sendPacket = null;
            if (connected.containsKey(ipAddress)) {
                System.out.println(String.format("->%s:%d %s", new Object[]{ipAddress, clientPort, message}));

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
                System.out.println(String.format("o-%s:%d %s", new Object[]{ipAddress, clientPort, message}));
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

            System.out.println(String.format("<-%s:%d %s", new Object[]{ipAddress, clientPort, new String(sendData)}));
            sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, clientPort);
            try {
                socket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("server closed");
    }
}
