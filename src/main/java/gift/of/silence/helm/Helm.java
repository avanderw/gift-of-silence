package gift.of.silence.helm;

import com.google.gson.Gson;
import gift.of.silence.event.EventManager;
import gift.of.silence.core.IConnectedSystem;
import gift.of.silence.core.ISimulatedSystem;
import gift.of.silence.core.Vector2D;

public class Helm implements ISimulatedSystem, IConnectedSystem {

    public transient EventManager events = new EventManager();

    Vector2D position = new Vector2D();
    Double maxRotation = 2 * Math.PI / 180; // rad/s
    Double maxAcceleration = 1D; // m/s

    public class Depth {

        public double design;
        public double test;
        public double maximum;
        public double crush;
    }

    Double targetHeading = 270D / 180 * Math.PI;
    Double currentHeading = 270D / 180 * Math.PI;

    public void heading(Double heading) {
        heading += 270D;
        heading %= 360D;
        targetHeading = heading / 180 * Math.PI;
        events.notify("helm:target-heading", targetHeading);
    }

    Double maxSpeed = 5D; // m/s
    Double targetSpeed = 0D;
    Double currentSpeed = 0D;

    public void speed(double scale) {
        targetSpeed = maxSpeed * scale;
        events.notify("helm:target-speed", targetSpeed);
    }

    Double maxDepth = 100D;
    Double targetDepth = 0D;
    Double currentDepth = 0D;

    public void depth(double depth) {
        targetDepth = depth;
        events.notify("helm:target-depth", targetDepth);
    }

    public void toggleDock() {

    }

    @Override
    public String onMessage(String message) {
        if (message.startsWith("heading:")) {
            heading(Double.parseDouble(message.substring("heading:".length())));
            return "helm: heading set";
        } else if (message.startsWith("speed:")) {
            speed(Double.parseDouble(message.substring("speed:".length())));
            return "helm: speed set";
        } else if (message.startsWith("depth:")) {
            depth(Double.parseDouble(message.substring("depth:".length())));
            return "helm: depth set";
        } else {
            return "helm: noop";
        }
    }

    @Override
    public void simulate(long delta) {
        Vector2D currentVelocity = new Vector2D();
        currentVelocity.offsetPolar(currentSpeed, currentHeading);

        Vector2D targetVelocity = new Vector2D();
        targetVelocity.offsetPolar(targetSpeed, targetHeading);

        Vector2D newVelocity;
        if (currentVelocity.isNear(targetVelocity)) {
            newVelocity = currentVelocity.clone();
        } else {
            Double deltaSpeed = targetSpeed - currentSpeed;
            Double acceleration = Math.min(maxAcceleration, Math.abs(deltaSpeed));
            if (deltaSpeed < 0) {
                acceleration = -acceleration;
            }

            if (currentVelocity.isZero()) {
                newVelocity = targetVelocity.clone().length(acceleration);
            } else {
                Double targetRotation = currentVelocity.angleBetween(targetVelocity);
                Double rotation = Math.min(maxRotation, Math.abs(targetRotation));
                if (targetRotation < 0) {
                    rotation = -rotation;
                }
                Vector2D rotatedVelocity = currentVelocity.clone().rotate(rotation);
                newVelocity = rotatedVelocity.clone().length(currentVelocity.length() + acceleration);
            }
        }

        position.add(newVelocity.clone().multiply(newVelocity.length() / 1000 * delta));

        currentSpeed = newVelocity.length();
        currentHeading = newVelocity.angle();
        
        
        events.notify("helm:simulate", new Gson().toJson(this));       
    }
}
