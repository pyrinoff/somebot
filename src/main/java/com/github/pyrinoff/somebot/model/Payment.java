package com.github.pyrinoff.somebot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.HashSet;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "payments")
public class Payment {
    public static final int PAYMENT_SYSTEM_QIWI = 2;

    public final static int PAYMENT_STATUS_AWAITING = 200;
    public final static int PAYMENT_STATUS_PAID = 201;
    public final static int PAYMENT_STATUS_REJECTED = 202;
    public final static int PAYMENT_STATUS_EXPIRED = 203;

    public final static Integer PAYMENT_CURRENCY_RUB = 2;
    public final static Integer PAYMENT_CURRENCY_USD = 3;

    @Transient
    HashSet<Integer> correctCurrencies = new HashSet<>() {{
        add(PAYMENT_CURRENCY_RUB);
    }};

    @Transient
    HashSet<Integer> correctPaymentSystem = new HashSet<>() {{
        add(PAYMENT_SYSTEM_QIWI);
    }};

    public static final int FLOOD_SECONDS_ON_PAYMENT_CHECK = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    @Column(name="id")
    private Long id; //long для bigint (Types#BIGINT)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName= "chat_id", nullable = false)
    private User user;

    private String externalId;

    @Column(nullable = false)
    private Double amount;

    private Integer status;

    private String url;

    @Column(name = "payment_system", nullable = false)
    private Integer paymentSystem;

    private String comment;

    @Column(nullable = false)
    private Integer currency;

    @Column(nullable = true)
    //@Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(nullable = true)
    //@Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;

    @Column(name = "last_checked", nullable = true)
    private Timestamp lastChecked;

    public Payment() {

    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public boolean isPayed() {
        return getStatus() == PAYMENT_STATUS_PAID;
    }

    public boolean isExpired() {
        return getStatus() == PAYMENT_STATUS_EXPIRED;
    }

    public boolean isRejected() {
        return getStatus() == PAYMENT_STATUS_REJECTED;
    }

    public Timestamp getLastChecked() {
        return lastChecked != null ? lastChecked : new Timestamp(0);
    }
}
