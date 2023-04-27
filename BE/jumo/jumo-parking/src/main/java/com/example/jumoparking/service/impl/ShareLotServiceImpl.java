package com.example.jumoparking.service.impl;

import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.domain.dto.ShareSaveDto;
import com.example.domain.entity.Image;
import com.example.domain.entity.ParkingLot;
import com.example.domain.entity.ShareLot;
import com.example.domain.repo.ImageRepo;
import com.example.domain.repo.ParkingLotRepo;
import com.example.domain.repo.ShareLotRepo;
import com.example.jumoparking.service.ShareLotService;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShareLotServiceImpl implements ShareLotService {

    private final ShareLotRepo shareLotRepo;

    private final ImageRepo imageRepo;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String drawingStorage;

    private final Storage storage;


    @Override
    public boolean saveShareLot(ShareSaveDto shareSaveDto, @RequestPart List<MultipartFile> files) throws IOException {

        ShareLot shareLot = ShareLot.builder(shareSaveDto).build();

        if(files.size() == 0){
            shareLot = shareLotRepo.save(shareLot);

            if (shareLot == null){

                return false;
            }
            return true;
        }
        else{

            shareLot = shareLotRepo.save(shareLot);

            for (MultipartFile file : files){

                String uuid = UUID.randomUUID().toString(); // GCS에 저장될 파일 이름
                String type =file.getContentType(); // 파일 형식

                // cloud 이미지 업로드
                BlobInfo blobInfo = storage.create(
                        BlobInfo.newBuilder(drawingStorage, uuid)
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                                .setContentType(type)
                                .build(),
                        file.getInputStream()
                );

                Image image = Image.builder()
                        .url("https://storage.googleapis.com/bucket_lunamires/" + uuid)
                        .shareLot(shareLot)
                        .build();

                image = imageRepo.save(image);

            }


            if (shareLot == null){

                return false;
            }
            return true;
        }

    }

    @Override
    public void deleteShareLot(Long sha_id) {
        Optional<ShareLot> shareLot = shareLotRepo.findById(sha_id);
        if (shareLot.isPresent()){
            shareLotRepo.delete(shareLot.get());
        }
    }

    @Override
    public List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto) {
        List<ShareLot> shareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());

        return shareLots.stream().map(shareLot -> new ParkingListDto(shareLot)).collect(Collectors.toList());
    }


}
