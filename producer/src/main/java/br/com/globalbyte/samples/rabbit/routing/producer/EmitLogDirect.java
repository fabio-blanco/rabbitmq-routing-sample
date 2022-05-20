package br.com.globalbyte.samples.rabbit.routing.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String severity = getSeverity(argv);
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
        }
    }

    private static String getMessage(String[] argv) {
        if ( argv.length == 0 ) {
            return "No message";
        } else if ( argv.length == 1 ) {
            return argv[0];
        } else if (isFirstItemLogLevel(argv)) {
            return String.join(" ", Arrays.copyOfRange(argv, 1, argv.length));
        } else {
            return String.join(" ", argv);
        }
    }

    private static String getSeverity(String[] argv) {
        if (argv.length <= 1 || !isFirstItemLogLevel(argv) ) {
            return "info";
        } else {
            return argv[0];
        }
    }

    private static boolean isFirstItemLogLevel(String[] argv) {
        return "info".equals(argv[0].toLowerCase()) || "warning".equals(argv[0].toLowerCase()) || "error".equals(argv[0].toLowerCase());
    }

}
