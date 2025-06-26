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

    /**
     * Host 도메인 객체로부터 DTO 생성
     */
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

    /**
     * VO 객체로 변환 (클라이언트 응답용)
     */
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