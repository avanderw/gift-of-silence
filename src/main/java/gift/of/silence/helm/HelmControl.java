package gift.of.silence.helm;

import gift.of.silence.network.Network;
import gift.of.silence.statemachine.StateMachine;
import java.net.DatagramPacket;
import org.pmw.tinylog.Logger;

public class HelmControl {

    StateMachine state = new StateMachine();
    final HelmData data;
    
    HelmControl(HelmData data) {
        this.data = data;
    }

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String op = !message.contains(":") ? message : message.substring(0, message.indexOf(":"));
        String response;
        switch (op) {
            case "helm":
                response = "registered";
                Logger.info(String.format("%s:%s %s", packet.getAddress(), packet.getPort(), response));
                break;
            case "heading":
                data.heading.target(Double.parseDouble(message));
                response = String.format("heading (target=%s, current=%s)", data.heading.target(), data.heading.current());
                Logger.info(response);
                break;
            case "speed":
                data.speed.target(Double.parseDouble(message));
                response = String.format("speed (target=%s, current=%s)", data.speed.target(), data.speed.current());
                Logger.info(response);
                break;
            case "depth":
                data.depth.target(Double.parseDouble(message));
                response = String.format("depth (target=%s, current=%s)", data.depth.target(), data.depth.current());
                Logger.info(response);
                break;
            case "disconnect":
                Network.connections.remove(packet.getAddress(), packet.getPort());
                response = String.format("disconnecting");
                Logger.info(response);
                break;
            default:
                response = String.format("unknown message = %s", message);
                Logger.warn(response);
        }

        return response.getBytes();
    }

}
