package com.parkmate.hostservice.host.application;

import com.parkmate.hostservice.host.dto.request.HostRegisterRequestForHostServiceDto;

public interface HostService {

    void register(HostRegisterRequestForHostServiceDto hostRegisterRequestForHostServiceDto);
}
