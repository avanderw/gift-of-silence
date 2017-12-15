package gift.of.silence.helm;

import net.avdw.eventmanager.EventManager;
import java.net.DatagramPacket;
import gift.of.silence.lib.network.APacketHandler;

public class Helm implements APacketHandler {

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
