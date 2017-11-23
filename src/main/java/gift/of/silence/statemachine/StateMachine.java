package gift.of.silence.statemachine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StateMachine {

    private Class current;
    private final Map<Class, AState> states = new HashMap();

    public void addState(AState state, Class... fromStates) {
        states.put(state.getClass(), state);
        if (fromStates != null) {
            states.get(state.getClass()).from.addAll(Arrays.asList(fromStates));
        }
    }

    public void change(Class state) {
        if (current == null) {
            System.out.println(String.format("?-state-machine(%s): initial state not set", current.getSimpleName()));
        }
        
        if (!states.containsKey(state)) {
            System.out.println(String.format("x-state-machine(%s): unknown state(%s)", current.getSimpleName(), state.getSimpleName()));
            return;
        }

        if (states.get(state).from.contains(current)) {
            System.out.println(String.format(" -state-machine(%s): change to state(%s)", current.getSimpleName(), state.getSimpleName()));
            
            states.get(current).exit();
            states.get(state).enter();
            
            current = state;
        } else {
            System.out.println(String.format("?-state-machine(%s): cannot change to state (%s)", current.getSimpleName(), state.getSimpleName()));
        }
    }

    public void initial(Class state) {
        states.get(state).enter();
        current = state;
    }

}
