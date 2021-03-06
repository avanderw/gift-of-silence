package gift.of.silence.helm;

import gift.of.silence.server.control.Control;
import gift.of.silence.network.Network;
import java.util.function.Consumer;
import net.avdw.vector.Vector2D;
import org.pmw.tinylog.Logger;

public class HelmSimulator {

    Helm helm;
    HelmData data;

    HelmSimulator(Helm helm, HelmData data) {
        this.data = data;
        this.helm = helm;

        Control.events.subscribe(Control.Event.UPDATE, update);
    }

    Consumer<Long> update = (delta) -> {
        Logger.trace("update");
        Vector2D currentVelocity = data.velocity.current();
        if (currentVelocity.isZero() && data.speed.acceleration() != 0) {
            currentVelocity.offsetPolar(data.speed.acceleration(), data.velocity.rotation());
        } else {
            currentVelocity.length(currentVelocity.length() + data.speed.acceleration());
            currentVelocity.rotate(data.velocity.rotation());
        }

        data.position.add(currentVelocity.clone().multiply(currentVelocity.length() / 1000 * delta));
        data.velocity.current(currentVelocity);

        data.depth.current(data.depth.current() + data.depth.climb());

        Network.send(Helm.class, String.format("s:%s, h:%s, d:%s", data.speed.current, data.heading.current, data.depth.current).getBytes());
    };
}
