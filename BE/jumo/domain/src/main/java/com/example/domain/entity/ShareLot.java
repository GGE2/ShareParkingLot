package com.example.domain.entity;


import com.example.domain.dto.ShareSaveDto;
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
    private Long sha_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int sha_type;

    private String sha_name;

    private String sha_jibun;

    private String sha_road;

    private int sha_field;

    private int sha_fee;

    @Column(columnDefinition="TEXT")
    private String sha_prop;

    private float latitude;

    private float longitude;

    @OneToMany(mappedBy = "img_id", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "favorite_id", cascade = CascadeType.ALL)
    private List<Favorite> favoriteList = new ArrayList<>();

    public static ShareLotBuilder builder(ShareSaveDto shareSaveDto) {
        return ShareLotBuilder()
                .latitude(shareSaveDto.getLatitude())
                .longitude(shareSaveDto.getLongitude())
                .sha_fee(shareSaveDto.getShaFee())
                .sha_field(shareSaveDto.getShaField())
                .sha_jibun(shareSaveDto.getJibun())
                .sha_road(shareSaveDto.getRoad())
                .sha_type(shareSaveDto.getShaType())
                .sha_name(shareSaveDto.getShaName())
                .sha_prop(shareSaveDto.getShaProp());
    }


}
