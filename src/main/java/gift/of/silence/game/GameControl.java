package gift.of.silence.game;

import java.net.DatagramPacket;

class GameControl {

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String response;
        switch (message) {
            case "play":
                response = "game: playing";
                break;
            case "pause":
                response = "game: pausing";
                break;
            case "disconnect":
                response = "game: disconnecting";
                break;
            default:
                response = "game: could not handle message";
        }

        return response.getBytes();
    }
}
