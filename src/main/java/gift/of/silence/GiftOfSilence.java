package gift.of.silence;

import gift.of.silence.game.Game;
import gift.of.silence.helm.Helm;
import gift.of.silence.network.Network;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GiftOfSilence {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        Game game = new Game();
        Helm helm = new Helm();
        Network network = new Network(game, helm);
        
        network.startPortListener();
    }
}
