package gift.of.silence.network;

import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortListener implements Runnable {

    private final Map<InetAddress, Map<Integer, PacketRouter>> connected = new HashMap();
    private final Game game;
    private final Helm helm;
    private volatile Thread thread;

    PortListener(Game game, Helm helm) {
        this.game = game;
        this.helm = helm;

    }
    @Override
    public void run() {
        Thread.currentThread().setName("port-listener");
        ExecutorService packetRouters = Executors.newCachedThreadPool();
        System.out.println(String.format("o-listener: started"));
        while (thread == Thread.currentThread()) {
            DatagramPacket packet = new DatagramPacket(new byte[508], 508);
            try {
                Network.socket.receive(packet);
                packetRouters.execute(new PacketRouter(packet, game, helm));
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
        System.out.println(String.format("x-listener: stopped"));
    }

    void start() {
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        thread = null;
    }

}
