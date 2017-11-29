package gift.of.silence.network;

class StatusMonitor implements Runnable {

    private volatile Thread thread;
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void start() {
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        thread = null;
    }
}
