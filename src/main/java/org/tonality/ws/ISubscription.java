package org.tonality.ws;

import org.tonality.type.SubscriptionStatus;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ISubscription {
    @WebMethod
    @WebResult(name = "subscription")
    org.tonality.model.Subscription createSubscription(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "albumId")
            long albumId
    );

    @WebMethod
    @WebResult(name = "subscription")
    org.tonality.model.Subscription updateSubscription(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "albumId")
            long albumId,
            @WebParam(name = "status")
            org.tonality.type.SubscriptionStatus status
    );

    @WebMethod
    @WebResult(name = "subscription")
    org.tonality.model.Subscription getSubscription(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "albumId")
            long albumId
    );

    @WebMethod
    @WebResult(name = "subscriptions")
    List<org.tonality.model.Subscription> getSubscriptionsByStatus(
            @WebParam(name = "status")
            SubscriptionStatus status
    );

    @WebMethod
    @WebResult(name = "subscriptions")
    List<org.tonality.model.Subscription> getSubscriptionsByUserId(
            @WebParam(name = "userId")
            long userId
    );

    @WebMethod
    boolean deleteSubscription(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "albumId")
            long albumId
    );

    @WebMethod
    boolean deleteSubscriptionsByUserId(
            @WebParam(name = "userId")
            long userId
    );

    @WebMethod
    boolean deleteSubscriptionsByAlbumId(
            @WebParam(name = "albumId")
            long albumId
    );
}
