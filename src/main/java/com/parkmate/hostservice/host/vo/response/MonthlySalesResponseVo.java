package com.parkmate.hostservice.host.vo.response;

import com.parkmate.hostservice.host.dto.response.MonthlySalesResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MonthlySalesResponseVo {

    private String yearMonth;
    private BigDecimal totalSalesAmount;

    @Builder
    public MonthlySalesResponseVo(String yearMonth, BigDecimal totalSalesAmount) {
        this.yearMonth = yearMonth;
        this.totalSalesAmount = totalSalesAmount;
    }

    public static MonthlySalesResponseVo from(MonthlySalesResponseDto dto) {
        return MonthlySalesResponseVo.builder()
                .yearMonth(dto.getYearMonth())
                .totalSalesAmount(dto.getTotalSalesAmount())
                .build();
    }
}
