package com.travelgo.backend.domain.user.dto;

import com.travelgo.backend.domain.user.entity.UserAgree;
import com.travelgo.backend.domain.user.model.Checking;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgreeDto {
    private Checking snsAgree;

    private Checking locationAgree;

    private Checking stepCountAgree;

    public static AgreeDto of(UserAgree userAgree) {
        return new AgreeDto(
                userAgree.getSnsAgree(),
                userAgree.getLocationAgree(),
                userAgree.getStepCountAgree()
        );
    }
}
