package gift.of.silence.game;

import gift.of.silence.eventmanager.EventManager;
import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Game implements IPacketHandler {
    public EventManager events = new EventManager(Event.UPDATE);

    GameLoop loop = new GameLoop(1L, this);
    GameControl control = new GameControl(this);

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

    public static final class Event {

        public static final String UPDATE = "game-update";
    }
}
