package org.tonality.model;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.tonality.type.SubscriptionStatus;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ColumnDefault;
import org.tonality.util.HibernateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity(name = "subscription")
@Table(name = "subscription")
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "subscription_id")
    private long subscriptionId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "album_id", nullable = false)
    private long albumId;

    @Column(name = "subscription_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDING'")
    private SubscriptionStatus status;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6)")
    private Date updatedAt;
}
