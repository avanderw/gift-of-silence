package gift.of.silence.network;

import gift.of.silence.debug.Debug;
import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import org.pmw.tinylog.Logger;

class PacketRouter implements Runnable {

    private static final Map<InetAddress, Map<Integer, IPacketHandler>> handlers = new HashMap();
    private final Debug debug;
    private final Game game;
    private final Helm helm;
    private final DatagramPacket receivePacket;

    PacketRouter(DatagramPacket packet, Game game, Helm helm, Debug debug) {
        this.receivePacket = packet;
        this.game = game;
        this.helm = helm;
        this.debug = debug;
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
            Logger.trace(String.format("<- %s", message));
            
            switch (message) {
                case "helm":
                    clientHandler = helm;
                    break;
                case "game":
                    clientHandler = game;
                    break;
                case "debug":
                    clientHandler = debug;
                    break;
                default:
                    byte[] response = String.format("%s:%s not registered", ipAddress, clientPort).getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(response, response.length, ipAddress, clientPort);
                    try {
                        Logger.warn(String.format("-> %s", new String(response)));
                        Network.socket.send(sendPacket);
                    } catch (IOException ex) {
                        Logger.error(String.format("%s", ex.getMessage()));
                    }
                    return;

            }
            handlers.get(ipAddress).put(clientPort, clientHandler);
        }

        byte[] response = handlers.get(ipAddress).get(clientPort).packetHandler(receivePacket);
        DatagramPacket sendPacket = new DatagramPacket(response, response.length, ipAddress, clientPort);
        try {
            Logger.trace(String.format("-> %s", new String(response)));
            Network.socket.send(sendPacket);
        } catch (IOException ex) {
            Logger.error(String.format("%s", ex.getMessage()));
        }
    }
}
