package gift.of.silence.intel;

import gift.of.silence.network.Network;
import java.net.DatagramPacket;
import org.pmw.tinylog.Logger;

public class IntelControl {

    IntelData data;

    IntelControl(IntelData data) {
        this.data = data;
    }

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String op = !message.contains(":") ? message : message.substring(0, message.indexOf(":"));
        String response;
        switch (op) {
            case "intel":
                response = String.format("registered");
                Logger.info(response);
                break;
            case "disconnect":
                Network.remove(packet.getAddress(), packet.getPort());
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
