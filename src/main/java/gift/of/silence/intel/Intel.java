package gift.of.silence.intel;

import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Intel implements IPacketHandler{
    final IntelControl control = new IntelControl();

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
