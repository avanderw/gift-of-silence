package gift.of.silence.helm;

import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Helm implements IPacketHandler {

    HelmControl control = new HelmControl();

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
