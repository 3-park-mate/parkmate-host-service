package com.parkmate.hostservice.host.presentation;

import com.parkmate.hostservice.common.response.ApiResponse;
import com.parkmate.hostservice.host.dto.request.HostRegisterRequestForHostServiceDto;
import com.parkmate.hostservice.host.application.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/hosts")
@RequiredArgsConstructor
public class HostInternalController {

    private final HostService hostService;

    @PostMapping("/register")
    public ApiResponse<String> registerHost(@RequestBody HostRegisterRequestForHostServiceDto hostRegisterRequestForHostServiceDto) {

        hostService.register(hostRegisterRequestForHostServiceDto);

        return ApiResponse.of(
                HttpStatus.CREATED,
                "호스트 회원가입이 완료되었습니다."
        );
    }
}