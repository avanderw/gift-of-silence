package gift.of.silence.game;

import java.util.List;
import net.avdw.statemachine.StateMachine.AState;

class StatePlaying implements AState {

    private final Game game;

    StatePlaying(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        game.loop.start();
    }

    @Override
    public void from(List<Class> asList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void exit() {
    }

    @Override
    public List<Class> from() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void process() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
