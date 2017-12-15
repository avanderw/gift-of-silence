package gift.of.silence.client.game;

import gift.of.silence.lib.network.IPacketHandler;
import java.net.DatagramPacket;

public class PacketHandler implements IPacketHandler {

    @Override
    public byte[] handle(DatagramPacket message) {
        return String.format("%s handled", message).getBytes();
    }

}
