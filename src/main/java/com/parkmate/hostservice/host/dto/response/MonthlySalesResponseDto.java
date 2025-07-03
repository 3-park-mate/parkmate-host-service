package com.parkmate.hostservice.host.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MonthlySalesResponseDto {

    private String yearMonth;
    private BigDecimal totalSalesAmount;

    @Builder
    public MonthlySalesResponseDto(String yearMonth, BigDecimal totalSalesAmount) {
        this.yearMonth = yearMonth;
        this.totalSalesAmount = totalSalesAmount;
    }

    public static MonthlySalesResponseDto from(String yearMonth, BigDecimal totalSalesAmount) {
        return MonthlySalesResponseDto.builder()
                .yearMonth(yearMonth)
                .totalSalesAmount(totalSalesAmount)
                .build();
    }
}