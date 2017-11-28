package gift.of.silence.game;

import java.util.concurrent.TimeUnit;
import org.pmw.tinylog.Logger;

public class GameSimulator implements Runnable {

    private final Long fps;
    private final Game game;

    private volatile Thread thread;

    GameSimulator(Long fps, Game game) {
        this.fps = fps;
        this.game = game;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("game-simulator");
        Logger.debug("started");
        long timeElapsed = 1000 / fps;
        while (thread == Thread.currentThread()) {
            long start = System.currentTimeMillis();
            long next = start + timeElapsed;

            game.events.fire(Game.Event.UPDATE, timeElapsed);

            long end = System.currentTimeMillis();
            if (next < end) {
                Logger.warn("taking longer than refresh rate");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(next - end);
            } catch (InterruptedException ex) {
                Logger.warn("thread interrupted");
            }
        }
    }

    void start() {
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        thread = null;
    }

}
