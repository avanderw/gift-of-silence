package gift.of.silence.game;

import gift.of.silence.statemachine.StateMachine;
import java.net.DatagramPacket;

class GameControl {
    StateMachine state = new StateMachine();
    
    GameControl() {
        state.addState(new StateUnregistered(), StateStopped.class);
        state.addState(new StateRegistered(), StateUnregistered.class);
        state.addState(new StatePlaying(), StateRegistered.class, StatePaused.class);
        state.addState(new StatePaused(), StatePlaying.class);
        state.addState(new StateStopped(), StatePlaying.class, StatePaused.class);
        
        state.initial(StateUnregistered.class);
    }

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String response;
        switch (message) {
            case "game": 
                response = "o-game: registered";
                state.change(StateRegistered.class);
                break;
            case "play":
                response = " -game: playing";
                state.change(StatePlaying.class);
                break;
            case "pause":
                response = " -game: pausing";
                state.change(StatePaused.class);
                break;
            case "stop":
                response = " -game: stopping";
                state.change(StateStopped.class);
                break;
            case "disconnect":
                response = "x-game: disconnecting";
                state.change(StateUnregistered.class);
                break;
            default:
                response = String.format("?-game: unknown (%s)", message);
        }

        return response.getBytes();
    }
}