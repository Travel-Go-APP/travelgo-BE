package com.travelgo.backend.domain.user.entity;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserExp {

    private static final int maxLevel = 100;
    private static final int startingExp = 500;
    private static final double growthRate = 1.07;

    public static int[] getExpTable(){
        int[] expTable = new int[maxLevel];

        for(int level = 0; level <maxLevel; level++){
            expTable[level] = (int) Math.round(startingExp * Math.pow(growthRate, level));
        }

        return expTable;
    }
}
