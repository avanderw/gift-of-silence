package gift.of.silence.network;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.pmw.tinylog.Logger;

class ConnectionManager {

    List<Client> clients = new ArrayList();
    Map<Class, List<Client>> connections = new HashMap();
    Cleanup cleanup;

    ConnectionManager() {
        cleanup = new Cleanup(clients);
        cleanup.start();
    }

    void refresh(InetAddress ip, int port) {
        clients.stream().filter((client) -> client.ip == ip && client.port == port)
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
        connections.values().forEach(systemClients -> {
            if (systemClients.contains(client)) {
                systemClients.remove(client);
                clients.remove(client);
                Logger.info("%s:%s disconnected", client.ip, client.port);
            }
        });
    }

    class Client {

        InetAddress ip;
        Integer port;
        Long last;

        Client(InetAddress ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }

    public class Cleanup implements Runnable {

        private volatile Thread thread;
        private final Long timeout = 300_000L;
        private final List<Client> clients;

        Cleanup(List<Client> clients) {
            this.clients = clients;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("connection-cleanup");
            Logger.debug("started");
            while (Thread.currentThread() == thread) {
                Logger.debug("cleanup");
                clients.forEach((client) -> {
                    if (System.currentTimeMillis() - client.last > timeout) {
                        remove(client);
                    }
                });
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
