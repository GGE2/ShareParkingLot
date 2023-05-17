package com.example.jumouser.service;

import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.response.*;

import java.util.List;

public interface TicketService {

    // 구매 가능한 주차권
    TypeResponseDto getTypeAvailability(Long shaId, int inTiming);

    // 주차권 구매
    TicketCreateResponseDto ticketCreate(Long userId, TicketCreateRequestDto ticketCreateRequestDto);

    // 구매 주차권 List 조회
    List<TicketBuyerResponseDto> getBoughtTicketList(Long userId);

    // 판매 주차권 List 조회
    List<TicketSellerResponseDto> getSoldTicketList(Long userId, Long shaId);

    // 주차권 상세 조회
    TicketDetailResponseDto getTicket(Long ticketId);

    // 판매 확정
    TicketSellConfirmResponseDto ticketSellConfirm(Long userId, Long ticketId);

    // 구매 확정
    TicketBuyConfirmResponseDto ticketBuyConfirm(Long userId, Long ticketId);
}
