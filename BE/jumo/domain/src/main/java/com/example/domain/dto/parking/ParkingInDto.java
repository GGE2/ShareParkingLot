package com.example.domain.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingInDto {
    private float startLat;
    private float endLat;
    private float startLng;
    private float endLng;
    private float centerLat;
    private float centerLng;
    private float zoomLevel;
}
