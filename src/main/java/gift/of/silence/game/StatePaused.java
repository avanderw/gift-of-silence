package gift.of.silence.game;

import java.util.List;
import net.avdw.statemachine.StateMachine.AState;

class StatePaused implements AState {
    private final Game game;

    StatePaused(Game game) {
        this.game = game;
    }

    @Override
    public List<Class> from() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void enter() {
        game.loop.stop();
    }

    @Override
    public void exit() {
    }

    @Override
    public void from(List<Class> asList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void process() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
