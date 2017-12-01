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

    static ConnectionManager connectionManager;

    static DatagramSocket socket;

    public static void remove(InetAddress ip, int port) {
        connectionManager.remove(ip, port);
    }

    public static void send(Class system, byte[] bytes) {
        if (connectionManager.connections.containsKey(system)) {
            connectionManager.connections.get(system).forEach((systemConnection) -> {
                send(bytes, systemConnection.ip, systemConnection.port);
            });
        }
    }

    static void send(byte[] bytes, InetAddress ip, int port) {
        DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, ip, port);
        try {
            Logger.trace(String.format("%s:%s -> %s", ip, port, new String(bytes)));
            socket.send(sendPacket);
        } catch (IOException ex) {
            Logger.error(String.format("%s", ex.getMessage()));
        }
    }

    private final Debug debug;
    private final Game game;
    private final Helm helm;
    private final Intel intel;
    private PortListener portListener;

    public Network(Game game, Debug debug, Helm helm, Intel intel) {
        Integer serverPort = 43397;
        try {
            socket = new DatagramSocket(serverPort);
            connectionManager = new ConnectionManager();
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
        this.game = game;
        this.helm = helm;
        this.debug = debug;
        this.intel = intel;

        startPortListener();
    }

    final void startPortListener() {
        portListener = new PortListener(game, debug, helm, intel);
        portListener.start();
    }

    void stopPortListener() {
        portListener.stop();
    }
}
