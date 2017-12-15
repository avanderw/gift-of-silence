package gift.of.silence.lib.network;

import java.net.DatagramPacket;

public interface APacketHandler {

    byte[] handle(DatagramPacket message);

}
