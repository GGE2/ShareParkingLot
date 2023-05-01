package com.example.domain.entity;

import javax.persistence.*;

import com.example.domain.dto.DaySaveDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "DayDataBuilder")
public class DayData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long day_id;

    @ManyToOne
    @JoinColumn(name = "sha_id")
    private ShareLot shareLot;

    @Enumerated(EnumType.STRING)
    private DayName day_str;

    private int day_start;

    private int day_end;

    @Builder
    public static DayDataBuilder builder(DaySaveDto daySaveDto, ShareLot shareLot){
        return DayDataBuilder()
                .shareLot(shareLot)
                .day_str(daySaveDto.getDay_str())
                .day_start(daySaveDto.getDay_start())
                .day_end(daySaveDto.getDay_end());
    }

}
