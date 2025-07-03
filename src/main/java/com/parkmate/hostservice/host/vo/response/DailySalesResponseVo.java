package com.parkmate.hostservice.host.vo.response;

import com.parkmate.hostservice.host.dto.response.DailySalesResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class DailySalesResponseVo {

    private String date;
    private BigDecimal totalSalesAmount;

    @Builder
    public DailySalesResponseVo(String date, BigDecimal totalSalesAmount) {
        this.date = date;
        this.totalSalesAmount = totalSalesAmount;
    }

    public static DailySalesResponseVo from(DailySalesResponseDto dto) {
        return DailySalesResponseVo.builder()
                .date(dto.getDate())
                .totalSalesAmount(dto.getTotalSalesAmount())
                .build();
    }
}