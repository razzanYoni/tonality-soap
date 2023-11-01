package org.tonality.model;

import org.tonality.type.SubscriptionStatus;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "subscription_id")
    private long subscriptionId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "album_id")
    private long albumId;

    @Column(name = "subscription_status")
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("'CURRENT_TIMESTAMP'")
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("'CURRENT_TIMESTAMP'")
    private Date updatedAt;
}
