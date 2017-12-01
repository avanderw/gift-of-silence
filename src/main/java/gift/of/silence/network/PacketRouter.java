package gift.of.silence.network;

import gift.of.silence.debug.Debug;
import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import gift.of.silence.intel.Intel;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import org.pmw.tinylog.Logger;

class PacketRouter implements Runnable {

    static final Map<InetAddress, Map<Integer, IPacketHandler>> handlers = new HashMap();
    private final Debug debug;
    private final Game game;
    private final Helm helm;
    private final Intel intel;
    private final DatagramPacket packet;

    PacketRouter(DatagramPacket packet, Game game, Debug debug, Helm helm, Intel intel) {
        this.packet = packet;
        this.game = game;
        this.helm = helm;
        this.debug = debug;
        this.intel = intel;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("packet-router");
        InetAddress ip = packet.getAddress();
        int port = packet.getPort();

        if (!handlers.containsKey(ip)) {
            handlers.put(ip, new HashMap());
        }

        if (!handlers.get(ip).containsKey(port)) {
            IPacketHandler handler;

            String message = new String(packet.getData());
            message = message.trim();
            Logger.trace(String.format("<- %s", message));

            switch (message) {
                case "helm":
                    Network.connectionManager.add(ip, port, Helm.class);
                    handler = helm;
                    break;
                case "game":
                    Network.connectionManager.add(ip, port, Game.class);
                    handler = game;
                    break;
                case "debug":
                    Network.connectionManager.add(ip, port, Debug.class);
                    handler = debug;
                    break;
                case "intel":
                    Network.connectionManager.add(ip, port, Intel.class);
                    handler = intel;
                    break;
                default:
                    byte[] response = String.format("%s:%s not registered", ip, port).getBytes();
                    Network.send(response, ip, port);
                    return;

            }
            handlers.get(ip).put(port, handler);
        }
        
        Network.connectionManager.refresh(ip, port);
        byte[] response = handlers.get(ip).get(port).packetHandler(packet);
        Network.send(response, ip, port);
    }
}
