package gift.of.silence;

import gift.of.silence.debug.Debug;
import gift.of.silence.server.control.Control;
import gift.of.silence.helm.Helm;
import gift.of.silence.intel.Intel;
import gift.of.silence.network.Network;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Server {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();

        Control game = new Control();
        Debug debug = new Debug();
        Helm helm = new Helm();
        Intel intel = new Intel(helm);

        Network network = new Network(game, debug, helm, intel);
    }
}
