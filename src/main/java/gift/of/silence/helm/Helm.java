package gift.of.silence.helm;

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

    Double targetHeading = 0D;
    Double currentHeading = 0D;

    public void heading(Double heading) {
        targetHeading = heading / 180 * Math.PI;
        System.out.println(String.format("helm: heading set to %s (%s)", heading, targetHeading));
    }

    Double maxSpeed = 10D;
    Double targetSpeed = 0D;
    Double currentSpeed = 0D;

    public void speed(double scale) {
        targetSpeed = maxSpeed * scale;
        System.out.println("helm: speed set to " + targetSpeed);
    }

    Double maxDepth = 100D;
    Double targetDepth = 0D;
    Double currentDepth = 0D;

    public void depth(double depth) {
        targetDepth = depth;
        System.out.println("helm: depth set to " + depth);
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
            speed(Double.parseDouble(message.substring("depth:".length())));
            return "helm: depth set";
        } else {
            return "helm: noop";
        }
    }

    @Override
    public void simulate(long delta) {
        Vector2D currentVelocity = new Vector2D();
        currentVelocity.offsetPolar(currentSpeed, currentHeading);
        System.out.println(String.format("helm: current velocity %s", currentVelocity));

        Vector2D targetVelocity = new Vector2D();
        targetVelocity.offsetPolar(targetSpeed, targetHeading);
        System.out.println(String.format("helm: target velocity %s", targetVelocity));

        Vector2D newVelocity;
        if (currentVelocity.isNear(targetVelocity)) {
            newVelocity = currentVelocity.clone();
        } else {
            Double deltaSpeed = targetSpeed - currentSpeed;
            Double acceleration = Math.min(maxAcceleration, Math.abs(deltaSpeed));
            if (deltaSpeed < 0) {
                acceleration = -acceleration;
            }
            System.out.println(String.format("helm: acceleration %s", acceleration));

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

            System.out.println(String.format("helm: new velocity %s", newVelocity));
        }

        position.add(newVelocity.clone().multiply(newVelocity.length() / 1000 * delta));

        currentSpeed = newVelocity.length();
        currentHeading = newVelocity.angle();

        System.out.println(String.format("helm: updated position %s", position));
    }
}
