package com.example.domain.entity;


import com.example.domain.dto.ShareSaveDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareLot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sha_id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
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


    public static ShareLotBuilder builder(ShareSaveDto studyDto) {
        return null;
    }


}
