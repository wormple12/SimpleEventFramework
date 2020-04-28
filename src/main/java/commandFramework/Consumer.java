package commandFramework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 *
 * @author Simon Norup
 */
public interface Consumer extends Runnable {

    default public void addListener(Listener listener) {
        getListeners().add(listener);
        if (!Event.isCommandAllowed(listener.getEventType())) {
            Event.allowCommand(listener.getEventType());
        }
    }

    default public boolean onEvent(Event event) {
        List<Listener> matchingListeners = getListeners().stream()
                .filter(listener -> listener.doesEventMatchExpectations(event))
                .collect(Collectors.toList());

        // notify all matching listeners of the event
        matchingListeners.forEach(listener -> listener.run(event.getParams()));

        // if an allowed command type has been used, and it still fails to find any matching listeners
        // the given parameters are not valid, and we return false
        return !matchingListeners.isEmpty();
    }

    @Override
    default public void run() {
        try {
            while (true) {
                Event event = getEventQueue().take();
                boolean wasValid = onEvent(event);
                if (!wasValid) {
                    rejectParameters();
                }
            }
        } catch (InterruptedException e) {
            handleInterruption();
        }
    }
    
    public List<Listener> getListeners();
    
    public BlockingQueue<Event> getEventQueue();

    public void rejectParameters();

    public void handleInterruption();
}
