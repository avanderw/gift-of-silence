package gift.of.silence.helm;

import gift.of.silence.eventmanager.EventManager;
import gift.of.silence.network.IPacketHandler;
import gift.of.silence.network.Network;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Helm implements IPacketHandler {

    final HelmData data = new HelmData();
    final HelmControl control = new HelmControl(data);
    final EventManager events = new EventManager();
    final HelmSimulator simulator= new HelmSimulator(this, data);
    final List<Network.Client> clients = new ArrayList();

    public void add(InetAddress ip, int port) {
        clients.add(new Network.Client(ip, port));
    }

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
