package commandFramework;

/**
 *
 * @author Simon Norup
 */
public interface Producer extends Runnable {

    @Override
    default public void run() {
        try {
            while (true) {
                Object input = getInput();
                String eventType = getEventType(input);
                String[] params = getParams(input);

                if (!Event.isCommandAllowed(eventType)) {
                    rejectCommand();
                    continue;
                }

                putEventInQueue(new Event(eventType, params));
            }
        } catch (InterruptedException e) {
            handleInterruption(e);
        }
    }

    // could be private methods with Java 9
    
    public Object getInput();

    public String getEventType(Object input);

    public String[] getParams(Object input);
    
    public void rejectCommand();

    public void putEventInQueue(Event event) throws InterruptedException;
    
    public void handleInterruption(Exception e);

}
