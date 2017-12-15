package gift.of.silence.client.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.pmw.tinylog.Logger;
import gift.of.silence.lib.network.APacketHandler;

public class Network {

    final String server;
    final APacketHandler packetHandler;
    final String id;

    DatagramSocket socket;
    InetAddress ipAddress;

    public Network(String id, String server, APacketHandler packetHandler) {
        this.id = id;
        this.server = server;
        this.packetHandler = packetHandler;
    }

    public void open() {
        try {
            socket = new DatagramSocket();
            ipAddress = InetAddress.getByName(server);

            Listener listener = new Listener(packetHandler);
            Thread thread = new Thread(listener);
            thread.start();

            Logger.trace(String.format("%s network opened", id));
        } catch (SocketException | UnknownHostException ex) {
            Logger.error(ex);
        }
    }

    public void send(byte[] data) {
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, ipAddress, 43397);
        try {
            socket.send(sendPacket);
            Logger.trace(String.format("-> %s", new String(sendPacket.getData()).trim()));
        } catch (IOException ex) {
            Logger.error(ex);
        }
    }

    public void close() {
        socket.close();
        Logger.info(String.format("%s network closed", id));
    }

    class Listener implements Runnable {

        APacketHandler packetHandler;

        Listener(APacketHandler packetHandler) {
            this.packetHandler = packetHandler;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(String.format("%s-listener", id));
            Logger.info("started...");
            while (!socket.isClosed()) {
                DatagramPacket receivePacket = new DatagramPacket(new byte[508], 508);
                try {
                    socket.receive(receivePacket);
                    packetHandler.handle(receivePacket);
                    Logger.trace(String.format("<- %s", new String(receivePacket.getData()).trim()));
                } catch (IOException ex) {
                    Logger.info("stopping");
                }
            }
            Logger.info("stopped!");
        }
    }
}
