package gift.of.silence.network;

import java.net.DatagramPacket;

public interface IPacketHandler {

    byte[] packetHandler(DatagramPacket message);

}
