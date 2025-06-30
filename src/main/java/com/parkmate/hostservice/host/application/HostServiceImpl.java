package com.parkmate.hostservice.host.application;

import com.parkmate.hostservice.common.exception.BaseException;
import com.parkmate.hostservice.common.response.ResponseStatus;
import com.parkmate.hostservice.host.domain.Host;
import com.parkmate.hostservice.host.domain.SettlementCycle;
import com.parkmate.hostservice.host.dto.request.HostRegisterRequestForHostServiceDto;
import com.parkmate.hostservice.host.dto.response.DailySalesResponseDto;
import com.parkmate.hostservice.host.dto.response.HostProfileResponseDto;
import com.parkmate.hostservice.host.dto.response.MonthlySalesResponseDto;
import com.parkmate.hostservice.host.infrastructure.HostRepository;
import com.parkmate.hostservice.host.infrastructure.client.HostSettlementFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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
}
