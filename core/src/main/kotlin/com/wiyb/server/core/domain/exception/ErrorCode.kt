package com.wiyb.server.core.domain.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val code: Int,
    val message: String
) {
    // Common
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 400, "invalid input"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "forbidden"),
    API_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "not found"),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, 413, "file size exceeded"),
    UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "unknown server error"),

    // Auth - Common
    FAILED_SIGN_IN(HttpStatus.UNAUTHORIZED, 1000, "로그인에 실패하였습니다."),
    INSUFFICIENT_AUTHORITY(HttpStatus.FORBIDDEN, 1001, "권한이 부족합니다."),

    // Auth - Token
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 1100, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1101, "올바르지 않은 토큰입니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, 1102, "잘못된 JWT 시그니처입니다."),
    TOKEN_NOT_REFRESHABLE(HttpStatus.UNAUTHORIZED, 1103, "인증 정보가 만료되었습니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "USER_NOT_FOUND"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, 2001, "USER_ALREADY_EXISTS"),

    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 3000, "PRODUCT_NOT_FOUND"),

    // Product Review
    ALREADY_REVIEWED(HttpStatus.BAD_REQUEST, 3100, "이미 리뷰를 작성하셨습니다."),

    // Product Bookmark
    ALREADY_BOOKMARKED(HttpStatus.BAD_REQUEST, 3200, "이미 북마크를 추가하셨습니다."),
    NOT_BOOKMARKED(HttpStatus.BAD_REQUEST, 3201, "북마크를 추가하지 않았습니다.")
}
