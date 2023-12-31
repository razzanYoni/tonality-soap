package org.tonality.service;

import org.tonality.type.SubscriptionStatus;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@WebService
public interface ISubscription {
    @WebMethod
    @WebResult(name = "subscription")
    org.tonality.model.Subscription createSubscription(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "username")
            String username,
            @WebParam(name = "premiumAlbumId")
            long premiumAlbumId,
            @WebParam(name = "albumName")
            String albumName,
            @WebParam(name = "artist")
            String artist
    );

    @WebMethod
    @WebResult(name = "subscription")
    org.tonality.model.Subscription updateSubscription(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "premiumAlbumId")
            long premiumAlbumId,
            @WebParam(name = "status")
            org.tonality.type.SubscriptionStatus status
    );

    @WebMethod
    @WebResult(name = "subscription")
    org.tonality.model.Subscription getSubscription(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "premiumAlbumId")
            long premiumAlbumId
    );

    @WebMethod
    @WebResult(name = "subscription")
    List<org.tonality.model.Subscription> searchSubscription(
            @WebParam(name = "status")
            SubscriptionStatus status,
            @WebParam(name = "searchInput")
            String searchInput,
            @WebParam(name = "orderBy")
            String orderBy,
            @WebParam(name = "page")
            int page,
            @WebParam(name = "size")
            int size
    );

    @WebMethod
    @WebResult(name = "subscription")
    List<org.tonality.model.Subscription> getSubscriptionsByStatus(
            @WebParam(name = "status")
            SubscriptionStatus status,
            @WebParam(name = "orderBy")
            String orderBy,
            @WebParam(name = "page")
            int page,
            @WebParam(name = "size")
            int size
    );

    @WebMethod
    @WebResult(name = "subscription")
    List<org.tonality.model.Subscription> getSubscriptionsByUserId(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "page")
            int page,
            @WebParam(name = "size")
            int size
    );

    @WebMethod
    boolean deleteSubscription(
            @WebParam(name = "userId")
            long userId,
            @WebParam(name = "premiumAlbumId")
            long premiumAlbumId
    );

    @WebMethod
    boolean deleteSubscriptionsByUserId(
            @WebParam(name = "userId")
            long userId
    );

    @WebMethod
    boolean deleteSubscriptionsByAlbumId(
            @WebParam(name = "premiumAlbumId")
            long premiumAlbumId
    );
}
