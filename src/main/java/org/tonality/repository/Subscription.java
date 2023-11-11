package org.tonality.repository;

import org.tonality.model.SubscriptionId;

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

    // Test
    public static void main(String[] args) {
        Subscription subscription = Subscription.getInstance();

        // add
        org.tonality.model.Subscription entity = new org.tonality.model.Subscription();
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                entity.setUserId(i);
                entity.setAlbumId(j);
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
        java.util.List<org.tonality.model.Subscription> entities = subscription.search(andConditions, orConditions);
        for (org.tonality.model.Subscription e : entities) {
            System.out.println(e + "\n");
        }

        // delete by id
        id = new SubscriptionId(1, 2);
        System.out.println(subscription.deleteById(id));

        // delete by entity
        entity = new org.tonality.model.Subscription();
        entity.setUserId(1);
        entity.setAlbumId(3);
        System.out.println("Successfully deleted : \n" + subscription.delete(entity));

        // delete by conditions
        andConditions = new java.util.HashMap<>();
        andConditions.put("albumId", 1);
        System.out.println("Successfully deleted : \n" + subscription.delete(andConditions));
    }
}
