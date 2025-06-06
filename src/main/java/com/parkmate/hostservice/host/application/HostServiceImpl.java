package com.parkmate.hostservice.host.application;

import com.parkmate.hostservice.host.domain.Host;
import com.parkmate.hostservice.host.dto.request.HostRegisterRequestForHostServiceDto;
import com.parkmate.hostservice.host.infrastructure.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    @Transactional
    @Override
    public void register(HostRegisterRequestForHostServiceDto hostRegisterRequestForHostServiceDto) {

        Host host = Host.builder()
                .hostUuid(hostRegisterRequestForHostServiceDto.getHostUuid())
                .name(hostRegisterRequestForHostServiceDto.getName())
                .phoneNumber(hostRegisterRequestForHostServiceDto.getPhoneNumber())
                .accountNumber(hostRegisterRequestForHostServiceDto.getAccountNumber())
                .businessRegistrationNumber(hostRegisterRequestForHostServiceDto.getBusinessRegistrationNumber())
                .settlementCycle(hostRegisterRequestForHostServiceDto.getSettlementCycle())
                .build();

        hostRepository.save(host);
    }
}
