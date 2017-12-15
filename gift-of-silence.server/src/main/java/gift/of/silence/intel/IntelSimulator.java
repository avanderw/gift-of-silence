package gift.of.silence.intel;

import gift.of.silence.server.control.Control;
import gift.of.silence.helm.Helm;
import gift.of.silence.network.Network;
import java.util.function.Consumer;
import org.pmw.tinylog.Logger;

public class IntelSimulator {

    Intel intel;
    IntelData data;
    Helm helm;

    IntelSimulator(Intel intel, Helm helm, IntelData data) {
        this.intel = intel;

        Control.events.subscribe(Control.Event.UPDATE, update);
        this.helm = helm;
        this.data = data;
    }

    Consumer<Long> update = (delta) -> {
        Logger.trace("update");

        Network.send(Intel.class, helm.intel().getBytes());
    };
}
