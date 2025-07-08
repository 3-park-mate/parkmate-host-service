package com.parkmate.hostservice.host.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class WeeklySalesResponseDto {
    private String startDate;
    private String endDate;
    private List<DailySales> dailySalesList;

    @Getter
    @NoArgsConstructor
    public static class DailySales {
        private String date; // yyyy-MM-dd
        private int amount;

        @Builder
        public DailySales(String date, int amount) {
            this.date = date;
            this.amount = amount;
        }
    }

    @Builder
    public WeeklySalesResponseDto(String startDate, String endDate, List<DailySales> dailySalesList) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailySalesList = dailySalesList;
    }
} 