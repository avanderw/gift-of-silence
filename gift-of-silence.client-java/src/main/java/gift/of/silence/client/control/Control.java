package gift.of.silence.client.control;

import gift.of.silence.client.network.Network;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.avdw.eventmanager.EventManager;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Control {

    final Api api;
    final Network network;
    final EventManager events;

    Control() {
        network = new Network("game", "localhost", new PacketHandler(this));
        api = new Api(network);
        events = new EventManager(Event.PACKET_RECEIVED);
    }

    public static final class Event {
        public static final String PACKET_RECEIVED = "packet-received";
    }

    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();

        Control game = new Control();
        Gui gamePanel = new Gui(game);

        Frame frame = new Frame("Game");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                game.network.close();
            }
        });
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);
    }
}
