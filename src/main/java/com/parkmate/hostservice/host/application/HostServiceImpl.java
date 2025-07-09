package com.parkmate.hostservice.host.application;

import com.parkmate.hostservice.common.exception.BaseException;
import com.parkmate.hostservice.common.response.ResponseStatus;
import com.parkmate.hostservice.host.domain.Host;
import com.parkmate.hostservice.host.domain.SettlementCycle;
import com.parkmate.hostservice.host.dto.request.HostRegisterRequestForHostServiceDto;
import com.parkmate.hostservice.host.dto.response.*;
import com.parkmate.hostservice.host.infrastructure.HostRepository;
import com.parkmate.hostservice.host.infrastructure.client.HostSettlementFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import com.parkmate.hostservice.common.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;
    private final HostSettlementFeignClient hostSettlementFeignClient;

    @Transactional
    @Override
    public void register(HostRegisterRequestForHostServiceDto hostRegisterRequestForHostServiceDto) {

        Host host = Host.builder()
                .hostUuid(hostRegisterRequestForHostServiceDto.getHostUuid())
                .name(hostRegisterRequestForHostServiceDto.getName())
                .phoneNumber(hostRegisterRequestForHostServiceDto.getPhoneNumber())
                .bankName(hostRegisterRequestForHostServiceDto.getBankName())
                .accountNumber(hostRegisterRequestForHostServiceDto.getAccountNumber())
                .businessRegistrationNumber(hostRegisterRequestForHostServiceDto.getBusinessRegistrationNumber())
                .settlementCycle(SettlementCycle.from(hostRegisterRequestForHostServiceDto.getSettlementCycle()))
                .build();

        hostRepository.save(host);
    }

    @Transactional
    @Override
    public HostProfileResponseDto getHostProfile(String hostUuid) {
        Host host = hostRepository.findByHostUuid(hostUuid)
                .orElseThrow(() -> new BaseException(ResponseStatus.HOST_NOT_FOUND));

        return HostProfileResponseDto.from(host);
    }

    @Transactional
    @Override
    public DailySalesResponseDto getDailySales(String hostUuid, String parkingLotUuid, LocalDate date) {
        return hostSettlementFeignClient.getDailySales(hostUuid, parkingLotUuid, date.toString());
    }

    @Transactional
    @Override
    public MonthlySalesResponseDto getMonthlySales(String hostUuid, String parkingLotUuid, int year, int month, SettlementCycle cycle) {
        return hostSettlementFeignClient.getMonthlySales(hostUuid, parkingLotUuid, year, month, cycle);
    }

    @Transactional
    @Override
    public List<ParkingLotWeeklySalesDto> getParkingLotsWeeklySalesByRange(String hostUuid, String startDate, String endDate) {
        try {
            ApiResponse<List<ParkingLotWeeklySalesDto>> response = hostSettlementFeignClient.getParkingLotsWeeklySalesByRange(hostUuid, startDate, endDate);
            List<ParkingLotWeeklySalesDto> result = response != null && response.getData() != null
                    ? response.getData()
                    : java.util.Collections.emptyList();
            return result;
        } catch (Exception e) {
            // 에러 로그 남기기 (운영 환경에서는 로거 사용 권장)
            System.err.println("FeignClient 호출 에러: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    @Override
    public WeeklySalesResponseDto getWeeklySalesByRange(String hostUuid, String parkingLotUuid, String startDate, String endDate) {
        List<DailySalesSummaryDto> dailyList = hostSettlementFeignClient.getWeeklySalesByRange(hostUuid, parkingLotUuid, startDate, endDate);

        List<WeeklySalesResponseDto.DailySales> dailySalesList = dailyList.stream()
                .map(dto -> WeeklySalesResponseDto.DailySales.builder()
                        .date(dto.getDate())
                        .amount(dto.getAmount())
                        .build())
                .toList();

        return WeeklySalesResponseDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .dailySalesList(dailySalesList)
                .build();
    }

    @Transactional
    @Override
    public List<ParkingLotSalesSummaryDto> getParkingLotSalesSummary(String hostUuid, int year, Integer month, Integer weekOfMonth) {
        try {
            ApiResponse<List<ParkingLotSalesSummaryDto>> response = hostSettlementFeignClient.getParkingLotSalesSummary(hostUuid, year, month, weekOfMonth);
            List<ParkingLotSalesSummaryDto> result = response != null && response.getData() != null
                    ? response.getData()
                    : java.util.Collections.emptyList();
            return result;
        } catch (Exception e) {
            System.err.println("FeignClient 호출 에러: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
}
