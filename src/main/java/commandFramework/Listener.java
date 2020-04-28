package commandFramework;

/**
 *
 * @author Simon Norup
 */
public abstract class Listener {

    private final String eventType;
    private final ParamType[] expectedParams;

    public Listener(String eventType, ParamType... expectedParams) {
        this.eventType = eventType;
        this.expectedParams = expectedParams;
    }

    public String getEventType() {
        return eventType;
    }

    public boolean doesEventMatchExpectations(Event event) {
        if (!eventType.equals(event.getEventType())) {
            return false;
        }
        // match checks not required if no params are expected
        if (expectedParams.length == 0) {
            return true;
        }
        // actual number of params less than expected is NOT allowed
        if (expectedParams.length > event.getParams().length) {
            return false;
        }
        try {
            for (int i = 0; i < event.getParams().length; i++) {
                String currentParam = event.getParams()[i];
                // check match on the last expected param if actual number of params exceed the expected
                int matchNr = (i >= expectedParams.length)
                        ? currentParam.length() - 1 : i;
                // check match on param type
                if (expectedParams[matchNr].equals(ParamType.INTEGER)) {
                    Integer.parseInt(event.getParams()[i]);
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public abstract void run(String... params);

}
