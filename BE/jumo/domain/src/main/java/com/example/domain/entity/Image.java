package com.example.domain.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long img_id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sha_id")
    private ShareLot shareLot;

}
