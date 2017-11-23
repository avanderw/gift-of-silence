package gift.of.silence.network;

import gift.of.silence.core.Server;
import gift.of.silence.core.SubSystem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortListener implements Runnable {

    private final Map<InetAddress, Map<Integer, PacketRouter>> connected = new HashMap();
    private volatile Thread thread;

    void start() {
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        thread = null;
    }

    @Override
    public void run() {
        ExecutorService packetRouters = Executors.newCachedThreadPool();
        System.out.println(String.format("o-listener: started"));
        while (thread == Thread.currentThread()) {
            DatagramPacket packet = new DatagramPacket(new byte[508], 508);
            try {
                Network.socket.receive(packet);
                packetRouters.execute(new PacketRouter(packet));
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
        System.out.println(String.format("x-listener: stopped"));
    }
}
