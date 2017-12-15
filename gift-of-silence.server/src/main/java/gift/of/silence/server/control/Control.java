package gift.of.silence.server.control;

import net.avdw.eventmanager.EventManager;
import java.net.DatagramPacket;
import gift.of.silence.lib.network.APacketHandler;

public class Control implements APacketHandler {

    public static EventManager events = new EventManager(Event.UPDATE);

    GameSimulator loop = new GameSimulator(1L, this);
    PacketHandler packetHandler = new PacketHandler(this);
    Api api = new Api(this);

    @Override
    public byte[] handle(DatagramPacket packet) {
        return packetHandler.handle(packet);
    }

    public static final class Event {
        public static final String UPDATE = "game-update";
    }
}
