package gift.of.silence.debug;

import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Debug implements IPacketHandler {

    DebugControl control = new DebugControl();

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }
}
