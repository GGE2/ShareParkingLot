package com.example.jumouser.service.impl;

import com.example.domain.dto.point.response.*;
import com.example.domain.entity.*;
import com.example.domain.repo.ShareLotRepo;
import com.example.domain.repo.TicketRepo;
import com.example.domain.repo.TransactionRepo;
import com.example.domain.repo.UserRepo;
import com.example.error.exception.InputException;
import com.example.jumouser.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final ShareLotRepo shareLotRepo;
    private final TicketRepo ticketRepo;

    @Override
    public int getUserPoint(Long userId) {
        Optional<User> currUser = userRepo.findById(userId);
        if (currUser.isPresent()) {
            return currUser.get().getPtHas();
        } else throw new IllegalStateException();
    }

    @Override
    public List<PointEarnedResponseDto> getEarnedPointList(Long userId, String year, String month) {
        Optional<User> currUser = userRepo.findById(userId);
        String date = year + "-" + month;

        if (currUser.isPresent()) {
            List<Transaction> transactions = transactionRepo.findAllByUserAndTransactionDateStartingWithAndPtGetGreaterThan(currUser.get(), date, 0);
            return transactions.stream().map(PointEarnedResponseDto::new).collect(Collectors.toList());
        }
        throw new InputException("this user is not present");
    }

    @Override
    public List<PointSpentResponseDto> getSpentPointList(Long userId, String year, String month) {
        Optional<User> currUser = userRepo.findById(userId);
        String date = year + "-" + month;

        if (currUser.isPresent()) {
            List<Transaction> transactions = transactionRepo.findAllByUserAndTransactionDateStartingWithAndPtLoseGreaterThan(currUser.get(), date, 0);

            return transactions.stream().map(PointSpentResponseDto::new).collect(Collectors.toList());
        }
        throw new InputException("this user is not present");
    }

    @Override
    @Transactional
    public PointChargeResponseDto chargePoint(Long userId, int ptCharge) {
        Optional<User> user = userRepo.findById(userId);

        // 포인트 증가 User Entity 반영
        if(user.isPresent()){
            User currUser = user.get();
            currUser.addPoint(ptCharge);
            userRepo.save(currUser);

            return new PointChargeResponseDto(currUser, ptCharge);

        }
        return null;
    }

}
