package com.example.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@RedisHash("PhoneValidation")
public class PhoneValidation {

    @Id
    private String phone;
    private String validation_value;
    private Boolean flag;

    public PhoneValidation changeFlag(){
        this.flag =!flag;
        return this;
    }
}
