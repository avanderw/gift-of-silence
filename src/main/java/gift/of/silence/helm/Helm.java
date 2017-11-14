/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gift.of.silence.helm;

import com.google.gson.Gson;
import com.sun.javafx.geom.Vec3d;
import gift.of.silence.event.EventManager;
import gift.of.silence.simulator.ISimulated;

/**
 *
 * @author Andrew
 */
public class Helm implements ISimulated {

    public transient EventManager events = new EventManager();
    
    public transient double turnRate;
    public Vec3d position = new Vec3d();
    public transient Vec3d velocity = new Vec3d(1,0,0); // m/s
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
    }

    public void speed(double speed) {
    }

    public void depth(double depth) {
    }

    public void toggleDock() {
        
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
