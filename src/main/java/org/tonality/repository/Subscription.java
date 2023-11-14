package org.tonality.repository;

import org.tonality.model.SubscriptionId;
import org.tonality.type.SubscriptionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Subscription extends BaseRepository<org.tonality.model.Subscription>{
    private static Subscription instance = null;

    @Override
    protected Class<org.tonality.model.Subscription> getEntityClass() {
        return org.tonality.model.Subscription.class;
    }

    public static synchronized Subscription getInstance() {
        if (instance == null) {
            instance = new Subscription();
        }
        return instance;
    }

    public org.tonality.model.Subscription createSubscription(long userId, String username, long premiumAlbumId, String albumName, String artist) {
        try {
            org.tonality.model.Subscription subscription = new org.tonality.model.Subscription();
            subscription.setUserId(userId);
            subscription.setUsername(username);
            subscription.setPremiumAlbumId(premiumAlbumId);
            subscription.setAlbumName(albumName);
            subscription.setArtist(artist);
            if (org.tonality.repository.Subscription.getInstance().add(subscription) == null) {
                throw new Exception("Failed to create subscription");
            }
            return subscription;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.tonality.model.Subscription updateSubscription(long userId, long premiumAlbumId, SubscriptionStatus status) {
        try {
            org.tonality.model.Subscription subscription = org.tonality.repository.Subscription.getInstance().getById(new org.tonality.model.SubscriptionId(userId, premiumAlbumId));
            subscription.setStatus(status);
            if (update(subscription) == null) {
                throw new Exception("Failed to update subscription");
            }
            return subscription;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<org.tonality.model.Subscription> searchSubscriptions(SubscriptionStatus status, String searchInput, String orderBy, int page, int size) {
        try {
            if (searchInput == null || searchInput.isEmpty() || searchInput.equals("[string?]")) {
                searchInput = "";
            }

            if (orderBy == null || orderBy.isEmpty() || orderBy.equals("[string?]")) {
                orderBy = "albumName";
            } else {
                orderBy = orderBy.replaceAll(" ", "_");
            }

            if (page < 1) {
                page = 1;
            }
            if (size < 1) {
                size = 15;
            }

            java.util.Map<String, Object> andConditions = new java.util.HashMap<>();
            andConditions.put("status", status);
            java.util.Map<String, Object> orConditions = new java.util.HashMap<>();
            ArrayList<String> searchField = new ArrayList<>();
            searchField.add("username");
            searchField.add("artist");
            searchField.add("albumName");
            return search(andConditions, orConditions, searchInput, searchField, orderBy, page, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<org.tonality.model.Subscription> getSubscriptionByStatus(SubscriptionStatus status, String orderBy, int page, int size) {
        try {
            if (orderBy == null || orderBy.isEmpty() || orderBy.equals("[string?]")) {
                orderBy = "albumName";
            } else {
                orderBy = orderBy.replaceAll(" ", "_");
            }

            if (page < 1) {
                page = 1;
            }
            if (size < 1) {
                size = 15;
            }

            java.util.Map<String, Object> andConditions = new java.util.HashMap<>();
            andConditions.put("status", status);
            java.util.Map<String, Object> orConditions = new java.util.HashMap<>();
            return search(andConditions, orConditions, "", new ArrayList<>(), orderBy, page, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<org.tonality.model.Subscription> getSubscriptionsByUserId(long userId, int page, int size) {
        try {
            java.util.Map<String, Object> andConditions = new java.util.HashMap<>();
            andConditions.put("userId", userId);
            java.util.Map<String, Object> orConditions = new java.util.HashMap<>();
            return search(andConditions, orConditions, "", new ArrayList<>(), "albumName", page, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteSubscriptionsByUserId(long userId) {
        try {
            java.util.Map<String, Object> conditions = new java.util.HashMap<>();
            conditions.put("userId", userId);
            if (!delete(conditions)) {
                throw new Exception("Failed to delete subscriptions");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSubscriptionsByAlbumId(long premiumAlbumId) {
        try {
            java.util.Map<String, Object> conditions = new java.util.HashMap<>();
            conditions.put("premiumAlbumId", premiumAlbumId);
            if (!delete(conditions)) {
                throw new Exception("Failed to delete subscriptions");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Test
    public static void main(String[] args) {
        Subscription subscription = Subscription.getInstance();

        // add
        org.tonality.model.Subscription entity = new org.tonality.model.Subscription();
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                entity.setUserId(i);
                entity.setPremiumAlbumId(j);
                entity.setUsername("User " + i);
                entity.setAlbumName("Album " + j);
                entity.setArtist("Artist " + 10);
                System.out.println(subscription.add(entity));
            }
        }

        // update
        entity.setStatus(org.tonality.type.SubscriptionStatus.ACTIVE);
        System.out.println(subscription.update(entity));

        // get by id
        SubscriptionId id = new SubscriptionId(1, 1);
        entity = subscription.getById(id);
        System.out.println(entity);

        // get by conditions
        Map<String, Object> andConditions = new java.util.HashMap<>();
        andConditions.put("status", org.tonality.type.SubscriptionStatus.ACTIVE.toString());
        java.util.Map<String, Object> orConditions = new java.util.HashMap<>();
        java.util.List<org.tonality.model.Subscription> entities = subscription.search(andConditions, orConditions, "", new ArrayList<>(), "albumName", 1, 15);
        for (org.tonality.model.Subscription e : entities) {
            System.out.println(e + "\n");
        }

        // delete by id
        id = new SubscriptionId(1, 2);
        System.out.println(subscription.deleteById(id));

        // delete by entity
        entity = new org.tonality.model.Subscription();
        entity.setUserId(1);
        entity.setPremiumAlbumId(3);
        System.out.println("Successfully deleted : \n" + subscription.delete(entity));

        // delete by conditions
        andConditions = new java.util.HashMap<>();
        andConditions.put("premiumAlbumId", 1);
        System.out.println("Successfully deleted : \n" + subscription.delete(andConditions));
    }
}
