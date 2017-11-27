package gift.of.silence.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventManager {

    Map<String, List<Consumer>> listeners = new HashMap();

    public EventManager(String... eventTypes) {
        for (String eventType : eventTypes) {
            listeners.put(eventType, new ArrayList());
        }
    }

    public void subscribe(String eventType, Consumer listener) {
        listeners.get(eventType).add(listener);
    }

    public void unsubscribe(String eventType, Consumer listener) {
        listeners.get(eventType).remove(listener);
    }

    public void notify(String eventType, Object payload) {
        System.out.println(String.format("event{time:%s, type:%s, payload:%s}", new Date(), eventType, payload));
        if (listeners.get(eventType) != null) {
            listeners.get(eventType).forEach((listener) -> {
                listener.accept(payload);
            });
        }
    }
}
