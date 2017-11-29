package gift.of.silence.intel;

import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Intel implements IPacketHandler {
    
    final IntelData data = new IntelData();
    final IntelControl control = new IntelControl(data);

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
