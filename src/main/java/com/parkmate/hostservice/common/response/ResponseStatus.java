package com.parkmate.hostservice.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    // ✅ 2xx: 성공
    SUCCESS(HttpStatus.OK, true, 200, "요청에 성공하였습니다."),
    CREATED(HttpStatus.CREATED, true, 201, "회원가입이 완료되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.NO_CONTENT, true, 204, "로그아웃 되었습니다."),
    AUTH_EMAIL_SENT_AFTER_LOCK(HttpStatus.OK, true, 200, "계정이 잠금 처리되었으며, 이메일 알림을 전송했습니다."),

    // ❌ 4xx: 클라이언트 오류
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, false, 400, "잘못된 요청입니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, false, 400, "비밀번호 형식이 올바르지 않습니다."),
    INVALID_SETTLEMENT_CYCLE(HttpStatus.BAD_REQUEST, false, 400, "정산 주기는 15일 또는 30일만 가능합니다."),
    AUTH_SOCIAL_PROVIDER_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, false, 400, "지원하지 않는 소셜 로그인 제공자입니다."),

    // ❌ 401: 인증 실패
    INVALID_AUTH_PASSWORD(HttpStatus.UNAUTHORIZED, false, 401, "비밀번호가 일치하지 않습니다."),
    AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, false, 401, "토큰이 만료되었습니다."),
    AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, false, 401, "유효하지 않은 토큰입니다."),
    AUTH_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, false, 401, "이메일 인증에 실패하였습니다."),
    AUTH_LOGIN_FAILED_TOO_MANY_TIMES(HttpStatus.UNAUTHORIZED, false, 401, "로그인 시도 횟수가 초과되었습니다."),
    AUTH_VERIFICATION_CODE_NOT_FOUND(HttpStatus.UNAUTHORIZED, false, 401, "인증코드가 만료되었거나 존재하지 않습니다."),

    // ❌ 403: 권한 없음
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, false, 403, "접근 권한이 없습니다."),
    REVIEW_FORBIDDEN(HttpStatus.FORBIDDEN, false, 403, "리뷰에 대한 권한이 없습니다."),

    // ❌ 404: 리소스 없음 - code 통일
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "리소스가 존재하지 않습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "리뷰를 찾을 수 없습니다."),
    AUTH_USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 사용자입니다."),
    AUTH_HOST_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 호스트입니다."),
    HOST_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "호스트 정보를 찾을 수 없습니다."),

    // ❌ 409: 충돌
    REVIEW_ALREADY_EXISTS_FOR_PARKING_LOT(HttpStatus.CONFLICT, false, 409, "이미 해당 주차장에 대해 리뷰가 등록되었습니다."),
    AUTH_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, false, 409, "이미 존재하는 이메일입니다."),

    // ❌ 422: 유효하지 않은 데이터
    AUTH_BIZNO_INVALID(HttpStatus.UNPROCESSABLE_ENTITY, false, 422, "유효하지 않은 사업자등록번호입니다."),

    // ❌ 423: 계정 잠금
    AUTH_ACCOUNT_LOCKED(HttpStatus.LOCKED, false, 423, "로그인 실패가 누적되어 계정이 잠금 처리되었습니다. 이메일을 확인하세요."),

    // ❗ 5xx: 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "서버 내부 오류가 발생했습니다."),
    AUTH_USER_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "사용자 서비스 연동에 실패했습니다."),
    AUTH_BIZNO_API_FAILED(HttpStatus.SERVICE_UNAVAILABLE, false, 503, "사업자등록번호 검증 서비스에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final int code;
    private final String message;
}
