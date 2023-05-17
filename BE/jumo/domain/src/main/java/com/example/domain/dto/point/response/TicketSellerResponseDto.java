package com.example.domain.dto.point.response;

import com.example.domain.entity.Ticket;
import com.example.domain.etc.OutTiming;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@RequiredArgsConstructor

public class TicketSellerResponseDto {
    @JsonIgnore
    private final OutTiming outTiming;

    private Long ticketId;
    private Long shaId;
    private int ptToEarn;
    private String parkingRegion;
    private String parkingDate;
    private int inTiming;
    private int outTime;
    private boolean sellConfirm;
    private boolean buyConfirm;

    @Builder
    public TicketSellerResponseDto(OutTiming outTiming, Ticket ticket) {
        this.outTiming = outTiming;
        this.ticketId = ticket.getTicket_id();
        this.shaId = ticket.getShareLot().getShaId();
        this.ptToEarn = ticket.getCost();
        this.parkingRegion = ticket.getParkingRegion();
        this.parkingDate = ticket.getParkingDate();
        this.inTiming = ticket.getIn_timing();
        this.outTime = outTiming.OutTimingMethod(ticket.getIn_timing(), ticket.getType());
        this.sellConfirm = ticket.isSell_confirm();
        this.buyConfirm = ticket.isBuy_confirm();
    }

}
