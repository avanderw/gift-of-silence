package gift.of.silence.client.debug;

import gift.of.silence.client.network.Network;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.avdw.eventmanager.EventManager;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Debug {

    final Api api;
    final Network network;
    final EventManager events;

    Debug() {
        network = new Network("debug", "localhost", new PacketHandler(this));
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

        Debug debug = new Debug();
        Gui gui = new Gui(debug);

        Frame frame = new Frame("Game");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                debug.network.close();
            }
        });
        frame.add(gui);
        frame.pack();
        frame.setVisible(true);
    }
}
