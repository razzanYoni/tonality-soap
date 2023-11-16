package org.tonality;

import org.tonality.service.Subscription;

import javax.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/subscription", new Subscription());
        System.out.println("Endpoint published at http://localhost:8888/subscription");
    }
}
