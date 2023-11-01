package org.tonality.type;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum SubscriptionStatus {
    PENDING,
    ACTIVE,
    REJECTED,
    EXPIRED,
}
