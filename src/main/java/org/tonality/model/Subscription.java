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

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = SubscriptionStatus.PENDING;
        }
        updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

    public String toString() {
        return "Subscription: " + subscriptionId + " " + userId + " " + albumId + " " + status;
    }

    // Test
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Create subscription
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Subscription subscription = new Subscription();
            subscription.setUserId(1);
            subscription.setAlbumId(1);
            session.save(subscription);
            tx.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Get subscription from database
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Subscription lastSubsId =  session.createQuery("from subscription order by subscriptionId desc", Subscription.class).setMaxResults(1).list().get(0);
            Subscription subscription = session.get(Subscription.class, lastSubsId.getSubscriptionId());
            System.out.println(subscription);
            tx.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Update subscription
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Subscription firstSubsId =  session.createQuery("from subscription order by subscriptionId asc", Subscription.class).setMaxResults(1).list().get(0);
            Subscription subscription = session.get(Subscription.class, firstSubsId.getSubscriptionId());
            subscription.setStatus(SubscriptionStatus.ACTIVE);
            session.update(subscription);
            tx.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Delete subscription
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Subscription lastSubsId =  session.createQuery("from subscription order by subscriptionId desc", Subscription.class).setMaxResults(1).list().get(0);
            Subscription subscription = session.get(Subscription.class, lastSubsId.getSubscriptionId());
            session.delete(subscription);
            tx.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
