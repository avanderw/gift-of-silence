package gift.of.silence.network;

import gift.of.silence.debug.Debug;
import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import gift.of.silence.intel.Intel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.pmw.tinylog.Logger;

public class PortListener implements Runnable {

    private final Map<InetAddress, Map<Integer, PacketRouter>> connected = new HashMap();
    private final Debug debug;
    private final Game game;
    private final Helm helm;
    private final Intel intel;
    private volatile Thread thread;

    PortListener(Game game, Debug debug, Helm helm, Intel intel) {
        this.game = game;
        this.helm = helm;
        this.debug = debug;
        this.intel = intel;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("port-listener");
        ExecutorService packetRouters = Executors.newCachedThreadPool();
        Logger.info(String.format("started"));
        while (thread == Thread.currentThread()) {
            DatagramPacket packet = new DatagramPacket(new byte[508], 508);
            try {
                Network.socket.receive(packet);
                packetRouters.execute(new PacketRouter(packet, game, debug, helm, intel));
            } catch (IOException ex) {
                Logger.error(ex.getMessage());
            }
        }
        Logger.info(String.format("stopped"));
    }

    void start() {
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        thread = null;
    }

}
