package com.mosaic.balance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorResult {
    GAME_NOT_EXIST(HttpStatus.BAD_REQUEST,"Game not Exist"),
    PWD_INCORRECT(HttpStatus.BAD_REQUEST, "PWD is not correct"),
    COMMENT_NOT_EXIST(HttpStatus.BAD_REQUEST, "Comment not Exist"),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception");
    private final HttpStatus httpStatus;
    private final String msg;
}
