package com.parkmate.hostservice.host.application;

import com.parkmate.hostservice.host.domain.SettlementCycle;
import com.parkmate.hostservice.host.dto.request.HostRegisterRequestForHostServiceDto;
import com.parkmate.hostservice.host.dto.response.DailySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.HostProfileResponseDto;
import com.parkmate.hostservice.host.dto.response.MonthlySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.ParkingLotSalesSummaryDto;
import com.parkmate.hostservice.host.dto.response.ParkingLotWeeklySalesDto;
import com.parkmate.hostservice.host.dto.response.WeeklySalesResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface HostService {

    void register(HostRegisterRequestForHostServiceDto hostRegisterRequestForHostServiceDto);

    HostProfileResponseDto getHostProfile(String hostUuid);

    DailySalesResponseDto getDailySales(String hostUuid, String parkingLotUuid, LocalDate date);

    MonthlySalesResponseDto getMonthlySales(String hostUuid, String parkingLotUuid, int year, int month, SettlementCycle cycle);

    List<ParkingLotSalesSummaryDto> getParkingLotSalesSummary(String hostUuid, int year, Integer month, Integer weekOfMonth);

    List<ParkingLotWeeklySalesDto> getParkingLotsWeeklySalesByRange(String hostUuid, String startDate, String endDate);

    WeeklySalesResponseDto getWeeklySalesByRange(String hostUuid, String parkingLotUuid, String startDate, String endDate);
}

