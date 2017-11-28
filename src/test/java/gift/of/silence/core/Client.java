package gift.of.silence.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

public class Client implements Runnable {

    static DatagramSocket socket;

    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        try {
            socket = new DatagramSocket();
            InetAddress ipAddress = InetAddress.getByName("localhost");

            Client client = new Client();
            Thread thread = new Thread(client);
            thread.start();

            Logger.info("input ready");
            String request = null;
            while (request == null || !request.equals("disconnect")) {
                try {
                    request = console.readLine();
                    byte[] sendData = request.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 43397);
                    socket.send(sendPacket);
                } catch (IOException ex) {
                    Logger.error(ex);
                }
            }
            socket.close();
        } catch (SocketException | UnknownHostException ex) {
            Logger.error(ex);
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName("listener");
        Logger.info("started");
        while (!socket.isClosed()) {
            DatagramPacket receivePacket = new DatagramPacket(new byte[508], 508);
            try {
                socket.receive(receivePacket);
                Logger.info(String.format("<- %s", new String(receivePacket.getData())));
            } catch (IOException ex) {
                
            }
        }        
        Logger.info("stopped");
    }
}
