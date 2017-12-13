package gift.of.silence.network;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.pmw.tinylog.Logger;

class ConnectionManager {

    List<Client> clients = new ArrayList();
    Map<Class, List<Client>> connections = new HashMap();
    Cleanup cleanup;

    ConnectionManager() {
        cleanup = new Cleanup(this);
        cleanup.start();
    }

    void refresh(InetAddress ip, int port) {
        Logger.debug(String.format("refresh: %s:%s, clients:%s", ip, port, clients));
        clients.stream().filter((client) -> client.ip.equals(ip) && client.port.equals(port))
                .findAny().get().last = System.currentTimeMillis();
    }

    void add(InetAddress ip, int port, Class system) {
        Client client = new Client(ip, port);
        if (!connections.containsKey(system)) {
            connections.put(system, new ArrayList());
        }

        if (!connections.get(system).contains(client)) {
            connections.get(system).add(client);
            clients.add(client);
            Logger.info(String.format("%s:%s connected", ip, port));
        } else {
            Logger.warn(String.format("%s:%s already connected", ip, port));
        }
    }

    void remove(InetAddress ip, int port) {
        Client client = new Client(ip, port);
        remove(client);
    }

    void remove(Client client) {
        final List<Class> clean = new ArrayList();
        connections.keySet().forEach((key) -> {
            List<Client> systemClients = connections.get(key);
            if (systemClients.contains(client)) {
                systemClients.remove(client);
                clients.remove(client);
                PacketRouter.handlers.get(client.ip).remove(client.port);
                Logger.info(String.format("%s:%s disconnected", client.ip, client.port));
                if (systemClients.isEmpty()) {
                    Logger.debug(String.format("%s has no handlers", key));
                    clean.add(key);
                }
            }
        });

        clean.forEach(key -> connections.remove(key));
    }

    class Client {

        InetAddress ip;
        Long last;
        Integer port;

        Client(InetAddress ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Client) {
                Client that = (Client) obj;
                return this.ip.equals(that.ip) && this.port.equals(that.port);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + Objects.hashCode(this.ip);
            hash = 79 * hash + Objects.hashCode(this.port);
            return hash;
        }

        @Override
        public String toString() {
            return String.format("%s:%s", ip, port);
        }

    }

    public class Cleanup implements Runnable {

        private volatile Thread thread;
        private final Long timeout = 300_000L;
        private final ConnectionManager connectionManager;

        Cleanup(ConnectionManager connectionManager) {
            this.connectionManager = connectionManager;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("connection-cleanup");
            Logger.debug("started");
            while (Thread.currentThread() == thread) {
                Logger.debug("cleanup");
                List<Client> removeClients = clients.stream().filter(client -> System.currentTimeMillis() - client.last > timeout).collect(Collectors.toList());
                if (!removeClients.isEmpty()) {
                    Logger.info(String.format("disconnecting clients (%s) which timed-out", removeClients));
                    removeClients.forEach(client -> {
                        Network.send("you timed out".getBytes(), client.ip, client.port);
                        connectionManager.remove(client);
                    });
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(timeout / 2);
                } catch (InterruptedException ex) {
                    Logger.info("interrupted");
                }
            }
            Logger.debug("stopped");
        }

        void start() {
            thread = new Thread(this);
            thread.start();
        }

        void stop() {
            thread = null;
        }
    }
}
