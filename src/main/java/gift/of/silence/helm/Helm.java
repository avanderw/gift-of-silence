/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gift.of.silence.helm;

import com.sun.javafx.geom.Vec3d;
import gift.of.silence.event.EventManager;
import gift.of.silence.simulator.ISimulated;

/**
 *
 * @author Andrew
 */
public class Helm implements ISimulated {
    public EventManager events; 
    public double turnRate;
    public double climbRate;
    public double diveRate;
    
    public Vec3d velocity;
    public Vec3d acceleration;
    
    public Vec3d maxAcceleration;
    public Vec3d maxVelocity;
    
    public Depth depth;
    public class Depth {
        public double design;
        public double test;
        public double maximum;
        public double crush;
    }
    
    
    public Helm() {
        events = new EventManager();
    }
    
    public void setHeading(){}
    public void setVelocity(){}
    public void setDepth(){}
    public void toggleDock(){}

    
    
    @Override
    public void simulate(long refreshRate) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
