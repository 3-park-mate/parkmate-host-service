package com.parkmate.hostservice.host.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class DailySalesResponseDto {

    private String date;
    private BigDecimal totalSalesAmount;

    @Builder
    private DailySalesResponseDto(String date, BigDecimal totalSalesAmount) {
        this.date = date;
        this.totalSalesAmount = totalSalesAmount;
    }

    public static DailySalesResponseDto from(String date, BigDecimal totalSalesAmount) {
        return DailySalesResponseDto.builder()
                .date(date)
                .totalSalesAmount(totalSalesAmount)
                .build();
    }
}
