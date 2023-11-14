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

    @Column(name = "premium_album_id", nullable = false)
    private long premiumAlbumId;

    public SubscriptionId() {}

    public SubscriptionId(long userId, long premiumAlbumId) {
        this.userId = userId;
        this.premiumAlbumId = premiumAlbumId;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SubscriptionId) {
            SubscriptionId other = (SubscriptionId) o;
            return (userId == other.userId) && (premiumAlbumId == other.premiumAlbumId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (userId + premiumAlbumId);
    }
}
