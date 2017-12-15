package gift.of.silence.client.control;

import java.net.DatagramPacket;
import gift.of.silence.lib.network.APacketHandler;

public class PacketHandler implements APacketHandler {

    private final Control control;

    PacketHandler(Control control) {
        this.control = control;
    }

    @Override
    public byte[] handle(DatagramPacket message) {
        control.events.fire(Control.Event.PACKET_RECEIVED, new String(message.getData()).trim());
        return String.format("%s handled", message).getBytes();
    }

}
