package com.example.domain.dto.point.response;

import com.example.domain.entity.Image;
import com.example.domain.entity.Ticket;
import com.example.domain.etc.OutTiming;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TicketDetailResponseDto {
    @JsonIgnore
    private final OutTiming outTiming;

    private Long ticketId;
    private String parkingRegion;
    private String address;
    private String nickname;
    private String carNumber;
    private String parkingDate;
    private int type;
    private int inTiming;
    private int outTime;
    private int cost;
    private List<String> images;
    private boolean sellConfirm;
    private boolean buyConfirm;
    private String buyerNumber;
    private String sellerNumber;

    @Builder
    public TicketDetailResponseDto(OutTiming outTiming, Ticket ticket, String buyerNumber, String sellerNumber) {
        this.outTiming = outTiming;
        this.ticketId = ticket.getTicket_id();
        this.parkingRegion = ticket.getParkingRegion();
        this.address = ticket.getAddress();
        this.nickname = ticket.getBuyer().getName();
        this.carNumber = ticket.getCar_number();
        this.parkingDate = ticket.getParkingDate();
        this.type = ticket.getType();
        this.inTiming = ticket.getIn_timing();
        this.outTime = outTiming.OutTimingMethod(ticket.getIn_timing(), ticket.getType());
        this.cost = ticket.getCost();
        List<Image> images = ticket.getShareLot().getImages();
        List<String> imageUrl = new ArrayList<>();
        for (Image image :images){
            imageUrl.add(image.getUrl());
        }
        this.images = imageUrl;
        this.sellConfirm = ticket.isSell_confirm();
        this.buyConfirm = ticket.isBuy_confirm();
        this.buyerNumber = buyerNumber;
        this.sellerNumber = sellerNumber;
    }
}
