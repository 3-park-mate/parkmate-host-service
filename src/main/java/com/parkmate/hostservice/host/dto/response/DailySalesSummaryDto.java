package com.parkmate.hostservice.host.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailySalesSummaryDto {
    private String hostUuid;
    private String parkingLotUuid;
    private String date; // yyyy-MM-dd
    private int amount;
} 