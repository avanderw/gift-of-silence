package gift.of.silence.game;

import java.util.concurrent.TimeUnit;

public class GameLoop implements Runnable {

    private final Long fps;
    private final Game game;

    private volatile Thread thread;

    GameLoop(Long fps, Game game) {
        this.fps = fps;
        this.game = game;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("game-loop");
        System.out.println("game-loop: started");
        long timeElapsed = 1000 / fps;
        while (thread == Thread.currentThread()) {
            long start = System.currentTimeMillis();
            long next = start + timeElapsed;

            game.events.fire(Game.Event.UPDATE, timeElapsed);

            long end = System.currentTimeMillis();
            if (next < end) {
                System.out.println("game-loop: taking longer than refresh rate");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(next - end);
            } catch (InterruptedException ex) {
                System.out.println("game-loop: thread interrupted");
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
