package gift.of.silence.network;

import gift.of.silence.debug.Debug;
import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import gift.of.silence.intel.Intel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import org.pmw.tinylog.Logger;

public class Network {

    static DatagramSocket socket;

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

    public static void send(byte[] bytes, InetAddress ip, Integer port) {
        DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, ip, port);
        try {
            Logger.trace(String.format("%s:%s -> %s", ip, port, new String(bytes)));
            socket.send(sendPacket);
        } catch (IOException ex) {
            Logger.error(String.format("%s", ex.getMessage()));
        }
    }

    public static class Client {

        public InetAddress ip;
        public Integer port;

        public Client(InetAddress ip, int port) {
            this.ip = ip;
            this.port = port;
        }
    }
}
