package com.example.domain.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Builder(builderMethodName = "TransactionBuilder")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long credit_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "lot_name")
    private String lot_name;

    @Column(name = "pt_get")
    private int ptGet;

    @Column(name = "pt_lose")
    private int ptLose;

    @Column(name = "sha_id")
    private Long sha_id;

    private int type;

    @Builder
    public Transaction(User user, String name, int get, int lose, Long shaId, int type)
    {
        this.user = user;
        this.transactionDate = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.lot_name = name;
        this.ptGet = get;
        this.ptLose = lose;
        this.sha_id = shaId;
        this.type = type;
    }

}
