package gift.of.silence.helm;

import net.avdw.eventmanager.EventManager;
import gift.of.silence.lib.network.IPacketHandler;
import java.net.DatagramPacket;

public class Helm implements IPacketHandler {

    final HelmData data = new HelmData();
    final HelmControl control = new HelmControl(data);
    final HelmSimulator simulator = new HelmSimulator(this, data);
    final EventManager events = new EventManager();

    public String intel() {
        return String.format("p:%s", data.position);
    }

    @Override
    public byte[] handle(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
