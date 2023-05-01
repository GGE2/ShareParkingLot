package com.example.domain.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long car_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String car_str;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean car_rep;

    // ??? car_rep 왜 lombock 자동 getter 가 안돼는지 모르겠다.
    public boolean getCar_rep() {
        return car_rep;
    }

    public void setCar_rep(boolean car_rep) {
        this.car_rep = car_rep;
    }
}
