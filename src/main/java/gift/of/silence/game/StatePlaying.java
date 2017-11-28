package gift.of.silence.game;

import gift.of.silence.statemachine.AState;

class StatePlaying extends AState {

    private final Game game;

    StatePlaying(Game game) {
        this.game = game;
    }

    @Override
    protected void enter() {
        game.loop.start();
    }

    @Override
    protected void exit() {
    }

}
