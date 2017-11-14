package gift.of.silence;

import gift.of.silence.helm.Helm;
import gift.of.silence.simulator.ISimulated;
import gift.of.silence.simulator.Simulator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameOfSilence {

    public static void main(String[] args) throws InterruptedException {
        Helm helm = new Helm();
        List<ISimulated> systems = new ArrayList();
        systems.add(helm);
        
        Simulator simulator = new Simulator(systems);
        simulator.start();
        TimeUnit.SECONDS.sleep(5);
        simulator.stop();
    }
}
