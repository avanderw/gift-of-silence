package gift.of.silence.eventmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.pmw.tinylog.Logger;

public class EventManager {

    Map<String, List<Consumer>> consumers = new HashMap();

    public EventManager(String... eventTypes) {
        for (String eventType : eventTypes) {
            consumers.put(eventType, new ArrayList());
        }
    }

    public void subscribe(String eventType, Consumer consumer) {
        
        consumers.get(eventType).add(consumer);
    }

    public void unsubscribe(String eventType, Consumer consumer) {
        consumers.get(eventType).remove(consumer);
    }

    public void fire(String eventType, Object payload) {
        Logger.trace(String.format("%tT: event{%s(%s)}", new Date(), eventType, payload));
        if (consumers.get(eventType) != null) {
            consumers.get(eventType).forEach((listener) -> {
                listener.accept(payload);
            });
        }
    }
}
