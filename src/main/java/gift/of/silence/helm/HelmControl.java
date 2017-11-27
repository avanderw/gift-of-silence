package gift.of.silence.helm;

import gift.of.silence.statemachine.StateMachine;
import java.net.DatagramPacket;

public class HelmControl {

    StateMachine state = new StateMachine();
    HelmData data;

    HelmControl() {

    }

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String response;
        switch (message.substring(message.indexOf(":"))) {
            case "helm":
                response = "o-helm: registered";
                break;
            case "heading":
                data.heading.target(Double.parseDouble(message));
                response = String.format(" -helm: heading (target=%s, current=%s)", data.heading.target(), data.heading.current());
                break;
            case "speed":
                data.speed.target(Double.parseDouble(message));
                response = String.format(" -helm: speed (target=%s, current=%s)", data.speed.target(), data.speed.current());
                break;
            case "depth":
                data.depth.target(Double.parseDouble(message));
                response = String.format(" -helm: depth (target=%s, current=%s)", data.depth.target(), data.depth.current());
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
