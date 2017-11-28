package gift.of.silence;

import gift.of.silence.debug.Debug;
import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import gift.of.silence.intel.Intel;
import gift.of.silence.network.Network;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class GiftOfSilence {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();

        Game game = new Game();
        Debug debug = new Debug();
        Helm helm = new Helm();
        Intel intel = new Intel();
        
        Network network = new Network(game, debug, helm, intel);

        network.startPortListener();
    }
}
