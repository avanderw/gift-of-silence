package gift.of.silence.core;

import java.net.InetAddress;

public interface IClientSystem {

    void add(InetAddress ip, int port);
    void remove(InetAddress ip, int port);
    
}
