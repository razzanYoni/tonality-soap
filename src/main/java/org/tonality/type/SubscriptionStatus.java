package org.tonality.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum SubscriptionStatus {
    @XmlEnumValue("PENDING")
    PENDING,
    @XmlEnumValue("ACTIVE")
    ACTIVE,
    @XmlEnumValue("REJECTED")
    REJECTED,
    @XmlEnumValue("EXPIRED")
    EXPIRED,
}
