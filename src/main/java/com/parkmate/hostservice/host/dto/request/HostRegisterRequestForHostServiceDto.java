package com.parkmate.hostservice.host.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostRegisterRequestForHostServiceDto {

    private String hostUuid;
    private String name;
    private String phoneNumber;
    private String bankName;
    private String accountNumber;
    private String businessRegistrationNumber;
    private Integer settlementCycle;

    @Builder
    private HostRegisterRequestForHostServiceDto(String hostUuid,
                                                String name,
                                                String phoneNumber,
                                                String bankName,
                                                String accountNumber,
                                                String businessRegistrationNumber,
                                                Integer settlementCycle) {
        this.hostUuid = hostUuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.settlementCycle = settlementCycle;
    }
}
