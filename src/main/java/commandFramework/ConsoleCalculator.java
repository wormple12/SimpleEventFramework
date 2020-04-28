package commandFramework;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Simon Norup
 */
public class ConsoleCalculator {

    private static final BlockingQueue<Event> EVENT_QUEUE = new ArrayBlockingQueue(10);
    private static Thread consumerThread;
    private static Thread producerThread;

    public static void main(String[] args) {
        System.out.println("Welcome!");
        Consumer consumer = new ConsoleConsumer(EVENT_QUEUE);
        Producer producer = new ConsoleProducer(EVENT_QUEUE);

        consumerThread = new Thread(consumer);
        consumerThread.start();

        producerThread = new Thread(producer);
        producerThread.start();

        populate(consumer);
    }

    private static void populate(Consumer consumer) {
        consumer.addListener(new Listener("exit") {
            @Override
            public void run(String... params) {
                System.out.println("Exiting program...");
                System.exit(0);
            }
        });

        consumer.addListener(new Listener("add", ParamType.INTEGER, ParamType.INTEGER) {
            @Override
            public void run(String... params) {
                if (params.length == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                Integer[] numbers = Arrays.asList(params).stream()
                        .map(param -> Integer.parseInt(param))
                        .toArray(Integer[]::new);
                int result = 0;
                for (int value : numbers) {
                    result += value;
                }
                System.out.println("Result: " + result);
            }
        });

        consumer.addListener(new Listener("subtract", ParamType.INTEGER, ParamType.INTEGER) {
            @Override
            public void run(String... params) {
                if (params.length == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                Integer[] numbers = Arrays.asList(params).stream()
                        .map(param -> Integer.parseInt(param))
                        .toArray(Integer[]::new);
                int result = numbers[0];
                for (int i = 1; i < numbers.length; i++) {
                    result -= numbers[i];
                }
                System.out.println("Result: " + result);
            }
        });
    }

}
