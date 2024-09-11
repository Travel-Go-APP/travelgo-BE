package com.travelgo.backend.domain.user.entity;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserExp {

    private static final int MAX_LEVEL = 10;

    public static int[] getExpTable(){
        int[] expTable = new int[MAX_LEVEL];
        expTable[0] = 0; // 1레벨
        expTable[1] = 125; // 2레벨
        expTable[2] = 175; // ...
        expTable[3] = 200;
        expTable[4] = 250;

        expTable[5] = 320;
        expTable[6] = 400;
        expTable[7] = 500;
        expTable[8] = 620;
        expTable[9] = 999999;

        return expTable;
    }
}
