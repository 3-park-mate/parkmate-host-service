package com.parkmate.hostservice.host.infrastructure.client;

import com.parkmate.hostservice.host.domain.SettlementCycle;
import com.parkmate.hostservice.host.dto.response.DailySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.MonthlySalesResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "batch-service")
public interface HostSettlementFeignClient {

    @GetMapping("/internal/settlements/daily")
    DailySalesResponseDto getDailySales(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam("parkingLotUuid") String parkingLotUuid,
            @RequestParam("date") String date
    );

    @GetMapping("/internal/settlements/monthly")
    MonthlySalesResponseDto getMonthlySales(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam("parkingLotUuid") String parkingLotUuid,
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam("cycle") SettlementCycle cycle
    );
}
