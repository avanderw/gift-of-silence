package gift.of.silence.network;

import gift.of.silence.debug.Debug;
import gift.of.silence.server.control.Control;
import gift.of.silence.helm.Helm;
import gift.of.silence.intel.Intel;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import org.pmw.tinylog.Logger;
import gift.of.silence.lib.network.APacketHandler;

class PacketRouter implements Runnable {

    static final Map<InetAddress, Map<Integer, APacketHandler>> handlers = new HashMap();
    private final Debug debug;
    private final Control game;
    private final Helm helm;
    private final Intel intel;
    private final DatagramPacket packet;

    PacketRouter(DatagramPacket packet, Control game, Debug debug, Helm helm, Intel intel) {
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

        String message = new String(packet.getData());
        message = message.trim();
        Logger.trace(String.format("<- %s", message));

        if (!handlers.containsKey(ip)) {
            handlers.put(ip, new HashMap());
        }

        if (!handlers.get(ip).containsKey(port)) {
            APacketHandler handler = null;

            switch (message) {
                case "helm":
                    Network.connectionManager.add(ip, port, Helm.class);
                    handler = helm;
                    break;
                case "game":
                    Network.connectionManager.add(ip, port, Control.class);
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
                    Network.send(String.format("%s:%s not registered", ip, port).getBytes(), ip, port);
                    return;

            }
            handlers.get(ip).put(port, handler);
        }

        Network.connectionManager.refresh(ip, port);
        byte[] response = handlers.get(ip).get(port).handle(packet);
        switch (message) {
            case "disconnect":
                Network.connectionManager.remove(ip, port);
                break;
        }
        Network.send(response, ip, port);
    }
}
