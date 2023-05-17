package com.example.domain.dto.point.response;

import com.example.domain.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PointSpentResponseDto {
    private Long creditId;
    private String lotName;
    private String transactionDate;
    private int ptLose;
    private int type;

    @Builder
    public PointSpentResponseDto(Transaction transaction) {
        this.creditId = transaction.getCredit_id();
        this.lotName = transaction.getLot_name();
        this.transactionDate = transaction.getTransactionDate();
        this.ptLose = transaction.getPtLose();
        this.type = transaction.getType();
    }
}
