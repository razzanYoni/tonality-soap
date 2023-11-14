import org.tonality.service.Subscription;

import javax.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
        System.out.println("Endpoint published at http://localhost:8888/subscription");
        Endpoint.publish("http://localhost:8888/subscription", new Subscription());
    }
}
