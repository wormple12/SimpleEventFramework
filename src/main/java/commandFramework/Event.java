package commandFramework;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon Norup
 */
public class Event {

    private static final List<String> ALLOWED_COMMANDS = new ArrayList<>();

    private final String eventType;
    private final String[] params;

    public Event(String eventType, String... params) {
        this.eventType = eventType;
        this.params = params;
    }

    public String getEventType() {
        return eventType;
    }

    public String[] getParams() {
        return params;
    }

    public static boolean isCommandAllowed(String eventType) {
        return ALLOWED_COMMANDS.contains(eventType);
    }

    public static void allowCommand(String eventType) {
        ALLOWED_COMMANDS.add(eventType);
    }

}
