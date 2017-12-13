package gift.of.silence.game;

import java.util.ArrayList;
import java.util.List;
import net.avdw.statemachine.StateMachine.AState;

class StatePaused implements AState {

    private final Game game;
    List<Class> from = new ArrayList();

    StatePaused(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        game.loop.stop();
    }

    @Override
    public void exit() {
    }

    @Override
    public void from(List<Class> states) {
        from.addAll(states);
    }

    @Override
    public List<Class> from() {
        return from;
    }

    @Override
    public void process() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
