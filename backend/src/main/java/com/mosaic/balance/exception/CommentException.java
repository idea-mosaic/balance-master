package com.mosaic.balance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentException extends RuntimeException{
    private final CommentErrorResult errorResult;
}
