package gift.of.silence.lib.network;

import java.net.DatagramPacket;

public interface IPacketHandler {

    byte[] handle(DatagramPacket message);

}
