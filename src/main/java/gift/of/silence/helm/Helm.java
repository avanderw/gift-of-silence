package gift.of.silence.helm;

import gift.of.silence.eventmanager.EventManager;
import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Helm implements IPacketHandler{

    final HelmData data = new HelmData();
    final HelmControl control = new HelmControl(data);
    final EventManager events = new EventManager();
    final HelmSimulator simulator = new HelmSimulator(this, data);

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
