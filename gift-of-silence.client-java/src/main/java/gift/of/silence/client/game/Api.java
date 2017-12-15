package gift.of.silence.client.game;

import gift.of.silence.api.AGameApi;
import gift.of.silence.client.network.Network;

class Api implements AGameApi {

    final Network network;

    Api(Network network) {
        this.network = network;
    }

    @Override
    public void play() {
        network.send("play".getBytes());
    }

    @Override
    public void register() {
        network.send("game".getBytes());
    }

    @Override
    public void pause() {
        network.send("pause".getBytes());
    }

    @Override
    public void disconnect() {
        network.send("disconnect".getBytes());
    }

}
