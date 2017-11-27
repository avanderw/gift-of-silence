package gift.of.silence.game;

import gift.of.silence.event.EventManager;
import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Game implements IPacketHandler {
    GameControl control = new GameControl();
    EventManager events = new EventManager();
    
    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }
}
