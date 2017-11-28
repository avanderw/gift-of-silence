package gift.of.silence.network;

import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Network {

    public static DatagramSocket socket;
    private final Game game;
    private final Helm helm;
    private PortListener portListener;

    public Network(Game game, Helm helm) {
        Integer serverPort = 43397;
        try {
            socket = new DatagramSocket(serverPort);
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
        this.game = game;
        this.helm = helm;
    }

    public void startPortListener() {
        portListener = new PortListener(game, helm);
        portListener.start();
    }

    public void stopPortListener() {
        portListener.stop();
    }
}
