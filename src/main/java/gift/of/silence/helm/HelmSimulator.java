package gift.of.silence.helm;

import gift.of.silence.game.Game;
import gift.of.silence.core.Vector2D;
import java.util.function.Consumer;
import org.pmw.tinylog.Logger;

public class HelmSimulator {

    HelmData data;
    Game game;
    
    HelmSimulator(Game game, HelmData data) {
        this.game = game;
        this.data = data;
        
        game.events.subscribe(Game.Event.UPDATE, update);
    }
    
    Consumer<Long> update = (delta) -> {
        Logger.trace("update");
        Vector2D currentVelocity = data.velocity.current();
        currentVelocity.rotate(data.velocity.rotation());
        currentVelocity.length(currentVelocity.length() + data.speed.acceleration());

        data.position.add(currentVelocity.clone().multiply(currentVelocity.length() / 1000 * delta));
        data.velocity.current(currentVelocity);
    };
}
