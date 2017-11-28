package gift.of.silence.game;

import gift.of.silence.statemachine.StateMachine;
import java.net.DatagramPacket;
import org.pmw.tinylog.Logger;

class GameControl {
    StateMachine state = new StateMachine();
    
    GameControl(Game game) {
        state.addState(new StateUnregistered(), StateStopped.class);
        state.addState(new StateRegistered(), StateUnregistered.class);
        state.addState(new StatePlaying(game), StateRegistered.class, StatePaused.class);
        state.addState(new StatePaused(game), StatePlaying.class);
        state.addState(new StateStopped(), StatePlaying.class, StatePaused.class);
        
        state.initial(StateUnregistered.class);
    }

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String response;
        switch (message) {
            case "game": 
                response = "registered";
                Logger.info(String.format("%s:%s %s", packet.getAddress(), packet.getPort(), response));
                state.transition(StateRegistered.class);
                break;
            case "play":
                response = "playing";
                Logger.info(response);
                state.transition(StatePlaying.class);
                break;
            case "pause":
                response = "pausing";
                Logger.info(response);
                state.transition(StatePaused.class);
                break;
            case "stop":
                response = "stopping";
                Logger.info(response);
                state.transition(StateStopped.class);
                break;
            case "disconnect":
                response = "disconnecting";
                Logger.info(response);
                state.transition(StateUnregistered.class);
                break;
            default:
                response = String.format("unknown message = %s", message);
                Logger.warn(response);
        }

        return response.getBytes();
    }
}
