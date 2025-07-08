package com.parkmate.hostservice.host.infrastructure.client;

import com.parkmate.hostservice.host.domain.SettlementCycle;
import com.parkmate.hostservice.host.dto.response.DailySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.MonthlySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.ParkingLotWeeklySalesDto;
import com.parkmate.hostservice.host.dto.response.WeeklySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.DailySalesSummaryDto;
import com.parkmate.hostservice.host.dto.response.ParkingLotSalesSummaryDto;
import com.parkmate.hostservice.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/internal/settlements/weekly")
    WeeklySalesResponseDto getWeeklySales(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam("parkingLotUuid") String parkingLotUuid,
            @RequestParam("year") int year,
            @RequestParam("week") int week
    );

    @GetMapping("/internal/settlements/weekly/range")
    List<DailySalesSummaryDto> getWeeklySalesByRange(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam("parkingLotUuid") String parkingLotUuid,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    );

    @GetMapping("/internal/settlements/summary")
    List<ParkingLotSalesSummaryDto> getParkingLotSalesSummary(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam(value = "week", required = false) Integer week
    );

    // 기존: 날짜 범위로 전체 주차장 주별 매출 조회
    @GetMapping("/internal/settlements/weekly/range/all")
    ApiResponse<List<ParkingLotWeeklySalesDto>> getParkingLotsWeeklySalesByRange(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    );

    // 신규: year, week로 전체 주차장 주별 매출 조회
    @GetMapping("/internal/settlements/weekly/all")
    List<ParkingLotWeeklySalesDto> getAllParkingLotsWeeklySales(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam("year") int year,
            @RequestParam("week") int week
    );
}

