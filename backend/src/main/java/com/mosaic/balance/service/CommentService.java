package com.mosaic.balance.service;

import com.mosaic.balance.dto.CommentDTO;

public interface CommentService {
    /**
     * Create Comment
     * @param gameSeq
     * @param requestCreateDTO
     * @return PK of created comment and created date
     */
    CommentDTO.ResponseCreateDTO createComment(long gameSeq, CommentDTO.RequestCreateDTO requestCreateDTO);

    /**
     * Get Comment details
     * @param gameSeq
     * @param requestReadDTO
     * @return detail comments each color
     * @throws com.mosaic.balance.exception.CommentException GAME_NOT_EXIST
     */
    CommentDTO.ResponseReadDTO getComments(long gameSeq, CommentDTO.RequestReadDTO requestReadDTO);

    /**
     * Modify comment
     * @param gameSeq
     * @param commentSeq
     * @param requestUpdateDTO
     * @return PK of created comment and modified date
     * @throws com.mosaic.balance.exception.CommentException PWD_INCORRECT
     * @throws com.mosaic.balance.exception.CommentException GAME_NOT_EXIST
     * @throws com.mosaic.balance.exception.CommentException COMMENT_NOT_EXIST
     */
    CommentDTO.ResponseUpdateDTO updateComment(long gameSeq, long commentSeq, CommentDTO.RequestUpdateDTO requestUpdateDTO);

    /**
     * Delete Comment
     * @param commentSeq
     * @param requestDeleteDTO
     * @throws com.mosaic.balance.exception.CommentException PWD_INCORRECT
     * @throws com.mosaic.balance.exception.CommentException GAME_NOT_EXIST
     */
    void deleteComment(long commentSeq, CommentDTO.RequestDeleteDTO requestDeleteDTO);
}
