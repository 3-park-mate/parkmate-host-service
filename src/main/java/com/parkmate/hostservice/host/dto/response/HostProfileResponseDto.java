package com.parkmate.hostservice.host.dto.response;

import com.parkmate.hostservice.host.domain.Host;
import com.parkmate.hostservice.host.vo.response.HostProfileResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostProfileResponseDto {

    private String name;
    private String phoneNumber;
    private String bankName;
    private String accountNumber;
    private String businessRegistrationNumber;
    private String settlementCycle;

    @Builder
    private HostProfileResponseDto(String name,
                                   String phoneNumber,
                                   String bankName,
                                   String accountNumber,
                                   String businessRegistrationNumber,
                                   String settlementCycle) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.settlementCycle = settlementCycle;
    }

    public static HostProfileResponseDto from(Host host) {
        return HostProfileResponseDto.builder()
                .name(host.getName())
                .phoneNumber(host.getPhoneNumber())
                .bankName(host.getBankName())
                .accountNumber(host.getAccountNumber())
                .businessRegistrationNumber(host.getBusinessRegistrationNumber())
                .settlementCycle(host.getSettlementCycle().name())
                .build();
    }

    public HostProfileResponseVo toVo() {
        return HostProfileResponseVo.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .bankName(bankName)
                .accountNumber(accountNumber)
                .businessRegistrationNumber(businessRegistrationNumber)
                .settlementCycle(settlementCycle)
                .build();
    }
}