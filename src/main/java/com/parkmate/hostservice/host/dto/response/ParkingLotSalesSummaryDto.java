package com.parkmate.hostservice.host.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotSalesSummaryDto {
    private String parkingLotUuid;
    private String parkingLotName;
    private int monthlySales;
    private int weeklySales;
} 