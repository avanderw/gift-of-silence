package gift.of.silence.helm;

import gift.of.silence.eventmanager.EventManager;
import gift.of.silence.game.Game;
import gift.of.silence.network.IPacketHandler;
import java.net.DatagramPacket;

public class Helm implements IPacketHandler {

    final HelmControl control = new HelmControl();
    final HelmData data = new HelmData();
    final EventManager events = new EventManager();
    final HelmSimulator simulator;

    public Helm(Game game) {
        simulator = new HelmSimulator(game, data);
    }

    @Override
    public byte[] packetHandler(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
