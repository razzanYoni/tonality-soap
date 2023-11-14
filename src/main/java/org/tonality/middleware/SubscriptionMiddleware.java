package org.tonality.middleware;

import org.tonality.repository.Subscription;
import org.tonality.type.SubscriptionStatus;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriptionMiddleware extends BaseMiddleware<org.tonality.model.Subscription> {
    @Override
    public org.tonality.model.Subscription execute(org.tonality.model.Subscription entity) {
        if (entity.getStatus() == SubscriptionStatus.ACTIVE
                && new Date().getTime() - entity.getUpdatedAt().getTime() > (long) Integer.parseInt(System.getenv("EXPIRATION_DAYS")) * 60 * 60 * 1000) {
            entity.setStatus(SubscriptionStatus.EXPIRED);
            Subscription.getInstance().update(entity);
        }
        return entity;
    }

    public List<org.tonality.model.Subscription> execute(List<org.tonality.model.Subscription> entities) {
        List<org.tonality.model.Subscription> result = new ArrayList<>();
        for (org.tonality.model.Subscription entity : entities) {
            result.add(this.execute(entity));
        }
        return result;
    }
}
