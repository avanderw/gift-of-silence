package gift.of.silence.helm;

import gift.of.silence.statemachine.StateMachine;
import java.net.DatagramPacket;

public class HelmControl {

    StateMachine state = new StateMachine();

    HelmControl() {

    }

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String response = null;
        switch (message) {
            case "helm":
                response = "o-helm: registered";
                break;
            case "heading":
                response = String.format(" -helm: heading (target=%s, current=%s)");
                break;
            case "speed":
                response = String.format(" -helm: speed (target=%s, current=%s)");
                break;
            case "depth":
                response = String.format(" -helm: depth (target=%s, current=%s)");
                break;
            case "disconnect":
                response = String.format("x-helm: disconnecting");
                break;
            default:
                response = String.format("?-helm: unknown (%s)", message);
        }

        return response.getBytes();
    }

}
