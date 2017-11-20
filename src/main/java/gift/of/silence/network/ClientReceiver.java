package gift.of.silence.network;

class ClientReceiver implements Runnable {

    private volatile Thread thread;

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread = null;
    }

    @Override
    public void run() {

    }
}
