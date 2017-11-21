package gift.of.silence.network;

public class Network {

    private PortListener portListener;

    public void startPortListener() {
        portListener = new PortListener();
        portListener.start();
    }

    public void stopPortListener() {
        portListener.stop();
    }
}
