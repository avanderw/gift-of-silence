package gift.of.silence.statemachine;

import java.util.ArrayList;
import java.util.List;

public abstract class AState {
    
    List<Class> from = new ArrayList();

    protected abstract void enter();

    protected abstract void exit();

}
