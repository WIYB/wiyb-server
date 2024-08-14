package com.wiyb.server.core.handler.exception

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<String> =
        when (e) {
            is CommonException -> {
                ResponseEntity.status(e.getStatus()).body(e.toJson())
            }

            is HttpMessageNotReadableException -> {
                ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CommonException(ErrorCode.INVALID_INPUT).toJson())
            }

            is MethodArgumentNotValidException -> {
                ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CommonException(ErrorCode.INVALID_INPUT, e.message).toJson())
            }

            is NoHandlerFoundException -> {
                ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CommonException(ErrorCode.API_NOT_FOUND, e.message).toJson())
            }

            is HttpRequestMethodNotSupportedException -> {
                ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CommonException(ErrorCode.API_NOT_FOUND, e.message).toJson())
            }

            is MaxUploadSizeExceededException -> {
                ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CommonException(ErrorCode.FILE_SIZE_EXCEEDED).toJson())
            }

            is AuthorizationDeniedException -> {
                ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(CommonException(ErrorCode.FORBIDDEN).toJson())
            }

            else -> {
                println(e.printStackTrace())
                ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonException(ErrorCode.UNKNOWN_SERVER_ERROR).toJson())
            }
        }
}
