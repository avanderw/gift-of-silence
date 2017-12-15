package gift.of.silence.intel;

import gift.of.silence.helm.Helm;
import java.net.DatagramPacket;
import gift.of.silence.lib.network.APacketHandler;

public class Intel implements APacketHandler {

    Helm helm;

    final IntelData data = new IntelData();
    final IntelControl control = new IntelControl(data);
    final IntelSimulator simulator;

    public Intel(Helm helm) {
        this.helm = helm;
        this.simulator = new IntelSimulator(this, helm, data);
    }

    @Override
    public byte[] handle(DatagramPacket packet) {

        return control.packetHandler(packet);
    }

}
