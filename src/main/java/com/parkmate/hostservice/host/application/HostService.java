package com.parkmate.hostservice.host.application;

import com.parkmate.hostservice.host.domain.SettlementCycle;
import com.parkmate.hostservice.host.dto.request.HostRegisterRequestForHostServiceDto;
import com.parkmate.hostservice.host.dto.response.DailySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.HostProfileResponseDto;
import com.parkmate.hostservice.host.dto.response.MonthlySalesResponseDto;

import java.time.LocalDate;

public interface HostService {

    void register(HostRegisterRequestForHostServiceDto hostRegisterRequestForHostServiceDto);

    HostProfileResponseDto getHostProfile(String hostUuid);

    DailySalesResponseDto getDailySales(String hostUuid, String parkingLotUuid, LocalDate date);

    MonthlySalesResponseDto getMonthlySales(String hostUuid, String parkingLotUuid, int year, int month, SettlementCycle cycle);
}

