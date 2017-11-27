package gift.of.silence.helm;

import gift.of.silence.game.Game;
import gift.of.silence.core.Vector2D;

public class HelmSimulator {

    HelmData data;
    Game game;

    public void toggleDock() {

    }

    public void simulate(long delta) {
        Vector2D currentVelocity = data.velocity.current();
        currentVelocity.rotate(data.velocity.rotation());
        currentVelocity.length(currentVelocity.length() + data.speed.acceleration());

        data.position.add(currentVelocity.clone().multiply(currentVelocity.length() / 1000 * delta));
        data.velocity.current(currentVelocity);
    }
}
