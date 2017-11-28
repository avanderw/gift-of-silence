package gift.of.silence.helm;

import gift.of.silence.eventmanager.EventManager;
import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Helm implements IPacketHandler {

    HelmControl control = new HelmControl();
    EventManager events = new EventManager();

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
