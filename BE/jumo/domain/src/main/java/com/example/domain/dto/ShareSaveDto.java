package com.example.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareSaveDto {

    private int shaType;
    private String shaName;
    private String jibun;
    private String road;
    private int shaField;
    private int shaFee;
    private String shaProp;
    private float latitude;
    private float longitude;

}
