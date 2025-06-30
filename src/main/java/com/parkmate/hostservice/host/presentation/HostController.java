package com.parkmate.hostservice.host.presentation;

import com.parkmate.hostservice.common.exception.BaseException;
import com.parkmate.hostservice.common.response.ApiResponse;
import com.parkmate.hostservice.common.response.ResponseStatus;
import com.parkmate.hostservice.host.application.HostService;
import com.parkmate.hostservice.host.domain.SettlementCycle;
import com.parkmate.hostservice.host.dto.response.DailySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.MonthlySalesResponseDto;
import com.parkmate.hostservice.host.vo.response.DailySalesResponseVo;
import com.parkmate.hostservice.host.vo.response.HostProfileResponseVo;
import com.parkmate.hostservice.host.vo.response.MonthlySalesResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
}
