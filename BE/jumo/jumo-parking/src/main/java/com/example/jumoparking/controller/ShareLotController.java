package com.example.jumoparking.controller;

import com.example.domain.dto.ShareSaveDto;
import com.example.domain.entity.ShareLot;
import com.example.jumoparking.service.ShareLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shareLot")
@RequiredArgsConstructor
public class ShareLotController {

    private final ShareLotService shareLotService;
    @PostMapping("/save")
    public boolean createShareLot(@Validated ShareSaveDto saveDto, @RequestPart List<MultipartFile> files) throws  Exception{

        return shareLotService.saveShareLot(saveDto, files);
    }


}
