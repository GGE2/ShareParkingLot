package com.example.domain.etc;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OutTiming {

    public int OutTimingMethod(int inTiming, int type) {
        int[] typeToHour = new int[]{1, 3, 5, 24};
        int outTiming = inTiming + typeToHour[type];

        outTiming = Math.min(outTiming, 24);
        return outTiming;
    }


}
