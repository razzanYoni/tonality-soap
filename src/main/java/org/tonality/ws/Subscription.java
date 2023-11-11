package org.tonality.ws;

import org.tonality.type.SubscriptionStatus;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "org.tonality.ws.ISubscription")
public class Subscription implements ISubscription {
    @Override
    public org.tonality.model.Subscription createSubscription(long userId, long albumId) {
        try {
            org.tonality.model.Subscription subscription = new org.tonality.model.Subscription();
            subscription.setUserId(userId);
            subscription.setAlbumId(albumId);
            if (org.tonality.repository.Subscription.getInstance().add(subscription) == null) {
                throw new Exception("Failed to create subscription");
            }
            return subscription;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public org.tonality.model.Subscription updateSubscription(long userId, long albumId, SubscriptionStatus status) {
        try {
            org.tonality.model.Subscription subscription = new org.tonality.model.Subscription();
            subscription.setUserId(userId);
            subscription.setAlbumId(albumId);
            subscription.setStatus(status);
            if (org.tonality.repository.Subscription.getInstance().update(subscription) == null) {
                throw new Exception("Failed to update subscription");
            }
            return subscription;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public org.tonality.model.Subscription getSubscription(long userId, long albumId) {
        try {
            org.tonality.model.SubscriptionId id = new org.tonality.model.SubscriptionId(userId, albumId);
            return org.tonality.repository.Subscription.getInstance().getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<org.tonality.model.Subscription> getSubscriptionsByStatus(SubscriptionStatus status) {
        try {
            java.util.Map<String, Object> andConditions = new java.util.HashMap<>();
            andConditions.put("status", status.toString());
            java.util.Map<String, Object> orConditions = new java.util.HashMap<>();
            return org.tonality.repository.Subscription.getInstance().search(andConditions, orConditions);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<org.tonality.model.Subscription> getSubscriptionsByUserId(long userId) {
        try {
            java.util.Map<String, Object> andConditions = new java.util.HashMap<>();
            andConditions.put("userId", userId);
            java.util.Map<String, Object> orConditions = new java.util.HashMap<>();
            return org.tonality.repository.Subscription.getInstance().search(andConditions, orConditions);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteSubscription(long userId, long albumId) {
        try {
            org.tonality.model.SubscriptionId id = new org.tonality.model.SubscriptionId(userId, albumId);
            if (!org.tonality.repository.Subscription.getInstance().deleteById(id)) {
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
            java.util.Map<String, Object> conditions = new java.util.HashMap<>();
            conditions.put("userId", userId);
            if (!org.tonality.repository.Subscription.getInstance().delete(conditions)) {
                throw new Exception("Failed to delete subscriptions");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSubscriptionsByAlbumId(long albumId) {
        try {
            java.util.Map<String, Object> conditions = new java.util.HashMap<>();
            conditions.put("albumId", albumId);
            if (!org.tonality.repository.Subscription.getInstance().delete(conditions)) {
                throw new Exception("Failed to delete subscriptions");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
