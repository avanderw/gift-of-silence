package gift.of.silence.client.debug;

import gift.of.silence.lib.network.APacketHandler;
import java.net.DatagramPacket;

public class PacketHandler implements APacketHandler {

    final Debug debug;

    PacketHandler(Debug debug) {
        this.debug = debug;
    }
@Override
    public byte[] handle(DatagramPacket message) {
        debug.events.fire(Debug.Event.PACKET_RECEIVED, new String(message.getData()).trim());
        return String.format("%s handled", message).getBytes();
    }

}
