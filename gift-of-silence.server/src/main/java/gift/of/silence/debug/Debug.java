package gift.of.silence.debug;

import gift.of.silence.lib.network.IPacketHandler;
import java.net.DatagramPacket;

public class Debug implements IPacketHandler {

    DebugControl control = new DebugControl();

    @Override
    public byte[] handle(DatagramPacket packet) {

        return control.packetHandler(packet);
    }
}
