package org.tonality.model;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.tonality.type.SubscriptionStatus;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity(name = "subscription")
@Table(name = "subscription")
@IdClass(SubscriptionId.class)
public class Subscription implements Serializable {
    @Id
    private long userId;

    @Id
    private long albumId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "album_name", nullable = false)
    private String albumName;

    @Column(name = "artist", nullable = false)
    private String artist;

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
        return "Subscription: \n" + "userId :" + userId + "\n" + "username :" + username + "\n" + "albumId :" + albumId + "\n" + "albumName :" + albumName + "\n" + "artist :" + artist + "\n" + "status :" + status + "\n" + "createdAt :" + createdAt + "\n" + "updatedAt :" + updatedAt + "\n";
    }
}
