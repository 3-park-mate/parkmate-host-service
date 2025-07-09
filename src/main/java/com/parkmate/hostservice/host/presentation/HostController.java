package com.parkmate.hostservice.host.presentation;

import com.parkmate.hostservice.common.exception.BaseException;
import com.parkmate.hostservice.common.response.ApiResponse;
import com.parkmate.hostservice.common.response.ResponseStatus;
import com.parkmate.hostservice.host.application.HostService;
import com.parkmate.hostservice.host.domain.SettlementCycle;
import com.parkmate.hostservice.host.dto.response.DailySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.MonthlySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.ParkingLotSalesSummaryDto;
import com.parkmate.hostservice.host.dto.response.ParkingLotWeeklySalesDto;
import com.parkmate.hostservice.host.dto.response.WeeklySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.FlexibleWeeklyStatisticsDto;
import com.parkmate.hostservice.host.vo.response.DailySalesResponseVo;
import com.parkmate.hostservice.host.vo.response.HostProfileResponseVo;
import com.parkmate.hostservice.host.vo.response.MonthlySalesResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hosts")
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @Operation(
            summary = "호스트 정보 조회",
            description = "X-Host-UUID 헤더를 통해 hostUuid를 전달받아 해당 호스트 정보를 조회하는 API 입니다.",
            tags = {"HOST-SERVICE"}
    )
    @GetMapping("/profile")
    public ApiResponse<HostProfileResponseVo> getHostByUuid(@RequestHeader("X-Host-UUID") String hostUuid) {

        System.out.println("[호스트 UUID 수신] hostUuid = " + hostUuid); // 로그 찍기

        if (hostUuid == null || hostUuid.isBlank()) {
            throw new BaseException(ResponseStatus.INVALID_REQUEST); // 명확한 예외
        }

        return ApiResponse.ok(
                hostService.getHostProfile(hostUuid).toVo()
        );
    }

    @Operation(
            summary = "호스트 일매출 조회",
            description = "특정 호스트가 관리하는 특정 주차장의 지정 날짜에 대한 일매출 총합을 조회합니다.",
            tags = {"SETTLEMENT"}
    )
    @GetMapping("/parking-lots/{parkingLotUuid}/sales/daily")
    public ApiResponse<DailySalesResponseVo> getDailySales(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @PathVariable String parkingLotUuid,
            @RequestParam String date) {

        LocalDate localDate = LocalDate.parse(date);
        DailySalesResponseDto dto = hostService.getDailySales(hostUuid, parkingLotUuid, localDate);
        DailySalesResponseVo response = DailySalesResponseVo.from(dto);

        return ApiResponse.of(
                HttpStatus.OK,
                "일매출 조회 성공",
                response
        );
    }

    @Operation(
            summary = "호스트 월매출 조회",
            description = "특정 호스트가 관리하는 특정 주차장의 월매출 총합을 조회합니다. 15일 또는 말일 기준으로 선택 가능합니다.",
            tags = {"SETTLEMENT"}
    )
    @GetMapping("/parking-lots/{parkingLotUuid}/sales/monthly")
    public ApiResponse<MonthlySalesResponseVo> getMonthlySales(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @PathVariable String parkingLotUuid,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(name = "cycle", defaultValue = "THIRTY") SettlementCycle cycle) {

        MonthlySalesResponseDto dto = hostService.getMonthlySales(hostUuid, parkingLotUuid, year, month, cycle);
        MonthlySalesResponseVo response = MonthlySalesResponseVo.from(dto);

        return ApiResponse.of(
                HttpStatus.OK,
                "월매출 조회 성공",
                response
        );
    }

    @Operation(
            summary = "호스트 주간 일매출 리스트 조회 (날짜 범위)",
            description = "특정 호스트가 관리하는 특정 주차장의 날짜 범위별 일매출 리스트를 조회합니다.",
            tags = {"SETTLEMENT"}
    )
    @GetMapping("/parking-lots/{parkingLotUuid}/sales/weekly-range")
    public ApiResponse<WeeklySalesResponseDto> getWeeklySalesByRange(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @PathVariable String parkingLotUuid,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        WeeklySalesResponseDto dto = hostService.getWeeklySalesByRange(hostUuid, parkingLotUuid, startDate, endDate);
        return ApiResponse.of(
                HttpStatus.OK,
                "주간 일매출 리스트(날짜 범위) 조회 성공",
                dto
        );
    }

    @Operation(
            summary = "호스트 전체 주차장별 월/주 매출 요약 조회",
            description = "호스트가 등록한 전체 주차장별 월/주 매출 요약을 조회합니다.",
            tags = {"SETTLEMENT"}
    )
    @GetMapping("/parking-lots/sales/summary")
    public ApiResponse<List<ParkingLotSalesSummaryDto>> getParkingLotSalesSummary(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(required = false) Integer weekOfMonth) {
        List<ParkingLotSalesSummaryDto> summaryList = hostService.getParkingLotSalesSummary(hostUuid, year, month, weekOfMonth);
        return ApiResponse.ok(summaryList);
    }

    @Operation(
            summary = "호스트 전체 주차장 주별 매출 조회",
            description = "호스트가 등록한 전체 주차장의 날짜 범위별 주별 매출을 조회합니다.",
            tags = {"SETTLEMENT"}
    )
    @GetMapping("/parking-lots/sales/weekly-range")
    public ApiResponse<List<ParkingLotWeeklySalesDto>> getParkingLotsWeeklySalesByRange(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<ParkingLotWeeklySalesDto> result = hostService.getParkingLotsWeeklySalesByRange(hostUuid, startDate, endDate);
        return ApiResponse.ok(result);
    }

    @Operation(
        summary = "호스트 flexible 주간 총매출 합계 조회",
        description = "호스트가 등록한 모든 주차장의 flexible 주간 총매출 합계를 조회합니다. (totalWeeklySales만 반환)",
        tags = {"SETTLEMENT"}
    )
    @GetMapping("/parking-lots/weekly-statistics-flexible")
    public ApiResponse<FlexibleWeeklyStatisticsDto> getFlexibleWeeklyStatistics(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam("baseDate") String baseDate,
            @RequestParam(value = "daysBefore", required = false) Integer daysBefore,
            @RequestParam(value = "daysAfter", required = false) Integer daysAfter
    ) {
        FlexibleWeeklyStatisticsDto result = hostService.getFlexibleWeeklyStatistics(hostUuid, baseDate, daysBefore, daysAfter);
        return ApiResponse.ok(result);
    }
}
