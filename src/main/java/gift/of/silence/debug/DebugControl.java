package gift.of.silence.debug;

import java.net.DatagramPacket;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

class DebugControl {

    byte[] packetHandler(DatagramPacket packet) {
        String message = new String(packet.getData());
        message = message.trim();

        String op = !message.contains(":") ? message : message.substring(0, message.indexOf(":"));
        String response;
        switch (op) {
            case "level":
                switch (message.substring("level:".length())) {
                    case "trace":
                        Configurator.currentConfig().level(Level.TRACE).activate();
                        break;
                    case "debug":
                        Configurator.currentConfig().level(Level.DEBUG).activate();
                        break;
                    case "warning":
                        Configurator.currentConfig().level(Level.WARNING).activate();
                        break;
                    case "error":
                        Configurator.currentConfig().level(Level.ERROR).activate();
                        break;
                    case "off":
                        Configurator.currentConfig().level(Level.OFF).activate();
                        break;
                    default:
                    case "info":
                        Configurator.currentConfig().level(Level.INFO).activate();
                        break;
                }

                response = String.format("log-level=%s", Logger.getLevel());
                Logger.debug(response);
                break;
            case "debug":
                response = String.format("registered");
                Logger.info(response);
                break;
            default:
                response = String.format("unknown message = %s", message);
                Logger.warn(response);
        }

        return response.getBytes();
    }
}
