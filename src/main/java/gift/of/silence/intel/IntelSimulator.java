package gift.of.silence.intel;

import gift.of.silence.game.Game;
import gift.of.silence.network.Network;
import java.util.function.Consumer;
import org.pmw.tinylog.Logger;

public class IntelSimulator {

    Intel intel;

    IntelSimulator(Intel intel) {
        this.intel = intel;

        Game.events.subscribe(Game.Event.UPDATE, update);
    }

    Consumer<Long> update = (delta) -> {
        Logger.trace("update");

        Network.send(Intel.class, "update".getBytes());
    };
}
