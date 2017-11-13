package gift.of.silence.simulator;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Simulator implements Runnable {

    private volatile Thread thread;
    private volatile List<ISimulated> systems;

    public Simulator(List<ISimulated> systems) {
        this.systems = systems;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread = null;
    }

    @Override
    public void run() {
        long refreshRate = 1000l / 30;
        while (thread == Thread.currentThread()) {
            long start = System.currentTimeMillis();
            long next = start + refreshRate;

            systems.forEach((system) -> {
                system.simulate(refreshRate);
            });
            
            long end = System.currentTimeMillis();
            if (next < end) {
                Logger.getLogger(this.getClass().getName()).warning("simulation taking longer than refresh rate");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(next - end);
            } catch (InterruptedException ex) {
                Logger.getLogger(this.getClass().getName()).warning("simulation thread interrupted");
            }
        }
    }

}
