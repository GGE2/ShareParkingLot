package com.example.domain.dto.parking;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareSaveDto {

    private int shaType;
    private String shaName;
    private Long userId;
    private String jibun;
    private String road;
    private int shaField;
    private int shaFee;
    private String shaProp;
    private float latitude;
    private float longitude;

}
