package gift.of.silence.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Game implements Runnable {
    
    private volatile Thread thread;
    private volatile List<ISimulatedSystem> systems = new ArrayList();
    
    private final long fps = 1;

    void simulate(ISimulatedSystem system) {
        systems.add(system);
    }

    void remove(ISimulatedSystem system) {
        systems.remove(system);
    }
    
    void start() {
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        thread = null;
    }

    @Override
    public void run() {
        long refreshRate = 1000 / fps;
        while (thread == Thread.currentThread()) {
            long start = System.currentTimeMillis();
            long next = start + refreshRate;

            systems.forEach((system) -> {
                system.simulate(refreshRate);
            });
            
            long end = System.currentTimeMillis();
            if (next < end) {
                System.out.println("core: simulation taking longer than refresh rate");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(next - end);
            } catch (InterruptedException ex) {
                System.out.println("core: thread interrupted");
            }
        }
    }
}
