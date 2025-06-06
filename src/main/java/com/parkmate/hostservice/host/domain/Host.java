package com.parkmate.hostservice.host.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "host")
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("호스트 고유 PK")
    private Long id;

    @Comment("호스트 UUID - 인증 서비스로부터 연동")
    @Column(nullable = false, unique = true, length = 36)
    private String hostUuid;

    @Comment("호스트 이름")
    @Column(nullable = false, length = 50)
    private String name;

    @Comment("전화번호")
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Comment("계좌번호")
    @Column(nullable = false, length = 30)
    private String accountNumber;

    @Comment("사업자등록번호")
    @Column(nullable = false, length = 20)
    private String businessRegistrationNumber;

    @Comment("정산 주기 (일 단위)")
    @Column(nullable = false)
    private Integer settlementCycle;

    @Builder
    private Host(Long id,
                 String hostUuid,
                 String name,
                 String phoneNumber,
                 String accountNumber,
                 String businessRegistrationNumber,
                 Integer settlementCycle) {

        this.id = id;
        this.hostUuid = hostUuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.settlementCycle = settlementCycle;
    }
}