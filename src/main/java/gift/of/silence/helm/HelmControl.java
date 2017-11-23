package gift.of.silence.helm;

import java.net.DatagramPacket;

public class HelmControl {

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String response;
        switch (message) {
            case "helm":
                response = " -helm: registered";
                break;
            default:
                response = String.format("?-helm: unknown message (%s)", message);
        }
        
        return response.getBytes();
    }

}
