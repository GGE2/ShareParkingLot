package com.example.domain.dto.point.response;

import com.example.domain.entity.Ticket;
import com.example.domain.etc.OutTiming;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TicketCreateResponseDto {
    @JsonIgnore
    private final OutTiming outTiming;

    private Long ticketId;
    private String buyer;
    private String carNumber;
    private Long shaId;
    private String parkingRegion;
    private String address;
    private int type;
    private int inTiming;
    private int outTime;
    private int cost;

    @Builder
    public TicketCreateResponseDto(OutTiming outTiming, Ticket ticket) {
        this.outTiming = outTiming;
        this.ticketId = ticket.getTicket_id();
        this.buyer = ticket.getBuyer().getName();
        this.carNumber = ticket.getCar_number();
        this.shaId = ticket.getShareLot().getShaId();
        this.parkingRegion = ticket.getParkingRegion();
        this.address = ticket.getAddress();
        this.type = ticket.getType();
        this.inTiming = ticket.getIn_timing();
        this.outTime = outTiming.OutTimingMethod(inTiming, type);
        this.cost = ticket.getCost();
    }
}
