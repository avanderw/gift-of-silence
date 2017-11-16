package gift.of.silence.helm;

import com.google.gson.Gson;
import com.sun.javafx.geom.Vec3d;
import gift.of.silence.event.EventManager;
import gift.of.silence.core.IConnectedSystem;
import gift.of.silence.core.ISimulatedSystem;

public class Helm implements ISimulatedSystem, IConnectedSystem {

    public transient EventManager events = new EventManager();

    public transient double turnRate;
    public Vec3d position = new Vec3d();
    public transient Vec3d velocity = new Vec3d(); // m/s
    public transient Vec3d acceleration = new Vec3d(); // m/s

    public transient double maxTurnRate;
    public transient Vec3d maxAcceleration = new Vec3d();
    public transient Vec3d maxVelocity = new Vec3d();

    public transient Depth depth = new Depth();

    public class Depth {

        public double design;
        public double test;
        public double maximum;
        public double crush;
    }

    public Helm() {

    }

    public void heading(double heading) {
        
        System.out.println("helm: heading set to " + heading);
    }

    public void speed(double scale) {
        System.out.println("helm: speed set to " + scale);
    }

    public void depth(double depth) {
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
        Vec3d deltaAcceleration = new Vec3d(acceleration);
        deltaAcceleration.mul(acceleration.length() / 1000 * delta);

        Vec3d deltaVelocity = new Vec3d(velocity);
        deltaVelocity.mul(velocity.length() / 1000 * delta);

        deltaVelocity.add(deltaAcceleration);
        position.add(deltaVelocity);

        System.out.println(this);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
