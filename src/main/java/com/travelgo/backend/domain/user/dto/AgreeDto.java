package com.travelgo.backend.domain.user.dto;

import com.travelgo.backend.domain.user.entity.UserAgree;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgreeDto {
    private Boolean snsAgree;

    private Boolean locationAgree;

    private Boolean stepCountAgree;

    public static AgreeDto of(UserAgree userAgree) {
        return new AgreeDto(
                userAgree.getSnsAgree(),
                userAgree.getLocationAgree(),
                userAgree.getStepCountAgree()
        );
    }
}
