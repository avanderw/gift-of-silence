package gift.of.silence.server.control;

import java.util.ArrayList;
import java.util.List;
import net.avdw.statemachine.StateMachine.AState;

class StateUnregistered implements AState {

    List<Class> from = new ArrayList();

    @Override
    public List<Class> from() {
        return this.from;
    }

    @Override
    public void enter() {

    }

    @Override
    public void from(List<Class> states) {
        from.addAll(states);
    }

    @Override
    public void exit() {

    }

    @Override
    public void process() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
