package com.example.domain.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long car_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "car_str")
    private String car_str;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean car_rep;

    // ????
    public boolean getCar_rep() {
        return car_rep;
    }

    public void setCar_rep(boolean car_rep) {
        this.car_rep = car_rep;
    }
}
