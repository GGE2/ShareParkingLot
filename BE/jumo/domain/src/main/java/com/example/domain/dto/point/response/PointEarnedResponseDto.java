package com.example.domain.dto.point.response;

import com.example.domain.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PointEarnedResponseDto {
    private Long creditId;
    private String lotName;
    private String transactionDate;
    private int type;
    private int ptGet;

    @Builder
    public PointEarnedResponseDto(Transaction transaction) {
        this.creditId = transaction.getCredit_id();
        this.lotName = transaction.getLot_name();
        this.transactionDate = transaction.getTransactionDate();
        this.type = transaction.getType();
        this.ptGet = transaction.getPtGet();
    }
}
