package gift.of.silence.network;

import gift.of.silence.debug.Debug;
import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import gift.of.silence.intel.Intel;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Network {

    public static DatagramSocket socket;
    private final Debug debug;
    private final Game game;
    private final Helm helm;
    private final Intel intel;
    private PortListener portListener;

    public Network(Game game, Debug debug, Helm helm, Intel intel) {
        Integer serverPort = 43397;
        try {
            socket = new DatagramSocket(serverPort);
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
        this.game = game;
        this.helm = helm;
        this.debug = debug;
        this.intel = intel;
    }

    public void startPortListener() {
        portListener = new PortListener(game, debug, helm, intel);
        portListener.start();
    }

    public void stopPortListener() {
        portListener.stop();
    }
}
