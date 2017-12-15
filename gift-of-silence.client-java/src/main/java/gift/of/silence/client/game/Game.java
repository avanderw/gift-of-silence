package gift.of.silence.client.game;

import gift.of.silence.client.network.Network;
import gift.of.silence.lib.network.IPacketHandler;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

public class Game {

    final Api api;
    final Network network;

    Game() {
        network = new Network("game", "localhost", new PacketHandler());
        api = new Api(network);
    }

    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();

        Game game = new Game();
        GamePanel gamePanel = new GamePanel(game);

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
