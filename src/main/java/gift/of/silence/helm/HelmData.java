package gift.of.silence.helm;

import gift.of.silence.core.Vector2D;

public class HelmData {

    Heading heading = new Heading();
    Speed speed = new Speed(10D, .1D);
    Depth depth = new Depth(100D);
    Velocity velocity = new Velocity(2D / 180D * Math.PI);
    Vector2D position = new Vector2D();

    class Velocity {

        final Double maxRotation;

        Velocity(Double maxRotation) {
            this.maxRotation = maxRotation;
        }

        Vector2D target() {
            Vector2D velocity = new Vector2D();
            velocity.offsetPolar(speed.target(), heading.target());

            return velocity;
        }

        void current(Vector2D velocity) {
            speed.current = velocity.length();
            heading.current = velocity.angle();
        }

        Vector2D current() {
            Vector2D velocity = new Vector2D();
            velocity.offsetPolar(speed.current(), heading.current());

            return velocity;
        }

        Double rotation() {
            Double delta = current().angleBetween(target());
            Double rotation = Math.min(maxRotation, Math.abs(delta));
            if (delta < 0) {
                rotation = -rotation;
            }
            return rotation;
        }
    }

    class Heading {

        Double target = 270D / 180D * Math.PI;
        Double current = 270D / 180D * Math.PI;

        void target(Double target) {
            this.target = target;
        }

        Double target() {
            return target;
        }

        Double current() {
            return current;
        }

    }

    class Speed {

        Double target = 0D;
        Double current = 0D;
        final Double maxSpeed;
        final Double maxAcceleration;

        Speed(Double maxSpeed, Double maxAcceleration) {
            this.maxSpeed = maxSpeed;
            this.maxAcceleration = maxAcceleration;
        }

        void target(Double scale) {
            this.target = maxSpeed * scale;
        }

        Double target() {
            return target;
        }

        Double current() {
            return current;
        }

        Double acceleration() {
            Double delta = target - current;
            Double acceleration = Math.min(maxAcceleration, Math.abs(delta));
            if (delta < 0) {
                acceleration = -acceleration;
            }
            return acceleration;
        }
    }

    class Depth {

        Double target = 0D;
        Double current = 0D;
        final Double design;
        final Double test;
        final Double max;
        final Double crush;

        Depth(Double design) {
            this.design = design;
            test = design * 1.1;
            max = design * 1.2;
            crush = design * 1.3;
        }

        void target(Double target) {
            this.target = target;
        }

        Double target() {
            return target;
        }

        Double current() {
            return current;
        }
    }

}
