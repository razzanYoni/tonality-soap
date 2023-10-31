package org.tonality.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Subscription {
    @WebMethod
    public String test() {
        return "Hello World!";
    }
}
