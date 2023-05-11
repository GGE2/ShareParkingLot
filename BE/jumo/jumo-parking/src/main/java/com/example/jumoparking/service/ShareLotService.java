package com.example.jumoparking.service;



import com.example.domain.dto.parking.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShareLotService {
    Long saveShareLot(ShareSaveDto shareSaveDto, List<MultipartFile> files) throws IOException;

    void deleteShareLot(Long sha_id);

    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);

    ParkingDetailDto getDetail(Long parkId, Long userId);

    boolean checkFavorite(Long userId, Long lotId);

    List<MyShareListDto> getListMyShare(Long userId);
}
