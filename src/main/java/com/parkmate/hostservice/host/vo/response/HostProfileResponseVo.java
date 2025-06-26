package com.parkmate.hostservice.host.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostProfileResponseVo {

    private String name;
    private String phoneNumber;
    private String bankName;
    private String accountNumber;
    private String businessRegistrationNumber;
    private String settlementCycle;

    @Builder
    public HostProfileResponseVo(String name,
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
}