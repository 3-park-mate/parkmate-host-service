package com.parkmate.hostservice.host.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotWeeklySalesDto {
    private String parkingLotUuid;
    private String parkingLotName;
    private List<DailySales> dailySalesList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DailySales {
        private String date;
        private int amount;
    }


} 