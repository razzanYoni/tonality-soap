package org.tonality.service;

import org.tonality.middleware.SubscriptionMiddleware;
import org.tonality.model.SubscriptionId;
import org.tonality.type.SubscriptionStatus;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "org.tonality.service.ISubscription")
public class Subscription extends BaseWebService implements ISubscription {
    @Override
    public org.tonality.model.Subscription createSubscription(long userId, String username, long premiumAlbumId, String albumName, String artist) {
        try {
            logRequest();
            return org.tonality.repository.Subscription.getInstance().createSubscription(userId, username, premiumAlbumId, albumName, artist);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public org.tonality.model.Subscription updateSubscription(long userId, long premiumAlbumId, SubscriptionStatus status) {
        try {
            logRequest();
            return org.tonality.repository.Subscription.getInstance().updateSubscription(userId, premiumAlbumId, status);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public org.tonality.model.Subscription getSubscription(long userId, long premiumAlbumId) {
        try {
            logRequest();
            SubscriptionMiddleware subscriptionMiddleware = new SubscriptionMiddleware();
            return subscriptionMiddleware.execute(org.tonality.repository.Subscription.getInstance().getById(new SubscriptionId(userId, premiumAlbumId)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<org.tonality.model.Subscription> searchSubscription(SubscriptionStatus status, String searchInput, String orderBy, int page, int size) {
        try {
            logRequest();
            SubscriptionMiddleware subscriptionMiddleware = new SubscriptionMiddleware();
            return subscriptionMiddleware.execute(org.tonality.repository.Subscription.getInstance().searchSubscriptions(status, searchInput, orderBy, page, size));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<org.tonality.model.Subscription> getSubscriptionsByStatus(SubscriptionStatus status, String orderBy, int page, int size) {
        try {
            logRequest();
            SubscriptionMiddleware subscriptionMiddleware = new SubscriptionMiddleware();
            return subscriptionMiddleware.execute(org.tonality.repository.Subscription.getInstance().getSubscriptionByStatus(status, orderBy, page, size));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<org.tonality.model.Subscription> getSubscriptionsByUserId(long userId, int page, int size) {
        try {
            logRequest();
            SubscriptionMiddleware subscriptionMiddleware = new SubscriptionMiddleware();
            return subscriptionMiddleware.execute(org.tonality.repository.Subscription.getInstance().getSubscriptionsByUserId(userId, page, size));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteSubscription(long userId, long premiumAlbumId) {
        try {
            logRequest();
            if (!org.tonality.repository.Subscription.getInstance().deleteById(new org.tonality.model.SubscriptionId(userId, premiumAlbumId))) {
                throw new Exception("Failed to delete subscription");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSubscriptionsByUserId(long userId) {
        try {
            logRequest();
            return org.tonality.repository.Subscription.getInstance().deleteSubscriptionsByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSubscriptionsByAlbumId(long premiumAlbumId) {
        try {
            logRequest();
            return org.tonality.repository.Subscription.getInstance().deleteSubscriptionsByAlbumId(premiumAlbumId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
