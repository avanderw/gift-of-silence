package gift.of.silence.network;

import gift.of.silence.helm.HelmHandler;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

class PacketHandler implements Runnable {

    private static final Map<InetAddress, Map<Integer, IClientHandler>> handlers = new HashMap();
    private final DatagramPacket packet;

    PacketHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        InetAddress ipAddress = packet.getAddress();
        int clientPort = packet.getPort();
        String message = new String(packet.getData());
        message = message.trim();
        
        if (!handlers.containsKey(ipAddress)) {
            handlers.put(ipAddress, new HashMap());
        }

        if (!handlers.get(ipAddress).containsKey(clientPort)) {
            IClientHandler clientHandler;
            switch (message) {
                case "": clientHandler = new HelmHandler();
                    break;
                default:
                    System.out.println(String.format("x-%s,%s not registered", ipAddress, clientPort));
                    return;
                    
            }
            handlers.get(ipAddress).put(clientPort, clientHandler);
        }

        handlers.get(ipAddress).get(clientPort).handle(message);
    }
}
