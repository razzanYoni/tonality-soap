package org.tonality.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
public class SubscriptionId implements Serializable {
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "album_id", nullable = false)
    private long albumId;

    public SubscriptionId() {}

    public SubscriptionId(long userId, long albumId) {
        this.userId = userId;
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SubscriptionId) {
            SubscriptionId other = (SubscriptionId) o;
            return (userId == other.userId) && (albumId == other.albumId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (userId + albumId);
    }
}
