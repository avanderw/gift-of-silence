package gift.of.silence.network;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pmw.tinylog.Logger;

class ConnectionHandler {

    static Map<Class, List<Client>> connections = new HashMap();

    static void send(Class system, byte[] bytes) {
        connections.get(system).forEach((systemConnection) -> {
            Network.send(bytes, systemConnection.ip, systemConnection.port);
        });
    }

    void add(InetAddress ip, int port, Class system) {
        Client client = new Client(ip, port);
        if (!connections.containsKey(system)) {
            connections.put(system, new ArrayList());
        }

        if (!connections.get(system).contains(client)) {
            connections.get(system).add(client);
            Logger.info(String.format("%s:%s connected", ip, port));
        } else {
            Logger.warn(String.format("%s:%s already connected", ip, port));
        }
    }

    void remove(InetAddress ip, int port) {
        Client client = new Client(ip, port);
        connections.values().forEach(systemConnections -> {
            if (systemConnections.contains(client)) {
                systemConnections.remove(client);
                Logger.info("%s:%s disconnected", ip, port);
            }
        });
    }

    class Client{

        public InetAddress ip;
        public Integer port;

        public Client(InetAddress ip, int port) {
            this.ip = ip;
            this.port = port;
        }
    }
}
