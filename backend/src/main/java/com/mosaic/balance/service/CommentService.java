package com.mosaic.balance.service;

import com.mosaic.balance.dto.CommentDTO;

public interface CommentService {
    CommentDTO.ResponseCreateDTO createComment(long gameSeq, CommentDTO.RequestCreateDTO requestCreateDTO);
    CommentDTO.ResponseReadDTO getComments(long gameSeq, int startRed, int sizeRed, int startBlue, int sizeBlue);
    CommentDTO.ResponseUpdateDTO updateComment(long gameSeq, long commentSeq, CommentDTO.RequestUpdateDTO requestUpdateDTO);
    void deleteComment(long commentSeq, CommentDTO.RequestDeleteDTO requestDeleteDTO);
}
