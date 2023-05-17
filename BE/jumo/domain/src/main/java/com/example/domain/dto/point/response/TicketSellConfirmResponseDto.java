package com.example.domain.dto.point.response;

import com.example.domain.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TicketSellConfirmResponseDto {
    private Long ticketId;
    private Long shaId;
    private int ptToEarn;
    private String parkingRegion;
    private String parkingDate;
    private boolean sellConfirm;

    @Builder
    public TicketSellConfirmResponseDto(Ticket ticket) {
        this.ticketId = ticket.getTicket_id();
        this.shaId = ticket.getShareLot().getShaId();
        this.ptToEarn = ticket.getCost();
        this.parkingRegion = ticket.getParkingRegion();
        this.parkingDate = ticket.getParkingDate();
        this.sellConfirm = ticket.isSell_confirm();
    }

}
