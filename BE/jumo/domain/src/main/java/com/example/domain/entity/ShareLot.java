package com.example.domain.entity;


import com.example.domain.dto.parking.ShareSaveDto;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "ShareLotBuilder")
public class ShareLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sha_id")
    private Long shaId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int sha_type;

    private String sha_name;

    private String sha_jibun;

    private String sha_road;

    private int sha_field;

    @Column(name = "sha_fee")
    private int shaFee;

    @Column(columnDefinition="TEXT")
    private String sha_prop;

    private float latitude;

    private float longitude;

    @Builder.Default
    @OneToMany(mappedBy = "shareLot", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "shareLot", cascade = CascadeType.ALL)
    private List<Favorite> favoriteList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "shareLot", cascade = CascadeType.ALL)
    private List<DayData> dayDataList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "shareLot", cascade = CascadeType.ALL)
    private List<Ticket> ticketList = new ArrayList<>();

    public static ShareLotBuilder builder(ShareSaveDto shareSaveDto, User user) {
        return ShareLotBuilder()
                .user(user)
                .latitude(shareSaveDto.getLatitude())
                .longitude(shareSaveDto.getLongitude())
                .shaFee(shareSaveDto.getShaFee())
                .sha_field(shareSaveDto.getShaField())
                .sha_jibun(shareSaveDto.getJibun())
                .sha_road(shareSaveDto.getRoad())
                .sha_type(shareSaveDto.getShaType())
                .sha_name(shareSaveDto.getShaName())
                .sha_prop(shareSaveDto.getShaProp());
    }


}
