package org.tonality.service;

import javax.annotation.Resource;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpExchange;

// https://stackoverflow.com/questions/12727989/jax-ws-getting-client-ip/13693024#13693024
public abstract class BaseWebService {
    @Resource
    protected javax.xml.ws.WebServiceContext context;

    protected final String httpExchangeKey = "com.sun.xml.internal.ws.http.exchange";

    protected String getClientIp() {
        MessageContext messageContext = context.getMessageContext();
        HttpExchange exchange = (HttpExchange) messageContext.get(httpExchangeKey);
        return exchange.getRemoteAddress().getAddress().getHostAddress();
    }

    protected String getClientApiKey() {
        MessageContext messageContext = context.getMessageContext();
        HttpExchange exchange = (HttpExchange) messageContext.get(httpExchangeKey);
        return exchange.getRequestHeaders().getFirst("X-API-KEY");
    }

    protected boolean isRequestValid() {
        return getClientApiKey() != null && getClientApiKey().equals(System.getenv("API_KEY"));
    }

    protected void logRequest() throws Exception {
        if (!isRequestValid()) throw new Exception("Invalid request");
        // TODO : implement logging to database
        System.out.println("Client IP: " + getClientIp());
        System.out.println("Client API Key: " + getClientApiKey());
    }
}
