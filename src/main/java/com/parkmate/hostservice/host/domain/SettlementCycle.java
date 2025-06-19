package com.parkmate.hostservice.host.domain;

import com.parkmate.hostservice.common.exception.BaseException;
import com.parkmate.hostservice.common.response.ResponseStatus;
import lombok.Getter;

@Getter
public enum SettlementCycle {

    FIFTEEN(15, "15일마다 정산"),
    THIRTY(30, "30일마다 정산");

    private final int days;
    private final String description;

    SettlementCycle(int days, String description) {
        this.days = days;
        this.description = description;
    }

    public static SettlementCycle from(int days) {
        return switch (days) {
            case 15 -> FIFTEEN;
            case 30 -> THIRTY;
            default -> throw new BaseException(ResponseStatus.INVALID_SETTLEMENT_CYCLE);
        };
    }
}
