package gift.of.silence.server.control;

import java.util.ArrayList;
import java.util.List;
import net.avdw.statemachine.StateMachine.AState;

class StatePlaying implements AState {

    private final Control game;
    List<Class> from = new ArrayList();

    StatePlaying(Control game) {
        this.game = game;
    }

    @Override
    public void enter() {
        game.loop.start();
    }

    @Override
    public void from(List<Class> states) {
        from.addAll(states);
    }

    @Override
    public void exit() {
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
