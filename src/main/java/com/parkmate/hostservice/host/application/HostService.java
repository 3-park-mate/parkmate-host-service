package com.parkmate.hostservice.host.application;

import com.parkmate.hostservice.host.dto.request.HostRegisterRequestForHostServiceDto;
import com.parkmate.hostservice.host.dto.response.HostProfileResponseDto;

public interface HostService {

    void register(HostRegisterRequestForHostServiceDto hostRegisterRequestForHostServiceDto);

    HostProfileResponseDto getHostProfile(String hostUuid);
}
