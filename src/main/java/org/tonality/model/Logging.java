package org.tonality.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "logging")
@Table(name = "logging")
public class Logging {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "log_id", nullable = false)
    private long logId;

    @Column(name = "endpoint", nullable = false)
    private String endpoint;

    @Column(name = "ip", nullable = false)
    private String IP;

    @Column(name = "description", nullable = false)
    private String description;
}
