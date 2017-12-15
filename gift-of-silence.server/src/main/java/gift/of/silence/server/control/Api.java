package gift.of.silence.server.control;

import gift.of.silence.api.AGameApi;
import net.avdw.statemachine.StateMachine;
import java.net.DatagramPacket;
import org.pmw.tinylog.Logger;

class Api implements AGameApi {

    StateMachine state = new StateMachine();

    Api(Control game) {
        state.addState(new StateUnregistered(), StateStopped.class);
        state.addState(new StateRegistered(), StateUnregistered.class);
        state.addState(new StatePlaying(game), StateRegistered.class, StatePaused.class);
        state.addState(new StatePaused(game), StatePlaying.class);
        state.addState(new StateStopped(), StatePlaying.class, StatePaused.class);

        state.initial(StateUnregistered.class);
    }

    @Override
    public void disconnect() {
        state.transition(StateUnregistered.class);
    }

    @Override
    public void pause() {
        state.transition(StatePaused.class);
    }

    @Override
    public void play() {

        state.transition(StatePlaying.class);
    }

    @Override
    public void register() {
        state.transition(StateRegistered.class);
    }
}
