package com.parkmate.hostservice.host.presentation;

import com.parkmate.hostservice.common.exception.BaseException;
import com.parkmate.hostservice.common.response.ApiResponse;
import com.parkmate.hostservice.common.response.ResponseStatus;
import com.parkmate.hostservice.host.application.HostService;
import com.parkmate.hostservice.host.vo.response.HostProfileResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
