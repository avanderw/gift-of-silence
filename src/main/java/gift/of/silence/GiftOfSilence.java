package gift.of.silence;

import gift.of.silence.network.Network;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GiftOfSilence {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
//        Server server = new Server();
//        server.start();
        Network network = new Network();
        network.startPortListener();
    }
}
