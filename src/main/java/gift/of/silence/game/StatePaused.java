package gift.of.silence.game;

import gift.of.silence.statemachine.AState;

class StatePaused extends AState {
    private final Game game;

    StatePaused(Game game) {
        this.game = game;
    }
    
    @Override
    protected void enter() {
        game.loop.stop();
    }

    @Override
    protected void exit() {
    }

    @Override
    protected void process() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
