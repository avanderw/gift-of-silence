package gift.of.silence.server.control;

import gift.of.silence.lib.network.APacketHandler;
import java.net.DatagramPacket;
import org.pmw.tinylog.Logger;

public class PacketHandler implements APacketHandler {

    final Control game;

    PacketHandler(Control game) {
        this.game = game;
    }

    @Override
    public byte[] handle(DatagramPacket packet) {
        String msg = new String(packet.getData());
        msg = msg.trim();

        String response;
        switch (msg) {
            case "game":
                game.api.register();
                response = "registered";
                break;
            case "play":
                game.api.play();
                response = "playing";
                break;
            case "pause":
                game.api.pause();
                response = "pausing";
                break;
            case "disconnect":
                game.api.disconnect();
                response = "disconnecting";
                break;
            default:
                response = String.format("unknown message = %s", msg);
                Logger.warn(response);
        }

        return response.getBytes();
    }

}
