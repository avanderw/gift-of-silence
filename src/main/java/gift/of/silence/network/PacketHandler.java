package gift.of.silence.network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;

class PacketHandler implements Runnable {

    private final DatagramPacket packet;

    PacketHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        InetAddress ipAddress = packet.getAddress();
        int clientPort = packet.getPort();
        
        if (!connected.containsKey(ipAddress)) {
            connected.put(ipAddress, new HashMap());
        }

        if (!connected.get(ipAddress).containsKey(clientPort)) {
            connected.get(ipAddress).put(clientPort, new PacketHandler());
        }

        String message = new String(packet.getData());
        message = message.trim();
        connected.get(ipAddress).get(clientPort).onMessage(message);
    }
}
