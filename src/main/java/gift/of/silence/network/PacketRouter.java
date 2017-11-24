package gift.of.silence.network;

import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

class PacketRouter implements Runnable {

    private static final Map<InetAddress, Map<Integer, IPacketHandler>> handlers = new HashMap();
    private final DatagramPacket receivePacket;

    PacketRouter(DatagramPacket packet) {
        this.receivePacket = packet;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("packet-router");
        InetAddress ipAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();

        if (!handlers.containsKey(ipAddress)) {
            handlers.put(ipAddress, new HashMap());
        }

        if (!handlers.get(ipAddress).containsKey(clientPort)) {
            IPacketHandler clientHandler;

            String message = new String(receivePacket.getData());
            message = message.trim();
            switch (message) {
                case "helm":
                    clientHandler = new Helm();
                    break;
                case "game":
                    clientHandler = new Game();
                    break;
                default:
                    byte[] response = String.format("?-router: %s:%s not registered", ipAddress, clientPort).getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(response, response.length, ipAddress, clientPort);
                    try {
                        System.out.println(String.format("<-router: %s", new String(response)));
                        Network.socket.send(sendPacket);
                    } catch (IOException ex) {
                        System.out.println(String.format("x-router: %s", ex.getMessage()));
                    }
                    return;

            }
            handlers.get(ipAddress).put(clientPort, clientHandler);
        }

        byte[] response = handlers.get(ipAddress).get(clientPort).packetHandler(receivePacket);
        DatagramPacket sendPacket = new DatagramPacket(response, response.length, ipAddress, clientPort);
        try {
            System.out.println(String.format("<-router: %s", new String(response)));
            Network.socket.send(sendPacket);
        } catch (IOException ex) {
            System.out.println(String.format("x-router: %s", ex.getMessage()));
        }
    }
}
