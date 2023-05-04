package com.example.jumoparking.service.impl;

import com.example.domain.dto.*;
import com.example.domain.entity.*;
import com.example.domain.repo.*;
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

    private final FavoriteRepo favoriteRepo;

    private final UserRepo userRepo;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String drawingStorage;

    private final Storage storage;


    @Override
    public Long saveShareLot(ShareSaveDto shareSaveDto, List<MultipartFile> files) throws IOException {
        System.out.println(shareSaveDto.getUserId());
        Optional<User> user = userRepo.findById(shareSaveDto.getUserId());
        ShareLot shareLot = ShareLot.builder(shareSaveDto, user.get()).build();

        if(files == null || files.size() == 0){
            shareLot = shareLotRepo.save(shareLot);

            if (shareLot == null){

                return -1L;
            }
            return shareLot.getShaId();
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
                return -1L;
            }
            return shareLot.getShaId();
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

    @Override
    public ParkingDetailDto getDetail(Long parkId) {
        return new ParkingDetailDto(shareLotRepo.findById(parkId).get());
    }

    @Override
    public boolean checkFavorite(Long userId, Long lotId) {
        Favorite favorite = favoriteRepo.findFavoritesByShareLot_ShaIdAndUser_UserId(lotId, userId);
        if (favorite == null){
            Favorite newFavorite = Favorite.builder()
                    .parkingLot(null)
                    .shareLot(shareLotRepo.findById(lotId).get())
                    .user(userRepo.findById(userId).get())
                    .build();

            favoriteRepo.save(newFavorite);
            return true;
        }
        else{
            favoriteRepo.delete(favorite);
            return false;
        }
    }

    @Override
    public List<MyShareListDto> getListMyShare(Long userId) {
        List<ShareLot> shareLots = shareLotRepo.findShareLotsByUser_UserId(userId);

        return shareLots.stream().map(shareLot -> new MyShareListDto(shareLot.getShaId(), shareLot.getSha_name())).collect(Collectors.toList());
    }


}
