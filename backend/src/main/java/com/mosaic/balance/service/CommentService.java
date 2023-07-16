package com.mosaic.balance.service;

import com.mosaic.balance.dto.CommentDTO;

public interface CommentService {
    CommentDTO.ResponseCreateDTO createComment(long gameSeq, CommentDTO.RequestCreateDTO requestCreateDTO);
    CommentDTO.ResponseReadDTO getComments();
    CommentDTO.ResponseUpdateDTO updateComment(long gameSeq, long commentSeq, CommentDTO.RequestUpdateDTO requestUpdateDTO);
    void deleteComment();
}
