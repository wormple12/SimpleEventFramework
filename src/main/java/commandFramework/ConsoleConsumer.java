package commandFramework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Simon Norup
 */
public class ConsoleConsumer implements Consumer {

    private final List<Listener> listeners = new ArrayList<>();
    private final BlockingQueue<Event> eventQueue;

    public ConsoleConsumer(BlockingQueue<Event> eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public List<Listener> getListeners() {
        return listeners;
    }

    @Override
    public BlockingQueue<Event> getEventQueue() {
        return eventQueue;
    }

    @Override
    public void rejectParameters() {
        System.out.println("Please enter valid parameters.");
    }

    @Override
    public void handleInterruption() {
        System.out.println("Interrupted!");
    }

}
