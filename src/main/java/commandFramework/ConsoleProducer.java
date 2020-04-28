package commandFramework;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Simon Norup
 */
public class ConsoleProducer implements Producer {

    private static final Scanner SCANNER = new Scanner(System.in);
    private final BlockingQueue<Event> eventQueue;

    public ConsoleProducer(BlockingQueue<Event> eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public Object getInput() {
        return SCANNER.nextLine();
    }

    @Override
    public String getEventType(Object input) {
        return ((String) input).split(" ")[0];
    }

    @Override
    public String[] getParams(Object input) {
        String[] command = ((String) input).split(" ");
        String[] params = {};
        if (command.length > 1) {
            params = Arrays.asList(command)
                    .subList(1, command.length).stream()
                    .toArray(String[]::new);
        }
        return params;
    }

    @Override
    public void rejectCommand() {
        System.out.println("Please enter a valid command.");
    }

    @Override
    public void putEventInQueue(Event event) throws InterruptedException {
        eventQueue.put(event);
    }

    @Override
    public void handleInterruption(Exception e) {
        System.out.println("Interrupted!");
    }

}
