package com.example.jumoparking.service;



import com.example.domain.dto.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShareLotService {
    Long saveShareLot(ShareSaveDto shareSaveDto, List<MultipartFile> files) throws IOException;

    void deleteShareLot(Long sha_id);

    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);

    ParkingDetailDto getDetail(Long parkId);

    boolean checkFavorite(Long userId, Long lotId);

    List<MyShareListDto> getListMyShare(Long userId);

}
