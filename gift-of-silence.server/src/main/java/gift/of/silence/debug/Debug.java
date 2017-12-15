package gift.of.silence.debug;

import java.net.DatagramPacket;
import gift.of.silence.lib.network.APacketHandler;

public class Debug implements APacketHandler {

    DebugControl control = new DebugControl();

    @Override
    public byte[] handle(DatagramPacket packet) {

        return control.packetHandler(packet);
    }
}
